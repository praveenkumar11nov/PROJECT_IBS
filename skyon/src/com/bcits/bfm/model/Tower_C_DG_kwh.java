package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tower_C_DG_kwh",schema="dbo",catalog="EMS")
public class Tower_C_DG_kwh {

	// Fields

	private Timestamp datestr;
	private Double s1n282Kwh;
	private Double s1n283Kwh;
	private Double s1n284Kwh;
	private Double s1n285Kwh;
	private Double s1n286Kwh;
	private Double s1n287Kwh;
	private Double s1n288Kwh;
	private Double s1n289Kwh;
	private Double s1n290Kwh;
	private Double s1n291Kwh;
	private Double s1n292Kwh;
	private Double s1n293Kwh;
	private Double s1n294Kwh;
	private Double s1n295Kwh;
	private Double s1n296Kwh;
	private Double s1n297Kwh;
	private Double s1n298Kwh;
	private Double s1n299Kwh;
	private Double s1n300Kwh;
	private Double s1n301Kwh;
	private Double s1n302Kwh;
	private Double s1n303Kwh;
	private Double s1n304Kwh;
	private Double s1n305Kwh;
	private Double s1n306Kwh;
	private Double s1n307Kwh;
	private Double s1n308Kwh;
	private Double s1n309Kwh;
	private Double s1n310Kwh;
	private Double s1n311Kwh;
	private Double s1n312Kwh;
	private Double s1n313Kwh;
	private Double s1n314Kwh;
	private Double s1n315Kwh;
	private Double s1n316Kwh;
	private Double s1n317Kwh;
	private Double s1n318Kwh;
	private Double s1n319Kwh;
	private Double s1n320Kwh;
	private Double s1n321Kwh;
	private Double s1n322Kwh;
	private Double s1n323Kwh;
	private Double s1n324Kwh;
	private Double s1n325Kwh;

	// Constructors

	/** default constructor */
	public Tower_C_DG_kwh() {
	}

	/** full constructor */
	public Tower_C_DG_kwh(Timestamp datestr, Double s1n282Kwh, Double s1n283Kwh,
			Double s1n284Kwh, Double s1n285Kwh, Double s1n286Kwh,
			Double s1n287Kwh, Double s1n288Kwh, Double s1n289Kwh,
			Double s1n290Kwh, Double s1n291Kwh, Double s1n292Kwh,
			Double s1n293Kwh, Double s1n294Kwh, Double s1n295Kwh,
			Double s1n296Kwh, Double s1n297Kwh, Double s1n298Kwh,
			Double s1n299Kwh, Double s1n300Kwh, Double s1n301Kwh,
			Double s1n302Kwh, Double s1n303Kwh, Double s1n304Kwh,
			Double s1n305Kwh, Double s1n306Kwh, Double s1n307Kwh,
			Double s1n308Kwh, Double s1n309Kwh, Double s1n310Kwh,
			Double s1n311Kwh, Double s1n312Kwh, Double s1n313Kwh,
			Double s1n314Kwh, Double s1n315Kwh, Double s1n316Kwh,
			Double s1n317Kwh, Double s1n318Kwh, Double s1n319Kwh,
			Double s1n320Kwh, Double s1n321Kwh, Double s1n322Kwh,
			Double s1n323Kwh, Double s1n324Kwh, Double s1n325Kwh) {
		this.datestr = datestr;
		this.s1n282Kwh = s1n282Kwh;
		this.s1n283Kwh = s1n283Kwh;
		this.s1n284Kwh = s1n284Kwh;
		this.s1n285Kwh = s1n285Kwh;
		this.s1n286Kwh = s1n286Kwh;
		this.s1n287Kwh = s1n287Kwh;
		this.s1n288Kwh = s1n288Kwh;
		this.s1n289Kwh = s1n289Kwh;
		this.s1n290Kwh = s1n290Kwh;
		this.s1n291Kwh = s1n291Kwh;
		this.s1n292Kwh = s1n292Kwh;
		this.s1n293Kwh = s1n293Kwh;
		this.s1n294Kwh = s1n294Kwh;
		this.s1n295Kwh = s1n295Kwh;
		this.s1n296Kwh = s1n296Kwh;
		this.s1n297Kwh = s1n297Kwh;
		this.s1n298Kwh = s1n298Kwh;
		this.s1n299Kwh = s1n299Kwh;
		this.s1n300Kwh = s1n300Kwh;
		this.s1n301Kwh = s1n301Kwh;
		this.s1n302Kwh = s1n302Kwh;
		this.s1n303Kwh = s1n303Kwh;
		this.s1n304Kwh = s1n304Kwh;
		this.s1n305Kwh = s1n305Kwh;
		this.s1n306Kwh = s1n306Kwh;
		this.s1n307Kwh = s1n307Kwh;
		this.s1n308Kwh = s1n308Kwh;
		this.s1n309Kwh = s1n309Kwh;
		this.s1n310Kwh = s1n310Kwh;
		this.s1n311Kwh = s1n311Kwh;
		this.s1n312Kwh = s1n312Kwh;
		this.s1n313Kwh = s1n313Kwh;
		this.s1n314Kwh = s1n314Kwh;
		this.s1n315Kwh = s1n315Kwh;
		this.s1n316Kwh = s1n316Kwh;
		this.s1n317Kwh = s1n317Kwh;
		this.s1n318Kwh = s1n318Kwh;
		this.s1n319Kwh = s1n319Kwh;
		this.s1n320Kwh = s1n320Kwh;
		this.s1n321Kwh = s1n321Kwh;
		this.s1n322Kwh = s1n322Kwh;
		this.s1n323Kwh = s1n323Kwh;
		this.s1n324Kwh = s1n324Kwh;
		this.s1n325Kwh = s1n325Kwh;
	}

	// Property accessors

	@Id
	@Column(name = "Datestr", length = 23)
	public Timestamp getDatestr() {
		return this.datestr;
	}

	public void setDatestr(Timestamp datestr) {
		this.datestr = datestr;
	}

	@Column(name = "S1N282_KWH", precision = 53, scale = 0)
	public Double getS1n282Kwh() {
		return this.s1n282Kwh;
	}

	public void setS1n282Kwh(Double s1n282Kwh) {
		this.s1n282Kwh = s1n282Kwh;
	}

	@Column(name = "S1N283_KWH", precision = 53, scale = 0)
	public Double getS1n283Kwh() {
		return this.s1n283Kwh;
	}

	public void setS1n283Kwh(Double s1n283Kwh) {
		this.s1n283Kwh = s1n283Kwh;
	}

	@Column(name = "S1N284_KWH", precision = 53, scale = 0)
	public Double getS1n284Kwh() {
		return this.s1n284Kwh;
	}

	public void setS1n284Kwh(Double s1n284Kwh) {
		this.s1n284Kwh = s1n284Kwh;
	}

	@Column(name = "S1N285_KWH", precision = 53, scale = 0)
	public Double getS1n285Kwh() {
		return this.s1n285Kwh;
	}

	public void setS1n285Kwh(Double s1n285Kwh) {
		this.s1n285Kwh = s1n285Kwh;
	}

	@Column(name = "S1N286_KWH", precision = 53, scale = 0)
	public Double getS1n286Kwh() {
		return this.s1n286Kwh;
	}

	public void setS1n286Kwh(Double s1n286Kwh) {
		this.s1n286Kwh = s1n286Kwh;
	}

	@Column(name = "S1N287_KWH", precision = 53, scale = 0)
	public Double getS1n287Kwh() {
		return this.s1n287Kwh;
	}

	public void setS1n287Kwh(Double s1n287Kwh) {
		this.s1n287Kwh = s1n287Kwh;
	}

	@Column(name = "S1N288_KWH", precision = 53, scale = 0)
	public Double getS1n288Kwh() {
		return this.s1n288Kwh;
	}

	public void setS1n288Kwh(Double s1n288Kwh) {
		this.s1n288Kwh = s1n288Kwh;
	}

	@Column(name = "S1N289_KWH", precision = 53, scale = 0)
	public Double getS1n289Kwh() {
		return this.s1n289Kwh;
	}

	public void setS1n289Kwh(Double s1n289Kwh) {
		this.s1n289Kwh = s1n289Kwh;
	}

	@Column(name = "S1N290_KWH", precision = 53, scale = 0)
	public Double getS1n290Kwh() {
		return this.s1n290Kwh;
	}

	public void setS1n290Kwh(Double s1n290Kwh) {
		this.s1n290Kwh = s1n290Kwh;
	}

	@Column(name = "S1N291_KWH", precision = 53, scale = 0)
	public Double getS1n291Kwh() {
		return this.s1n291Kwh;
	}

	public void setS1n291Kwh(Double s1n291Kwh) {
		this.s1n291Kwh = s1n291Kwh;
	}

	@Column(name = "S1N292_KWH", precision = 53, scale = 0)
	public Double getS1n292Kwh() {
		return this.s1n292Kwh;
	}

	public void setS1n292Kwh(Double s1n292Kwh) {
		this.s1n292Kwh = s1n292Kwh;
	}

	@Column(name = "S1N293_KWH", precision = 53, scale = 0)
	public Double getS1n293Kwh() {
		return this.s1n293Kwh;
	}

	public void setS1n293Kwh(Double s1n293Kwh) {
		this.s1n293Kwh = s1n293Kwh;
	}

	@Column(name = "S1N294_KWH", precision = 53, scale = 0)
	public Double getS1n294Kwh() {
		return this.s1n294Kwh;
	}

	public void setS1n294Kwh(Double s1n294Kwh) {
		this.s1n294Kwh = s1n294Kwh;
	}

	@Column(name = "S1N295_KWH", precision = 53, scale = 0)
	public Double getS1n295Kwh() {
		return this.s1n295Kwh;
	}

	public void setS1n295Kwh(Double s1n295Kwh) {
		this.s1n295Kwh = s1n295Kwh;
	}

	@Column(name = "S1N296_KWH", precision = 53, scale = 0)
	public Double getS1n296Kwh() {
		return this.s1n296Kwh;
	}

	public void setS1n296Kwh(Double s1n296Kwh) {
		this.s1n296Kwh = s1n296Kwh;
	}

	@Column(name = "S1N297_KWH", precision = 53, scale = 0)
	public Double getS1n297Kwh() {
		return this.s1n297Kwh;
	}

	public void setS1n297Kwh(Double s1n297Kwh) {
		this.s1n297Kwh = s1n297Kwh;
	}

	@Column(name = "S1N298_KWH", precision = 53, scale = 0)
	public Double getS1n298Kwh() {
		return this.s1n298Kwh;
	}

	public void setS1n298Kwh(Double s1n298Kwh) {
		this.s1n298Kwh = s1n298Kwh;
	}

	@Column(name = "S1N299_KWH", precision = 53, scale = 0)
	public Double getS1n299Kwh() {
		return this.s1n299Kwh;
	}

	public void setS1n299Kwh(Double s1n299Kwh) {
		this.s1n299Kwh = s1n299Kwh;
	}

	@Column(name = "S1N300_KWH", precision = 53, scale = 0)
	public Double getS1n300Kwh() {
		return this.s1n300Kwh;
	}

	public void setS1n300Kwh(Double s1n300Kwh) {
		this.s1n300Kwh = s1n300Kwh;
	}

	@Column(name = "S1N301_KWH", precision = 53, scale = 0)
	public Double getS1n301Kwh() {
		return this.s1n301Kwh;
	}

	public void setS1n301Kwh(Double s1n301Kwh) {
		this.s1n301Kwh = s1n301Kwh;
	}

	@Column(name = "S1N302_KWH", precision = 53, scale = 0)
	public Double getS1n302Kwh() {
		return this.s1n302Kwh;
	}

	public void setS1n302Kwh(Double s1n302Kwh) {
		this.s1n302Kwh = s1n302Kwh;
	}

	@Column(name = "S1N303_KWH", precision = 53, scale = 0)
	public Double getS1n303Kwh() {
		return this.s1n303Kwh;
	}

	public void setS1n303Kwh(Double s1n303Kwh) {
		this.s1n303Kwh = s1n303Kwh;
	}

	@Column(name = "S1N304_KWH", precision = 53, scale = 0)
	public Double getS1n304Kwh() {
		return this.s1n304Kwh;
	}

	public void setS1n304Kwh(Double s1n304Kwh) {
		this.s1n304Kwh = s1n304Kwh;
	}

	@Column(name = "S1N305_KWH", precision = 53, scale = 0)
	public Double getS1n305Kwh() {
		return this.s1n305Kwh;
	}

	public void setS1n305Kwh(Double s1n305Kwh) {
		this.s1n305Kwh = s1n305Kwh;
	}

	@Column(name = "S1N306_KWH", precision = 53, scale = 0)
	public Double getS1n306Kwh() {
		return this.s1n306Kwh;
	}

	public void setS1n306Kwh(Double s1n306Kwh) {
		this.s1n306Kwh = s1n306Kwh;
	}

	@Column(name = "S1N307_KWH", precision = 53, scale = 0)
	public Double getS1n307Kwh() {
		return this.s1n307Kwh;
	}

	public void setS1n307Kwh(Double s1n307Kwh) {
		this.s1n307Kwh = s1n307Kwh;
	}

	@Column(name = "S1N308_KWH", precision = 53, scale = 0)
	public Double getS1n308Kwh() {
		return this.s1n308Kwh;
	}

	public void setS1n308Kwh(Double s1n308Kwh) {
		this.s1n308Kwh = s1n308Kwh;
	}

	@Column(name = "S1N309_KWH", precision = 53, scale = 0)
	public Double getS1n309Kwh() {
		return this.s1n309Kwh;
	}

	public void setS1n309Kwh(Double s1n309Kwh) {
		this.s1n309Kwh = s1n309Kwh;
	}

	@Column(name = "S1N310_KWH", precision = 53, scale = 0)
	public Double getS1n310Kwh() {
		return this.s1n310Kwh;
	}

	public void setS1n310Kwh(Double s1n310Kwh) {
		this.s1n310Kwh = s1n310Kwh;
	}

	@Column(name = "S1N311_KWH", precision = 53, scale = 0)
	public Double getS1n311Kwh() {
		return this.s1n311Kwh;
	}

	public void setS1n311Kwh(Double s1n311Kwh) {
		this.s1n311Kwh = s1n311Kwh;
	}

	@Column(name = "S1N312_KWH", precision = 53, scale = 0)
	public Double getS1n312Kwh() {
		return this.s1n312Kwh;
	}

	public void setS1n312Kwh(Double s1n312Kwh) {
		this.s1n312Kwh = s1n312Kwh;
	}

	@Column(name = "S1N313_KWH", precision = 53, scale = 0)
	public Double getS1n313Kwh() {
		return this.s1n313Kwh;
	}

	public void setS1n313Kwh(Double s1n313Kwh) {
		this.s1n313Kwh = s1n313Kwh;
	}

	@Column(name = "S1N314_KWH", precision = 53, scale = 0)
	public Double getS1n314Kwh() {
		return this.s1n314Kwh;
	}

	public void setS1n314Kwh(Double s1n314Kwh) {
		this.s1n314Kwh = s1n314Kwh;
	}

	@Column(name = "S1N315_KWH", precision = 53, scale = 0)
	public Double getS1n315Kwh() {
		return this.s1n315Kwh;
	}

	public void setS1n315Kwh(Double s1n315Kwh) {
		this.s1n315Kwh = s1n315Kwh;
	}

	@Column(name = "S1N316_KWH", precision = 53, scale = 0)
	public Double getS1n316Kwh() {
		return this.s1n316Kwh;
	}

	public void setS1n316Kwh(Double s1n316Kwh) {
		this.s1n316Kwh = s1n316Kwh;
	}

	@Column(name = "S1N317_KWH", precision = 53, scale = 0)
	public Double getS1n317Kwh() {
		return this.s1n317Kwh;
	}

	public void setS1n317Kwh(Double s1n317Kwh) {
		this.s1n317Kwh = s1n317Kwh;
	}

	@Column(name = "S1N318_KWH", precision = 53, scale = 0)
	public Double getS1n318Kwh() {
		return this.s1n318Kwh;
	}

	public void setS1n318Kwh(Double s1n318Kwh) {
		this.s1n318Kwh = s1n318Kwh;
	}

	@Column(name = "S1N319_KWH", precision = 53, scale = 0)
	public Double getS1n319Kwh() {
		return this.s1n319Kwh;
	}

	public void setS1n319Kwh(Double s1n319Kwh) {
		this.s1n319Kwh = s1n319Kwh;
	}

	@Column(name = "S1N320_KWH", precision = 53, scale = 0)
	public Double getS1n320Kwh() {
		return this.s1n320Kwh;
	}

	public void setS1n320Kwh(Double s1n320Kwh) {
		this.s1n320Kwh = s1n320Kwh;
	}

	@Column(name = "S1N321_KWH", precision = 53, scale = 0)
	public Double getS1n321Kwh() {
		return this.s1n321Kwh;
	}

	public void setS1n321Kwh(Double s1n321Kwh) {
		this.s1n321Kwh = s1n321Kwh;
	}

	@Column(name = "S1N322_KWH", precision = 53, scale = 0)
	public Double getS1n322Kwh() {
		return this.s1n322Kwh;
	}

	public void setS1n322Kwh(Double s1n322Kwh) {
		this.s1n322Kwh = s1n322Kwh;
	}

	@Column(name = "S1N323_KWH", precision = 53, scale = 0)
	public Double getS1n323Kwh() {
		return this.s1n323Kwh;
	}

	public void setS1n323Kwh(Double s1n323Kwh) {
		this.s1n323Kwh = s1n323Kwh;
	}

	@Column(name = "S1N324_KWH", precision = 53, scale = 0)
	public Double getS1n324Kwh() {
		return this.s1n324Kwh;
	}

	public void setS1n324Kwh(Double s1n324Kwh) {
		this.s1n324Kwh = s1n324Kwh;
	}

	@Column(name = "S1N325_KWH", precision = 53, scale = 0)
	public Double getS1n325Kwh() {
		return this.s1n325Kwh;
	}

	public void setS1n325Kwh(Double s1n325Kwh) {
		this.s1n325Kwh = s1n325Kwh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tower_C_DG_kwh))
			return false;
		Tower_C_DG_kwh castOther = (Tower_C_DG_kwh) other;

		return ((this.getDatestr() == castOther.getDatestr()) || (this
				.getDatestr() != null && castOther.getDatestr() != null && this
				.getDatestr().equals(castOther.getDatestr())))
				&& ((this.getS1n282Kwh() == castOther.getS1n282Kwh()) || (this
						.getS1n282Kwh() != null
						&& castOther.getS1n282Kwh() != null && this
						.getS1n282Kwh().equals(castOther.getS1n282Kwh())))
				&& ((this.getS1n283Kwh() == castOther.getS1n283Kwh()) || (this
						.getS1n283Kwh() != null
						&& castOther.getS1n283Kwh() != null && this
						.getS1n283Kwh().equals(castOther.getS1n283Kwh())))
				&& ((this.getS1n284Kwh() == castOther.getS1n284Kwh()) || (this
						.getS1n284Kwh() != null
						&& castOther.getS1n284Kwh() != null && this
						.getS1n284Kwh().equals(castOther.getS1n284Kwh())))
				&& ((this.getS1n285Kwh() == castOther.getS1n285Kwh()) || (this
						.getS1n285Kwh() != null
						&& castOther.getS1n285Kwh() != null && this
						.getS1n285Kwh().equals(castOther.getS1n285Kwh())))
				&& ((this.getS1n286Kwh() == castOther.getS1n286Kwh()) || (this
						.getS1n286Kwh() != null
						&& castOther.getS1n286Kwh() != null && this
						.getS1n286Kwh().equals(castOther.getS1n286Kwh())))
				&& ((this.getS1n287Kwh() == castOther.getS1n287Kwh()) || (this
						.getS1n287Kwh() != null
						&& castOther.getS1n287Kwh() != null && this
						.getS1n287Kwh().equals(castOther.getS1n287Kwh())))
				&& ((this.getS1n288Kwh() == castOther.getS1n288Kwh()) || (this
						.getS1n288Kwh() != null
						&& castOther.getS1n288Kwh() != null && this
						.getS1n288Kwh().equals(castOther.getS1n288Kwh())))
				&& ((this.getS1n289Kwh() == castOther.getS1n289Kwh()) || (this
						.getS1n289Kwh() != null
						&& castOther.getS1n289Kwh() != null && this
						.getS1n289Kwh().equals(castOther.getS1n289Kwh())))
				&& ((this.getS1n290Kwh() == castOther.getS1n290Kwh()) || (this
						.getS1n290Kwh() != null
						&& castOther.getS1n290Kwh() != null && this
						.getS1n290Kwh().equals(castOther.getS1n290Kwh())))
				&& ((this.getS1n291Kwh() == castOther.getS1n291Kwh()) || (this
						.getS1n291Kwh() != null
						&& castOther.getS1n291Kwh() != null && this
						.getS1n291Kwh().equals(castOther.getS1n291Kwh())))
				&& ((this.getS1n292Kwh() == castOther.getS1n292Kwh()) || (this
						.getS1n292Kwh() != null
						&& castOther.getS1n292Kwh() != null && this
						.getS1n292Kwh().equals(castOther.getS1n292Kwh())))
				&& ((this.getS1n293Kwh() == castOther.getS1n293Kwh()) || (this
						.getS1n293Kwh() != null
						&& castOther.getS1n293Kwh() != null && this
						.getS1n293Kwh().equals(castOther.getS1n293Kwh())))
				&& ((this.getS1n294Kwh() == castOther.getS1n294Kwh()) || (this
						.getS1n294Kwh() != null
						&& castOther.getS1n294Kwh() != null && this
						.getS1n294Kwh().equals(castOther.getS1n294Kwh())))
				&& ((this.getS1n295Kwh() == castOther.getS1n295Kwh()) || (this
						.getS1n295Kwh() != null
						&& castOther.getS1n295Kwh() != null && this
						.getS1n295Kwh().equals(castOther.getS1n295Kwh())))
				&& ((this.getS1n296Kwh() == castOther.getS1n296Kwh()) || (this
						.getS1n296Kwh() != null
						&& castOther.getS1n296Kwh() != null && this
						.getS1n296Kwh().equals(castOther.getS1n296Kwh())))
				&& ((this.getS1n297Kwh() == castOther.getS1n297Kwh()) || (this
						.getS1n297Kwh() != null
						&& castOther.getS1n297Kwh() != null && this
						.getS1n297Kwh().equals(castOther.getS1n297Kwh())))
				&& ((this.getS1n298Kwh() == castOther.getS1n298Kwh()) || (this
						.getS1n298Kwh() != null
						&& castOther.getS1n298Kwh() != null && this
						.getS1n298Kwh().equals(castOther.getS1n298Kwh())))
				&& ((this.getS1n299Kwh() == castOther.getS1n299Kwh()) || (this
						.getS1n299Kwh() != null
						&& castOther.getS1n299Kwh() != null && this
						.getS1n299Kwh().equals(castOther.getS1n299Kwh())))
				&& ((this.getS1n300Kwh() == castOther.getS1n300Kwh()) || (this
						.getS1n300Kwh() != null
						&& castOther.getS1n300Kwh() != null && this
						.getS1n300Kwh().equals(castOther.getS1n300Kwh())))
				&& ((this.getS1n301Kwh() == castOther.getS1n301Kwh()) || (this
						.getS1n301Kwh() != null
						&& castOther.getS1n301Kwh() != null && this
						.getS1n301Kwh().equals(castOther.getS1n301Kwh())))
				&& ((this.getS1n302Kwh() == castOther.getS1n302Kwh()) || (this
						.getS1n302Kwh() != null
						&& castOther.getS1n302Kwh() != null && this
						.getS1n302Kwh().equals(castOther.getS1n302Kwh())))
				&& ((this.getS1n303Kwh() == castOther.getS1n303Kwh()) || (this
						.getS1n303Kwh() != null
						&& castOther.getS1n303Kwh() != null && this
						.getS1n303Kwh().equals(castOther.getS1n303Kwh())))
				&& ((this.getS1n304Kwh() == castOther.getS1n304Kwh()) || (this
						.getS1n304Kwh() != null
						&& castOther.getS1n304Kwh() != null && this
						.getS1n304Kwh().equals(castOther.getS1n304Kwh())))
				&& ((this.getS1n305Kwh() == castOther.getS1n305Kwh()) || (this
						.getS1n305Kwh() != null
						&& castOther.getS1n305Kwh() != null && this
						.getS1n305Kwh().equals(castOther.getS1n305Kwh())))
				&& ((this.getS1n306Kwh() == castOther.getS1n306Kwh()) || (this
						.getS1n306Kwh() != null
						&& castOther.getS1n306Kwh() != null && this
						.getS1n306Kwh().equals(castOther.getS1n306Kwh())))
				&& ((this.getS1n307Kwh() == castOther.getS1n307Kwh()) || (this
						.getS1n307Kwh() != null
						&& castOther.getS1n307Kwh() != null && this
						.getS1n307Kwh().equals(castOther.getS1n307Kwh())))
				&& ((this.getS1n308Kwh() == castOther.getS1n308Kwh()) || (this
						.getS1n308Kwh() != null
						&& castOther.getS1n308Kwh() != null && this
						.getS1n308Kwh().equals(castOther.getS1n308Kwh())))
				&& ((this.getS1n309Kwh() == castOther.getS1n309Kwh()) || (this
						.getS1n309Kwh() != null
						&& castOther.getS1n309Kwh() != null && this
						.getS1n309Kwh().equals(castOther.getS1n309Kwh())))
				&& ((this.getS1n310Kwh() == castOther.getS1n310Kwh()) || (this
						.getS1n310Kwh() != null
						&& castOther.getS1n310Kwh() != null && this
						.getS1n310Kwh().equals(castOther.getS1n310Kwh())))
				&& ((this.getS1n311Kwh() == castOther.getS1n311Kwh()) || (this
						.getS1n311Kwh() != null
						&& castOther.getS1n311Kwh() != null && this
						.getS1n311Kwh().equals(castOther.getS1n311Kwh())))
				&& ((this.getS1n312Kwh() == castOther.getS1n312Kwh()) || (this
						.getS1n312Kwh() != null
						&& castOther.getS1n312Kwh() != null && this
						.getS1n312Kwh().equals(castOther.getS1n312Kwh())))
				&& ((this.getS1n313Kwh() == castOther.getS1n313Kwh()) || (this
						.getS1n313Kwh() != null
						&& castOther.getS1n313Kwh() != null && this
						.getS1n313Kwh().equals(castOther.getS1n313Kwh())))
				&& ((this.getS1n314Kwh() == castOther.getS1n314Kwh()) || (this
						.getS1n314Kwh() != null
						&& castOther.getS1n314Kwh() != null && this
						.getS1n314Kwh().equals(castOther.getS1n314Kwh())))
				&& ((this.getS1n315Kwh() == castOther.getS1n315Kwh()) || (this
						.getS1n315Kwh() != null
						&& castOther.getS1n315Kwh() != null && this
						.getS1n315Kwh().equals(castOther.getS1n315Kwh())))
				&& ((this.getS1n316Kwh() == castOther.getS1n316Kwh()) || (this
						.getS1n316Kwh() != null
						&& castOther.getS1n316Kwh() != null && this
						.getS1n316Kwh().equals(castOther.getS1n316Kwh())))
				&& ((this.getS1n317Kwh() == castOther.getS1n317Kwh()) || (this
						.getS1n317Kwh() != null
						&& castOther.getS1n317Kwh() != null && this
						.getS1n317Kwh().equals(castOther.getS1n317Kwh())))
				&& ((this.getS1n318Kwh() == castOther.getS1n318Kwh()) || (this
						.getS1n318Kwh() != null
						&& castOther.getS1n318Kwh() != null && this
						.getS1n318Kwh().equals(castOther.getS1n318Kwh())))
				&& ((this.getS1n319Kwh() == castOther.getS1n319Kwh()) || (this
						.getS1n319Kwh() != null
						&& castOther.getS1n319Kwh() != null && this
						.getS1n319Kwh().equals(castOther.getS1n319Kwh())))
				&& ((this.getS1n320Kwh() == castOther.getS1n320Kwh()) || (this
						.getS1n320Kwh() != null
						&& castOther.getS1n320Kwh() != null && this
						.getS1n320Kwh().equals(castOther.getS1n320Kwh())))
				&& ((this.getS1n321Kwh() == castOther.getS1n321Kwh()) || (this
						.getS1n321Kwh() != null
						&& castOther.getS1n321Kwh() != null && this
						.getS1n321Kwh().equals(castOther.getS1n321Kwh())))
				&& ((this.getS1n322Kwh() == castOther.getS1n322Kwh()) || (this
						.getS1n322Kwh() != null
						&& castOther.getS1n322Kwh() != null && this
						.getS1n322Kwh().equals(castOther.getS1n322Kwh())))
				&& ((this.getS1n323Kwh() == castOther.getS1n323Kwh()) || (this
						.getS1n323Kwh() != null
						&& castOther.getS1n323Kwh() != null && this
						.getS1n323Kwh().equals(castOther.getS1n323Kwh())))
				&& ((this.getS1n324Kwh() == castOther.getS1n324Kwh()) || (this
						.getS1n324Kwh() != null
						&& castOther.getS1n324Kwh() != null && this
						.getS1n324Kwh().equals(castOther.getS1n324Kwh())))
				&& ((this.getS1n325Kwh() == castOther.getS1n325Kwh()) || (this
						.getS1n325Kwh() != null
						&& castOther.getS1n325Kwh() != null && this
						.getS1n325Kwh().equals(castOther.getS1n325Kwh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDatestr() == null ? 0 : this.getDatestr().hashCode());
		result = 37 * result
				+ (getS1n282Kwh() == null ? 0 : this.getS1n282Kwh().hashCode());
		result = 37 * result
				+ (getS1n283Kwh() == null ? 0 : this.getS1n283Kwh().hashCode());
		result = 37 * result
				+ (getS1n284Kwh() == null ? 0 : this.getS1n284Kwh().hashCode());
		result = 37 * result
				+ (getS1n285Kwh() == null ? 0 : this.getS1n285Kwh().hashCode());
		result = 37 * result
				+ (getS1n286Kwh() == null ? 0 : this.getS1n286Kwh().hashCode());
		result = 37 * result
				+ (getS1n287Kwh() == null ? 0 : this.getS1n287Kwh().hashCode());
		result = 37 * result
				+ (getS1n288Kwh() == null ? 0 : this.getS1n288Kwh().hashCode());
		result = 37 * result
				+ (getS1n289Kwh() == null ? 0 : this.getS1n289Kwh().hashCode());
		result = 37 * result
				+ (getS1n290Kwh() == null ? 0 : this.getS1n290Kwh().hashCode());
		result = 37 * result
				+ (getS1n291Kwh() == null ? 0 : this.getS1n291Kwh().hashCode());
		result = 37 * result
				+ (getS1n292Kwh() == null ? 0 : this.getS1n292Kwh().hashCode());
		result = 37 * result
				+ (getS1n293Kwh() == null ? 0 : this.getS1n293Kwh().hashCode());
		result = 37 * result
				+ (getS1n294Kwh() == null ? 0 : this.getS1n294Kwh().hashCode());
		result = 37 * result
				+ (getS1n295Kwh() == null ? 0 : this.getS1n295Kwh().hashCode());
		result = 37 * result
				+ (getS1n296Kwh() == null ? 0 : this.getS1n296Kwh().hashCode());
		result = 37 * result
				+ (getS1n297Kwh() == null ? 0 : this.getS1n297Kwh().hashCode());
		result = 37 * result
				+ (getS1n298Kwh() == null ? 0 : this.getS1n298Kwh().hashCode());
		result = 37 * result
				+ (getS1n299Kwh() == null ? 0 : this.getS1n299Kwh().hashCode());
		result = 37 * result
				+ (getS1n300Kwh() == null ? 0 : this.getS1n300Kwh().hashCode());
		result = 37 * result
				+ (getS1n301Kwh() == null ? 0 : this.getS1n301Kwh().hashCode());
		result = 37 * result
				+ (getS1n302Kwh() == null ? 0 : this.getS1n302Kwh().hashCode());
		result = 37 * result
				+ (getS1n303Kwh() == null ? 0 : this.getS1n303Kwh().hashCode());
		result = 37 * result
				+ (getS1n304Kwh() == null ? 0 : this.getS1n304Kwh().hashCode());
		result = 37 * result
				+ (getS1n305Kwh() == null ? 0 : this.getS1n305Kwh().hashCode());
		result = 37 * result
				+ (getS1n306Kwh() == null ? 0 : this.getS1n306Kwh().hashCode());
		result = 37 * result
				+ (getS1n307Kwh() == null ? 0 : this.getS1n307Kwh().hashCode());
		result = 37 * result
				+ (getS1n308Kwh() == null ? 0 : this.getS1n308Kwh().hashCode());
		result = 37 * result
				+ (getS1n309Kwh() == null ? 0 : this.getS1n309Kwh().hashCode());
		result = 37 * result
				+ (getS1n310Kwh() == null ? 0 : this.getS1n310Kwh().hashCode());
		result = 37 * result
				+ (getS1n311Kwh() == null ? 0 : this.getS1n311Kwh().hashCode());
		result = 37 * result
				+ (getS1n312Kwh() == null ? 0 : this.getS1n312Kwh().hashCode());
		result = 37 * result
				+ (getS1n313Kwh() == null ? 0 : this.getS1n313Kwh().hashCode());
		result = 37 * result
				+ (getS1n314Kwh() == null ? 0 : this.getS1n314Kwh().hashCode());
		result = 37 * result
				+ (getS1n315Kwh() == null ? 0 : this.getS1n315Kwh().hashCode());
		result = 37 * result
				+ (getS1n316Kwh() == null ? 0 : this.getS1n316Kwh().hashCode());
		result = 37 * result
				+ (getS1n317Kwh() == null ? 0 : this.getS1n317Kwh().hashCode());
		result = 37 * result
				+ (getS1n318Kwh() == null ? 0 : this.getS1n318Kwh().hashCode());
		result = 37 * result
				+ (getS1n319Kwh() == null ? 0 : this.getS1n319Kwh().hashCode());
		result = 37 * result
				+ (getS1n320Kwh() == null ? 0 : this.getS1n320Kwh().hashCode());
		result = 37 * result
				+ (getS1n321Kwh() == null ? 0 : this.getS1n321Kwh().hashCode());
		result = 37 * result
				+ (getS1n322Kwh() == null ? 0 : this.getS1n322Kwh().hashCode());
		result = 37 * result
				+ (getS1n323Kwh() == null ? 0 : this.getS1n323Kwh().hashCode());
		result = 37 * result
				+ (getS1n324Kwh() == null ? 0 : this.getS1n324Kwh().hashCode());
		result = 37 * result
				+ (getS1n325Kwh() == null ? 0 : this.getS1n325Kwh().hashCode());
		return result;
	}

}
