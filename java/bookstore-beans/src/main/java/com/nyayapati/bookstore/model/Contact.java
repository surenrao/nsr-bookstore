package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * Universal Contact/Customer/Client model
 * @author Surya Nyayapati
 *
 */
public abstract class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5378158040838594909L;
	private int contactId;
	private String firstName;
	private String lastName;
	private Images images;
	private String gender;
	private Date birthDate;
	private List<Address> addresses;
	private List<String> emails;
	private String phone;
	private String mobile;
	private Date modified;
	private Date created;
}
