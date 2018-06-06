package com.bcits.bfm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({ 
	@NamedQuery(name = "CamChargesEntity.findAll", query = "SELECT cs.camId,cs.camChargesBasedOn,cs.rateForFlat,cs.rateForSqft FROM CamChargesEntity cs ORDER BY cs.camId DESC"),
	@NamedQuery(name = "CamChargesEntity.findAllData", query = "SELECT cs FROM CamChargesEntity cs"),
})


@Entity
@Table(name = "CAM_CHARGE_SETTINGS")
public class CamChargesEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@SequenceGenerator(name = "cam_charge_settings_seq", sequenceName = "CAM_CHARGE_SETTINGS_SEQ")
		@GeneratedValue(generator = "cam_charge_settings_seq")
		@Column(name = "ID")
		private int camId;
		
		@Column(name="CHARGES_BASED_ON")
		private String camChargesBasedOn;
		
		@Column(name="RATE_FOR_FLAT")
		private int rateForFlat;
		
		@Column(name="RATE_FOR_SQFT")
		private int rateForSqft;

		public int getCamId() {
			return camId;
		}

		public void setCamId(int camId) {
			this.camId = camId;
		}

		public String getCamChargesBasedOn() {
			return camChargesBasedOn;
		}

		public void setCamChargesBasedOn(String camChargesBasedOn) {
			this.camChargesBasedOn = camChargesBasedOn;
		}

		public int getRateForFlat() {
			return rateForFlat;
		}

		public void setRateForFlat(int rateForFlat) {
			this.rateForFlat = rateForFlat;
		}

		public int getRateForSqft() {
			return rateForSqft;
		}

		public void setRateForSqft(int rateForSqft) {
			this.rateForSqft = rateForSqft;
		}
		
	
}
		
		