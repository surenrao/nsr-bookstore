package com.nyayapati.bookstore.model;

import java.util.List;

/**
 * Used to login/Authenticate inside the system
 * @author Surya Nyayapati
 *
 */
public class User extends Contact {
	private String username;
	private String password;
	private List<Group> groups;
}
