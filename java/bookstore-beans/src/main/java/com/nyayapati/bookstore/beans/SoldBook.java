package com.nyayapati.bookstore.beans;

import java.io.Serializable;
import java.util.Date;
/**
 * This bean represents sold multiple copies of same book.
 * for example a customer bought 3 copies of book name XYZ with isbn 123. 
 * Can get price tax and discount from book class
 * @author Surya Nyayapati
 *
 */
public class SoldBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4875788928507725034L;
	/**
	 * Each Soldbook has unique id
	 */
	private int id;
	/**
	 * a recieptNumber can have multiple unique Soldbook id
	 */
	private String recieptNumber;
	private Book book;
	private int quantity;	
	private Customer customer;	
	private Date sellTime;
}
