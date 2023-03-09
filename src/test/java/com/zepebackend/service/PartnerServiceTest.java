package com.zepebackend.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.zepebackend.ZepeBackendApplication;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.entity.Partner;

@SpringBootTest(classes = ZepeBackendApplication.class)
public class PartnerServiceTest {
	/*@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private PartnerService partnerService;
	@Test
    public void FindByNameTest() {
        PartnerDto partnerdto = new PartnerDto();

        partnerdto.setName("Grand Café");
        partnerdto.setAdress("cité keur gorgui");

           entityManager.persist(partnerdto);
           entityManager.flush();
           Partner found = partnerService.findByName(partnerdto.getName()).getBody();
           assertThat(found.getName())
           .isEqualTo(partnerdto.getName());
     }
	public void FindById() throws ParseException {
		 PartnerDto partnerdto = new PartnerDto();

		 partnerdto.setName("Grand Café");
		 partnerdto.setAdress("cité keur gorgui");
         entityManager.persist(partnerdto);
         entityManager.flush();
         Partner partner=partnerService.addPartner(partnerdto);
         Partner found = partnerService.getPartnerById(partner.getIdPartner()).getBody();
         assertThat(found)
         .isEqualTo(partner);

	}*/

}
