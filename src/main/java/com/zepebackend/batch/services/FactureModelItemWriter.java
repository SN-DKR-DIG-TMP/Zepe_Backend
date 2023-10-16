// BY MOMATH NDIAYE

package com.zepebackend.batch.services;

import com.zepebackend.batch.model.FactureModel;
import com.zepebackend.dao.facture.FactureDaoWrite;
import com.zepebackend.dao.partenariat.PartenariatDaoRead;
import com.zepebackend.entity.Facture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class FactureModelItemWriter implements ItemWriter<FactureModel> {

    private static final Logger logger = LoggerFactory.getLogger(FactureModelItemWriter.class);

    final  FactureDaoWrite factureDaoWrite;

    private final PartenariatDaoRead partenariatDaoRead;

    public FactureModelItemWriter(FactureDaoWrite factureDaoWrite, PartenariatDaoRead partenariatDaoRead) {
        this.factureDaoWrite = factureDaoWrite;
        this.partenariatDaoRead = partenariatDaoRead;
    }


    @Override
    public void write(List<? extends FactureModel> list)  {
        for (FactureModel f:list) {
            Facture facture = new Facture();
            facture.setDateFacture(f.getDateFacture());
            //facture.setDateFacture(new Date("Thu Mar 31 23:59:59 UTC 2022"));
            facture.setPartenariat(partenariatDaoRead.findById(f.getPartenariatId()).get());
            //facture.setPeriode("Du 01/03/2022 au 31/03/2022");
            facture.setPeriode(f.getPeriode());
            facture.setMontant(f.getMontant());
            System.out.println("+++++++++++++++Date format+++++++++++++++++++" + facture.getPeriode());
            factureDaoWrite.save(facture);
            logger.info("facture", f);
        }
    }
}
