package com.nyayapati.bookstore.model;

import java.io.Serializable;
/**
 * Model OrderItem shows one entry in a Order/receipt.
 * @author Surya Nyayapati
 *
 */
public class OrderItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1758421567195909136L;
	private int itemId;
	private int orderId;
	private SaleBook book;
	private int quantityOrdered;//default is 1
	private String comments;
}
