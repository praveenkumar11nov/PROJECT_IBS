package com.bcits.bfm.model;

import java.util.ArrayList;

public class Ldap {
    
    private int id;
    private String text;
 
    private ArrayList<Ldap> items;       
    
    public String getText()
    {
        return this.text;
    }
    
    public void setText(String value)
    {
        this.text = value;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int value)
    {
        this.id = value;
    }   
   
    public ArrayList<Ldap> getItems()
    {        
        return this.items;
    }  

    public Ldap(int id,String text)
    {
    	this.setId(id);
        this.setText(text);
    }
    
    public Ldap() {
		// TODO Auto-generated constructor stub
	}

	public void setFields(int id,String text)
    {
        this.setId(id);
        this.setText(text);
     
    }
}
