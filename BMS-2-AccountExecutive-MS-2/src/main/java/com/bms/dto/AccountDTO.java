package com.bms.dto;

import java.time.LocalDate;
import com.bms.beans.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDTO {

	private Long accountId;
	private String accountNumber;
	private AccountType accountType;
	private LocalDate accountCreationDate;
	private boolean accountStatus;
	private Long fkCustomerId;

}
