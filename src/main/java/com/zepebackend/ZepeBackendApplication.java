package com.zepebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZepeBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZepeBackendApplication.class, args);
	}

	// @Bean
//	CommandLineRunner start(PartnerDaoWrite partnerDaoWrite, ConfigurationDaoWrite configurationDaoWrite,
//			PaymentTokenDaoWrite paymentTokenDaoWrite, JetonDaoWrite jetonDaoWrite, PartnerDaoRead partnerDaoRead,
//			PartenariatDaoWrite partenariatDaoWrite, PartenariatDaoRead partenariatDaoRead,
//			FactureDaoWrite factureDaoWrite, CashmentDaoWrite cashmentDaoWrite, CashmentDaoRead cashmentDaoRead) {
//		return args -> {
//
//			Partner commerce1 = new Partner();
//			commerce1.setAdress("Mariste");
//			commerce1.setName("La gondole");
//			commerce1.setPartnerType(Partner.PartnerType.TRADE);
//			partnerDaoWrite.save(commerce1);
//
//			Partner commerce2 = new Partner();
//			commerce2.setAdress("Yoff");
//			commerce2.setName("Galette");
//			commerce2.setPartnerType(Partner.PartnerType.TRADE);
//			partnerDaoWrite.save(commerce2);
//
//			Partner entreprise1 = new Partner();
//			entreprise1.setAdress("Mermoz");
//			entreprise1.setName("Atos Senegal");
//			entreprise1.setPartnerType(Partner.PartnerType.BUSINESS);
//			partnerDaoWrite.save(entreprise1);
//
//			Partner commerce3 = new Partner();
//			commerce3.setAdress("Scat Urbam");
//			commerce3.setName("La pampa");
//			commerce3.setPartnerType(Partner.PartnerType.TRADE);
//			partnerDaoWrite.save(commerce3);
//
//			Partner entreprise2 = new Partner();
//			entreprise2.setAdress("VDN");
//			entreprise2.setName("Sonatel Senegal");
//			entreprise2.setPartnerType(Partner.PartnerType.BUSINESS);
//			partnerDaoWrite.save(entreprise2);
//
//			Configuration configuration = new Configuration();
//			configuration.setValeur(2000);
//			configuration.setCle("atos.subvention.restaurant.nbprice");
//			configuration.setPartner(entreprise1);
//			configurationDaoWrite.save(configuration);
//
//			Configuration configuration1 = new Configuration();
//			configuration1.setValeur(20);
//			configuration1.setCle("atos.subvention.restaurant.maxticket");
//			configuration1.setPartner(entreprise1);
//			configurationDaoWrite.save(configuration1);
//
//			Jeton jeton = new Jeton();
//			jeton.setMontantEmploye(1000);
//			jeton.setMontantEntreprise(1000);
//			jeton.setPartner(partnerDaoRead.getOne(1L));
//			jetonDaoWrite.save(jeton);
//
//			Partenariat partenariat = new Partenariat();
//			partenariat.setEntreprise(partnerDaoRead.getOne(3L));
//			partenariat.setCommerce(partnerDaoRead.getOne(1L));
//			partenariatDaoWrite.save(partenariat);
//
//			Partenariat partenariat1 = new Partenariat();
//			partenariat1.setEntreprise(partnerDaoRead.getOne(3L));
//			partenariat1.setCommerce(partnerDaoRead.getOne(2L));
//			partenariatDaoWrite.save(partenariat1);
//
//			Partenariat partenariat2 = new Partenariat();
//			partenariat2.setEntreprise(partnerDaoRead.getOne(4L));
//			partenariat2.setCommerce(partnerDaoRead.getOne(1L));
//			partenariatDaoWrite.save(partenariat2);
//
//		};
	// }
}
