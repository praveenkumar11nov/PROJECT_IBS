package com.bcits.bfm.view;

public class Node {

	private String name;
	private String url;
	private int level;

	/**
	 * Node constructor
	 * 
	 * @param name
	 *            of the node
	 * @param value
	 *            of the node
	 * @param level
	 *            in the breadcrumb ex root 0 menu 1 sub-menu 2 etc...
	 */
	public Node(String name, String value, int level) {
		this.name = name;
		this.url = value;
		this.level = level;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return url;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.url = value;
	}

	/**
	 * 
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
