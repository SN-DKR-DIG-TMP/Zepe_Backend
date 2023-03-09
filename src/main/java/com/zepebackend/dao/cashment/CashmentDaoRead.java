package com.zepebackend.dao.cashment;

import com.zepebackend.agg.ICashment;
import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Cashment;
import com.zepebackend.entity.Partner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface CashmentDaoRead extends CoreAbstractQueryRepository<Cashment, Long> {

	Set<Cashment> findByCashierAndDateBetween(String cashier, Date start, Date end);

	Set<Cashment> findByCustomerAndDateBetween(String customer, Date start, Date fin);
	
	List<Cashment> findByTradeAndDateBetween(Partner trade, Date start, Date fin);
	
	List<Cashment> findByBusinessAndDateBetween(Partner trade, Date start, Date fin);
	
	Set<Cashment> findByCustomer(String customer);
	
	@Query(value = "select c.business as businessId, p.name as businessName, sum (c.amount) as cashmentAmount from cashment as c "
			+ ", partner as p where c.business= p.id_partner and c.trade=:trade "
			+ "and c.date between :startD and :endD group by c.business, p.name", nativeQuery = true)
	List<ICashment> getCashmentByBusinessAndDates(Long trade, Date startD, Date endD);

}
