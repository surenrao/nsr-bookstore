package com.nyayapati.bookstore.beans;
import java.io.Serializable;
import java.util.Date;

public class Author implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5127285200919115344L;

	private int id;
	private String firstName;
	private String lastName;
	private Date created;
	private Date modified;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	
}
