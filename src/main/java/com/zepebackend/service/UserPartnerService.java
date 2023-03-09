package com.zepebackend.service;

import static com.zepebackend.utils.ZepeConstants.ADMIN_COMMERCE;
import static com.zepebackend.utils.ZepeConstants.ADMIN_ENTREPRISE;
import static com.zepebackend.utils.ZepeConstants.ADMIN_MEDIATION;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.zepebackend.bridge.microuser.dto.ForgotPwdBadRequestDto;
import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.bridge.microuser.proxy.UserProxy;
import com.zepebackend.dao.forgotpwd.ForgotPwdDaoWrite;
import com.zepebackend.dao.userpartner.UserPartnerDaoRead;
import com.zepebackend.dao.userpartner.UserPartnerDaoWrite;
import com.zepebackend.dto.ForgotPwdRequestDto;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.dto.UserPartnerDto;
import com.zepebackend.dto.output.Admin;
import com.zepebackend.entity.ForgotPwd;
import com.zepebackend.entity.ForgotPwdBadRequest;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.UserPartner;

@Service
public class UserPartnerService {
	private final UserProxy userProxy;
	private final UserPartnerDaoRead userPartnerDaoRead;
	private final UserPartnerDaoWrite userPartnerWrite;
	private final ForgotPwdDaoWrite forgotPwdDaoWrite;
	final ModelMapper modelMapper;

	public UserPartnerService(UserPartnerDaoRead userPartnerDaoRead, UserPartnerDaoWrite userPartnerWrite, ForgotPwdDaoWrite forgotPwdDaoWrite,
			UserProxy userProxy, ModelMapper modelMapper) {
		this.userPartnerDaoRead = userPartnerDaoRead;
		this.userPartnerWrite = userPartnerWrite;
		this.forgotPwdDaoWrite = forgotPwdDaoWrite;
		this.modelMapper = modelMapper;
		this.userProxy = userProxy;
	}

	public UserPartner save(UserPartnerDto user) {
		return userPartnerWrite.save(convertDtoToEntity(user));
	}
	
	//Forgt Password
	public ForgotPwd save(ForgotPwdRequestDto forgotPwdRequest) {
		return forgotPwdDaoWrite.save(convertDtoToEntity(forgotPwdRequest));
	}
	
	//Forgt Password
	public ForgotPwdBadRequest saveBadReq(ForgotPwdBadRequestDto forgotPwdBadRequest) {
		return forgotPwdDaoWrite.save(convertDtoToEntity(forgotPwdBadRequest));
	}
	
	
	public UserPartner save(UserPartner userPartner) {
		UserPartnerDto userPartnerDto = convertEntityToDto(userPartner);
		return save(userPartnerDto);
	}

	public UserPartner findUserPartnerByIdUser(String idUser) {
		return userPartnerDaoRead.findByUserPartnerIdUser(idUser);
	}

	public List<UserPartner> findUserPartnerByRoles(List<String> role) {

		return userPartnerDaoRead.findAllByRoleIn(role);

	}

	public List<Admin> getAdmin(String token) {
		List<String> rolesAdmin = new ArrayList<>();
		rolesAdmin.add(ADMIN_MEDIATION);
		rolesAdmin.add(ADMIN_ENTREPRISE);
		rolesAdmin.add(ADMIN_COMMERCE);

		List<String> userAdminId = new ArrayList<>();
		List<UserPartner> userPartners = findUserPartnerByRoles(rolesAdmin);
		userPartners.forEach(up -> userAdminId.add(up.getUserPartner().getIdUser()));

		List<Admin> userAdminList = userProxy.getUserByUids(userAdminId, token, Admin[].class);

		for (Admin adminInfo : userAdminList) {
//			adminInfo.setUser(user);
			UserPartner uPartner = userPartners.stream()
					.filter(up -> adminInfo.getUserId().equalsIgnoreCase(up.getUserPartner().getIdUser())).findFirst()
					.get();
			adminInfo.setPartner(convertEntityToDto(uPartner.getUserPartner().getPartner()));
			adminInfo.setRole(uPartner.getRole());
		}
		return userAdminList;

	}

	public List<User> getUsers(Long idPartner, String token) {
		List<UserPartner> liste = findUserPartnerByPartner(idPartner);
		List<String> listId = new ArrayList<>();
		liste.forEach(up -> listId.add(up.getUserPartner().getIdUser()));

		List<User> userList = userProxy.getUserByUids(listId, token, User[].class);
		for (User us : userList) {
//			adminInfo.setUser(user);
			UserPartner uPartner = liste.stream()
					.filter(up -> us.getUserId().equalsIgnoreCase(up.getUserPartner().getIdUser())).findFirst().get();
			us.setPartner(uPartner.getUserPartner().getPartner().getName());
			us.setZepeRole(uPartner.getRole());
		}
		return userList;
	}

	public List<UserPartner> findUserPartnerByPartner(Long idPartner) {
		return userPartnerDaoRead.findByUserPartnerPartnerIdPartner(idPartner);
	}

	public List<UserPartner> saveAll(List<UserPartner> userPartners) {
		return userPartnerWrite.saveAll(userPartners);
	}

	private PartnerDto convertEntityToDto(Partner userPartner) {
		return modelMapper.map(userPartner, PartnerDto.class);
	}

	private UserPartnerDto convertEntityToDto(UserPartner userPartner) {
		return modelMapper.map(userPartner, UserPartnerDto.class);
	}

	private UserPartner convertDtoToEntity(UserPartnerDto userPartnerDto) {
		return modelMapper.map(userPartnerDto, UserPartner.class);
	}
	
	//Forgot Pwd
	private ForgotPwd convertDtoToEntity(ForgotPwdRequestDto forgotPwdRequestDto) {
		return modelMapper.map(forgotPwdRequestDto, ForgotPwd.class);
	}

	private ForgotPwdBadRequest convertDtoToEntity(ForgotPwdBadRequestDto forgotPwdBadRequestDto) {
		return modelMapper.map(forgotPwdBadRequestDto, ForgotPwdBadRequest.class);
	}
}
