package com.zepebackend.service;

import com.zepebackend.bridge.microauth.dto.MessageResponse;
import com.zepebackend.bridge.microauth.dto.ResetPwdRequest;
import com.zepebackend.bridge.microauth.dto.UpdatePwdRequest;
import com.zepebackend.bridge.microauth.proxy.AuthentificationProxy;
import com.zepebackend.bridge.microuser.dto.ForgotPwdBadRequestDto;
import com.zepebackend.bridge.microuser.dto.ForgotPwdRequest;
import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.bridge.microuser.dto.UserAccount;
import com.zepebackend.bridge.microuser.dto.UserStatus;
import com.zepebackend.bridge.microuser.proxy.UserProxy;
import com.zepebackend.dao.forgotpwd.ForgotPwdDaoRead;
import com.zepebackend.dao.forgotpwd.ForgotPwdDaoWrite;
import com.zepebackend.dao.partner.PartnerDaoRead;
import com.zepebackend.dao.userpartner.UserPartnerDaoRead;
import com.zepebackend.dto.ConfigKeyValue;
import com.zepebackend.dto.ForgotPwdRequestDto;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.dto.UserPartnerDto;
import com.zepebackend.dto.output.Admin;
import com.zepebackend.entity.Configuration;
import com.zepebackend.entity.ForgotPwd;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.UserPartner;
import com.zepebackend.entity.UserPartnerId;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import com.zepebackend.utils.ZepeConstants;
import com.zepebackend.utils.ZepeUtils;

import net.bytebuddy.asm.Advice.Return;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import io.jsonwebtoken.*;

import static com.zepebackend.utils.ZepeConstants.*;

@Service
@Transactional
public class UserService {
	final UserPartnerDaoRead userPartnerDaoRead;
	final ForgotPwdDaoRead forgotPwdDaoRead;
	public UserService(UserPartnerDaoRead userPartnerDaoRead, ForgotPwdDaoRead forgotPwdDaoRead) {
		this.userPartnerDaoRead = userPartnerDaoRead;
		this.forgotPwdDaoRead = forgotPwdDaoRead;
	}
	
    @Autowired
    private UserProxy<User> userProxy;
    @Autowired
    private AuthentificationProxy authProxy;
    @Autowired
    private UserPartnerService userPartnerService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private JavaMailSender mailSender;
    
    
    final ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<?> registerUser(UserAccount request, String token) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseEntity<MessageResponse> responseAuth = authProxy.signup(request, token);
        Assert.isTrue(HttpStatus.OK.equals(responseAuth.getStatusCode()),
                ZepeConstants.USER_REGISTRATION_FAILED + responseAuth.getStatusCode());

        ResponseEntity<MessageResponse> responseUser = userProxy.addUser(convertAccountToUser(request), token);
        Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()),
                ZepeConstants.USER_REGISTRATION_FAILED + responseUser.getStatusCode());

        UserPartnerId userPartnerId = new UserPartnerId(request.getUserId(),
                convertDtoToEntity(partnerService.findByName(request.getPartner()).getBody()));
        UserPartnerDto userPartner = new UserPartnerDto(userPartnerId, request.getRole());
        userPartnerService.save(userPartner);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
    
    //Forgot Password
    public ResponseEntity<?> saveForgotPwdReq (String uid, HttpServletRequest request)
    {
    	try {
    		
			ResponseEntity<UserPartnerDto> user = existByUid(uid);
			//ForgotPwd forgotPwd = new ForgotPwd(user.getBody().getUserPartnerId().getIdUser(), user.getBody().getUserPartnerId().getPartner().getIdPartner(), request.getTreated(), request.getDate());
			ForgotPwdRequestDto forgotPwdRequestDto = new ForgotPwdRequestDto(user.getBody().getUserPartnerId().getPartner().getIdPartner(), user.getBody().getUserPartnerId().getIdUser(), false, new Date());
			
			//Set Status to BLOCKED
			blockUser(uid);
			
			userPartnerService.save(forgotPwdRequestDto);
			
		} catch (Exception e) {
			System.out.println("ip addr"+request.getRemoteAddr());
			ForgotPwdBadRequestDto badRequestDto = new ForgotPwdBadRequestDto(uid, request.getRemoteAddr(), new Date());
			userPartnerService.saveBadReq(badRequestDto);
			System.out.println(e.getMessage());
		}
    	
    	
    	//Sending mail Notification
    	try {
			sendMailNotification(uid);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    public void sendMailNotification(String uid) throws MessagingException {
    	
    	//SimpleMailMessage message = new SimpleMailMessage();
    	MimeMessage message = mailSender.createMimeMessage();
    			
    	//Generate token
    	List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ADMIN");
    	
    	String token = Jwts.builder()
    	.setIssuer("admin_med")
		.setSubject("admin_med")
		.claim("authorities",
				grantedAuthorities.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
		.setIssuedAt(new Date())
		.setExpiration(new Date((new Date()).getTime() + 600000))
		.signWith(SignatureAlgorithm.HS512, "admin_mediation".getBytes())
		.compact();
    	
    	System.out.println(token);
    	
    	//Get list of admin and retieve mails
    	ArrayList<String> mailsArray = new ArrayList<>(); 	
    	mailsArray.add("mouhamed.3.ndiaye@atos.net");
    	List<Admin> admins = userPartnerService.getAdmin(token);
    	ResponseEntity<UserPartnerDto> user = existByUid(uid);
    	for (Admin admin : admins) {
    		if(admin.getPartner().getIdPartner() == user.getBody().getUserPartnerId().getPartner().getIdPartner()) {
    			System.out.println(admin);
    			mailsArray.add(admin.getEmail());
    		}
		}
    	
    	//Sending mail to all admin
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	String [] mails = GetStringArray(mailsArray);
    	message.setFrom("noreply@atos.com");
	    helper.setTo(mails); 
	    helper.setSubject("Zepe: Mot de passe oublié"); 
	    helper.setText("Bonjour,\nL'utilisateur dont l'ID est: " +uid+ " demande la modification de son mot de passe.\n\nCordialement."
	    		+ "<html><body>"
	    		+ "<a style='background-color: #008CBA;  border-radius: 8px; color: white; padding: 15px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; font-weight: bold;' href='google.com'>Cliquez ici</a>"
	    		+ "</body></html>", true);
	    mailSender.send(message);
	    
	    System.out.println("Mail successfuly send !");
    }
    
    public ResponseEntity<?> searchUser(String uid, HttpServletRequest request)
    {
    	ResponseEntity<User> user = null;
    	try {
    		
			 user = userProxy.searchUser(uid);
			
		} catch (Exception e) {
			System.out.println("ip addr"+request.getRemoteAddr());
			ForgotPwdBadRequestDto badRequestDto = new ForgotPwdBadRequestDto(uid, request.getRemoteAddr(), new Date());
			userPartnerService.saveBadReq(badRequestDto);
			System.out.println(e.getMessage());
		}
    	
    	return user;
    }
    
    public static String[] GetStringArray(ArrayList<String> arr)
    {
  
        // declaration and initialise String Array
        String str[] = new String[arr.size()];
  
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {
  
            // Assign each value to String array
            str[j] = arr.get(j);
        }
  
        return str;
    }
    
    public ResponseEntity<String> deleteUser(String uid, String token) {
        if (StringUtils.isEmpty(uid)) {
            return ResponseEntity.noContent().build();
        }
        authProxy.deleteAccount(uid, token);
        return userProxy.deleteUser(uid, token);
    }
    
    public ResponseEntity<String> blockUser(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return ResponseEntity.noContent().build();
        }
        authProxy.blockAccount(uid);
        return userProxy.blockeUser(uid);
    }
    
    public ResponseEntity<User> updateUser(User userDto, String token) {
        if (userDto == null || StringUtils.isEmpty(userDto.getUserId()) || StringUtils.isEmpty(userDto.getZepeRole())) {
            return ResponseEntity.noContent().build();
        }
        UserPartner uPartner = userPartnerService.findUserPartnerByIdUser(userDto.getUserId());
        if (!StringUtils.equalsIgnoreCase(userDto.getZepeRole(), uPartner.getRole())) {
            authProxy.updateRole(userDto.getUserId(), ZepeUtils.convertRole(userDto.getZepeRole()), token);
        }

        uPartner.setRole(userDto.getZepeRole());
        Partner newPartner = partnerService.convertDtoToEntity(partnerService.findByName(userDto.getPartner()).getBody());
        UserPartnerId userPartnerId = uPartner.getUserPartner();
        userPartnerId.setPartner(newPartner);
        uPartner.setUserPartner(userPartnerId);

     //   UserPartner userPartner = userPartnerService.save(uPartner);


        ResponseEntity<User> updatedUser = userProxy.updateUser(userDto, token);
        Objects.requireNonNull(updatedUser.getBody()).setZepeRole(userDto.getZepeRole());
        return updatedUser;
    }

    public ResponseEntity<?> reactivateUser(User userDto, String token) {
        if (userDto == null || StringUtils.isEmpty(userDto.getUserId())) {
            return ResponseEntity.noContent().build();
        }
        ResetPwdRequest resetPwd = new ResetPwdRequest();
        PartnerDto partnerDto = partnerService.findByName(userDto.getPartner()).getBody();
        if (userDto.getStatus().equals(UserStatus.BLOCKED) || userDto.getStatus().equals(UserStatus.DELETED)) {
            assert partnerDto != null;
            resetPwd.setUserId(userDto.getUserId());
            Optional<Configuration> configKeyValue = configurationService.getConfigKey(DEFAULT_PWD_KEY, partnerDto.getIdPartner());
            if (configKeyValue.isPresent()) {
                resetPwd.setPwd(configKeyValue.get().getStrValeur());
            } else {
                resetPwd.setPwd(DEFAULT_PWD);
            }
            authProxy.reactivateUser(resetPwd, token);
            return userProxy.reactivateUser(userDto, token);
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }

    }

    public ResponseEntity<?> updatePassword(UpdatePwdRequest upPwd, String token) {
        UserPartner uPartner = userPartnerService.findUserPartnerByIdUser(upPwd.getUserId());
        Optional<Configuration> configKeyValue = configurationService.getConfigKey(DEFAULT_PWD_KEY,
                uPartner.getUserPartner().getPartner().getIdPartner());
        String password = "";
        if (configKeyValue.isPresent()) {
            password = configKeyValue.get().getStrValeur();
        } else {
            password = DEFAULT_PWD;
        }
        if (upPwd == null || StringUtils.isEmpty(upPwd.getUserId())
                || StringUtils.isEmpty(upPwd.getNewPwd())
                || StringUtils.isEmpty(upPwd.getOldPwd())
                || password.equals(upPwd.getNewPwd())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        return authProxy.updatePassword(upPwd, token);
    }

    //Forgot pwd
    public ResponseEntity<?> resetPassword(UpdatePwdRequest upPwd, String token) {
        UserPartner uPartner = userPartnerService.findUserPartnerByIdUser(upPwd.getUserId());
       
        if (upPwd == null || StringUtils.isEmpty(upPwd.getUserId())
                || StringUtils.isEmpty(upPwd.getNewPwd())
                || StringUtils.isEmpty(upPwd.getOldPwd())
                ) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        List <ForgotPwd> forgotPwds = getForgotPwdRequestByIdUser(upPwd.getUserId());
        for (ForgotPwd forgotPwd : forgotPwds) {
			forgotPwd.setTreated(true);
		}
        
        return authProxy.resetPassword(upPwd, token);
    }
    
    public ResponseEntity<?> registerAll(List<UserAccount> accounts, String token) {
        if (CollectionUtils.isEmpty(accounts)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseEntity<MessageResponse> responseAuth = authProxy.registerAll(accounts, token);
        Assert.isTrue(HttpStatus.OK.equals(responseAuth.getStatusCode()),
                ZepeConstants.USER_REGISTRATION_FAILED + responseAuth.getStatusCode());

        ResponseEntity<MessageResponse> responseUser = userProxy.addAll(convertAccountsToUsers(accounts), token);
        Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()),
                ZepeConstants.USER_REGISTRATION_FAILED + responseUser.getStatusCode());

        List<UserPartner> userPartners = userPartnerService.saveAll(createUserPartnerFromAccount(accounts));

        return ResponseEntity.ok(userPartners.stream().map(this::convertUpToDto).collect(Collectors.toList()));
    }

    private Partner convertDtoToEntity(PartnerDto partner) {
        return modelMapper.map(partner, Partner.class);
    }

    private User convertAccountToUser(UserAccount acc) {
        return new User(acc.getUserId(), acc.getFirstName(), acc.getLastName(), acc.getEmail(), acc.getMobile(),
                acc.getPartner());
    }

    private List<User> convertAccountsToUsers(List<UserAccount> accounts) {
        List<User> users = new ArrayList<>();
        if (CollectionUtils.isEmpty(accounts)) {
            return users;
        }

        accounts.forEach(acc -> users.add(convertAccountToUser(acc)));
        return users;
    }

    private List<UserPartner> createUserPartnerFromAccount(List<UserAccount> accounts) {
        List<UserPartner> uPartners = new ArrayList<>();
        if (CollectionUtils.isEmpty(accounts)) {
            return uPartners;
        }

        ResponseEntity<PartnerDto> partner = partnerService.findByName(accounts.get(0).getPartner());
        Assert.isTrue(HttpStatus.OK.equals(partner.getStatusCode()),
                ZepeConstants.PARTENARIAT_NOT_FOUND + accounts.get(0).getPartner());

        accounts.forEach(acc -> uPartners.add(new UserPartner(
                new UserPartnerId(acc.getUserId(), convertDtoToEntity(partner.getBody())), acc.getRole())));
        return uPartners;
    }

    private UserPartnerDto convertUpToDto(UserPartner up) {
        return modelMapper.map(up, UserPartnerDto.class);
    }
    
    public ResponseEntity<UserPartnerDto> existByUid(String uid){
    	UserPartnerDto userpartner = convertUpToDto(
    			userPartnerDaoRead.findByUserPartnerIdUser(uid));
    	return ResponseEntity.ok().body(userpartner);
	}
    
    
    private ForgotPwdRequestDto convertEntityToDto(ForgotPwd forgotPwd) {
		return modelMapper.map(forgotPwd, ForgotPwdRequestDto.class);
	}
    
    //Forgot PWD
    public List<ForgotPwd> getAllForgotPwdRequest(){
    			return forgotPwdDaoRead.findAll();
    }
    public List<ForgotPwd> getForgotPwdRequestByIdUser(String uid){
		return forgotPwdDaoRead.findByIdUser(uid);
    }
    
}
