package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



/**
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "STATE")
@NamedQueries({ 	
	@NamedQuery(name = "State.findAll", query = "SELECT s FROM State s ORDER BY s.stateName"),
	@NamedQuery(name = "State.getIdBasedOnStateName", query = "SELECT s.stateId FROM State s WHERE s.stateName = :stateName"),
	@NamedQuery(name = "State.findId", query = "SELECT s FROM State s WHERE s.stateName=:sname"),
	@NamedQuery(name = "State.findName", query = "SELECT s FROM State s WHERE s.stateId=:id"),
	@NamedQuery(name = "State.getStateListAfterPassingStateName", query = "SELECT s FROM State s WHERE s.stateName=:stateName AND s.countryId=:countryId"),

})
public class State  implements java.io.Serializable {


    // Fields    

     private int stateId;
     private String stateName;
     private int countryId;
   
   /*private Set<City> cities = new HashSet<City>(0);
  	
     @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
     @JoinColumn(name = "CITY_ID", nullable = false)
     public Set<City> getCities()
 	 {
 		 return cities;
 	 }

 	 public void setCities(Set<City> cities)
 	 {
 		 this.cities = cities;
 	 }
 	*/
	/** default constructor */
    public State() {
    }

	public State(String stateName, int countryId)
	{
		this.stateName = stateName;
		this.countryId = countryId;
	}

	// Property accessors
    @Id     
    @SequenceGenerator(name = "stateSeq", sequenceName = "STATE_SEQ")
	@GeneratedValue(generator = "stateSeq")
    @Column(name="STATE_ID")
    public int getStateId() {
        return this.stateId;
    }
    
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
	
    @Column(name="STATE_NAME", nullable=false, length=45)
    @NotNull(message = "'State Name is Required'")
    public String getStateName() {
        return this.stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Min(value=1, message = "'Country' not Selected")
    @Column(name="COUNTRY_ID")
	public int getCountryId()
	{
		return countryId;
	}


	public void setCountryId(int countryId)
	{
		this.countryId = countryId;
	} 

}