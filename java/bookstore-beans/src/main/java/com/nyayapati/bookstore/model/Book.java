package com.nyayapati.bookstore.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * Universal Book model.
 * @author Surya Nyayapati
 *
 */
public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7966052150785310014L;
	
	private String isbn13;//primary 13 number or bookId
	private String isbn10;//?amazon says so?
	private String title;
	private List<Author> authors;
	private Publisher publisher;
	private String edition;
	private Date publicationDate;
	private Images images;	
	private String description;
	private BigDecimal listPrice;
	private String language;//english
	private String bookFormat;//paperback,hardback,audio etc
	private List<Category> catagories;//murder,mystery, computer etc
	private int pages;//194 pages
	private Dimension dimension;// 8.9 x 7.4 x 2 inches
	private Weight weight;//3.4 pounds
		
}
