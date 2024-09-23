package com.abpoint.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abpoint.model.ApprovalPendingPayment;
import com.abpoint.model.SocietyMaintenanceEntry;

@Repository
public interface ApprovalPendingPaymentRepository extends JpaRepository<ApprovalPendingPayment, Long> {

	List<ApprovalPendingPayment> findByFlatNumber(int flatNumber);

	@Query("SELECT e FROM ApprovalPendingPayment e WHERE e.flatNumber = :flatNumber AND e.transactionId = :transactionId AND e.amount = :amount AND e.paymentMethod = :paymentMethod AND e.date = :date")
	Optional<ApprovalPendingPayment> findByParameters(@Param("flatNumber") int flatNumber,
			@Param("transactionId") String transactionId, @Param("amount") double amount,
			@Param("paymentMethod") String paymentMethod, @Param("date") Date date);


}
