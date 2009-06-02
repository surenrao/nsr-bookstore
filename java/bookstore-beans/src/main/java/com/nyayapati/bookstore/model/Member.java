package com.nyayapati.bookstore.model;
/**
 * A contact who rents a book or is member of library
 * @author Surya Nyayapati
 *
 */
public class Member extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6944722638774602198L;
	private int memberId;
	//book by book, subscribe monthly (2 books or 3 books etc),queue system
	private String memberType;
}
