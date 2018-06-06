package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tower_A_DG_kwh",schema="dbo",catalog="EMS")
public class Tower_A_DG_kwh {


	// Fields

	private Timestamp datestr;
	private Double s1n238Kwh;
	private Double s1n239Kwh;
	private Double s1n240Kwh;
	private Double s1n241Kwh;
	private Double s1n242Kwh;
	private Double s1n243Kwh;
	private Double s1n244Kwh;
	private Double s1n245Kwh;
	private Double s1n246Kwh;
	private Double s1n247Kwh;
	private Double s1n248Kwh;
	private Double s1n249Kwh;
	private Double s1n250Kwh;
	private Double s1n251Kwh;
	private Double s1n252Kwh;
	private Double s1n253Kwh;
	private Double s1n254Kwh;
	private Double s1n255Kwh;
	private Double s1n256Kwh;
	private Double s1n257Kwh;
	private Double s1n258Kwh;
	private Double s1n259Kwh;
	private Double s1n260Kwh;
	private Double s1n261Kwh;
	private Double s1n262Kwh;
	private Double s1n263Kwh;
	private Double s1n264Kwh;
	private Double s1n265Kwh;
	private Double s1n266Kwh;
	private Double s1n267Kwh;
	private Double s1n268Kwh;
	private Double s1n269Kwh;
	private Double s1n270Kwh;
	private Double s1n271Kwh;
	private Double s1n272Kwh;
	private Double s1n273Kwh;
	private Double s1n274Kwh;
	private Double s1n275Kwh;
	private Double s1n276Kwh;
	private Double s1n277Kwh;
	private Double s1n278Kwh;
	private Double s1n279Kwh;
	private Double s1n280Kwh;
	private Double s1n281Kwh;

	// Constructors

	/** default constructor */
	public Tower_A_DG_kwh() {
	}

	/** full constructor */
	public Tower_A_DG_kwh(Timestamp datestr, Double s1n238Kwh, Double s1n239Kwh,
			Double s1n240Kwh, Double s1n241Kwh, Double s1n242Kwh,
			Double s1n243Kwh, Double s1n244Kwh, Double s1n245Kwh,
			Double s1n246Kwh, Double s1n247Kwh, Double s1n248Kwh,
			Double s1n249Kwh, Double s1n250Kwh, Double s1n251Kwh,
			Double s1n252Kwh, Double s1n253Kwh, Double s1n254Kwh,
			Double s1n255Kwh, Double s1n256Kwh, Double s1n257Kwh,
			Double s1n258Kwh, Double s1n259Kwh, Double s1n260Kwh,
			Double s1n261Kwh, Double s1n262Kwh, Double s1n263Kwh,
			Double s1n264Kwh, Double s1n265Kwh, Double s1n266Kwh,
			Double s1n267Kwh, Double s1n268Kwh, Double s1n269Kwh,
			Double s1n270Kwh, Double s1n271Kwh, Double s1n272Kwh,
			Double s1n273Kwh, Double s1n274Kwh, Double s1n275Kwh,
			Double s1n276Kwh, Double s1n277Kwh, Double s1n278Kwh,
			Double s1n279Kwh, Double s1n280Kwh, Double s1n281Kwh) {
		this.datestr = datestr;
		this.s1n238Kwh = s1n238Kwh;
		this.s1n239Kwh = s1n239Kwh;
		this.s1n240Kwh = s1n240Kwh;
		this.s1n241Kwh = s1n241Kwh;
		this.s1n242Kwh = s1n242Kwh;
		this.s1n243Kwh = s1n243Kwh;
		this.s1n244Kwh = s1n244Kwh;
		this.s1n245Kwh = s1n245Kwh;
		this.s1n246Kwh = s1n246Kwh;
		this.s1n247Kwh = s1n247Kwh;
		this.s1n248Kwh = s1n248Kwh;
		this.s1n249Kwh = s1n249Kwh;
		this.s1n250Kwh = s1n250Kwh;
		this.s1n251Kwh = s1n251Kwh;
		this.s1n252Kwh = s1n252Kwh;
		this.s1n253Kwh = s1n253Kwh;
		this.s1n254Kwh = s1n254Kwh;
		this.s1n255Kwh = s1n255Kwh;
		this.s1n256Kwh = s1n256Kwh;
		this.s1n257Kwh = s1n257Kwh;
		this.s1n258Kwh = s1n258Kwh;
		this.s1n259Kwh = s1n259Kwh;
		this.s1n260Kwh = s1n260Kwh;
		this.s1n261Kwh = s1n261Kwh;
		this.s1n262Kwh = s1n262Kwh;
		this.s1n263Kwh = s1n263Kwh;
		this.s1n264Kwh = s1n264Kwh;
		this.s1n265Kwh = s1n265Kwh;
		this.s1n266Kwh = s1n266Kwh;
		this.s1n267Kwh = s1n267Kwh;
		this.s1n268Kwh = s1n268Kwh;
		this.s1n269Kwh = s1n269Kwh;
		this.s1n270Kwh = s1n270Kwh;
		this.s1n271Kwh = s1n271Kwh;
		this.s1n272Kwh = s1n272Kwh;
		this.s1n273Kwh = s1n273Kwh;
		this.s1n274Kwh = s1n274Kwh;
		this.s1n275Kwh = s1n275Kwh;
		this.s1n276Kwh = s1n276Kwh;
		this.s1n277Kwh = s1n277Kwh;
		this.s1n278Kwh = s1n278Kwh;
		this.s1n279Kwh = s1n279Kwh;
		this.s1n280Kwh = s1n280Kwh;
		this.s1n281Kwh = s1n281Kwh;
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

	@Column(name = "S1N238_KWH", precision = 53, scale = 0)
	public Double getS1n238Kwh() {
		return this.s1n238Kwh;
	}

	public void setS1n238Kwh(Double s1n238Kwh) {
		this.s1n238Kwh = s1n238Kwh;
	}

	@Column(name = "S1N239_KWH", precision = 53, scale = 0)
	public Double getS1n239Kwh() {
		return this.s1n239Kwh;
	}

	public void setS1n239Kwh(Double s1n239Kwh) {
		this.s1n239Kwh = s1n239Kwh;
	}

	@Column(name = "S1N240_KWH", precision = 53, scale = 0)
	public Double getS1n240Kwh() {
		return this.s1n240Kwh;
	}

	public void setS1n240Kwh(Double s1n240Kwh) {
		this.s1n240Kwh = s1n240Kwh;
	}

	@Column(name = "S1N241_KWH", precision = 53, scale = 0)
	public Double getS1n241Kwh() {
		return this.s1n241Kwh;
	}

	public void setS1n241Kwh(Double s1n241Kwh) {
		this.s1n241Kwh = s1n241Kwh;
	}

	@Column(name = "S1N242_KWH", precision = 53, scale = 0)
	public Double getS1n242Kwh() {
		return this.s1n242Kwh;
	}

	public void setS1n242Kwh(Double s1n242Kwh) {
		this.s1n242Kwh = s1n242Kwh;
	}

	@Column(name = "S1N243_KWH", precision = 53, scale = 0)
	public Double getS1n243Kwh() {
		return this.s1n243Kwh;
	}

	public void setS1n243Kwh(Double s1n243Kwh) {
		this.s1n243Kwh = s1n243Kwh;
	}

	@Column(name = "S1N244_KWH", precision = 53, scale = 0)
	public Double getS1n244Kwh() {
		return this.s1n244Kwh;
	}

	public void setS1n244Kwh(Double s1n244Kwh) {
		this.s1n244Kwh = s1n244Kwh;
	}

	@Column(name = "S1N245_KWH", precision = 53, scale = 0)
	public Double getS1n245Kwh() {
		return this.s1n245Kwh;
	}

	public void setS1n245Kwh(Double s1n245Kwh) {
		this.s1n245Kwh = s1n245Kwh;
	}

	@Column(name = "S1N246_KWH", precision = 53, scale = 0)
	public Double getS1n246Kwh() {
		return this.s1n246Kwh;
	}

	public void setS1n246Kwh(Double s1n246Kwh) {
		this.s1n246Kwh = s1n246Kwh;
	}

	@Column(name = "S1N247_KWH", precision = 53, scale = 0)
	public Double getS1n247Kwh() {
		return this.s1n247Kwh;
	}

	public void setS1n247Kwh(Double s1n247Kwh) {
		this.s1n247Kwh = s1n247Kwh;
	}

	@Column(name = "S1N248_KWH", precision = 53, scale = 0)
	public Double getS1n248Kwh() {
		return this.s1n248Kwh;
	}

	public void setS1n248Kwh(Double s1n248Kwh) {
		this.s1n248Kwh = s1n248Kwh;
	}

	@Column(name = "S1N249_KWH", precision = 53, scale = 0)
	public Double getS1n249Kwh() {
		return this.s1n249Kwh;
	}

	public void setS1n249Kwh(Double s1n249Kwh) {
		this.s1n249Kwh = s1n249Kwh;
	}

	@Column(name = "S1N250_KWH", precision = 53, scale = 0)
	public Double getS1n250Kwh() {
		return this.s1n250Kwh;
	}

	public void setS1n250Kwh(Double s1n250Kwh) {
		this.s1n250Kwh = s1n250Kwh;
	}

	@Column(name = "S1N251_KWH", precision = 53, scale = 0)
	public Double getS1n251Kwh() {
		return this.s1n251Kwh;
	}

	public void setS1n251Kwh(Double s1n251Kwh) {
		this.s1n251Kwh = s1n251Kwh;
	}

	@Column(name = "S1N252_KWH", precision = 53, scale = 0)
	public Double getS1n252Kwh() {
		return this.s1n252Kwh;
	}

	public void setS1n252Kwh(Double s1n252Kwh) {
		this.s1n252Kwh = s1n252Kwh;
	}

	@Column(name = "S1N253_KWH", precision = 53, scale = 0)
	public Double getS1n253Kwh() {
		return this.s1n253Kwh;
	}

	public void setS1n253Kwh(Double s1n253Kwh) {
		this.s1n253Kwh = s1n253Kwh;
	}

	@Column(name = "S1N254_KWH", precision = 53, scale = 0)
	public Double getS1n254Kwh() {
		return this.s1n254Kwh;
	}

	public void setS1n254Kwh(Double s1n254Kwh) {
		this.s1n254Kwh = s1n254Kwh;
	}

	@Column(name = "S1N255_KWH", precision = 53, scale = 0)
	public Double getS1n255Kwh() {
		return this.s1n255Kwh;
	}

	public void setS1n255Kwh(Double s1n255Kwh) {
		this.s1n255Kwh = s1n255Kwh;
	}

	@Column(name = "S1N256_KWH", precision = 53, scale = 0)
	public Double getS1n256Kwh() {
		return this.s1n256Kwh;
	}

	public void setS1n256Kwh(Double s1n256Kwh) {
		this.s1n256Kwh = s1n256Kwh;
	}

	@Column(name = "S1N257_KWH", precision = 53, scale = 0)
	public Double getS1n257Kwh() {
		return this.s1n257Kwh;
	}

	public void setS1n257Kwh(Double s1n257Kwh) {
		this.s1n257Kwh = s1n257Kwh;
	}

	@Column(name = "S1N258_KWH", precision = 53, scale = 0)
	public Double getS1n258Kwh() {
		return this.s1n258Kwh;
	}

	public void setS1n258Kwh(Double s1n258Kwh) {
		this.s1n258Kwh = s1n258Kwh;
	}

	@Column(name = "S1N259_KWH", precision = 53, scale = 0)
	public Double getS1n259Kwh() {
		return this.s1n259Kwh;
	}

	public void setS1n259Kwh(Double s1n259Kwh) {
		this.s1n259Kwh = s1n259Kwh;
	}

	@Column(name = "S1N260_KWH", precision = 53, scale = 0)
	public Double getS1n260Kwh() {
		return this.s1n260Kwh;
	}

	public void setS1n260Kwh(Double s1n260Kwh) {
		this.s1n260Kwh = s1n260Kwh;
	}

	@Column(name = "S1N261_KWH", precision = 53, scale = 0)
	public Double getS1n261Kwh() {
		return this.s1n261Kwh;
	}

	public void setS1n261Kwh(Double s1n261Kwh) {
		this.s1n261Kwh = s1n261Kwh;
	}

	@Column(name = "S1N262_KWH", precision = 53, scale = 0)
	public Double getS1n262Kwh() {
		return this.s1n262Kwh;
	}

	public void setS1n262Kwh(Double s1n262Kwh) {
		this.s1n262Kwh = s1n262Kwh;
	}

	@Column(name = "S1N263_KWH", precision = 53, scale = 0)
	public Double getS1n263Kwh() {
		return this.s1n263Kwh;
	}

	public void setS1n263Kwh(Double s1n263Kwh) {
		this.s1n263Kwh = s1n263Kwh;
	}

	@Column(name = "S1N264_KWH", precision = 53, scale = 0)
	public Double getS1n264Kwh() {
		return this.s1n264Kwh;
	}

	public void setS1n264Kwh(Double s1n264Kwh) {
		this.s1n264Kwh = s1n264Kwh;
	}

	@Column(name = "S1N265_KWH", precision = 53, scale = 0)
	public Double getS1n265Kwh() {
		return this.s1n265Kwh;
	}

	public void setS1n265Kwh(Double s1n265Kwh) {
		this.s1n265Kwh = s1n265Kwh;
	}

	@Column(name = "S1N266_KWH", precision = 53, scale = 0)
	public Double getS1n266Kwh() {
		return this.s1n266Kwh;
	}

	public void setS1n266Kwh(Double s1n266Kwh) {
		this.s1n266Kwh = s1n266Kwh;
	}

	@Column(name = "S1N267_KWH", precision = 53, scale = 0)
	public Double getS1n267Kwh() {
		return this.s1n267Kwh;
	}

	public void setS1n267Kwh(Double s1n267Kwh) {
		this.s1n267Kwh = s1n267Kwh;
	}

	@Column(name = "S1N268_KWH", precision = 53, scale = 0)
	public Double getS1n268Kwh() {
		return this.s1n268Kwh;
	}

	public void setS1n268Kwh(Double s1n268Kwh) {
		this.s1n268Kwh = s1n268Kwh;
	}

	@Column(name = "S1N269_KWH", precision = 53, scale = 0)
	public Double getS1n269Kwh() {
		return this.s1n269Kwh;
	}

	public void setS1n269Kwh(Double s1n269Kwh) {
		this.s1n269Kwh = s1n269Kwh;
	}

	@Column(name = "S1N270_KWH", precision = 53, scale = 0)
	public Double getS1n270Kwh() {
		return this.s1n270Kwh;
	}

	public void setS1n270Kwh(Double s1n270Kwh) {
		this.s1n270Kwh = s1n270Kwh;
	}

	@Column(name = "S1N271_KWH", precision = 53, scale = 0)
	public Double getS1n271Kwh() {
		return this.s1n271Kwh;
	}

	public void setS1n271Kwh(Double s1n271Kwh) {
		this.s1n271Kwh = s1n271Kwh;
	}

	@Column(name = "S1N272_KWH", precision = 53, scale = 0)
	public Double getS1n272Kwh() {
		return this.s1n272Kwh;
	}

	public void setS1n272Kwh(Double s1n272Kwh) {
		this.s1n272Kwh = s1n272Kwh;
	}

	@Column(name = "S1N273_KWH", precision = 53, scale = 0)
	public Double getS1n273Kwh() {
		return this.s1n273Kwh;
	}

	public void setS1n273Kwh(Double s1n273Kwh) {
		this.s1n273Kwh = s1n273Kwh;
	}

	@Column(name = "S1N274_KWH", precision = 53, scale = 0)
	public Double getS1n274Kwh() {
		return this.s1n274Kwh;
	}

	public void setS1n274Kwh(Double s1n274Kwh) {
		this.s1n274Kwh = s1n274Kwh;
	}

	@Column(name = "S1N275_KWH", precision = 53, scale = 0)
	public Double getS1n275Kwh() {
		return this.s1n275Kwh;
	}

	public void setS1n275Kwh(Double s1n275Kwh) {
		this.s1n275Kwh = s1n275Kwh;
	}

	@Column(name = "S1N276_KWH", precision = 53, scale = 0)
	public Double getS1n276Kwh() {
		return this.s1n276Kwh;
	}

	public void setS1n276Kwh(Double s1n276Kwh) {
		this.s1n276Kwh = s1n276Kwh;
	}

	@Column(name = "S1N277_KWH", precision = 53, scale = 0)
	public Double getS1n277Kwh() {
		return this.s1n277Kwh;
	}

	public void setS1n277Kwh(Double s1n277Kwh) {
		this.s1n277Kwh = s1n277Kwh;
	}

	@Column(name = "S1N278_KWH", precision = 53, scale = 0)
	public Double getS1n278Kwh() {
		return this.s1n278Kwh;
	}

	public void setS1n278Kwh(Double s1n278Kwh) {
		this.s1n278Kwh = s1n278Kwh;
	}

	@Column(name = "S1N279_KWH", precision = 53, scale = 0)
	public Double getS1n279Kwh() {
		return this.s1n279Kwh;
	}

	public void setS1n279Kwh(Double s1n279Kwh) {
		this.s1n279Kwh = s1n279Kwh;
	}

	@Column(name = "S1N280_KWH", precision = 53, scale = 0)
	public Double getS1n280Kwh() {
		return this.s1n280Kwh;
	}

	public void setS1n280Kwh(Double s1n280Kwh) {
		this.s1n280Kwh = s1n280Kwh;
	}

	@Column(name = "S1N281_KWH", precision = 53, scale = 0)
	public Double getS1n281Kwh() {
		return this.s1n281Kwh;
	}

	public void setS1n281Kwh(Double s1n281Kwh) {
		this.s1n281Kwh = s1n281Kwh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tower_A_DG_kwh))
			return false;
		Tower_A_DG_kwh castOther = (Tower_A_DG_kwh) other;

		return ((this.getDatestr() == castOther.getDatestr()) || (this
				.getDatestr() != null && castOther.getDatestr() != null && this
				.getDatestr().equals(castOther.getDatestr())))
				&& ((this.getS1n238Kwh() == castOther.getS1n238Kwh()) || (this
						.getS1n238Kwh() != null
						&& castOther.getS1n238Kwh() != null && this
						.getS1n238Kwh().equals(castOther.getS1n238Kwh())))
				&& ((this.getS1n239Kwh() == castOther.getS1n239Kwh()) || (this
						.getS1n239Kwh() != null
						&& castOther.getS1n239Kwh() != null && this
						.getS1n239Kwh().equals(castOther.getS1n239Kwh())))
				&& ((this.getS1n240Kwh() == castOther.getS1n240Kwh()) || (this
						.getS1n240Kwh() != null
						&& castOther.getS1n240Kwh() != null && this
						.getS1n240Kwh().equals(castOther.getS1n240Kwh())))
				&& ((this.getS1n241Kwh() == castOther.getS1n241Kwh()) || (this
						.getS1n241Kwh() != null
						&& castOther.getS1n241Kwh() != null && this
						.getS1n241Kwh().equals(castOther.getS1n241Kwh())))
				&& ((this.getS1n242Kwh() == castOther.getS1n242Kwh()) || (this
						.getS1n242Kwh() != null
						&& castOther.getS1n242Kwh() != null && this
						.getS1n242Kwh().equals(castOther.getS1n242Kwh())))
				&& ((this.getS1n243Kwh() == castOther.getS1n243Kwh()) || (this
						.getS1n243Kwh() != null
						&& castOther.getS1n243Kwh() != null && this
						.getS1n243Kwh().equals(castOther.getS1n243Kwh())))
				&& ((this.getS1n244Kwh() == castOther.getS1n244Kwh()) || (this
						.getS1n244Kwh() != null
						&& castOther.getS1n244Kwh() != null && this
						.getS1n244Kwh().equals(castOther.getS1n244Kwh())))
				&& ((this.getS1n245Kwh() == castOther.getS1n245Kwh()) || (this
						.getS1n245Kwh() != null
						&& castOther.getS1n245Kwh() != null && this
						.getS1n245Kwh().equals(castOther.getS1n245Kwh())))
				&& ((this.getS1n246Kwh() == castOther.getS1n246Kwh()) || (this
						.getS1n246Kwh() != null
						&& castOther.getS1n246Kwh() != null && this
						.getS1n246Kwh().equals(castOther.getS1n246Kwh())))
				&& ((this.getS1n247Kwh() == castOther.getS1n247Kwh()) || (this
						.getS1n247Kwh() != null
						&& castOther.getS1n247Kwh() != null && this
						.getS1n247Kwh().equals(castOther.getS1n247Kwh())))
				&& ((this.getS1n248Kwh() == castOther.getS1n248Kwh()) || (this
						.getS1n248Kwh() != null
						&& castOther.getS1n248Kwh() != null && this
						.getS1n248Kwh().equals(castOther.getS1n248Kwh())))
				&& ((this.getS1n249Kwh() == castOther.getS1n249Kwh()) || (this
						.getS1n249Kwh() != null
						&& castOther.getS1n249Kwh() != null && this
						.getS1n249Kwh().equals(castOther.getS1n249Kwh())))
				&& ((this.getS1n250Kwh() == castOther.getS1n250Kwh()) || (this
						.getS1n250Kwh() != null
						&& castOther.getS1n250Kwh() != null && this
						.getS1n250Kwh().equals(castOther.getS1n250Kwh())))
				&& ((this.getS1n251Kwh() == castOther.getS1n251Kwh()) || (this
						.getS1n251Kwh() != null
						&& castOther.getS1n251Kwh() != null && this
						.getS1n251Kwh().equals(castOther.getS1n251Kwh())))
				&& ((this.getS1n252Kwh() == castOther.getS1n252Kwh()) || (this
						.getS1n252Kwh() != null
						&& castOther.getS1n252Kwh() != null && this
						.getS1n252Kwh().equals(castOther.getS1n252Kwh())))
				&& ((this.getS1n253Kwh() == castOther.getS1n253Kwh()) || (this
						.getS1n253Kwh() != null
						&& castOther.getS1n253Kwh() != null && this
						.getS1n253Kwh().equals(castOther.getS1n253Kwh())))
				&& ((this.getS1n254Kwh() == castOther.getS1n254Kwh()) || (this
						.getS1n254Kwh() != null
						&& castOther.getS1n254Kwh() != null && this
						.getS1n254Kwh().equals(castOther.getS1n254Kwh())))
				&& ((this.getS1n255Kwh() == castOther.getS1n255Kwh()) || (this
						.getS1n255Kwh() != null
						&& castOther.getS1n255Kwh() != null && this
						.getS1n255Kwh().equals(castOther.getS1n255Kwh())))
				&& ((this.getS1n256Kwh() == castOther.getS1n256Kwh()) || (this
						.getS1n256Kwh() != null
						&& castOther.getS1n256Kwh() != null && this
						.getS1n256Kwh().equals(castOther.getS1n256Kwh())))
				&& ((this.getS1n257Kwh() == castOther.getS1n257Kwh()) || (this
						.getS1n257Kwh() != null
						&& castOther.getS1n257Kwh() != null && this
						.getS1n257Kwh().equals(castOther.getS1n257Kwh())))
				&& ((this.getS1n258Kwh() == castOther.getS1n258Kwh()) || (this
						.getS1n258Kwh() != null
						&& castOther.getS1n258Kwh() != null && this
						.getS1n258Kwh().equals(castOther.getS1n258Kwh())))
				&& ((this.getS1n259Kwh() == castOther.getS1n259Kwh()) || (this
						.getS1n259Kwh() != null
						&& castOther.getS1n259Kwh() != null && this
						.getS1n259Kwh().equals(castOther.getS1n259Kwh())))
				&& ((this.getS1n260Kwh() == castOther.getS1n260Kwh()) || (this
						.getS1n260Kwh() != null
						&& castOther.getS1n260Kwh() != null && this
						.getS1n260Kwh().equals(castOther.getS1n260Kwh())))
				&& ((this.getS1n261Kwh() == castOther.getS1n261Kwh()) || (this
						.getS1n261Kwh() != null
						&& castOther.getS1n261Kwh() != null && this
						.getS1n261Kwh().equals(castOther.getS1n261Kwh())))
				&& ((this.getS1n262Kwh() == castOther.getS1n262Kwh()) || (this
						.getS1n262Kwh() != null
						&& castOther.getS1n262Kwh() != null && this
						.getS1n262Kwh().equals(castOther.getS1n262Kwh())))
				&& ((this.getS1n263Kwh() == castOther.getS1n263Kwh()) || (this
						.getS1n263Kwh() != null
						&& castOther.getS1n263Kwh() != null && this
						.getS1n263Kwh().equals(castOther.getS1n263Kwh())))
				&& ((this.getS1n264Kwh() == castOther.getS1n264Kwh()) || (this
						.getS1n264Kwh() != null
						&& castOther.getS1n264Kwh() != null && this
						.getS1n264Kwh().equals(castOther.getS1n264Kwh())))
				&& ((this.getS1n265Kwh() == castOther.getS1n265Kwh()) || (this
						.getS1n265Kwh() != null
						&& castOther.getS1n265Kwh() != null && this
						.getS1n265Kwh().equals(castOther.getS1n265Kwh())))
				&& ((this.getS1n266Kwh() == castOther.getS1n266Kwh()) || (this
						.getS1n266Kwh() != null
						&& castOther.getS1n266Kwh() != null && this
						.getS1n266Kwh().equals(castOther.getS1n266Kwh())))
				&& ((this.getS1n267Kwh() == castOther.getS1n267Kwh()) || (this
						.getS1n267Kwh() != null
						&& castOther.getS1n267Kwh() != null && this
						.getS1n267Kwh().equals(castOther.getS1n267Kwh())))
				&& ((this.getS1n268Kwh() == castOther.getS1n268Kwh()) || (this
						.getS1n268Kwh() != null
						&& castOther.getS1n268Kwh() != null && this
						.getS1n268Kwh().equals(castOther.getS1n268Kwh())))
				&& ((this.getS1n269Kwh() == castOther.getS1n269Kwh()) || (this
						.getS1n269Kwh() != null
						&& castOther.getS1n269Kwh() != null && this
						.getS1n269Kwh().equals(castOther.getS1n269Kwh())))
				&& ((this.getS1n270Kwh() == castOther.getS1n270Kwh()) || (this
						.getS1n270Kwh() != null
						&& castOther.getS1n270Kwh() != null && this
						.getS1n270Kwh().equals(castOther.getS1n270Kwh())))
				&& ((this.getS1n271Kwh() == castOther.getS1n271Kwh()) || (this
						.getS1n271Kwh() != null
						&& castOther.getS1n271Kwh() != null && this
						.getS1n271Kwh().equals(castOther.getS1n271Kwh())))
				&& ((this.getS1n272Kwh() == castOther.getS1n272Kwh()) || (this
						.getS1n272Kwh() != null
						&& castOther.getS1n272Kwh() != null && this
						.getS1n272Kwh().equals(castOther.getS1n272Kwh())))
				&& ((this.getS1n273Kwh() == castOther.getS1n273Kwh()) || (this
						.getS1n273Kwh() != null
						&& castOther.getS1n273Kwh() != null && this
						.getS1n273Kwh().equals(castOther.getS1n273Kwh())))
				&& ((this.getS1n274Kwh() == castOther.getS1n274Kwh()) || (this
						.getS1n274Kwh() != null
						&& castOther.getS1n274Kwh() != null && this
						.getS1n274Kwh().equals(castOther.getS1n274Kwh())))
				&& ((this.getS1n275Kwh() == castOther.getS1n275Kwh()) || (this
						.getS1n275Kwh() != null
						&& castOther.getS1n275Kwh() != null && this
						.getS1n275Kwh().equals(castOther.getS1n275Kwh())))
				&& ((this.getS1n276Kwh() == castOther.getS1n276Kwh()) || (this
						.getS1n276Kwh() != null
						&& castOther.getS1n276Kwh() != null && this
						.getS1n276Kwh().equals(castOther.getS1n276Kwh())))
				&& ((this.getS1n277Kwh() == castOther.getS1n277Kwh()) || (this
						.getS1n277Kwh() != null
						&& castOther.getS1n277Kwh() != null && this
						.getS1n277Kwh().equals(castOther.getS1n277Kwh())))
				&& ((this.getS1n278Kwh() == castOther.getS1n278Kwh()) || (this
						.getS1n278Kwh() != null
						&& castOther.getS1n278Kwh() != null && this
						.getS1n278Kwh().equals(castOther.getS1n278Kwh())))
				&& ((this.getS1n279Kwh() == castOther.getS1n279Kwh()) || (this
						.getS1n279Kwh() != null
						&& castOther.getS1n279Kwh() != null && this
						.getS1n279Kwh().equals(castOther.getS1n279Kwh())))
				&& ((this.getS1n280Kwh() == castOther.getS1n280Kwh()) || (this
						.getS1n280Kwh() != null
						&& castOther.getS1n280Kwh() != null && this
						.getS1n280Kwh().equals(castOther.getS1n280Kwh())))
				&& ((this.getS1n281Kwh() == castOther.getS1n281Kwh()) || (this
						.getS1n281Kwh() != null
						&& castOther.getS1n281Kwh() != null && this
						.getS1n281Kwh().equals(castOther.getS1n281Kwh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDatestr() == null ? 0 : this.getDatestr().hashCode());
		result = 37 * result
				+ (getS1n238Kwh() == null ? 0 : this.getS1n238Kwh().hashCode());
		result = 37 * result
				+ (getS1n239Kwh() == null ? 0 : this.getS1n239Kwh().hashCode());
		result = 37 * result
				+ (getS1n240Kwh() == null ? 0 : this.getS1n240Kwh().hashCode());
		result = 37 * result
				+ (getS1n241Kwh() == null ? 0 : this.getS1n241Kwh().hashCode());
		result = 37 * result
				+ (getS1n242Kwh() == null ? 0 : this.getS1n242Kwh().hashCode());
		result = 37 * result
				+ (getS1n243Kwh() == null ? 0 : this.getS1n243Kwh().hashCode());
		result = 37 * result
				+ (getS1n244Kwh() == null ? 0 : this.getS1n244Kwh().hashCode());
		result = 37 * result
				+ (getS1n245Kwh() == null ? 0 : this.getS1n245Kwh().hashCode());
		result = 37 * result
				+ (getS1n246Kwh() == null ? 0 : this.getS1n246Kwh().hashCode());
		result = 37 * result
				+ (getS1n247Kwh() == null ? 0 : this.getS1n247Kwh().hashCode());
		result = 37 * result
				+ (getS1n248Kwh() == null ? 0 : this.getS1n248Kwh().hashCode());
		result = 37 * result
				+ (getS1n249Kwh() == null ? 0 : this.getS1n249Kwh().hashCode());
		result = 37 * result
				+ (getS1n250Kwh() == null ? 0 : this.getS1n250Kwh().hashCode());
		result = 37 * result
				+ (getS1n251Kwh() == null ? 0 : this.getS1n251Kwh().hashCode());
		result = 37 * result
				+ (getS1n252Kwh() == null ? 0 : this.getS1n252Kwh().hashCode());
		result = 37 * result
				+ (getS1n253Kwh() == null ? 0 : this.getS1n253Kwh().hashCode());
		result = 37 * result
				+ (getS1n254Kwh() == null ? 0 : this.getS1n254Kwh().hashCode());
		result = 37 * result
				+ (getS1n255Kwh() == null ? 0 : this.getS1n255Kwh().hashCode());
		result = 37 * result
				+ (getS1n256Kwh() == null ? 0 : this.getS1n256Kwh().hashCode());
		result = 37 * result
				+ (getS1n257Kwh() == null ? 0 : this.getS1n257Kwh().hashCode());
		result = 37 * result
				+ (getS1n258Kwh() == null ? 0 : this.getS1n258Kwh().hashCode());
		result = 37 * result
				+ (getS1n259Kwh() == null ? 0 : this.getS1n259Kwh().hashCode());
		result = 37 * result
				+ (getS1n260Kwh() == null ? 0 : this.getS1n260Kwh().hashCode());
		result = 37 * result
				+ (getS1n261Kwh() == null ? 0 : this.getS1n261Kwh().hashCode());
		result = 37 * result
				+ (getS1n262Kwh() == null ? 0 : this.getS1n262Kwh().hashCode());
		result = 37 * result
				+ (getS1n263Kwh() == null ? 0 : this.getS1n263Kwh().hashCode());
		result = 37 * result
				+ (getS1n264Kwh() == null ? 0 : this.getS1n264Kwh().hashCode());
		result = 37 * result
				+ (getS1n265Kwh() == null ? 0 : this.getS1n265Kwh().hashCode());
		result = 37 * result
				+ (getS1n266Kwh() == null ? 0 : this.getS1n266Kwh().hashCode());
		result = 37 * result
				+ (getS1n267Kwh() == null ? 0 : this.getS1n267Kwh().hashCode());
		result = 37 * result
				+ (getS1n268Kwh() == null ? 0 : this.getS1n268Kwh().hashCode());
		result = 37 * result
				+ (getS1n269Kwh() == null ? 0 : this.getS1n269Kwh().hashCode());
		result = 37 * result
				+ (getS1n270Kwh() == null ? 0 : this.getS1n270Kwh().hashCode());
		result = 37 * result
				+ (getS1n271Kwh() == null ? 0 : this.getS1n271Kwh().hashCode());
		result = 37 * result
				+ (getS1n272Kwh() == null ? 0 : this.getS1n272Kwh().hashCode());
		result = 37 * result
				+ (getS1n273Kwh() == null ? 0 : this.getS1n273Kwh().hashCode());
		result = 37 * result
				+ (getS1n274Kwh() == null ? 0 : this.getS1n274Kwh().hashCode());
		result = 37 * result
				+ (getS1n275Kwh() == null ? 0 : this.getS1n275Kwh().hashCode());
		result = 37 * result
				+ (getS1n276Kwh() == null ? 0 : this.getS1n276Kwh().hashCode());
		result = 37 * result
				+ (getS1n277Kwh() == null ? 0 : this.getS1n277Kwh().hashCode());
		result = 37 * result
				+ (getS1n278Kwh() == null ? 0 : this.getS1n278Kwh().hashCode());
		result = 37 * result
				+ (getS1n279Kwh() == null ? 0 : this.getS1n279Kwh().hashCode());
		result = 37 * result
				+ (getS1n280Kwh() == null ? 0 : this.getS1n280Kwh().hashCode());
		result = 37 * result
				+ (getS1n281Kwh() == null ? 0 : this.getS1n281Kwh().hashCode());
		return result;
	}

}
