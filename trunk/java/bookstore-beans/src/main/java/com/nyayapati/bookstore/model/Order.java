package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * Order model represents bookstore's receipt for one customer.
 * Tax is applied after adding all prices and applying discount
 * Discount can be applied to either individual 
 * or as a whole but never together
 * @author Surya Nyayapati
 *
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7253420685554203980L;
	private int orderId;
	private List<OrderItem> items;
	private Customer customer;
	private Date orderDate;
	private BigDecimal totalPrice;//totalprice-discount	
	private BigDecimal tax;//percent
}
