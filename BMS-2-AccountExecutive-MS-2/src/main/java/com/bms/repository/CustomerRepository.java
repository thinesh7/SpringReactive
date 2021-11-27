package com.bms.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.bms.entities.Customer;

import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

	@Query("SELECT * FROM ITC_CUSTOMERS c WHERE c.CUSTOMER_SSN_ID = :customerSSNID and c.CUSTOMER_MOBILE_NUMBER= :customerMobileNumber")
	public Mono<Customer> checkCustomerAlreadyExist(@Param("status") String customerSSNID,
			@Param("customerMobileNumber") String customerMobileNumber);
}
