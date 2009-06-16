package com.nyayapati.bookstore.model.security;

import java.io.Serializable;
import java.util.List;
/**
 * Group model is used to assign set of permission after 
 * authentication
 * @author Surya Nyayapati
 *
 */
public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2231162062300720081L;
	private int groupId;
	private int name;
	private int level;
	private List<Permission> permissions;
}
