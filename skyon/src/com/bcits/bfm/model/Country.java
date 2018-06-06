package com.bcits.bfm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COUNTRY")
@NamedQueries({ 	
	@NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c ORDER BY c.countryName"),
	@NamedQuery(name = "Country.findId", query = "SELECT c FROM Country c WHERE c.countryName=:cname")
})
public class Country  implements java.io.Serializable {


    // Fields    

     private int countryId;
     private String countryName;
     
    /* private Set<State> states = new HashSet<State>(0);
 	
     @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
     @JoinColumn(name = "STATE_ID", nullable = false)
 	 public Set<State> getStates()
	 {
		 return states;
	 }

	 public void setStates(Set<State> states)
	 {
	 	 this.states = states;
	 }*/
	
 	
    // Constructors

    /** default constructor */
    public Country() {
    }

    public Country(String countryName)
	{
		this.countryName = countryName;
	}



	 public Country(int id,String text)
    {
    	this.setCountryId(id);
        this.setCountryName(text);
    }

	// Property accessors
    @Id     
    @SequenceGenerator(name = "countrySeq", sequenceName = "COUNTRY_SEQ")
	@GeneratedValue(generator = "countrySeq")
    @Column(name="COUNTRY_ID")
    public int getCountryId() {
        return this.countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    @Column(name="COUNTRY_NAME", nullable=false, length=45)

    public String getCountryName() {
        return this.countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}