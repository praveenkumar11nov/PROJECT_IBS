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
@Table(name = "CITY")
@NamedQueries({ 	
	@NamedQuery(name = "City.findAll", query = "SELECT c FROM City c ORDER BY c.cityName"),
	@NamedQuery(name = "City.getIdBasedOnCityName", query = "SELECT c.cityId FROM City c WHERE c.cityName = :cityName"),
	@NamedQuery(name = "City.findName", query = "SELECT c FROM City c WHERE c.cityId=:id"),
	@NamedQuery(name = "City.cityListAfterPassingCityNameAndStId", query = "SELECT c FROM City c WHERE c.cityName=:cityName AND c.stateId=:stateId")

})
public class City  implements java.io.Serializable 
{
    // Fields    
	
	private int cityId;
	private String cityName;    
    private int stateId;

	public City()
	{
		// TODO Auto-generated constructor stub
	}

    public City(String cityName, int stateId)
	{
		this.cityName = cityName;
		this.stateId = stateId;
	}

	 public City(int id,String city,int state)
    {
    	this.setCityId(id);
        this.setCityName(city);
        this.setStateId(state);
    }

	@Id     
    @SequenceGenerator(name = "citySeq", sequenceName = "CITY_SEQ")
	@GeneratedValue(generator = "citySeq")
    @Column(name="CITY_ID")
	public int getCityId()
	{
		return cityId;
	}

	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}

	@Column(name="CITY_NAME")
	@NotNull
	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}
	
	@Min(value=1, message = "'State' not Selected")
	@Column(name="STATE_ID")
	public int getStateId()
	{
		return stateId;
	}

	public void setStateId(int stateId)
	{
		this.stateId = stateId;
	}
	
}