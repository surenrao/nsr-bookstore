package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Model TabItem shows one entry in a Tab/receipt.
 * @author Surya Nyayapati
 *
 */
public class TabItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5858838166026260382L;
	private int itemId;
	private int tabId;
	private RentBook book;
	private int quantityLoaned;		
	private Date issueDate;
	private Date dueDate;
	private Date returnedDate;
	private BigDecimal rentFees;
	private BigDecimal lateFees;
}
