package com.nyayapati.bookstore.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This model represents books present in shop for sale.
 * One SaleBook entity can be a group of same 
 * books(isbn,inventorId) of same grade.
 * 
 * @author Surya Nyayapati
 *
 */
public class SaleBook extends Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4875788928507725034L;
	private int inventoryId;
	private int quantityInStock;	
	private int discount;//in percent	
	private String grade;//new, old, torn etc
	private Date created;
	private Date modified;
}
