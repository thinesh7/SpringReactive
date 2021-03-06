package com.bms.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bms.beans.AccountType;
import com.bms.dto.CustomerDTO;
import com.bms.entities.Account;
import com.bms.entities.Customer;
import com.bms.repository.AccountRepository;
import com.bms.repository.CustomerRepository;
import com.bms.util.AppUtils;
import reactor.core.publisher.Mono;

@RestController
public class BMSController {

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private AccountRepository accRepo;

	@GetMapping(value = "/save", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<CustomerDTO> getCustomer() {

		Customer customer = new Customer("SSN1001", "AAA", 25, "AADR-1", "ADDR-2", "91-55560", "CBE", "TN", 642110);

		Mono<CustomerDTO> res = custRepo.save(customer).doOnNext(i -> {
			Account acc1 = new Account("ACC01", AccountType.CURRENT, LocalDate.now(), true, i.getCustomerId());
			Account acc2 = new Account("ACC02", AccountType.SAVINGS, LocalDate.now(), true, i.getCustomerId());
			accRepo.save(acc1).log().subscribe();
			accRepo.save(acc2).log().subscribe();
		}).map(i -> AppUtils.converCustomerEntityToCustomerDTO(i)).doOnNext(i -> {
			System.out.println(i);
			accRepo.findById(1l).subscribe(j -> {
				System.out.println(j);
				i.getAccountList().add(AppUtils.converAccountEntityToAccountDTO(j));
			});
		});

		return res;
	}
}
