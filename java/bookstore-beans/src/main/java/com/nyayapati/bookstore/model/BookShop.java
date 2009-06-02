package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.util.List;
/**
 * This model represents a bookstore/bookshop with
 * lots of books on sale.
 * @author Surya Nyayapati
 *
 */
public class BookShop implements Serializable{
	private int shopId;
	private List<SaleBook> booksInventory;
}
