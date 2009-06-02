package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * This model represents books present in library for renting.
 * One RentBook entity can be a group of same 
 * books(isbn,inventorId) of same grade.
 * @author Surya Nyayapati
 *
 */
public class RentBook extends Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551085211689140604L;
	private int inventoryId;
	private int quantityInStock;
	private int booksOut;
	private BigDecimal fees;
	private int discount;	
	private String grade;//new old torn etc
	private Date created;
	private Date modified;
}
