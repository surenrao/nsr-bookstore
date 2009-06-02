package com.nyayapati.bookstore.model;

import java.io.Serializable;
/**
 * Image model has image path for books
 * images has three sizes small, medium, large
 * with different views like regular,front,back,reight & left side,
 * top and bottom
 * @author Surya Nyayapati
 *
 */
public class Images implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6465976568088117735L;
	private String regularSmall;
	private String regularMedium;
	private String regularLarge;
	
	private String frontSmall;
	private String frontMedium;
	private String frontLarge;
	
	private String backSmall;
	private String backMedium;
	private String backLarge;
	
	private String sideRigtSmall;
	private String sideRigtSMedium;
	private String sideRigtSLarge;
	
	private String sideLeftSmall;
	private String sideLeftMedium;
	private String sideLeftLarge;
	
	private String topSmall;
	private String topMedium;
	private String topLarge;
	
	private String bottomSmall;
	private String bottomMedium;
	private String bottomLarge;	
}
