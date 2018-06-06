package com.bcits.bfm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * CarMake entity. 
 * @author Manjunath Kotagi
 */
@Entity
@Table(name = "CAR_MAKE")
public class CarMake implements java.io.Serializable {

	// Fields

	private int carId;
	private String carName;
	
	// Constructors

	/** default constructor */
	public CarMake() {
	}	

	/** full constructor */
	public CarMake(int carId,String carName) {
		this.carId = carId;
		this.carName = carName;
	}

	
	
	// Property accessors
	@Id
	@SequenceGenerator(name = "CAR_MAKE_SEQ", sequenceName = "CAR_MAKE_SEQ")
	@GeneratedValue(generator = "CAR_MAKE_SEQ")
	@Column(name = "CAR_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getCarId() {
		return this.carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}	

	@Column(name = "CAR_NAME", nullable = false, length = 45)
	public String getCarName() {
		return this.carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}
	
}