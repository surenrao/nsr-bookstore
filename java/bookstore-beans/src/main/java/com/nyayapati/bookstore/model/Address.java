package com.nyayapati.bookstore.model;

import java.io.Serializable;
/**
 * Universal Address model
 * @author Surya Nyayapati
 *
 */
public class Address implements Serializable{
	private int addressId;
	private String street;
	private String streetNumber;
	private String streetAdditional;
	private String company;
	private String city;
	private String stateRegion;
	private String zipPostalCode;
	private String country;
	private String additional;
	private String typeAddress;
}
