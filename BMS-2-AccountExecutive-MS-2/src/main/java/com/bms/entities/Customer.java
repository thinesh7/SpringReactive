package com.bms.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(value = "ITC_CUSTOMERS")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(value = "CUSTOMER_ID")
	private Long customerId;

	@Column(value = "CUSTOMER_SSN_ID")
	private String customerSSNID;

	@Column(value = "CUSTOMER_NAME")
	private String customerName;

	@Column(value = "CUSTOMER_AGE")
	private Integer age;

	@Column(value = "CUSTOMER_ADDR_LINE_1")
	private String addressLine1;

	@Column(value = "CUSTOMER_ADDR_LINE_2")
	private String addressLine2;

	@Column(value = "CUSTOMER_MOBILE_NUMBER")
	private String customerMobileNumber;

	@Column(value = "CUSTOMER_CITY")
	private String city;

	@Column(value = "CUSTOMER_STATE")
	private String state;

	@Column(value = "CUSTOMER_PINCODE")
	private Integer pincode;

	public Customer(String customerSSNID, String customerName, int age, String addressLine1, String addressLine2,
			String customerMobileNumber, String city, String state, int pincode) {
		this.customerSSNID = customerSSNID;
		this.customerName = customerName;
		this.age = age;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.customerMobileNumber = customerMobileNumber;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

}
