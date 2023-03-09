package com.zepebackend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.zepebackend.entity.Configuration;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zepebackend.bridge.microauth.dto.AuthentificationResponse;
import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.bridge.microuser.proxy.UserProxy;
import com.zepebackend.dto.CashmentDto;
import com.zepebackend.dto.ConfigPartenaireDto;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.FactureDto;
import com.zepebackend.dto.PaymentDto;
import com.zepebackend.dto.PaymentTokenDto;
import com.zepebackend.dto.output.AdminCommerceDTO;
import com.zepebackend.dto.output.AdminEntrepriseDTO;
import com.zepebackend.dto.output.AdminMediationDTO;
import com.zepebackend.dto.output.AffRoleDto;
import com.zepebackend.dto.output.CaissierDTO;
import com.zepebackend.dto.output.ClientDTO;
import com.zepebackend.dto.output.GestionnaireCommerceDTO;
import com.zepebackend.dto.output.GestionnaireEntrepriseDTO;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.PaymentToken;
import com.zepebackend.entity.UserPartner;
import com.zepebackend.utils.PartnerType;
import com.zepebackend.utils.ZepeConstants;
import com.zepebackend.utils.ZepeUtils;

import static com.zepebackend.utils.ZepeConstants.*;
import static com.zepebackend.utils.ZepeConstants.DEFAULT_PWD;

@Service
public class AuthentificationService {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private CashmentService cashmentService;

	@Autowired
	private FactureService factureService;

	@Autowired
	private PartenariatService partenariatService;

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentTokenService paymentTokenService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	UserPartnerService userPartnerService;

	@Autowired
	UserProxy<User> userProxy;

	public ConnexionResponseDto getUserInfo(ConnexionResponseDto connexion) {
		String token = connexion.getAccount().getTokenType() + " " + connexion.getAccount().getAccessToken();
		AuthentificationResponse account = connexion.getAccount();

		UserPartner userPartner = userPartnerService.findUserPartnerByIdUser(account.getUsername());
		Partner partner = userPartner.getUserPartner().getPartner();
		User userInfo = userProxy.getUserByUid(connexion.getAccount().getUsername(), token).getBody();

		String role = userPartner.getRole();
		connexion.getAccount().setZepeRole(role);
		long idPartner = partner.getIdPartner();
		connexion.getAccount().setIdPartner(idPartner);
		connexion.getAccount().setPartner(partner.getName());

		switch (role) {
		case CAISSIER:
			CaissierDTO caissier = new CaissierDTO(connexion);
			if (userInfo != null) {
				caissier.getAccount().setFirstName(userInfo.getFirstName());
				caissier.getAccount().setLastName(userInfo.getLastName());
			}

			List<CashmentDto> encaissementDTOs = cashmentService.findByCashierAndDate(account.getUsername(),
					new DateTime());
			caissier.setEncaissementDTOList(encaissementDTOs);
			return caissier;
		case CLIENT:
			ClientDTO client = new ClientDTO(connexion);
			initUserInfo(client, userInfo);
			return client;
		case ADMIN_ENTREPRISE:
			AdminEntrepriseDTO adminEntreprise = new AdminEntrepriseDTO(connexion);
			initUserInfo(adminEntreprise, userInfo);

			adminEntreprise.setUserList(userPartnerService.getUsers(idPartner, token));
			adminEntreprise.setParteneriatDTOList(partenariatService.getPartenariatParEntreprise(idPartner));
			adminEntreprise
					.setAffRole(new AffRoleDto(PartnerType.BUSINESS, Arrays.asList(CLIENT, GESTIONNAIRE_ENTREPRISE)));
			return adminEntreprise;
		case ADMIN_MEDIATION:
			AdminMediationDTO adminMediation = new AdminMediationDTO(connexion);
			initUserInfo(adminMediation, userInfo);

			adminMediation.setPartenariats(partenariatService.getAllPartenariat());
			adminMediation.setPartners(partnerService.getAllPartner());
			adminMediation.setUserAdmin(userPartnerService.getAdmin(token));
			adminMediation.setAffRoles(Arrays.asList(new AffRoleDto(null, Arrays.asList(ADMIN_MEDIATION)),
					new AffRoleDto(PartnerType.BUSINESS, Arrays.asList(ADMIN_ENTREPRISE)),
					new AffRoleDto(PartnerType.TRADE, Arrays.asList(ADMIN_COMMERCE))));
			return adminMediation;
		case GESTIONNAIRE_COMMERCE:
			GestionnaireCommerceDTO gestCommerce = new GestionnaireCommerceDTO(connexion);
			if (userInfo != null) {
				gestCommerce.getAccount().setFirstName(userInfo.getFirstName());
				gestCommerce.getAccount().setLastName(userInfo.getLastName());
			}
			Map<String, LocalDateTime> anneeEncours = ZepeUtils.debutFinAnnee();
			List<FactureDto> listeFacture = factureService.listeFactureParCommerce(idPartner,
					anneeEncours.get(ZepeConstants.START).toDate(), anneeEncours.get(ZepeConstants.END).toDate());
			Map<String, DateTime> moisEncours = ZepeUtils.moisEnCours();

			gestCommerce.setFactures(listeFacture);
			gestCommerce.setCashments(cashmentService.findByTradeAndTwoDate(idPartner,
					moisEncours.get(ZepeConstants.START), moisEncours.get(ZepeConstants.END)));
			return gestCommerce;
		case GESTIONNAIRE_ENTREPRISE:
			GestionnaireEntrepriseDTO gestEntreprise = new GestionnaireEntrepriseDTO(connexion);
			initUserInfo(gestEntreprise, userInfo);
			Map<String, DateTime> datesP = ZepeUtils.moisEnCours();
			gestEntreprise.setFactures(factureService.listeFactureParEntreprise(idPartner));
			gestEntreprise.setPayments(paymentService.getPaymentByBusinessAndDates(idPartner,
					datesP.get(START).toDate(), datesP.get(END).toDate()));

			return gestEntreprise;
		case ADMIN_COMMERCE:
			AdminCommerceDTO adminCommerce = new AdminCommerceDTO(connexion);

			initUserInfo(adminCommerce, userInfo);

			if (userInfo != null) {
				adminCommerce.getAccount().setFirstName(userInfo.getFirstName());
				adminCommerce.getAccount().setLastName(userInfo.getLastName());
			}

			adminCommerce.setParteneriatDTOList(partenariatService.getPartenariatParCommerce(idPartner));
			adminCommerce.setUserList(userPartnerService.getUsers(idPartner, token));
			adminCommerce.setAffRole(new AffRoleDto(PartnerType.TRADE, Arrays.asList(CAISSIER, GESTIONNAIRE_COMMERCE)));
			return adminCommerce;
		default:
			return null;
		}

	}

	private PaymentTokenDto convertEntityToDto(PaymentToken paymentToken) {
		return modelMapper.map(paymentToken, PaymentTokenDto.class);
	}

	private void initUserInfo(ClientDTO authentificationDTO, User userInfo) {
		if (authentificationDTO == null || authentificationDTO.getAccount() == null || userInfo == null) {
			return;
		}

		authentificationDTO.getAccount().setFirstName(userInfo.getFirstName());
		authentificationDTO.getAccount().setLastName(userInfo.getLastName());

		ConfigPartenaireDto configPartenaireDto = configurationService
				.getConfigParPartenaire(authentificationDTO.getAccount().getIdPartner());
		authentificationDTO.setConfigPartenaireDto(configPartenaireDto);
		List<PaymentDto> paymentDtos = paymentService.getAllPaymentByDateAndUser(userInfo.getUserId(), new DateTime());
		authentificationDTO.setPaymentDTOList(paymentDtos);
		authentificationDTO
				.setPaymentTokenDto(convertEntityToDto(paymentTokenService.generatePaymentToken(userInfo.getUserId())));
	}

}
