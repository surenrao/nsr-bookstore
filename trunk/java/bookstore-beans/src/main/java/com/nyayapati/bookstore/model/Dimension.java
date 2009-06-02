package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * A dimension of a book
 * i.e 9 x 6.1 x 1.6 inches
 * @author Surya Nyayapati
 *
 */
public class Dimension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8064275790571301598L;
	private BigDecimal width;	
	private BigDecimal length;
	private BigDecimal height;
	private String units;//cms,inches
}
