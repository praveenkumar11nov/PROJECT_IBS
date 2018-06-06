package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * CarModel entity.
 * 
 * @author Manjunath Kotagi
 *
 */
@Entity
@Table(name = "CAR_MODEL")
public class CarModel implements java.io.Serializable {

	// Fields	
	private static final long serialVersionUID = 1L;
	private int carmId;
	private CarMake carMake;
	private String modelName;

	// Constructors

	/** default constructor */
	public CarModel() {
	}

	/** full constructor */
	public CarModel(int carmId, CarMake carMake, String modelName) {
		this.carmId = carmId;
		this.carMake = carMake;
		this.modelName = modelName;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "CAR_MODEL_SEQ", sequenceName = "CAR_MODEL_SEQ")
	@GeneratedValue(generator = "CAR_MODEL_SEQ")
	@Column(name = "CARM_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public int getCarmId() {
		return this.carmId;
	}

	public void setCarmId(int carmId) {
		this.carmId = carmId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CAR_ID", nullable = false)
	public CarMake getCarMake() {
		return this.carMake;
	}

	public void setCarMake(CarMake carMake) {
		this.carMake = carMake;
	}

	@Column(name = "MODEL_NAME", nullable = false, length = 45)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

}