package com.nyayapati.bookstore.model.security;

import java.util.List;

import com.nyayapati.bookstore.model.Contact;

/**
 * Used to login/Authenticate inside the system
 * @author Surya Nyayapati
 *
 */
public class User extends Contact {
	private String username;
	private String password;
	private List<Group> groups;
	private List<PermissionExemption> permissions;
}
