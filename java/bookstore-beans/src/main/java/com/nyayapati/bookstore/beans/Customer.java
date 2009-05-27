package com.nyayapati.bookstore.beans;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {
	
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> emails;
	private String phone;
	private String mobile;
}
