// BY MOMATH NDIAYE
package com.zepebackend.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepebackend.dao.configuration.ConfigurationDaoRead;
import com.zepebackend.dao.configuration.ConfigurationDaoWrite;
import com.zepebackend.dao.partner.PartnerDaoRead;
import com.zepebackend.dto.ConfigKeyValue;
import com.zepebackend.dto.ConfigPartenaireDto;
import com.zepebackend.dto.ConfigurationDto;
import com.zepebackend.dto.JetonWithOutPartnerDto;
import com.zepebackend.dto.NamedConfigDto;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.entity.Configuration;
import com.zepebackend.entity.ConfigurationId;
import com.zepebackend.entity.Partner;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import com.zepebackend.utils.ZepeConstants;

import static com.zepebackend.utils.ZepeConstants.*;

@Service
@Transactional
public class ConfigurationService {
    private final ConfigurationDaoRead configurationDaoRead;
    private final ConfigurationDaoWrite configurationDaoWrite;
    private final PartnerDaoRead partnerDaoRead;
    private final ModelMapper modelMapper;
    private final JetonService jetonService;
    Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

    public ConfigurationService(ConfigurationDaoRead configurationDaoRead, ConfigurationDaoWrite configurationDaoWrite,
                                PartnerDaoRead partnerDaoRead, ModelMapper modelMapper, JetonService jetonService) {
        this.configurationDaoRead = configurationDaoRead;
        this.configurationDaoWrite = configurationDaoWrite;
        this.partnerDaoRead = partnerDaoRead;
        this.modelMapper = modelMapper;
        this.jetonService = jetonService;
    }

    public List<ConfigurationDto> configPerPartner(Long idPartner) {
        Partner partner = partnerDaoRead.getOne(idPartner);
        return configurationDaoRead.findByConfigurationIdPartner(partner).stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }

    public ConfigurationDto addConfiguration(ConfigurationDto configurationDto) throws ParseException {
        Configuration configuration = this.convertDtoToEntity(configurationDto);
        ConfigurationId configId = new ConfigurationId(configurationDto.getCle(),
                convertDtoToEntity(configurationDto.getPartner()));
        configuration.setConfigurationId(configId);
        Configuration response = configurationDaoWrite.save(configuration);
        return convertEntityToDto(response);
    }

    public Optional<Configuration> getConfigKey(String configKey, Long idPartner) {
        return configurationDaoRead.findByConfigurationIdPartnerIdPartnerAndConfigurationIdCle(idPartner, configKey);
    }

    public ResponseEntity<ConfigurationDto> updateConfiguration(String cle, ConfigurationDto configurationDto)
            throws ResourceNotFoundExceptions {
        Configuration configuration = configurationDaoRead
                .findById(new ConfigurationId(configurationDto.getCle(),
                        convertDtoToEntity(configurationDto.getPartner())))
                .orElseThrow(() -> new ResourceNotFoundException(CONFIGURATION_KEY_EXCEPTION + cle));

        configuration.setStrValeur(configurationDto.getStrValeur());
        configuration.setValeur(configurationDto.getValeur());
        final Configuration updatedConfiguration = configurationDaoWrite.save(configuration);
        return ResponseEntity.ok(convertEntityToDto(updatedConfiguration));
    }

    private ConfigurationDto convertEntityToDto(Configuration configuration) {
        return modelMapper.map(configuration, ConfigurationDto.class);
    }

    private Configuration convertDtoToEntity(ConfigurationDto configurationDto) {
        return modelMapper.map(configurationDto, Configuration.class);
    }

    public ConfigPartenaireDto getConfigParPartenaire(Long idPartner) {
        /*
         * on recupere le partenaire on recupere ensuite les configurations associée au
         * partenaire concernant le nombre maximal de ticket on recupere le jeton
         * concernant le partenaire
         */
        ConfigPartenaireDto configPartenaireDto = new ConfigPartenaireDto();
        List<NamedConfigDto> keys = Arrays
                .asList(new NamedConfigDto(ZepeConstants.NB_MAX_TICKET_KEY, ZepeConstants.NB_MAX_TICKET_NAME),
                        new NamedConfigDto(ZepeConstants.DEFAULT_PWD_KEY, ZepeConstants.DEFAULT_PWD_NAME),
                        new NamedConfigDto(ZepeConstants.AMOUNT_EXCEED_AUTORISED_KEY, ZepeConstants.AMOUNT_EXCEED_AUTORISED_NAME),
                        new NamedConfigDto(ZepeConstants.MINIMUM_AMOUNT_KEY, ZepeConstants.MINIMUM_AMOUNT_NAME));
        				
        configPartenaireDto.setConfigs(new ArrayList<>());
        configPartenaireDto.setKeys(keys);

        try {
            Partner partner = partnerDaoRead.getOne(idPartner);
            List<Configuration> configurations = configurationDaoRead.findByConfigurationIdPartner(partner);
            configurations.forEach(conf -> configPartenaireDto.getConfigs().add(convertConfigurationToDto(conf)));

            JetonWithOutPartnerDto jetonWithOutPartnerDto = jetonService.getJetonPartenaire(idPartner);
            configPartenaireDto.setJetonEntreprise(jetonWithOutPartnerDto);
            configPartenaireDto.setMessage("Ok");
        } catch (Exception e) {
            logger.error(CONFIG_NOT_FOUND + idPartner);
            logger.error(e.getMessage(), e);
            configPartenaireDto.setMessage(e.getMessage());
        }

        return configPartenaireDto;

    }

    private Partner convertDtoToEntity(PartnerDto partner) {
        return modelMapper.map(partner, Partner.class);
    }

    private ConfigKeyValue convertConfigurationToDto(Configuration conf) {
        if (ZepeConstants.NB_MAX_TICKET_KEY.equals(conf.getConfigurationId().getCle())) {
            return new ConfigKeyValue(
                    new NamedConfigDto(ZepeConstants.NB_MAX_TICKET_KEY, ZepeConstants.NB_MAX_TICKET_NAME),
                    conf.getValeur(), StringUtils.EMPTY);
        }

        if (ZepeConstants.DEFAULT_PWD_KEY.equals(conf.getConfigurationId().getCle())) {
            return new ConfigKeyValue(
                    new NamedConfigDto(ZepeConstants.DEFAULT_PWD_KEY, ZepeConstants.DEFAULT_PWD_NAME), 0,
                    conf.getStrValeur());
        }
        
        if (ZepeConstants.AMOUNT_EXCEED_AUTORISED_KEY.equals(conf.getConfigurationId().getCle())) {
            return new ConfigKeyValue(
                    new NamedConfigDto(ZepeConstants.AMOUNT_EXCEED_AUTORISED_KEY, ZepeConstants.AMOUNT_EXCEED_AUTORISED_NAME),
                    conf.getValeur(), StringUtils.EMPTY);
        }
        
        if (ZepeConstants.MINIMUM_AMOUNT_KEY.equals(conf.getConfigurationId().getCle())) {
            return new ConfigKeyValue(
                    new NamedConfigDto(ZepeConstants.MINIMUM_AMOUNT_KEY, ZepeConstants.MINIMUM_AMOUNT_NAME),
                    conf.getValeur(), StringUtils.EMPTY);
        }
        
        return new ConfigKeyValue();
    }

}
