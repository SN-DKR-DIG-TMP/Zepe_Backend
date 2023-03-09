// BY MOMATH NDIAYE

package com.zepebackend.batch.services;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.zepebackend.batch.model.FactureModel;


@Component
public class FactureModelItemProcessor implements ItemProcessor<FactureModel, FactureModel> {
    @Override
    public FactureModel process(FactureModel factureModel )  {
        return factureModel;
    }
}
