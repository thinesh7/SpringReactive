package com.bms.entities;

import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.bms.beans.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(value = "ITC_ACCOUNTS")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(value = "ACCOUNT_ID")
	private Long accountId;

	@Column(value = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Enumerated(EnumType.STRING) 
	@Column(value = "ACCOUNT_TYPE")
	private AccountType accountType;

	@Column(value = "ACCOUNT_CREATION_DATE")
	private LocalDate accountCreationDate;

	@Column(value = "ACCOUNT_STATUS")
	private boolean accountStatus;
	
	@Column(value = "FK_CUSTOMER_ID")
	private Long fkCustomerId;

	public Account(String accountNumber, AccountType accountType, LocalDate accountCreationDate, boolean accountStatus,
			Long fkCustomerId) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountCreationDate = accountCreationDate;
		this.accountStatus = accountStatus;
		this.fkCustomerId = fkCustomerId;
	}	
}
