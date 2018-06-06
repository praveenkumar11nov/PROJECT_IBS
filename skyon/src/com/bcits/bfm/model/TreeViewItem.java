package com.bcits.bfm.model;

import java.util.ArrayList;

public class TreeViewItem {
    
    private int id;
    private String ReportsTo;
    private String text;
    private Boolean expanded;
    private String spriteCssClass;
    private ArrayList<TreeViewItem> items;       
    
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
    
    
    
    
    public String getReportsTo() {
		return this.ReportsTo;
	}

	public void setReportsTo(String ReportsTo) {
		this.ReportsTo = ReportsTo;
	}

	public Boolean getExpanded()
    {
        return this.expanded;
    }
    
    public void setExpanded(Boolean value)
    {
        this.expanded = value;
    }
    
    public String getSpriteCssClass()
    {
        return this.spriteCssClass;
    }
    
    public void setSpriteCssClass(String value)
    {
        this.spriteCssClass = value;
    }
    
    public ArrayList<TreeViewItem> getItems()
    {        
        return this.items;
    }  

    public void setFields(String text,Boolean expanded)
    {
       // this.setId(id);
        this.setText(text);
       // this.setSpriteCssClass(spriteCssClass);
        this.setExpanded(expanded);
    }
    
    public void setField(int id,String text,Boolean expanded,String ReportsTo)
    {
       this.setId(id);
        this.setText(text);
       // this.setSpriteCssClass(spriteCssClass);
        this.setExpanded(expanded);
        this.setReportsTo(ReportsTo);
    }
    
    public TreeViewItem AddSubItem()
    {               
        TreeViewItem item = new TreeViewItem();
        if(this.items == null){
            this.items = new ArrayList<TreeViewItem>();
        }
        
        this.items.add(item);
        return item;
    }    
   
}
