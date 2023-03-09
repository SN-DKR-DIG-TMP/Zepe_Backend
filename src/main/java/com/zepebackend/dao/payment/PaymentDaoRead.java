package com.zepebackend.dao.payment;

import com.zepebackend.agg.IPayment;
import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Partner;
import com.zepebackend.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface PaymentDaoRead extends CoreAbstractQueryRepository<Payment, Long> {

	Set<Payment> findByAccountAndDateBetweenOrderByDateDesc(String account, Date start, Date end);
	
	Set<Payment> findByAccount(String account);
	
	Set<Payment> findByBusinessAndDateBetween(Partner business, Date start, Date end);

	@Query(value = "select count(py.*) as tickets, py.account, py.customer_full_name as customerFullName, sum(montant_employe) as employeePart, "
			+ "sum(montant_entreprise) as entreprisePart "
			+ "from payment py left join jeton j on py.business=j.id_entreprise "
			+ "where py.business=:business and date between :startD and :endD "
			+ "group by py.account, py.customer_full_name", nativeQuery = true)
	List<IPayment> getPaymentByBusinessAndDates(Long business, Date startD, Date endD);

	/*
	 * @Override select count(py.*) as tickets, py.account, sum(montant_employe) as
	 * employeePart, sum(montant_entreprise) as businessPart from payment py, jeton
	 * j where date between '2020-12-01' and '2020-12-18' and
	 * py.business=j.id_entreprise group by py.account;
	 */
}
