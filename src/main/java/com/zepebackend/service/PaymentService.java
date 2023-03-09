package com.zepebackend.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zepebackend.agg.AggPayment;
import com.zepebackend.agg.IPayment;
import com.zepebackend.dao.partner.PartnerDaoRead;
import com.zepebackend.dao.payment.PaymentDaoRead;
import com.zepebackend.dao.payment.PaymentDaoWrite;
import com.zepebackend.dto.PaymentDto;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.Payment;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;

@Service
public class PaymentService {
	final PaymentDaoRead payRepRed;
	final PartnerDaoRead partnerRepRed;
	private final ModelMapper modelMapper;
	final PaymentDaoWrite payRepWr;

	Logger logger = LoggerFactory.getLogger(CashmentService.class);

	public PaymentService(PaymentDaoRead payRepRed, PartnerDaoRead partnerRepRed, ModelMapper modelMapper,
			PaymentDaoWrite payRepWr) {
		this.payRepRed = payRepRed;
		this.partnerRepRed = partnerRepRed;
		this.modelMapper = modelMapper;
		this.payRepWr = payRepWr;
	}

	public List<PaymentDto> getAllPayment() {
		return payRepRed.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public PaymentDto addPayment(PaymentDto paymentdto) {
		Partner trade;
		Partner business;
		if (partnerRepRed.findById(paymentdto.getTrade().getIdPartner()).isPresent()) {
			trade = partnerRepRed.findById(paymentdto.getTrade().getIdPartner()).get();
		} else
			throw new ResourceNotFoundExceptions("trade is null");
		if (partnerRepRed.findById(paymentdto.getBusiness().getIdPartner()).isPresent()) {
			business = partnerRepRed.findById(paymentdto.getBusiness().getIdPartner()).get();
		} else
			throw new ResourceNotFoundExceptions("business is null");
		Payment payment = new Payment(paymentdto.getAccount(), paymentdto.getDate(), trade, business, paymentdto.getCustomerFullName());

		Payment response = payRepWr.save(payment);
		return convertEntityToDto(response);
	}

	public List<PaymentDto> getAllPaymentByDateAndUser(String account, DateTime date) {
		DateTime dateDay = new DateTime(date);
		DateTime start = dateDay.minusMonths(2).withDayOfMonth(1).withTimeAtStartOfDay();
		DateTime end = dateDay.withTime(23, 59, 59, 999);
		Set<Payment> payments = payRepRed.findByAccountAndDateBetweenOrderByDateDesc(account, start.toDate(),
				end.toDate());
		return payments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}
	
	public List<PaymentDto> getAllPaymentUser (String account){
		Set<Payment> payments = payRepRed.findByAccount(account);
		return payments.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public Set<PaymentDto> getAllPaymentByDateAndBusiness(String partner, Date date) {
		DateTime dateDay = new DateTime(date);
		DateTime start = dateDay.withTimeAtStartOfDay();
		DateTime end = dateDay.withTime(23, 59, 59, 999);

		if (partnerRepRed.findById(Long.parseLong(partner)).isPresent()) {
			Partner partnerL = partnerRepRed.findById(Long.parseLong(partner)).get();
			Set<Payment> payments = payRepRed.findByBusinessAndDateBetween(partnerL, start.toDate(), end.toDate());
			return payments.stream().map(this::convertEntityToDto).collect(Collectors.toSet());
		} else
			throw new ResourceNotFoundExceptions("partner is null");
	}

	public List<AggPayment> getPaymentByBusinessAndDates(Long trade, Date start, Date end) {

		List<IPayment> iPayment = payRepRed.getPaymentByBusinessAndDates(trade, start, end);
		List<Payment> Payment = payRepRed.findAll();
		logger.info(Payment.toString());
		return iPayment.stream().map(ip -> convertIPaymentToAggPayment(ip, start, end)).collect(Collectors.toList());
	}

	private PaymentDto convertEntityToDto(Payment payment) {
		return modelMapper.map(payment, PaymentDto.class);
	}

	private AggPayment convertIPaymentToAggPayment(IPayment payment, Date start, Date end) {
		AggPayment aggCash = modelMapper.map(payment, AggPayment.class);
		aggCash.setEndDate(end);
		aggCash.setStartDate(start);
		return aggCash;
	}
}
