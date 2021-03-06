package com.bms.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bms.dto.CustomerDTO;
import com.bms.repository.CustomerRepository;
import com.bms.util.AppUtils;
import reactor.core.publisher.Mono;

@Component
public class BMSHelper {

	@Autowired
	private CustomerRepository custRepo;

	public Mono<Object> createCustomer(CustomerDTO dto) {
		Mono<Object> res = custRepo.checkCustomerAlreadyExist(dto.getCustomerSSNID(), dto.getCustomerMobileNumber())
				.flatMap(i -> {
					return Mono.error(new Exception("Already Exist"));
				}).doOnSuccess(j -> {
					custRepo.save(AppUtils.convertCustomerDTOtoCustomerEntity(dto)).subscribe(i -> {
						dto.setCustomerId(i.getCustomerId());
					});
				}).thenReturn(dto);
		return res;
	}

}
