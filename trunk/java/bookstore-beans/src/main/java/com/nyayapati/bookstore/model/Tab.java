package com.nyayapati.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
* Tab model represents Libraries receipt for one member.
* Tax is applied after adding all prices and adding late fees if any
* @author Surya Nyayapati
*
*/
public class Tab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6815646912192769286L;
	private int tabId;
	private List<TabItem> items;
	private Member member;
	private Date loanDate;
	private BigDecimal totalFees;//fees + latefees	
	private BigDecimal tax;//percent
}
