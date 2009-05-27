package com.nyayapati.bookstore.beans;

import java.io.Serializable;
import java.util.List;

public class Reciept implements Serializable {
	private int id;
	private List<SoldBook> soldBooks;
	private List<LoanedBook> loanedBooks;
}
