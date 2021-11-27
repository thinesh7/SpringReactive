package com.bms.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {

	private Long customerId;
	private String customerSSNID;
	private String customerName;
	private int age;
	private String addressLine1;
	private String addressLine2;
	private String customerMobileNumber;
	private String city;
	private String state;
	private int pincode;

	private List<AccountDTO> accountList = new ArrayList<AccountDTO>();
}
