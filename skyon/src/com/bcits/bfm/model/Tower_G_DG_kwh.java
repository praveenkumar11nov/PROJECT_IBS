package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tower_G_DG_kwh",schema="dbo",catalog="EMS")
public class Tower_G_DG_kwh {

	// Fields

	private Timestamp datestr;
	private Double s1n326Kwh;
	private Double s1n327Kwh;
	private Double s1n328Kwh;
	private Double s1n329Kwh;
	private Double s1n330Kwh;
	private Double s1n331Kwh;
	private Double s1n332Kwh;
	private Double s1n333Kwh;
	private Double s1n334Kwh;
	private Double s1n335Kwh;
	private Double s1n336Kwh;
	private Double s1n337Kwh;
	private Double s1n338Kwh;
	private Double s1n339Kwh;
	private Double s1n340Kwh;
	private Double s1n341Kwh;
	private Double s1n342Kwh;
	private Double s1n343Kwh;
	private Double s1n344Kwh;
	private Double s1n345Kwh;
	private Double s1n346Kwh;
	private Double s1n347Kwh;
	private Double s1n348Kwh;
	private Double s1n349Kwh;
	private Double s1n350Kwh;
	private Double s1n351Kwh;
	private Double s1n352Kwh;
	private Double s1n353Kwh;
	private Double s1n354Kwh;
	private Double s1n355Kwh;
	private Double s1n356Kwh;
	private Double s1n357Kwh;
	private Double s1n358Kwh;
	private Double s1n359Kwh;
	private Double s1n360Kwh;
	private Double s1n361Kwh;
	private Double s1n362Kwh;
	private Double s1n363Kwh;
	private Double s1n364Kwh;
	private Double s1n365Kwh;
	private Double s1n366Kwh;
	private Double s1n367Kwh;
	private Double s1n368Kwh;
	private Double s1n369Kwh;

	// Constructors

	/** default constructor */
	public Tower_G_DG_kwh() {
	}

	/** full constructor */
	public Tower_G_DG_kwh(Timestamp datestr, Double s1n326Kwh, Double s1n327Kwh,
			Double s1n328Kwh, Double s1n329Kwh, Double s1n330Kwh,
			Double s1n331Kwh, Double s1n332Kwh, Double s1n333Kwh,
			Double s1n334Kwh, Double s1n335Kwh, Double s1n336Kwh,
			Double s1n337Kwh, Double s1n338Kwh, Double s1n339Kwh,
			Double s1n340Kwh, Double s1n341Kwh, Double s1n342Kwh,
			Double s1n343Kwh, Double s1n344Kwh, Double s1n345Kwh,
			Double s1n346Kwh, Double s1n347Kwh, Double s1n348Kwh,
			Double s1n349Kwh, Double s1n350Kwh, Double s1n351Kwh,
			Double s1n352Kwh, Double s1n353Kwh, Double s1n354Kwh,
			Double s1n355Kwh, Double s1n356Kwh, Double s1n357Kwh,
			Double s1n358Kwh, Double s1n359Kwh, Double s1n360Kwh,
			Double s1n361Kwh, Double s1n362Kwh, Double s1n363Kwh,
			Double s1n364Kwh, Double s1n365Kwh, Double s1n366Kwh,
			Double s1n367Kwh, Double s1n368Kwh, Double s1n369Kwh) {
		this.datestr = datestr;
		this.s1n326Kwh = s1n326Kwh;
		this.s1n327Kwh = s1n327Kwh;
		this.s1n328Kwh = s1n328Kwh;
		this.s1n329Kwh = s1n329Kwh;
		this.s1n330Kwh = s1n330Kwh;
		this.s1n331Kwh = s1n331Kwh;
		this.s1n332Kwh = s1n332Kwh;
		this.s1n333Kwh = s1n333Kwh;
		this.s1n334Kwh = s1n334Kwh;
		this.s1n335Kwh = s1n335Kwh;
		this.s1n336Kwh = s1n336Kwh;
		this.s1n337Kwh = s1n337Kwh;
		this.s1n338Kwh = s1n338Kwh;
		this.s1n339Kwh = s1n339Kwh;
		this.s1n340Kwh = s1n340Kwh;
		this.s1n341Kwh = s1n341Kwh;
		this.s1n342Kwh = s1n342Kwh;
		this.s1n343Kwh = s1n343Kwh;
		this.s1n344Kwh = s1n344Kwh;
		this.s1n345Kwh = s1n345Kwh;
		this.s1n346Kwh = s1n346Kwh;
		this.s1n347Kwh = s1n347Kwh;
		this.s1n348Kwh = s1n348Kwh;
		this.s1n349Kwh = s1n349Kwh;
		this.s1n350Kwh = s1n350Kwh;
		this.s1n351Kwh = s1n351Kwh;
		this.s1n352Kwh = s1n352Kwh;
		this.s1n353Kwh = s1n353Kwh;
		this.s1n354Kwh = s1n354Kwh;
		this.s1n355Kwh = s1n355Kwh;
		this.s1n356Kwh = s1n356Kwh;
		this.s1n357Kwh = s1n357Kwh;
		this.s1n358Kwh = s1n358Kwh;
		this.s1n359Kwh = s1n359Kwh;
		this.s1n360Kwh = s1n360Kwh;
		this.s1n361Kwh = s1n361Kwh;
		this.s1n362Kwh = s1n362Kwh;
		this.s1n363Kwh = s1n363Kwh;
		this.s1n364Kwh = s1n364Kwh;
		this.s1n365Kwh = s1n365Kwh;
		this.s1n366Kwh = s1n366Kwh;
		this.s1n367Kwh = s1n367Kwh;
		this.s1n368Kwh = s1n368Kwh;
		this.s1n369Kwh = s1n369Kwh;
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

	@Column(name = "S1N326_KWH", precision = 53, scale = 0)
	public Double getS1n326Kwh() {
		return this.s1n326Kwh;
	}

	public void setS1n326Kwh(Double s1n326Kwh) {
		this.s1n326Kwh = s1n326Kwh;
	}

	@Column(name = "S1N327_KWH", precision = 53, scale = 0)
	public Double getS1n327Kwh() {
		return this.s1n327Kwh;
	}

	public void setS1n327Kwh(Double s1n327Kwh) {
		this.s1n327Kwh = s1n327Kwh;
	}

	@Column(name = "S1N328_KWH", precision = 53, scale = 0)
	public Double getS1n328Kwh() {
		return this.s1n328Kwh;
	}

	public void setS1n328Kwh(Double s1n328Kwh) {
		this.s1n328Kwh = s1n328Kwh;
	}

	@Column(name = "S1N329_KWH", precision = 53, scale = 0)
	public Double getS1n329Kwh() {
		return this.s1n329Kwh;
	}

	public void setS1n329Kwh(Double s1n329Kwh) {
		this.s1n329Kwh = s1n329Kwh;
	}

	@Column(name = "S1N330_KWH", precision = 53, scale = 0)
	public Double getS1n330Kwh() {
		return this.s1n330Kwh;
	}

	public void setS1n330Kwh(Double s1n330Kwh) {
		this.s1n330Kwh = s1n330Kwh;
	}

	@Column(name = "S1N331_KWH", precision = 53, scale = 0)
	public Double getS1n331Kwh() {
		return this.s1n331Kwh;
	}

	public void setS1n331Kwh(Double s1n331Kwh) {
		this.s1n331Kwh = s1n331Kwh;
	}

	@Column(name = "S1N332_KWH", precision = 53, scale = 0)
	public Double getS1n332Kwh() {
		return this.s1n332Kwh;
	}

	public void setS1n332Kwh(Double s1n332Kwh) {
		this.s1n332Kwh = s1n332Kwh;
	}

	@Column(name = "S1N333_KWH", precision = 53, scale = 0)
	public Double getS1n333Kwh() {
		return this.s1n333Kwh;
	}

	public void setS1n333Kwh(Double s1n333Kwh) {
		this.s1n333Kwh = s1n333Kwh;
	}

	@Column(name = "S1N334_KWH", precision = 53, scale = 0)
	public Double getS1n334Kwh() {
		return this.s1n334Kwh;
	}

	public void setS1n334Kwh(Double s1n334Kwh) {
		this.s1n334Kwh = s1n334Kwh;
	}

	@Column(name = "S1N335_KWH", precision = 53, scale = 0)
	public Double getS1n335Kwh() {
		return this.s1n335Kwh;
	}

	public void setS1n335Kwh(Double s1n335Kwh) {
		this.s1n335Kwh = s1n335Kwh;
	}

	@Column(name = "S1N336_KWH", precision = 53, scale = 0)
	public Double getS1n336Kwh() {
		return this.s1n336Kwh;
	}

	public void setS1n336Kwh(Double s1n336Kwh) {
		this.s1n336Kwh = s1n336Kwh;
	}

	@Column(name = "S1N337_KWH", precision = 53, scale = 0)
	public Double getS1n337Kwh() {
		return this.s1n337Kwh;
	}

	public void setS1n337Kwh(Double s1n337Kwh) {
		this.s1n337Kwh = s1n337Kwh;
	}

	@Column(name = "S1N338_KWH", precision = 53, scale = 0)
	public Double getS1n338Kwh() {
		return this.s1n338Kwh;
	}

	public void setS1n338Kwh(Double s1n338Kwh) {
		this.s1n338Kwh = s1n338Kwh;
	}

	@Column(name = "S1N339_KWH", precision = 53, scale = 0)
	public Double getS1n339Kwh() {
		return this.s1n339Kwh;
	}

	public void setS1n339Kwh(Double s1n339Kwh) {
		this.s1n339Kwh = s1n339Kwh;
	}

	@Column(name = "S1N340_KWH", precision = 53, scale = 0)
	public Double getS1n340Kwh() {
		return this.s1n340Kwh;
	}

	public void setS1n340Kwh(Double s1n340Kwh) {
		this.s1n340Kwh = s1n340Kwh;
	}

	@Column(name = "S1N341_KWH", precision = 53, scale = 0)
	public Double getS1n341Kwh() {
		return this.s1n341Kwh;
	}

	public void setS1n341Kwh(Double s1n341Kwh) {
		this.s1n341Kwh = s1n341Kwh;
	}

	@Column(name = "S1N342_KWH", precision = 53, scale = 0)
	public Double getS1n342Kwh() {
		return this.s1n342Kwh;
	}

	public void setS1n342Kwh(Double s1n342Kwh) {
		this.s1n342Kwh = s1n342Kwh;
	}

	@Column(name = "S1N343_KWH", precision = 53, scale = 0)
	public Double getS1n343Kwh() {
		return this.s1n343Kwh;
	}

	public void setS1n343Kwh(Double s1n343Kwh) {
		this.s1n343Kwh = s1n343Kwh;
	}

	@Column(name = "S1N344_KWH", precision = 53, scale = 0)
	public Double getS1n344Kwh() {
		return this.s1n344Kwh;
	}

	public void setS1n344Kwh(Double s1n344Kwh) {
		this.s1n344Kwh = s1n344Kwh;
	}

	@Column(name = "S1N345_KWH", precision = 53, scale = 0)
	public Double getS1n345Kwh() {
		return this.s1n345Kwh;
	}

	public void setS1n345Kwh(Double s1n345Kwh) {
		this.s1n345Kwh = s1n345Kwh;
	}

	@Column(name = "S1N346_KWH", precision = 53, scale = 0)
	public Double getS1n346Kwh() {
		return this.s1n346Kwh;
	}

	public void setS1n346Kwh(Double s1n346Kwh) {
		this.s1n346Kwh = s1n346Kwh;
	}

	@Column(name = "S1N347_KWH", precision = 53, scale = 0)
	public Double getS1n347Kwh() {
		return this.s1n347Kwh;
	}

	public void setS1n347Kwh(Double s1n347Kwh) {
		this.s1n347Kwh = s1n347Kwh;
	}

	@Column(name = "S1N348_KWH", precision = 53, scale = 0)
	public Double getS1n348Kwh() {
		return this.s1n348Kwh;
	}

	public void setS1n348Kwh(Double s1n348Kwh) {
		this.s1n348Kwh = s1n348Kwh;
	}

	@Column(name = "S1N349_KWH", precision = 53, scale = 0)
	public Double getS1n349Kwh() {
		return this.s1n349Kwh;
	}

	public void setS1n349Kwh(Double s1n349Kwh) {
		this.s1n349Kwh = s1n349Kwh;
	}

	@Column(name = "S1N350_KWH", precision = 53, scale = 0)
	public Double getS1n350Kwh() {
		return this.s1n350Kwh;
	}

	public void setS1n350Kwh(Double s1n350Kwh) {
		this.s1n350Kwh = s1n350Kwh;
	}

	@Column(name = "S1N351_KWH", precision = 53, scale = 0)
	public Double getS1n351Kwh() {
		return this.s1n351Kwh;
	}

	public void setS1n351Kwh(Double s1n351Kwh) {
		this.s1n351Kwh = s1n351Kwh;
	}

	@Column(name = "S1N352_KWH", precision = 53, scale = 0)
	public Double getS1n352Kwh() {
		return this.s1n352Kwh;
	}

	public void setS1n352Kwh(Double s1n352Kwh) {
		this.s1n352Kwh = s1n352Kwh;
	}

	@Column(name = "S1N353_KWH", precision = 53, scale = 0)
	public Double getS1n353Kwh() {
		return this.s1n353Kwh;
	}

	public void setS1n353Kwh(Double s1n353Kwh) {
		this.s1n353Kwh = s1n353Kwh;
	}

	@Column(name = "S1N354_KWH", precision = 53, scale = 0)
	public Double getS1n354Kwh() {
		return this.s1n354Kwh;
	}

	public void setS1n354Kwh(Double s1n354Kwh) {
		this.s1n354Kwh = s1n354Kwh;
	}

	@Column(name = "S1N355_KWH", precision = 53, scale = 0)
	public Double getS1n355Kwh() {
		return this.s1n355Kwh;
	}

	public void setS1n355Kwh(Double s1n355Kwh) {
		this.s1n355Kwh = s1n355Kwh;
	}

	@Column(name = "S1N356_KWH", precision = 53, scale = 0)
	public Double getS1n356Kwh() {
		return this.s1n356Kwh;
	}

	public void setS1n356Kwh(Double s1n356Kwh) {
		this.s1n356Kwh = s1n356Kwh;
	}

	@Column(name = "S1N357_KWH", precision = 53, scale = 0)
	public Double getS1n357Kwh() {
		return this.s1n357Kwh;
	}

	public void setS1n357Kwh(Double s1n357Kwh) {
		this.s1n357Kwh = s1n357Kwh;
	}

	@Column(name = "S1N358_KWH", precision = 53, scale = 0)
	public Double getS1n358Kwh() {
		return this.s1n358Kwh;
	}

	public void setS1n358Kwh(Double s1n358Kwh) {
		this.s1n358Kwh = s1n358Kwh;
	}

	@Column(name = "S1N359_KWH", precision = 53, scale = 0)
	public Double getS1n359Kwh() {
		return this.s1n359Kwh;
	}

	public void setS1n359Kwh(Double s1n359Kwh) {
		this.s1n359Kwh = s1n359Kwh;
	}

	@Column(name = "S1N360_KWH", precision = 53, scale = 0)
	public Double getS1n360Kwh() {
		return this.s1n360Kwh;
	}

	public void setS1n360Kwh(Double s1n360Kwh) {
		this.s1n360Kwh = s1n360Kwh;
	}

	@Column(name = "S1N361_KWH", precision = 53, scale = 0)
	public Double getS1n361Kwh() {
		return this.s1n361Kwh;
	}

	public void setS1n361Kwh(Double s1n361Kwh) {
		this.s1n361Kwh = s1n361Kwh;
	}

	@Column(name = "S1N362_KWH", precision = 53, scale = 0)
	public Double getS1n362Kwh() {
		return this.s1n362Kwh;
	}

	public void setS1n362Kwh(Double s1n362Kwh) {
		this.s1n362Kwh = s1n362Kwh;
	}

	@Column(name = "S1N363_KWH", precision = 53, scale = 0)
	public Double getS1n363Kwh() {
		return this.s1n363Kwh;
	}

	public void setS1n363Kwh(Double s1n363Kwh) {
		this.s1n363Kwh = s1n363Kwh;
	}

	@Column(name = "S1N364_KWH", precision = 53, scale = 0)
	public Double getS1n364Kwh() {
		return this.s1n364Kwh;
	}

	public void setS1n364Kwh(Double s1n364Kwh) {
		this.s1n364Kwh = s1n364Kwh;
	}

	@Column(name = "S1N365_KWH", precision = 53, scale = 0)
	public Double getS1n365Kwh() {
		return this.s1n365Kwh;
	}

	public void setS1n365Kwh(Double s1n365Kwh) {
		this.s1n365Kwh = s1n365Kwh;
	}

	@Column(name = "S1N366_KWH", precision = 53, scale = 0)
	public Double getS1n366Kwh() {
		return this.s1n366Kwh;
	}

	public void setS1n366Kwh(Double s1n366Kwh) {
		this.s1n366Kwh = s1n366Kwh;
	}

	@Column(name = "S1N367_KWH", precision = 53, scale = 0)
	public Double getS1n367Kwh() {
		return this.s1n367Kwh;
	}

	public void setS1n367Kwh(Double s1n367Kwh) {
		this.s1n367Kwh = s1n367Kwh;
	}

	@Column(name = "S1N368_KWH", precision = 53, scale = 0)
	public Double getS1n368Kwh() {
		return this.s1n368Kwh;
	}

	public void setS1n368Kwh(Double s1n368Kwh) {
		this.s1n368Kwh = s1n368Kwh;
	}

	@Column(name = "S1N369_KWH", precision = 53, scale = 0)
	public Double getS1n369Kwh() {
		return this.s1n369Kwh;
	}

	public void setS1n369Kwh(Double s1n369Kwh) {
		this.s1n369Kwh = s1n369Kwh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tower_G_DG_kwh))
			return false;
		Tower_G_DG_kwh castOther = (Tower_G_DG_kwh) other;

		return ((this.getDatestr() == castOther.getDatestr()) || (this
				.getDatestr() != null && castOther.getDatestr() != null && this
				.getDatestr().equals(castOther.getDatestr())))
				&& ((this.getS1n326Kwh() == castOther.getS1n326Kwh()) || (this
						.getS1n326Kwh() != null
						&& castOther.getS1n326Kwh() != null && this
						.getS1n326Kwh().equals(castOther.getS1n326Kwh())))
				&& ((this.getS1n327Kwh() == castOther.getS1n327Kwh()) || (this
						.getS1n327Kwh() != null
						&& castOther.getS1n327Kwh() != null && this
						.getS1n327Kwh().equals(castOther.getS1n327Kwh())))
				&& ((this.getS1n328Kwh() == castOther.getS1n328Kwh()) || (this
						.getS1n328Kwh() != null
						&& castOther.getS1n328Kwh() != null && this
						.getS1n328Kwh().equals(castOther.getS1n328Kwh())))
				&& ((this.getS1n329Kwh() == castOther.getS1n329Kwh()) || (this
						.getS1n329Kwh() != null
						&& castOther.getS1n329Kwh() != null && this
						.getS1n329Kwh().equals(castOther.getS1n329Kwh())))
				&& ((this.getS1n330Kwh() == castOther.getS1n330Kwh()) || (this
						.getS1n330Kwh() != null
						&& castOther.getS1n330Kwh() != null && this
						.getS1n330Kwh().equals(castOther.getS1n330Kwh())))
				&& ((this.getS1n331Kwh() == castOther.getS1n331Kwh()) || (this
						.getS1n331Kwh() != null
						&& castOther.getS1n331Kwh() != null && this
						.getS1n331Kwh().equals(castOther.getS1n331Kwh())))
				&& ((this.getS1n332Kwh() == castOther.getS1n332Kwh()) || (this
						.getS1n332Kwh() != null
						&& castOther.getS1n332Kwh() != null && this
						.getS1n332Kwh().equals(castOther.getS1n332Kwh())))
				&& ((this.getS1n333Kwh() == castOther.getS1n333Kwh()) || (this
						.getS1n333Kwh() != null
						&& castOther.getS1n333Kwh() != null && this
						.getS1n333Kwh().equals(castOther.getS1n333Kwh())))
				&& ((this.getS1n334Kwh() == castOther.getS1n334Kwh()) || (this
						.getS1n334Kwh() != null
						&& castOther.getS1n334Kwh() != null && this
						.getS1n334Kwh().equals(castOther.getS1n334Kwh())))
				&& ((this.getS1n335Kwh() == castOther.getS1n335Kwh()) || (this
						.getS1n335Kwh() != null
						&& castOther.getS1n335Kwh() != null && this
						.getS1n335Kwh().equals(castOther.getS1n335Kwh())))
				&& ((this.getS1n336Kwh() == castOther.getS1n336Kwh()) || (this
						.getS1n336Kwh() != null
						&& castOther.getS1n336Kwh() != null && this
						.getS1n336Kwh().equals(castOther.getS1n336Kwh())))
				&& ((this.getS1n337Kwh() == castOther.getS1n337Kwh()) || (this
						.getS1n337Kwh() != null
						&& castOther.getS1n337Kwh() != null && this
						.getS1n337Kwh().equals(castOther.getS1n337Kwh())))
				&& ((this.getS1n338Kwh() == castOther.getS1n338Kwh()) || (this
						.getS1n338Kwh() != null
						&& castOther.getS1n338Kwh() != null && this
						.getS1n338Kwh().equals(castOther.getS1n338Kwh())))
				&& ((this.getS1n339Kwh() == castOther.getS1n339Kwh()) || (this
						.getS1n339Kwh() != null
						&& castOther.getS1n339Kwh() != null && this
						.getS1n339Kwh().equals(castOther.getS1n339Kwh())))
				&& ((this.getS1n340Kwh() == castOther.getS1n340Kwh()) || (this
						.getS1n340Kwh() != null
						&& castOther.getS1n340Kwh() != null && this
						.getS1n340Kwh().equals(castOther.getS1n340Kwh())))
				&& ((this.getS1n341Kwh() == castOther.getS1n341Kwh()) || (this
						.getS1n341Kwh() != null
						&& castOther.getS1n341Kwh() != null && this
						.getS1n341Kwh().equals(castOther.getS1n341Kwh())))
				&& ((this.getS1n342Kwh() == castOther.getS1n342Kwh()) || (this
						.getS1n342Kwh() != null
						&& castOther.getS1n342Kwh() != null && this
						.getS1n342Kwh().equals(castOther.getS1n342Kwh())))
				&& ((this.getS1n343Kwh() == castOther.getS1n343Kwh()) || (this
						.getS1n343Kwh() != null
						&& castOther.getS1n343Kwh() != null && this
						.getS1n343Kwh().equals(castOther.getS1n343Kwh())))
				&& ((this.getS1n344Kwh() == castOther.getS1n344Kwh()) || (this
						.getS1n344Kwh() != null
						&& castOther.getS1n344Kwh() != null && this
						.getS1n344Kwh().equals(castOther.getS1n344Kwh())))
				&& ((this.getS1n345Kwh() == castOther.getS1n345Kwh()) || (this
						.getS1n345Kwh() != null
						&& castOther.getS1n345Kwh() != null && this
						.getS1n345Kwh().equals(castOther.getS1n345Kwh())))
				&& ((this.getS1n346Kwh() == castOther.getS1n346Kwh()) || (this
						.getS1n346Kwh() != null
						&& castOther.getS1n346Kwh() != null && this
						.getS1n346Kwh().equals(castOther.getS1n346Kwh())))
				&& ((this.getS1n347Kwh() == castOther.getS1n347Kwh()) || (this
						.getS1n347Kwh() != null
						&& castOther.getS1n347Kwh() != null && this
						.getS1n347Kwh().equals(castOther.getS1n347Kwh())))
				&& ((this.getS1n348Kwh() == castOther.getS1n348Kwh()) || (this
						.getS1n348Kwh() != null
						&& castOther.getS1n348Kwh() != null && this
						.getS1n348Kwh().equals(castOther.getS1n348Kwh())))
				&& ((this.getS1n349Kwh() == castOther.getS1n349Kwh()) || (this
						.getS1n349Kwh() != null
						&& castOther.getS1n349Kwh() != null && this
						.getS1n349Kwh().equals(castOther.getS1n349Kwh())))
				&& ((this.getS1n350Kwh() == castOther.getS1n350Kwh()) || (this
						.getS1n350Kwh() != null
						&& castOther.getS1n350Kwh() != null && this
						.getS1n350Kwh().equals(castOther.getS1n350Kwh())))
				&& ((this.getS1n351Kwh() == castOther.getS1n351Kwh()) || (this
						.getS1n351Kwh() != null
						&& castOther.getS1n351Kwh() != null && this
						.getS1n351Kwh().equals(castOther.getS1n351Kwh())))
				&& ((this.getS1n352Kwh() == castOther.getS1n352Kwh()) || (this
						.getS1n352Kwh() != null
						&& castOther.getS1n352Kwh() != null && this
						.getS1n352Kwh().equals(castOther.getS1n352Kwh())))
				&& ((this.getS1n353Kwh() == castOther.getS1n353Kwh()) || (this
						.getS1n353Kwh() != null
						&& castOther.getS1n353Kwh() != null && this
						.getS1n353Kwh().equals(castOther.getS1n353Kwh())))
				&& ((this.getS1n354Kwh() == castOther.getS1n354Kwh()) || (this
						.getS1n354Kwh() != null
						&& castOther.getS1n354Kwh() != null && this
						.getS1n354Kwh().equals(castOther.getS1n354Kwh())))
				&& ((this.getS1n355Kwh() == castOther.getS1n355Kwh()) || (this
						.getS1n355Kwh() != null
						&& castOther.getS1n355Kwh() != null && this
						.getS1n355Kwh().equals(castOther.getS1n355Kwh())))
				&& ((this.getS1n356Kwh() == castOther.getS1n356Kwh()) || (this
						.getS1n356Kwh() != null
						&& castOther.getS1n356Kwh() != null && this
						.getS1n356Kwh().equals(castOther.getS1n356Kwh())))
				&& ((this.getS1n357Kwh() == castOther.getS1n357Kwh()) || (this
						.getS1n357Kwh() != null
						&& castOther.getS1n357Kwh() != null && this
						.getS1n357Kwh().equals(castOther.getS1n357Kwh())))
				&& ((this.getS1n358Kwh() == castOther.getS1n358Kwh()) || (this
						.getS1n358Kwh() != null
						&& castOther.getS1n358Kwh() != null && this
						.getS1n358Kwh().equals(castOther.getS1n358Kwh())))
				&& ((this.getS1n359Kwh() == castOther.getS1n359Kwh()) || (this
						.getS1n359Kwh() != null
						&& castOther.getS1n359Kwh() != null && this
						.getS1n359Kwh().equals(castOther.getS1n359Kwh())))
				&& ((this.getS1n360Kwh() == castOther.getS1n360Kwh()) || (this
						.getS1n360Kwh() != null
						&& castOther.getS1n360Kwh() != null && this
						.getS1n360Kwh().equals(castOther.getS1n360Kwh())))
				&& ((this.getS1n361Kwh() == castOther.getS1n361Kwh()) || (this
						.getS1n361Kwh() != null
						&& castOther.getS1n361Kwh() != null && this
						.getS1n361Kwh().equals(castOther.getS1n361Kwh())))
				&& ((this.getS1n362Kwh() == castOther.getS1n362Kwh()) || (this
						.getS1n362Kwh() != null
						&& castOther.getS1n362Kwh() != null && this
						.getS1n362Kwh().equals(castOther.getS1n362Kwh())))
				&& ((this.getS1n363Kwh() == castOther.getS1n363Kwh()) || (this
						.getS1n363Kwh() != null
						&& castOther.getS1n363Kwh() != null && this
						.getS1n363Kwh().equals(castOther.getS1n363Kwh())))
				&& ((this.getS1n364Kwh() == castOther.getS1n364Kwh()) || (this
						.getS1n364Kwh() != null
						&& castOther.getS1n364Kwh() != null && this
						.getS1n364Kwh().equals(castOther.getS1n364Kwh())))
				&& ((this.getS1n365Kwh() == castOther.getS1n365Kwh()) || (this
						.getS1n365Kwh() != null
						&& castOther.getS1n365Kwh() != null && this
						.getS1n365Kwh().equals(castOther.getS1n365Kwh())))
				&& ((this.getS1n366Kwh() == castOther.getS1n366Kwh()) || (this
						.getS1n366Kwh() != null
						&& castOther.getS1n366Kwh() != null && this
						.getS1n366Kwh().equals(castOther.getS1n366Kwh())))
				&& ((this.getS1n367Kwh() == castOther.getS1n367Kwh()) || (this
						.getS1n367Kwh() != null
						&& castOther.getS1n367Kwh() != null && this
						.getS1n367Kwh().equals(castOther.getS1n367Kwh())))
				&& ((this.getS1n368Kwh() == castOther.getS1n368Kwh()) || (this
						.getS1n368Kwh() != null
						&& castOther.getS1n368Kwh() != null && this
						.getS1n368Kwh().equals(castOther.getS1n368Kwh())))
				&& ((this.getS1n369Kwh() == castOther.getS1n369Kwh()) || (this
						.getS1n369Kwh() != null
						&& castOther.getS1n369Kwh() != null && this
						.getS1n369Kwh().equals(castOther.getS1n369Kwh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDatestr() == null ? 0 : this.getDatestr().hashCode());
		result = 37 * result
				+ (getS1n326Kwh() == null ? 0 : this.getS1n326Kwh().hashCode());
		result = 37 * result
				+ (getS1n327Kwh() == null ? 0 : this.getS1n327Kwh().hashCode());
		result = 37 * result
				+ (getS1n328Kwh() == null ? 0 : this.getS1n328Kwh().hashCode());
		result = 37 * result
				+ (getS1n329Kwh() == null ? 0 : this.getS1n329Kwh().hashCode());
		result = 37 * result
				+ (getS1n330Kwh() == null ? 0 : this.getS1n330Kwh().hashCode());
		result = 37 * result
				+ (getS1n331Kwh() == null ? 0 : this.getS1n331Kwh().hashCode());
		result = 37 * result
				+ (getS1n332Kwh() == null ? 0 : this.getS1n332Kwh().hashCode());
		result = 37 * result
				+ (getS1n333Kwh() == null ? 0 : this.getS1n333Kwh().hashCode());
		result = 37 * result
				+ (getS1n334Kwh() == null ? 0 : this.getS1n334Kwh().hashCode());
		result = 37 * result
				+ (getS1n335Kwh() == null ? 0 : this.getS1n335Kwh().hashCode());
		result = 37 * result
				+ (getS1n336Kwh() == null ? 0 : this.getS1n336Kwh().hashCode());
		result = 37 * result
				+ (getS1n337Kwh() == null ? 0 : this.getS1n337Kwh().hashCode());
		result = 37 * result
				+ (getS1n338Kwh() == null ? 0 : this.getS1n338Kwh().hashCode());
		result = 37 * result
				+ (getS1n339Kwh() == null ? 0 : this.getS1n339Kwh().hashCode());
		result = 37 * result
				+ (getS1n340Kwh() == null ? 0 : this.getS1n340Kwh().hashCode());
		result = 37 * result
				+ (getS1n341Kwh() == null ? 0 : this.getS1n341Kwh().hashCode());
		result = 37 * result
				+ (getS1n342Kwh() == null ? 0 : this.getS1n342Kwh().hashCode());
		result = 37 * result
				+ (getS1n343Kwh() == null ? 0 : this.getS1n343Kwh().hashCode());
		result = 37 * result
				+ (getS1n344Kwh() == null ? 0 : this.getS1n344Kwh().hashCode());
		result = 37 * result
				+ (getS1n345Kwh() == null ? 0 : this.getS1n345Kwh().hashCode());
		result = 37 * result
				+ (getS1n346Kwh() == null ? 0 : this.getS1n346Kwh().hashCode());
		result = 37 * result
				+ (getS1n347Kwh() == null ? 0 : this.getS1n347Kwh().hashCode());
		result = 37 * result
				+ (getS1n348Kwh() == null ? 0 : this.getS1n348Kwh().hashCode());
		result = 37 * result
				+ (getS1n349Kwh() == null ? 0 : this.getS1n349Kwh().hashCode());
		result = 37 * result
				+ (getS1n350Kwh() == null ? 0 : this.getS1n350Kwh().hashCode());
		result = 37 * result
				+ (getS1n351Kwh() == null ? 0 : this.getS1n351Kwh().hashCode());
		result = 37 * result
				+ (getS1n352Kwh() == null ? 0 : this.getS1n352Kwh().hashCode());
		result = 37 * result
				+ (getS1n353Kwh() == null ? 0 : this.getS1n353Kwh().hashCode());
		result = 37 * result
				+ (getS1n354Kwh() == null ? 0 : this.getS1n354Kwh().hashCode());
		result = 37 * result
				+ (getS1n355Kwh() == null ? 0 : this.getS1n355Kwh().hashCode());
		result = 37 * result
				+ (getS1n356Kwh() == null ? 0 : this.getS1n356Kwh().hashCode());
		result = 37 * result
				+ (getS1n357Kwh() == null ? 0 : this.getS1n357Kwh().hashCode());
		result = 37 * result
				+ (getS1n358Kwh() == null ? 0 : this.getS1n358Kwh().hashCode());
		result = 37 * result
				+ (getS1n359Kwh() == null ? 0 : this.getS1n359Kwh().hashCode());
		result = 37 * result
				+ (getS1n360Kwh() == null ? 0 : this.getS1n360Kwh().hashCode());
		result = 37 * result
				+ (getS1n361Kwh() == null ? 0 : this.getS1n361Kwh().hashCode());
		result = 37 * result
				+ (getS1n362Kwh() == null ? 0 : this.getS1n362Kwh().hashCode());
		result = 37 * result
				+ (getS1n363Kwh() == null ? 0 : this.getS1n363Kwh().hashCode());
		result = 37 * result
				+ (getS1n364Kwh() == null ? 0 : this.getS1n364Kwh().hashCode());
		result = 37 * result
				+ (getS1n365Kwh() == null ? 0 : this.getS1n365Kwh().hashCode());
		result = 37 * result
				+ (getS1n366Kwh() == null ? 0 : this.getS1n366Kwh().hashCode());
		result = 37 * result
				+ (getS1n367Kwh() == null ? 0 : this.getS1n367Kwh().hashCode());
		result = 37 * result
				+ (getS1n368Kwh() == null ? 0 : this.getS1n368Kwh().hashCode());
		result = 37 * result
				+ (getS1n369Kwh() == null ? 0 : this.getS1n369Kwh().hashCode());
		return result;
	}

}
