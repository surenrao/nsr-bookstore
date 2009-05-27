package com.nyayapati.bookstore.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * This bean represents loaned/rented multiple copies of same book.
 * for example a customer took 2 copies of book name XYZ with isbn 123 
 * for a duration of 15 days
 * @author Surya Nyayapati
 *
 */
public class LoanedBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551085211689140604L;
	private int id;
	private String recieptNumber;
	private Book book;
	private int quantity;
	private Customer customer;	
	private Date loanDate;
	private Date dueDate;
	private BigDecimal rentFees;
	private BigDecimal lateFees;
}
