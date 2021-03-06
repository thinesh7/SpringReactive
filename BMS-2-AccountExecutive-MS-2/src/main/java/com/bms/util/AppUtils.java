package com.bms.util;

import org.springframework.beans.BeanUtils;

import com.bms.dto.AccountDTO;
import com.bms.dto.CustomerDTO;
import com.bms.entities.Account;
import com.bms.entities.Customer;

public class AppUtils {

	public static Customer convertCustomerDTOtoCustomerEntity(CustomerDTO dto) {
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity); // Source --> Destination
		return entity;
	}

	public static CustomerDTO converCustomerEntityToCustomerDTO(Customer entity) {
		CustomerDTO dto = new CustomerDTO();
		BeanUtils.copyProperties(entity, dto); // Source --> Destination
		return dto;
	}

	public static Account convertAccountDTOtoAccountEntity(AccountDTO dto) {
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity); // Source --> Destination
		return entity;
	}

	public static AccountDTO converAccountEntityToAccountDTO(Account entity) {
		AccountDTO dto = new AccountDTO();
		BeanUtils.copyProperties(entity, dto); // Source --> Destination
		return dto;
	}
}
