package com.nyayapati.bookstore.model;

import java.io.Serializable;
/**
 * A category model used to catagorize books. 
 * i.e. murder, mystery, cooking, programming etc
 * @author Surya Nyayapati
 *
 */
public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5164851911338996004L;
	private int categoryId;
	private String name;
}
