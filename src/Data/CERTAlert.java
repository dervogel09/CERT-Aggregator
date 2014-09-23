package Data;

import java.util.Date;

/**
 * 
 * @author Felix Fink
 * Core-Class to represent CERT-Alerts
 * 
 * For more information about the attributes refer to the thesis.
 */

public class CERTAlert {

	private String id;
	private Date create_date;
	private Date updatde_date;
	private String manufactorer;
	private String operating_system;
	private String product_name;
	private String product_version;
	private String description;
	private String type;
	private String cause;
	private String result;
	private String Solution;
	private String degree_of_danger;
	private String title;
	private String reference;
	private String language;
	
	/*
	 * Getter and Setter for the Parameters
	 */
	
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public CERTAlert(){
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @return the updatde_date
	 */
	public Date getUpdatde_date() {
		return updatde_date;
	}

	/**
	 * @return the manufactorer
	 */
	public String getManufactorer() {
		return manufactorer;
	}

	/**
	 * @return the operating_system
	 */
	public String getOperating_system() {
		return operating_system;
	}

	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @return the product_version
	 */
	public String getProduct_version() {
		return product_version;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the solution
	 */
	public String getSolution() {
		return Solution;
	}

	/**
	 * @return the degree_of_danger
	 */
	public String getDegree_of_danger() {
		return degree_of_danger;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @param updatde_date the updatde_date to set
	 */
	public void setUpdatde_date(Date updatde_date) {
		this.updatde_date = updatde_date;
	}

	/**
	 * @param manufactorer the manufactorer to set
	 */
	public void setManufactorer(String manufactorer) {
		this.manufactorer = manufactorer;
	}

	/**
	 * @param operating_system the operating_system to set
	 */
	public void setOperating_system(String operating_system) {
		this.operating_system = operating_system;
	}

	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**
	 * @param product_version the product_version to set
	 */
	public void setProduct_version(String product_version) {
		this.product_version = product_version;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(String solution) {
		Solution = solution;
	}

	/**
	 * @param degree_of_danger the degree_of_danger to set
	 */
	public void setDegree_of_danger(String degree_of_danger) {
		this.degree_of_danger = degree_of_danger;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
