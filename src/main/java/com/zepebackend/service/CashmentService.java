package com.zepebackend.service;

import com.zepebackend.agg.AggCashment;
import com.zepebackend.agg.ICashment;
import com.zepebackend.dao.cashment.CashmentDaoRead;
import com.zepebackend.dao.cashment.CashmentDaoWrite;
import com.zepebackend.dto.*;
import com.zepebackend.entity.Cashment;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.PaymentToken;
import com.zepebackend.fcm.PushNotificationRequest;
import com.zepebackend.fcm.PushNotificationService;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zepebackend.utils.ZepeConstants.PAYMENT_OR_CASHMENT_TOKEN_NOT_FOUND;
import static com.zepebackend.utils.ZepeConstants.PAYMENT_TOKEN_NOT_FOUND;

@Service
public class CashmentService {
    final ModelMapper modelMapper;
    final CashmentDaoRead cashmentDaoRead;
    final CashmentDaoWrite cashmentDaoWrite;
    final PartnerService partnerService;
    final PaymentService paymentService;
    final PaymentTokenService paymentTokenService;
    final PushNotificationService pushService;
    final UserService userService;
    Logger logger = LoggerFactory.getLogger(CashmentService.class);

    public CashmentService(ModelMapper modelMapper, CashmentDaoRead cashmentDaoRead, CashmentDaoWrite cashmentDaoWrite,
                           PartnerService partnerService, PaymentService paymentService, PaymentTokenService paymentTokenService,
                           PushNotificationService pushService, UserService userService) {
        this.modelMapper = modelMapper;
        this.cashmentDaoRead = cashmentDaoRead;
        this.cashmentDaoWrite = cashmentDaoWrite;
        this.partnerService = partnerService;
        this.paymentService = paymentService;
        this.paymentTokenService = paymentTokenService;
        this.pushService = pushService;
        this.userService = userService;
    }

    public ResponseEntity<String> recordCashment(CashmentDto cashmentDto) {
        if (cashmentDto == null || cashmentDto.getTokenPayment() == null
                || !StringUtils.hasText(cashmentDto.getTokenPayment().getToken())) {
            logger.warn(PAYMENT_OR_CASHMENT_TOKEN_NOT_FOUND + cashmentDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<PaymentToken> paymentToken = paymentTokenService
                .findPaymentToken(cashmentDto.getTokenPayment().getToken());
        if (!paymentToken.isPresent()) {
            logger.warn(PAYMENT_TOKEN_NOT_FOUND + cashmentDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (paymentToken.get().getStatus() == PaymentToken.Status.UNUSED) {
            try {
                Cashment cashmentSave = convertDtoToEntityCashment(cashmentDto);

                cashmentSave.setCashierFullName(cashmentDto.getCashierFullName());
                cashmentSave.setDate(new Date());
                cashmentSave.setTokenPayment(paymentToken.get());
                cashmentSave.setIsValid(cashmentDto.getIsValid());
                
                Partner business = convertDtoToEntity(
                        partnerService.getPartnerById(cashmentDto.getBusiness().getIdPartner()).getBody());
                Partner trade = convertDtoToEntity(
                        partnerService.getPartnerById(cashmentDto.getTrade().getIdPartner()).getBody());
                cashmentSave.setTrade(trade);
                cashmentSave.setBusiness(business);

                cashmentDaoWrite.saveAndFlush(cashmentSave);
                cashmentDto.setTrade(convertEntityToDto(trade));
                
                paymentTokenService.deactivatePaymentToken(paymentToken.get().getToken());

                PaymentDto paymentDto = new PaymentDto(cashmentDto.getCustomer(), new Date(),
                        cashmentDto.getTrade(), cashmentDto.getBusiness(), cashmentDto.getCustomerName());
                paymentService.addPayment(paymentDto);

                PaymentTokenDto paymentTokenG = paymentTokenService.genPaymentTokenDto(cashmentDto.getCustomer());
                if(cashmentDto.getCustomerTokenFirebase() != "") {
                	Thread t = new Thread() {
                    @Override
                    public void run() {
                        PushNotificationRequest request = new PushNotificationRequest(
                                "Payment effectué de "+ cashmentDto.getAmount() +" avec succès", "Merci d'avoir utilisé Zepe",
                                cashmentDto.getCustomerTokenFirebase());
                        pushService.sendPushNotification(paymentDto, paymentTokenG.getToken(), request);

                    }
                };
                t.start();
                }
                

                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.warn("error in the recording of the operation, " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }
        logger.info("the token payment is used ," + cashmentDto);

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();

    }
    
    public ResponseEntity<String> unvalidCashment(CashmentDto cashmentDto) {
    	try {
    		
    		Cashment cashmentSave = convertDtoToEntityCashment(cashmentDto);
    		System.out.println(cashmentDto);
    		cashmentDaoWrite.updateValue(cashmentSave.getTokenPayment().getToken());
    		
    		/*PaymentDto paymentDto = new PaymentDto(cashmentDto.getCustomer(), new Date(),
                    cashmentDto.getTrade(), cashmentDto.getBusiness(), cashmentDto.getCustomerName());
    		
    		 PaymentTokenDto paymentTokenG = paymentTokenService.genPaymentTokenDto(cashmentDto.getCustomer());
             Thread t = new Thread() {
                 @Override
                 public void run() {
                     PushNotificationRequest request = new PushNotificationRequest(
                             "Payment annulé avec succès", "Merci d'avoir utilisé Zepe",
                             cashmentDto.getCustomerTokenFirebase());
                     pushService.sendPushNotification(paymentDto, paymentTokenG.getToken(), request);

                 }
             };
             t.start();*/
             
    		return ResponseEntity.ok().build();
    		
    	}catch (Exception e) {
            logger.error(e.getMessage());
            logger.warn("error in the recording of the operation, " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    public List<CashmentDto> findByCustomer(String customer){
    	Set<Cashment> cashments = cashmentDaoRead.findByCustomer(customer);
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public List<CashmentDto> findByCashierAndDate(String cashier, DateTime date) {
        DateTime dateDay = new DateTime(date);
        DateTime start = dateDay.withTimeAtStartOfDay();
        DateTime end = dateDay.withTime(23, 59, 59, 999);
        Set<Cashment> cashments = cashmentDaoRead.findByCashierAndDateBetween(cashier, start.toDate(), end.toDate());
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());

    }
    
    
    public List<CashmentDto> findByTradeAndDate(String trade, DateTime date) {
        DateTime dateDay = new DateTime(date);
        DateTime start = dateDay.withTimeAtStartOfDay();
        DateTime end = dateDay.withTime(23, 59, 59, 999);
        Partner partnerL = convertDtoToEntity(partnerService.getPartnerById(Long.parseLong(trade)).getBody());

        List<Cashment> cashments = cashmentDaoRead.findByTradeAndDateBetween(partnerL, start.toDate(), end.toDate());
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());

    }

    public List<CashmentDto> cashmentList() {
        return cashmentDaoRead.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public CashmentDto addCashment(CashmentDto cashmentDto) {
        if (cashmentDto == null) {
            return cashmentDto;
        }
        Cashment saveCashment = cashmentDaoWrite.save(convertDtoToEntity(cashmentDto));
        return convertEntityToDto(saveCashment);
    }

    private CashmentDto convertEntityToDto(Cashment cashment) {
        return modelMapper.map(cashment, CashmentDto.class);
    }

    private Cashment convertDtoToEntity(CashmentDto cashmentDto) {
        return modelMapper.map(cashmentDto, Cashment.class);
    }

    public List<CashmentDto> findByTradeAndTwoDate(Long idTrade, DateTime start, DateTime end) {
        Partner partnerL = convertDtoToEntity(partnerService.getPartnerById(idTrade).getBody());
        logger.info(end.toDate().toString());
        List<Cashment> cashments = cashmentDaoRead.findByTradeAndDateBetween(partnerL, start.toDate(), end.toDate());
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    
    public List<CashmentDto> findByEntrepriseAndTwoDate(Long idTrade, DateTime start, DateTime end) {
        Partner partnerL = convertDtoToEntity(partnerService.getPartnerById(idTrade).getBody());
        logger.info(end.toDate().toString());
        List<Cashment> cashments = cashmentDaoRead.findByBusinessAndDateBetween(partnerL, start.toDate(), end.toDate());
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    
    public List<CashmentDto> findByCustomerAndTwoDate(String customer, DateTime start, DateTime end){
    	
        
    	Set<Cashment> cashments = cashmentDaoRead.findByCustomerAndDateBetween(customer, start.toDate(), end.toDate());
        return cashments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
  
    public List<CashmentDto> findByTradeAndTwoDate(DateCashmentDto dateCashmentDto) {

        if (dateCashmentDto.getDateDebut() == null || dateCashmentDto.getDateFin() == null)
            return new ArrayList<>();

        DateTime debut = new DateTime(dateCashmentDto.getDateDebut());
        DateTime start = debut.withTimeAtStartOfDay();

        DateTime end = new DateTime(dateCashmentDto.getDateFin()).withTime(23, 59, 59, 999);

        return findByTradeAndTwoDate(dateCashmentDto.getIdCommerce(), start, end);
    }

    public List<CashmentDto> findByEntrepriseAndTwoDate(DateCashmentDto dateCashmentDto) {

        if (dateCashmentDto.getDateDebut() == null || dateCashmentDto.getDateFin() == null)
            return new ArrayList<>();

        DateTime debut = new DateTime(dateCashmentDto.getDateDebut());
        DateTime start = debut.withTimeAtStartOfDay();

        DateTime end = new DateTime(dateCashmentDto.getDateFin()).withTime(23, 59, 59, 999);

        return findByEntrepriseAndTwoDate(dateCashmentDto.getIdCommerce(), start, end);
    }
    
    public List<AggCashment> getCashmentByBusinessAndDates(Partner trade, Date start, Date end) {

        List<ICashment> iCashment = cashmentDaoRead.getCashmentByBusinessAndDates(trade.getIdPartner(), start, end);
        return iCashment.stream().map(ic -> convertICashmentToAggCashment(ic, start, end))
                .collect(Collectors.toList());
    }

    Partner convertDtoToEntity(PartnerDto partnerDto) {
        return modelMapper.map(partnerDto, Partner.class);
    }

    private PartnerDto convertEntityToDto(Partner partner) {
        return modelMapper.map(partner, PartnerDto.class);
    }

    private Cashment convertDtoToEntityCashment(CashmentDto cashmentDto) {
        return modelMapper.map(cashmentDto, Cashment.class);
    }

    private AggCashment convertICashmentToAggCashment(ICashment cashment, Date start, Date end) {
        AggCashment aggCash = modelMapper.map(cashment, AggCashment.class);
        aggCash.setEndDate(end);
        aggCash.setStartDate(start);
        return aggCash;
    }
}
