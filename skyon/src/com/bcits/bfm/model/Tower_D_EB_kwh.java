package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="Tower_D_EB_kwh.findALL" ,query="Select tower from Tower_D_EB_kwh tower")
})
@Entity
@Table(name="Tower_D_EB_kwh",schema="dbo",catalog="EMS")
public class Tower_D_EB_kwh {
	// Fields

	private Timestamp datestr;
	private Double s1n372Kwh;
	private Double s1n373Kwh;
	private Double s1n374Kwh;
	private Double s1n375Kwh;
	private Double s1n376Kwh;
	private Double s1n377Kwh;
	private Double s1n378Kwh;
	private Double s1n379Kwh;
	private Double s1n380Kwh;
	private Double s1n381Kwh;
	private Double s1n382Kwh;
	private Double s1n383Kwh;	
	private Double s1n384Kwh;
	private Double s1n385Kwh;
	private Double s1n386Kwh;
	private Double s1n387Kwh;
	private Double s1n388Kwh;
	private Double s1n389Kwh;
	private Double s1n390Kwh;
	private Double s1n391Kwh;
	private Double s1n392Kwh;
	private Double s1n393Kwh;
	private Double s1n394Kwh;
	private Double s1n395Kwh;
	private Double s1n396Kwh;
	private Double s1n397Kwh;
	private Double s1n398Kwh;
	private Double s1n399Kwh;
	private Double s1n400Kwh;
	private Double s1n401Kwh;
	private Double s1n402Kwh;
	private Double s1n403Kwh;
	private Double s1n404Kwh;
	private Double s1n405Kwh;
	private Double s1n406Kwh;
	private Double s1n407Kwh;
	private Double s1n408Kwh;
	private Double s1n409Kwh;
	private Double s1n410Kwh;
	private Double s1n411Kwh;	
	private Double s1n412Kwh;
	private Double s1n413Kwh;
	private Double s1n414Kwh;
	private Double s1n415Kwh;
	private Double s1n416Kwh;
	private Double s1n417Kwh;
	private Double s1n418Kwh;
	private Double s1n419Kwh;
	private Double s1n420Kwh;
	private Double s1n421Kwh;	
	private Double s1n422Kwh;
	private Double s1n423Kwh;
	private Double s1n424Kwh;
	private Double s1n425Kwh;
	private Double s1n426Kwh;
	private Double s1n427Kwh;
	private Double s1n428Kwh;
	private Double s1n429Kwh;
	private Double s1n430Kwh;
	private Double s1n431Kwh;
	private Double s1n432Kwh;
	private Double s1n433Kwh;
	private Double s1n434Kwh;
	private Double s1n435Kwh;
	private Double s1n436Kwh;
	private Double s1n437Kwh;
	private Double s1n438Kwh;
	private Double s1n439Kwh;
	private Double s1n440Kwh;
	private Double s1n441Kwh;
	private Double s1n442Kwh;
	private Double s1n443Kwh;
	private Double s1n444Kwh;
	private Double s1n445Kwh;
	private Double s1n446Kwh;
	private Double s1n447Kwh;
	private Double s1n448Kwh;
	private Double s1n449Kwh;
	private Double s1n450Kwh;	
	private Double s1n451Kwh;
	private Double s1n452Kwh;
	private Double s1n453Kwh;
	private Double s1n454Kwh;
	private Double s1n455Kwh;
	private Double s1n456Kwh;
	private Double s1n457Kwh;
	private Double s1n458Kwh;
	private Double s1n459Kwh;
	private Double s1n460Kwh;
	private Double s1n461Kwh;
	private Double s1n462Kwh;
	private Double s1n463Kwh;
	private Double s1n464Kwh;
	private Double s1n465Kwh;
	private Double s1n466Kwh;
	private Double s1n467Kwh;
	private Double s1n468Kwh;
	private Double s1n469Kwh;
	private Double s1n470Kwh;
	private Double s1n471Kwh;
	private Double s1n472Kwh;
	private Double s1n473Kwh;
	private Double s1n474Kwh;
	private Double s1n475Kwh;
	private Double s1n476Kwh;
	private Double s1n477Kwh;
	private Double s1n478Kwh;
	private Double s1n479Kwh;
	private Double s1n480Kwh;
	// Constructors
	@Id
	@Column(name = "Datestr", length = 23)
	public Timestamp getDatestr() {
		return datestr;
	}
	public void setDatestr(Timestamp datestr) {
		this.datestr = datestr;
	}
	@Column(name = "S1N372_KWH", precision = 53, scale = 0)
	public Double getS1n372Kwh() {
		return s1n372Kwh;
	}
	public void setS1n372Kwh(Double s1n372Kwh) {
		this.s1n372Kwh = s1n372Kwh;
	}
	@Column(name = "S1N373_KWH", precision = 53, scale = 0)
	public Double getS1n373Kwh() {
		return s1n373Kwh;
	}
	public void setS1n373Kwh(Double s1n373Kwh) {
		this.s1n373Kwh = s1n373Kwh;
	}
	@Column(name = "S1N374_KWH", precision = 53, scale = 0)
	public Double getS1n374Kwh() {
		return s1n374Kwh;
	}
	public void setS1n374Kwh(Double s1n374Kwh) {
		this.s1n374Kwh = s1n374Kwh;
	}
	@Column(name = "S1N375_KWH", precision = 53, scale = 0)
	public Double getS1n375Kwh() {
		return s1n375Kwh;
	}
	public void setS1n375Kwh(Double s1n375Kwh) {
		this.s1n375Kwh = s1n375Kwh;
	}
	@Column(name = "S1N376_KWH", precision = 53, scale = 0)
	public Double getS1n376Kwh() {
		return s1n376Kwh;
	}
	public void setS1n376Kwh(Double s1n376Kwh) {
		this.s1n376Kwh = s1n376Kwh;
	}
	@Column(name = "S1N377_KWH", precision = 53, scale = 0)
	public Double getS1n377Kwh() {
		return s1n377Kwh;
	}
	public void setS1n377Kwh(Double s1n377Kwh) {
		this.s1n377Kwh = s1n377Kwh;
	}
	@Column(name = "S1N378_KWH", precision = 53, scale = 0)
	public Double getS1n378Kwh() {
		return s1n378Kwh;
	}
	public void setS1n378Kwh(Double s1n378Kwh) {
		this.s1n378Kwh = s1n378Kwh;
	}
	@Column(name = "S1N379_KWH", precision = 53, scale = 0)
	public Double getS1n379Kwh() {
		return s1n379Kwh;
	}
	public void setS1n379Kwh(Double s1n379Kwh) {
		this.s1n379Kwh = s1n379Kwh;
	}
	@Column(name = "S1N380_KWH", precision = 53, scale = 0)
	public Double getS1n380Kwh() {
		return s1n380Kwh;
	}
	public void setS1n380Kwh(Double s1n380Kwh) {
		this.s1n380Kwh = s1n380Kwh;
	}
	@Column(name = "S1N381_KWH", precision = 53, scale = 0)
	public Double getS1n381Kwh() {
		return s1n381Kwh;
	}
	public void setS1n381Kwh(Double s1n381Kwh) {
		this.s1n381Kwh = s1n381Kwh;
	}
	@Column(name = "S1N382_KWH", precision = 53, scale = 0)
	public Double getS1n382Kwh() {
		return s1n382Kwh;
	}
	public void setS1n382Kwh(Double s1n382Kwh) {
		this.s1n382Kwh = s1n382Kwh;
	}
	@Column(name = "S1N383_KWH", precision = 53, scale = 0)
	public Double getS1n383Kwh() {
		return s1n383Kwh;
	}
	public void setS1n383Kwh(Double s1n383Kwh) {
		this.s1n383Kwh = s1n383Kwh;
	}
	@Column(name = "S1N384_KWH", precision = 53, scale = 0)
	public Double getS1n384Kwh() {
		return s1n384Kwh;
	}
	public void setS1n384Kwh(Double s1n384Kwh) {
		this.s1n384Kwh = s1n384Kwh;
	}
	@Column(name = "S1N385_KWH", precision = 53, scale = 0)
	public Double getS1n385Kwh() {
		return s1n385Kwh;
	}
	public void setS1n385Kwh(Double s1n385Kwh) {
		this.s1n385Kwh = s1n385Kwh;
	}
	@Column(name = "S1N386_KWH", precision = 53, scale = 0)
	public Double getS1n386Kwh() {
		return s1n386Kwh;
	}
	public void setS1n386Kwh(Double s1n386Kwh) {
		this.s1n386Kwh = s1n386Kwh;
	}
	@Column(name = "S1N387_KWH", precision = 53, scale = 0)
	public Double getS1n387Kwh() {
		return s1n387Kwh;
	}
	public void setS1n387Kwh(Double s1n387Kwh) {
		this.s1n387Kwh = s1n387Kwh;
	}
	@Column(name = "S1N388_KWH", precision = 53, scale = 0)
	public Double getS1n388Kwh() {
		return s1n388Kwh;
	}
	public void setS1n388Kwh(Double s1n388Kwh) {
		this.s1n388Kwh = s1n388Kwh;
	}
	@Column(name = "S1N389_KWH", precision = 53, scale = 0)
	public Double getS1n389Kwh() {
		return s1n389Kwh;
	}
	public void setS1n389Kwh(Double s1n389Kwh) {
		this.s1n389Kwh = s1n389Kwh;
	}
	@Column(name = "S1N390_KWH", precision = 53, scale = 0)
	public Double getS1n390Kwh() {
		return s1n390Kwh;
	}
	public void setS1n390Kwh(Double s1n390Kwh) {
		this.s1n390Kwh = s1n390Kwh;
	}
	@Column(name = "S1N391_KWH", precision = 53, scale = 0)
	public Double getS1n391Kwh() {
		return s1n391Kwh;
	}
	public void setS1n391Kwh(Double s1n391Kwh) {
		this.s1n391Kwh = s1n391Kwh;
	}
	@Column(name = "S1N392_KWH", precision = 53, scale = 0)
	public Double getS1n392Kwh() {
		return s1n392Kwh;
	}
	public void setS1n392Kwh(Double s1n392Kwh) {
		this.s1n392Kwh = s1n392Kwh;
	}
	@Column(name = "S1N393_KWH", precision = 53, scale = 0)
	public Double getS1n393Kwh() {
		return s1n393Kwh;
	}
	public void setS1n393Kwh(Double s1n393Kwh) {
		this.s1n393Kwh = s1n393Kwh;
	}
	@Column(name = "S1N394_KWH", precision = 53, scale = 0)
	public Double getS1n394Kwh() {
		return s1n394Kwh;
	}
	public void setS1n394Kwh(Double s1n394Kwh) {
		this.s1n394Kwh = s1n394Kwh;
	}
	@Column(name = "S1N395_KWH", precision = 53, scale = 0)
	public Double getS1n395Kwh() {
		return s1n395Kwh;
	}
	public void setS1n395Kwh(Double s1n395Kwh) {
		this.s1n395Kwh = s1n395Kwh;
	}
	@Column(name = "S1N396_KWH", precision = 53, scale = 0)
	public Double getS1n396Kwh() {
		return s1n396Kwh;
	}
	public void setS1n396Kwh(Double s1n396Kwh) {
		this.s1n396Kwh = s1n396Kwh;
	}
	@Column(name = "S1N397_KWH", precision = 53, scale = 0)
	public Double getS1n397Kwh() {
		return s1n397Kwh;
	}
	public void setS1n397Kwh(Double s1n397Kwh) {
		this.s1n397Kwh = s1n397Kwh;
	}
	@Column(name = "S1N398_KWH", precision = 53, scale = 0)
	public Double getS1n398Kwh() {
		return s1n398Kwh;
	}
	public void setS1n398Kwh(Double s1n398Kwh) {
		this.s1n398Kwh = s1n398Kwh;
	}
	@Column(name = "S1N399_KWH", precision = 53, scale = 0)
	public Double getS1n399Kwh() {
		return s1n399Kwh;
	}
	public void setS1n399Kwh(Double s1n399Kwh) {
		this.s1n399Kwh = s1n399Kwh;
	}
	@Column(name = "S1N400_KWH", precision = 53, scale = 0)
	public Double getS1n400Kwh() {
		return s1n400Kwh;
	}
	public void setS1n400Kwh(Double s1n400Kwh) {
		this.s1n400Kwh = s1n400Kwh;
	}
	@Column(name = "S1N401_KWH", precision = 53, scale = 0)
	public Double getS1n401Kwh() {
		return s1n401Kwh;
	}
	public void setS1n401Kwh(Double s1n401Kwh) {
		this.s1n401Kwh = s1n401Kwh;
	}
	@Column(name = "S1N402_KWH", precision = 53, scale = 0)
	public Double getS1n402Kwh() {
		return s1n402Kwh;
	}
	public void setS1n402Kwh(Double s1n402Kwh) {
		this.s1n402Kwh = s1n402Kwh;
	}
	@Column(name = "S1N403_KWH", precision = 53, scale = 0)
	public Double getS1n403Kwh() {
		return s1n403Kwh;
	}
	public void setS1n403Kwh(Double s1n403Kwh) {
		this.s1n403Kwh = s1n403Kwh;
	}
	@Column(name = "S1N404_KWH", precision = 53, scale = 0)
	public Double getS1n404Kwh() {
		return s1n404Kwh;
	}
	public void setS1n404Kwh(Double s1n404Kwh) {
		this.s1n404Kwh = s1n404Kwh;
	}
	@Column(name = "S1N405_KWH", precision = 53, scale = 0)
	public Double getS1n405Kwh() {
		return s1n405Kwh;
	}
	public void setS1n405Kwh(Double s1n405Kwh) {
		this.s1n405Kwh = s1n405Kwh;
	}
	@Column(name = "S1N406_KWH", precision = 53, scale = 0)
	public Double getS1n406Kwh() {
		return s1n406Kwh;
	}
	public void setS1n406Kwh(Double s1n406Kwh) {
		this.s1n406Kwh = s1n406Kwh;
	}
	@Column(name = "S1N407_KWH", precision = 53, scale = 0)
	public Double getS1n407Kwh() {
		return s1n407Kwh;
	}
	public void setS1n407Kwh(Double s1n407Kwh) {
		this.s1n407Kwh = s1n407Kwh;
	}
	@Column(name = "S1N408_KWH", precision = 53, scale = 0)
	public Double getS1n408Kwh() {
		return s1n408Kwh;
	}
	public void setS1n408Kwh(Double s1n408Kwh) {
		this.s1n408Kwh = s1n408Kwh;
	}
	@Column(name = "S1N409_KWH", precision = 53, scale = 0)
	public Double getS1n409Kwh() {
		return s1n409Kwh;
	}
	public void setS1n409Kwh(Double s1n409Kwh) {
		this.s1n409Kwh = s1n409Kwh;
	}
	@Column(name = "S1N410_KWH", precision = 53, scale = 0)
	public Double getS1n410Kwh() {
		return s1n410Kwh;
	}
	public void setS1n410Kwh(Double s1n410Kwh) {
		this.s1n410Kwh = s1n410Kwh;
	}
	@Column(name = "S1N411_KWH", precision = 53, scale = 0)
	public Double getS1n411Kwh() {
		return s1n411Kwh;
	}
	public void setS1n411Kwh(Double s1n411Kwh) {
		this.s1n411Kwh = s1n411Kwh;
	}
	@Column(name = "S1N412_KWH", precision = 53, scale = 0)
	public Double getS1n412Kwh() {
		return s1n412Kwh;
	}
	public void setS1n412Kwh(Double s1n412Kwh) {
		this.s1n412Kwh = s1n412Kwh;
	}
	@Column(name = "S1N413_KWH", precision = 53, scale = 0)
	public Double getS1n413Kwh() {
		return s1n413Kwh;
	}
	public void setS1n413Kwh(Double s1n413Kwh) {
		this.s1n413Kwh = s1n413Kwh;
	}
	@Column(name = "S1N414_KWH", precision = 53, scale = 0)
	public Double getS1n414Kwh() {
		return s1n414Kwh;
	}
	public void setS1n414Kwh(Double s1n414Kwh) {
		this.s1n414Kwh = s1n414Kwh;
	}
	@Column(name = "S1N415_KWH", precision = 53, scale = 0)
	public Double getS1n415Kwh() {
		return s1n415Kwh;
	}
	public void setS1n415Kwh(Double s1n415Kwh) {
		this.s1n415Kwh = s1n415Kwh;
	}
	@Column(name = "S1N416_KWH", precision = 53, scale = 0)
	public Double getS1n416Kwh() {
		return s1n416Kwh;
	}
	public void setS1n416Kwh(Double s1n416Kwh) {
		this.s1n416Kwh = s1n416Kwh;
	}
	@Column(name = "S1N417_KWH", precision = 53, scale = 0)
	public Double getS1n417Kwh() {
		return s1n417Kwh;
	}
	public void setS1n417Kwh(Double s1n417Kwh) {
		this.s1n417Kwh = s1n417Kwh;
	}
	@Column(name = "S1N418_KWH", precision = 53, scale = 0)
	public Double getS1n418Kwh() {
		return s1n418Kwh;
	}
	public void setS1n418Kwh(Double s1n418Kwh) {
		this.s1n418Kwh = s1n418Kwh;
	}
	@Column(name = "S1N419_KWH", precision = 53, scale = 0)
	public Double getS1n419Kwh() {
		return s1n419Kwh;
	}
	public void setS1n419Kwh(Double s1n419Kwh) {
		this.s1n419Kwh = s1n419Kwh;
	}
	@Column(name = "S1N420_KWH", precision = 53, scale = 0)
	public Double getS1n420Kwh() {
		return s1n420Kwh;
	}
	public void setS1n420Kwh(Double s1n420Kwh) {
		this.s1n420Kwh = s1n420Kwh;
	}
	@Column(name = "S1N421_KWH", precision = 53, scale = 0)
	public Double getS1n421Kwh() {
		return s1n421Kwh;
	}
	public void setS1n421Kwh(Double s1n421Kwh) {
		this.s1n421Kwh = s1n421Kwh;
	}
	@Column(name = "S1N422_KWH", precision = 53, scale = 0)
	public Double getS1n422Kwh() {
		return s1n422Kwh;
	}
	public void setS1n422Kwh(Double s1n422Kwh) {
		this.s1n422Kwh = s1n422Kwh;
	}
	@Column(name = "S1N423_KWH", precision = 53, scale = 0)
	public Double getS1n423Kwh() {
		return s1n423Kwh;
	}
	public void setS1n423Kwh(Double s1n423Kwh) {
		this.s1n423Kwh = s1n423Kwh;
	}
	@Column(name = "S1N424_KWH", precision = 53, scale = 0)
	public Double getS1n424Kwh() {
		return s1n424Kwh;
	}
	public void setS1n424Kwh(Double s1n424Kwh) {
		this.s1n424Kwh = s1n424Kwh;
	}
	@Column(name = "S1N425_KWH", precision = 53, scale = 0)
	public Double getS1n425Kwh() {
		return s1n425Kwh;
	}
	public void setS1n425Kwh(Double s1n425Kwh) {
		this.s1n425Kwh = s1n425Kwh;
	}
	@Column(name = "S1N426_KWH", precision = 53, scale = 0)
	public Double getS1n426Kwh() {
		return s1n426Kwh;
	}
	public void setS1n426Kwh(Double s1n426Kwh) {
		this.s1n426Kwh = s1n426Kwh;
	}
	@Column(name = "S1N427_KWH", precision = 53, scale = 0)
	public Double getS1n427Kwh() {
		return s1n427Kwh;
	}
	public void setS1n427Kwh(Double s1n427Kwh) {
		this.s1n427Kwh = s1n427Kwh;
	}
	@Column(name = "S1N428_KWH", precision = 53, scale = 0)
	public Double getS1n428Kwh() {
		return s1n428Kwh;
	}
	public void setS1n428Kwh(Double s1n428Kwh) {
		this.s1n428Kwh = s1n428Kwh;
	}
	@Column(name = "S1N429_KWH", precision = 53, scale = 0)
	public Double getS1n429Kwh() {
		return s1n429Kwh;
	}
	public void setS1n429Kwh(Double s1n429Kwh) {
		this.s1n429Kwh = s1n429Kwh;
	}
	@Column(name = "S1N430_KWH", precision = 53, scale = 0)
	public Double getS1n430Kwh() {
		return s1n430Kwh;
	}
	public void setS1n430Kwh(Double s1n430Kwh) {
		this.s1n430Kwh = s1n430Kwh;
	}
	@Column(name = "S1N431_KWH", precision = 53, scale = 0)
	public Double getS1n431Kwh() {
		return s1n431Kwh;
	}
	public void setS1n431Kwh(Double s1n431Kwh) {
		this.s1n431Kwh = s1n431Kwh;
	}
	@Column(name = "S1N432_KWH", precision = 53, scale = 0)
	public Double getS1n432Kwh() {
		return s1n432Kwh;
	}
	public void setS1n432Kwh(Double s1n432Kwh) {
		this.s1n432Kwh = s1n432Kwh;
	}
	@Column(name = "S1N433_KWH", precision = 53, scale = 0)
	public Double getS1n433Kwh() {
		return s1n433Kwh;
	}
	public void setS1n433Kwh(Double s1n433Kwh) {
		this.s1n433Kwh = s1n433Kwh;
	}
	@Column(name = "S1N434_KWH", precision = 53, scale = 0)
	public Double getS1n434Kwh() {
		return s1n434Kwh;
	}
	public void setS1n434Kwh(Double s1n434Kwh) {
		this.s1n434Kwh = s1n434Kwh;
	}
	@Column(name = "S1N435_KWH", precision = 53, scale = 0)
	public Double getS1n435Kwh() {
		return s1n435Kwh;
	}
	public void setS1n435Kwh(Double s1n435Kwh) {
		this.s1n435Kwh = s1n435Kwh;
	}
	@Column(name = "S1N436_KWH", precision = 53, scale = 0)
	public Double getS1n436Kwh() {
		return s1n436Kwh;
	}
	public void setS1n436Kwh(Double s1n436Kwh) {
		this.s1n436Kwh = s1n436Kwh;
	}
	@Column(name = "S1N437_KWH", precision = 53, scale = 0)
	public Double getS1n437Kwh() {
		return s1n437Kwh;
	}
	public void setS1n437Kwh(Double s1n437Kwh) {
		this.s1n437Kwh = s1n437Kwh;
	}
	@Column(name = "S1N438_KWH", precision = 53, scale = 0)
	public Double getS1n438Kwh() {
		return s1n438Kwh;
	}
	public void setS1n438Kwh(Double s1n438Kwh) {
		this.s1n438Kwh = s1n438Kwh;
	}
	@Column(name = "S1N439_KWH", precision = 53, scale = 0)
	public Double getS1n439Kwh() {
		return s1n439Kwh;
	}
	public void setS1n439Kwh(Double s1n439Kwh) {
		this.s1n439Kwh = s1n439Kwh;
	}
	@Column(name = "S1N440_KWH", precision = 53, scale = 0)
	public Double getS1n440Kwh() {
		return s1n440Kwh;
	}
	public void setS1n440Kwh(Double s1n440Kwh) {
		this.s1n440Kwh = s1n440Kwh;
	}
	@Column(name = "S1N441_KWH", precision = 53, scale = 0)
	public Double getS1n441Kwh() {
		return s1n441Kwh;
	}
	public void setS1n441Kwh(Double s1n441Kwh) {
		this.s1n441Kwh = s1n441Kwh;
	}
	@Column(name = "S1N442_KWH", precision = 53, scale = 0)
	public Double getS1n442Kwh() {
		return s1n442Kwh;
	}
	public void setS1n442Kwh(Double s1n442Kwh) {
		this.s1n442Kwh = s1n442Kwh;
	}
	@Column(name = "S1N443_KWH", precision = 53, scale = 0)
	public Double getS1n443Kwh() {
		return s1n443Kwh;
	}
	public void setS1n443Kwh(Double s1n443Kwh) {
		this.s1n443Kwh = s1n443Kwh;
	}
	@Column(name = "S1N444_KWH", precision = 53, scale = 0)
	public Double getS1n444Kwh() {
		return s1n444Kwh;
	}
	public void setS1n444Kwh(Double s1n444Kwh) {
		this.s1n444Kwh = s1n444Kwh;
	}
	@Column(name = "S1N445_KWH", precision = 53, scale = 0)
	public Double getS1n445Kwh() {
		return s1n445Kwh;
	}
	public void setS1n445Kwh(Double s1n445Kwh) {
		this.s1n445Kwh = s1n445Kwh;
	}
	@Column(name = "S1N446_KWH", precision = 53, scale = 0)
	public Double getS1n446Kwh() {
		return s1n446Kwh;
	}
	public void setS1n446Kwh(Double s1n446Kwh) {
		this.s1n446Kwh = s1n446Kwh;
	}
	@Column(name = "S1N447_KWH", precision = 53, scale = 0)
	public Double getS1n447Kwh() {
		return s1n447Kwh;
	}
	public void setS1n447Kwh(Double s1n447Kwh) {
		this.s1n447Kwh = s1n447Kwh;
	}
	@Column(name = "S1N448_KWH", precision = 53, scale = 0)
	public Double getS1n448Kwh() {
		return s1n448Kwh;
	}
	public void setS1n448Kwh(Double s1n448Kwh) {
		this.s1n448Kwh = s1n448Kwh;
	}
	@Column(name = "S1N449_KWH", precision = 53, scale = 0)
	public Double getS1n449Kwh() {
		return s1n449Kwh;
	}
	public void setS1n449Kwh(Double s1n449Kwh) {
		this.s1n449Kwh = s1n449Kwh;
	}
	@Column(name = "S1N450_KWH", precision = 53, scale = 0)
	public Double getS1n450Kwh() {
		return s1n450Kwh;
	}
	public void setS1n450Kwh(Double s1n450Kwh) {
		this.s1n450Kwh = s1n450Kwh;
	}
	@Column(name = "S1N451_KWH", precision = 53, scale = 0)
	public Double getS1n451Kwh() {
		return s1n451Kwh;
	}
	public void setS1n451Kwh(Double s1n451Kwh) {
		this.s1n451Kwh = s1n451Kwh;
	}
	@Column(name = "S1N452_KWH", precision = 53, scale = 0)
	public Double getS1n452Kwh() {
		return s1n452Kwh;
	}
	public void setS1n452Kwh(Double s1n452Kwh) {
		this.s1n452Kwh = s1n452Kwh;
	}
	@Column(name = "S1N453_KWH", precision = 53, scale = 0)
	public Double getS1n453Kwh() {
		return s1n453Kwh;
	}
	public void setS1n453Kwh(Double s1n453Kwh) {
		this.s1n453Kwh = s1n453Kwh;
	}
	@Column(name = "S1N454_KWH", precision = 53, scale = 0)
	public Double getS1n454Kwh() {
		return s1n454Kwh;
	}
	public void setS1n454Kwh(Double s1n454Kwh) {
		this.s1n454Kwh = s1n454Kwh;
	}
	@Column(name = "S1N455_KWH", precision = 53, scale = 0)
	public Double getS1n455Kwh() {
		return s1n455Kwh;
	}
	public void setS1n455Kwh(Double s1n455Kwh) {
		this.s1n455Kwh = s1n455Kwh;
	}
	@Column(name = "S1N456_KWH", precision = 53, scale = 0)
	public Double getS1n456Kwh() {
		return s1n456Kwh;
	}
	public void setS1n456Kwh(Double s1n456Kwh) {
		this.s1n456Kwh = s1n456Kwh;
	}
	@Column(name = "S1N457_KWH", precision = 53, scale = 0)
	public Double getS1n457Kwh() {
		return s1n457Kwh;
	}
	public void setS1n457Kwh(Double s1n457Kwh) {
		this.s1n457Kwh = s1n457Kwh;
	}
	@Column(name = "S1N458_KWH", precision = 53, scale = 0)
	public Double getS1n458Kwh() {
		return s1n458Kwh;
	}
	public void setS1n458Kwh(Double s1n458Kwh) {
		this.s1n458Kwh = s1n458Kwh;
	}
	@Column(name = "S1N459_KWH", precision = 53, scale = 0)
	public Double getS1n459Kwh() {
		return s1n459Kwh;
	}
	public void setS1n459Kwh(Double s1n459Kwh) {
		this.s1n459Kwh = s1n459Kwh;
	}
	@Column(name = "S1N460_KWH", precision = 53, scale = 0)
	public Double getS1n460Kwh() {
		return s1n460Kwh;
	}
	public void setS1n460Kwh(Double s1n460Kwh) {
		this.s1n460Kwh = s1n460Kwh;
	}
	@Column(name = "S1N461_KWH", precision = 53, scale = 0)
	public Double getS1n461Kwh() {
		return s1n461Kwh;
	}
	public void setS1n461Kwh(Double s1n461Kwh) {
		this.s1n461Kwh = s1n461Kwh;
	}
	@Column(name = "S1N462_KWH", precision = 53, scale = 0)
	public Double getS1n462Kwh() {
		return s1n462Kwh;
	}
	public void setS1n462Kwh(Double s1n462Kwh) {
		this.s1n462Kwh = s1n462Kwh;
	}
	@Column(name = "S1N463_KWH", precision = 53, scale = 0)
	public Double getS1n463Kwh() {
		return s1n463Kwh;
	}
	public void setS1n463Kwh(Double s1n463Kwh) {
		this.s1n463Kwh = s1n463Kwh;
	}
	@Column(name = "S1N464_KWH", precision = 53, scale = 0)
	public Double getS1n464Kwh() {
		return s1n464Kwh;
	}
	public void setS1n464Kwh(Double s1n464Kwh) {
		this.s1n464Kwh = s1n464Kwh;
	}
	@Column(name = "S1N465_KWH", precision = 53, scale = 0)
	public Double getS1n465Kwh() {
		return s1n465Kwh;
	}
	public void setS1n465Kwh(Double s1n465Kwh) {
		this.s1n465Kwh = s1n465Kwh;
	}
	@Column(name = "S1N466_KWH", precision = 53, scale = 0)
	public Double getS1n466Kwh() {
		return s1n466Kwh;
	}
	public void setS1n466Kwh(Double s1n466Kwh) {
		this.s1n466Kwh = s1n466Kwh;
	}
	@Column(name = "S1N467_KWH", precision = 53, scale = 0)
	public Double getS1n467Kwh() {
		return s1n467Kwh;
	}
	public void setS1n467Kwh(Double s1n467Kwh) {
		this.s1n467Kwh = s1n467Kwh;
	}
	@Column(name = "S1N468_KWH", precision = 53, scale = 0)
	public Double getS1n468Kwh() {
		return s1n468Kwh;
	}
	public void setS1n468Kwh(Double s1n468Kwh) {
		this.s1n468Kwh = s1n468Kwh;
	}
	@Column(name = "S1N469_KWH", precision = 53, scale = 0)
	public Double getS1n469Kwh() {
		return s1n469Kwh;
	}
	public void setS1n469Kwh(Double s1n469Kwh) {
		this.s1n469Kwh = s1n469Kwh;
	}
	@Column(name = "S1N470_KWH", precision = 53, scale = 0)
	public Double getS1n470Kwh() {
		return s1n470Kwh;
	}
	public void setS1n470Kwh(Double s1n470Kwh) {
		this.s1n470Kwh = s1n470Kwh;
	}
	@Column(name = "S1N471_KWH", precision = 53, scale = 0)
	public Double getS1n471Kwh() {
		return s1n471Kwh;
	}
	public void setS1n471Kwh(Double s1n471Kwh) {
		this.s1n471Kwh = s1n471Kwh;
	}
	@Column(name = "S1N472_KWH", precision = 53, scale = 0)
	public Double getS1n472Kwh() {
		return s1n472Kwh;
	}
	public void setS1n472Kwh(Double s1n472Kwh) {
		this.s1n472Kwh = s1n472Kwh;
	}
	@Column(name = "S1N473_KWH", precision = 53, scale = 0)
	public Double getS1n473Kwh() {
		return s1n473Kwh;
	}
	public void setS1n473Kwh(Double s1n473Kwh) {
		this.s1n473Kwh = s1n473Kwh;
	}
	@Column(name = "S1N474_KWH", precision = 53, scale = 0)
	public Double getS1n474Kwh() {
		return s1n474Kwh;
	}
	public void setS1n474Kwh(Double s1n474Kwh) {
		this.s1n474Kwh = s1n474Kwh;
	}
	@Column(name = "S1N475_KWH", precision = 53, scale = 0)
	public Double getS1n475Kwh() {
		return s1n475Kwh;
	}
	public void setS1n475Kwh(Double s1n475Kwh) {
		this.s1n475Kwh = s1n475Kwh;
	}
	@Column(name = "S1N476_KWH", precision = 53, scale = 0)
	public Double getS1n476Kwh() {
		return s1n476Kwh;
	}
	public void setS1n476Kwh(Double s1n476Kwh) {
		this.s1n476Kwh = s1n476Kwh;
	}
	@Column(name = "S1N477_KWH", precision = 53, scale = 0)
	public Double getS1n477Kwh() {
		return s1n477Kwh;
	}
	public void setS1n477Kwh(Double s1n477Kwh) {
		this.s1n477Kwh = s1n477Kwh;
	}
	@Column(name = "S1N478_KWH", precision = 53, scale = 0)
	public Double getS1n478Kwh() {
		return s1n478Kwh;
	}
	public void setS1n478Kwh(Double s1n478Kwh) {
		this.s1n478Kwh = s1n478Kwh;
	}
	@Column(name = "S1N479_KWH", precision = 53, scale = 0)
	public Double getS1n479Kwh() {
		return s1n479Kwh;
	}
	public void setS1n479Kwh(Double s1n479Kwh) {
		this.s1n479Kwh = s1n479Kwh;
	}
	@Column(name = "S1N480_KWH", precision = 53, scale = 0)
	public Double getS1n480Kwh() {
		return s1n480Kwh;
	}
	public void setS1n480Kwh(Double s1n480Kwh) {
		this.s1n480Kwh = s1n480Kwh;
	}
	
	public Tower_D_EB_kwh(){}
	public Tower_D_EB_kwh(Timestamp datestr, Double s1n372Kwh,
			Double s1n373Kwh, Double s1n374Kwh, Double s1n375Kwh,
			Double s1n376Kwh, Double s1n377Kwh, Double s1n378Kwh,
			Double s1n379Kwh, Double s1n380Kwh, Double s1n381Kwh,
			Double s1n382Kwh, Double s1n383Kwh, Double s1n384Kwh,
			Double s1n385Kwh, Double s1n386Kwh, Double s1n387Kwh,
			Double s1n388Kwh, Double s1n389Kwh, Double s1n390Kwh,
			Double s1n391Kwh, Double s1n392Kwh, Double s1n393Kwh,
			Double s1n394Kwh, Double s1n395Kwh, Double s1n396Kwh,
			Double s1n397Kwh, Double s1n398Kwh, Double s1n399Kwh,
			Double s1n400Kwh, Double s1n401Kwh, Double s1n402Kwh,
			Double s1n403Kwh, Double s1n404Kwh, Double s1n405Kwh,
			Double s1n406Kwh, Double s1n407Kwh, Double s1n408Kwh,
			Double s1n409Kwh, Double s1n410Kwh, Double s1n411Kwh,
			Double s1n412Kwh, Double s1n413Kwh, Double s1n414Kwh,
			Double s1n415Kwh, Double s1n416Kwh, Double s1n417Kwh,
			Double s1n418Kwh, Double s1n419Kwh, Double s1n420Kwh,
			Double s1n421Kwh, Double s1n422Kwh, Double s1n423Kwh,
			Double s1n424Kwh, Double s1n425Kwh, Double s1n426Kwh,
			Double s1n427Kwh, Double s1n428Kwh, Double s1n429Kwh,
			Double s1n430Kwh, Double s1n431Kwh, Double s1n432Kwh,
			Double s1n433Kwh, Double s1n434Kwh, Double s1n435Kwh,
			Double s1n436Kwh, Double s1n437Kwh, Double s1n438Kwh,
			Double s1n439Kwh, Double s1n440Kwh, Double s1n441Kwh,
			Double s1n442Kwh, Double s1n443Kwh, Double s1n444Kwh,
			Double s1n445Kwh, Double s1n446Kwh, Double s1n447Kwh,
			Double s1n448Kwh, Double s1n449Kwh, Double s1n450Kwh,
			Double s1n451Kwh, Double s1n452Kwh, Double s1n453Kwh,
			Double s1n454Kwh, Double s1n455Kwh, Double s1n456Kwh,
			Double s1n457Kwh, Double s1n458Kwh, Double s1n459Kwh,
			Double s1n460Kwh, Double s1n461Kwh, Double s1n462Kwh,
			Double s1n463Kwh, Double s1n464Kwh, Double s1n465Kwh,
			Double s1n466Kwh, Double s1n467Kwh, Double s1n468Kwh,
			Double s1n469Kwh, Double s1n470Kwh, Double s1n471Kwh,
			Double s1n472Kwh, Double s1n473Kwh, Double s1n474Kwh,
			Double s1n475Kwh, Double s1n476Kwh, Double s1n477Kwh,
			Double s1n478Kwh, Double s1n479Kwh, Double s1n480Kwh) {
		this.datestr = datestr;
		this.s1n372Kwh = s1n372Kwh;
		this.s1n373Kwh = s1n373Kwh;
		this.s1n374Kwh = s1n374Kwh;
		this.s1n375Kwh = s1n375Kwh;
		this.s1n376Kwh = s1n376Kwh;
		this.s1n377Kwh = s1n377Kwh;
		this.s1n378Kwh = s1n378Kwh;
		this.s1n379Kwh = s1n379Kwh;
		this.s1n380Kwh = s1n380Kwh;
		this.s1n381Kwh = s1n381Kwh;
		this.s1n382Kwh = s1n382Kwh;
		this.s1n383Kwh = s1n383Kwh;
		this.s1n384Kwh = s1n384Kwh;
		this.s1n385Kwh = s1n385Kwh;
		this.s1n386Kwh = s1n386Kwh;
		this.s1n387Kwh = s1n387Kwh;
		this.s1n388Kwh = s1n388Kwh;
		this.s1n389Kwh = s1n389Kwh;
		this.s1n390Kwh = s1n390Kwh;
		this.s1n391Kwh = s1n391Kwh;
		this.s1n392Kwh = s1n392Kwh;
		this.s1n393Kwh = s1n393Kwh;
		this.s1n394Kwh = s1n394Kwh;
		this.s1n395Kwh = s1n395Kwh;
		this.s1n396Kwh = s1n396Kwh;
		this.s1n397Kwh = s1n397Kwh;
		this.s1n398Kwh = s1n398Kwh;
		this.s1n399Kwh = s1n399Kwh;
		this.s1n400Kwh = s1n400Kwh;
		this.s1n401Kwh = s1n401Kwh;
		this.s1n402Kwh = s1n402Kwh;
		this.s1n403Kwh = s1n403Kwh;
		this.s1n404Kwh = s1n404Kwh;
		this.s1n405Kwh = s1n405Kwh;
		this.s1n406Kwh = s1n406Kwh;
		this.s1n407Kwh = s1n407Kwh;
		this.s1n408Kwh = s1n408Kwh;
		this.s1n409Kwh = s1n409Kwh;
		this.s1n410Kwh = s1n410Kwh;
		this.s1n411Kwh = s1n411Kwh;
		this.s1n412Kwh = s1n412Kwh;
		this.s1n413Kwh = s1n413Kwh;
		this.s1n414Kwh = s1n414Kwh;
		this.s1n415Kwh = s1n415Kwh;
		this.s1n416Kwh = s1n416Kwh;
		this.s1n417Kwh = s1n417Kwh;
		this.s1n418Kwh = s1n418Kwh;
		this.s1n419Kwh = s1n419Kwh;
		this.s1n420Kwh = s1n420Kwh;
		this.s1n421Kwh = s1n421Kwh;
		this.s1n422Kwh = s1n422Kwh;
		this.s1n423Kwh = s1n423Kwh;
		this.s1n424Kwh = s1n424Kwh;
		this.s1n425Kwh = s1n425Kwh;
		this.s1n426Kwh = s1n426Kwh;
		this.s1n427Kwh = s1n427Kwh;
		this.s1n428Kwh = s1n428Kwh;
		this.s1n429Kwh = s1n429Kwh;
		this.s1n430Kwh = s1n430Kwh;
		this.s1n431Kwh = s1n431Kwh;
		this.s1n432Kwh = s1n432Kwh;
		this.s1n433Kwh = s1n433Kwh;
		this.s1n434Kwh = s1n434Kwh;
		this.s1n435Kwh = s1n435Kwh;
		this.s1n436Kwh = s1n436Kwh;
		this.s1n437Kwh = s1n437Kwh;
		this.s1n438Kwh = s1n438Kwh;
		this.s1n439Kwh = s1n439Kwh;
		this.s1n440Kwh = s1n440Kwh;
		this.s1n441Kwh = s1n441Kwh;
		this.s1n442Kwh = s1n442Kwh;
		this.s1n443Kwh = s1n443Kwh;
		this.s1n444Kwh = s1n444Kwh;
		this.s1n445Kwh = s1n445Kwh;
		this.s1n446Kwh = s1n446Kwh;
		this.s1n447Kwh = s1n447Kwh;
		this.s1n448Kwh = s1n448Kwh;
		this.s1n449Kwh = s1n449Kwh;
		this.s1n450Kwh = s1n450Kwh;
		this.s1n451Kwh = s1n451Kwh;
		this.s1n452Kwh = s1n452Kwh;
		this.s1n453Kwh = s1n453Kwh;
		this.s1n454Kwh = s1n454Kwh;
		this.s1n455Kwh = s1n455Kwh;
		this.s1n456Kwh = s1n456Kwh;
		this.s1n457Kwh = s1n457Kwh;
		this.s1n458Kwh = s1n458Kwh;
		this.s1n459Kwh = s1n459Kwh;
		this.s1n460Kwh = s1n460Kwh;
		this.s1n461Kwh = s1n461Kwh;
		this.s1n462Kwh = s1n462Kwh;
		this.s1n463Kwh = s1n463Kwh;
		this.s1n464Kwh = s1n464Kwh;
		this.s1n465Kwh = s1n465Kwh;
		this.s1n466Kwh = s1n466Kwh;
		this.s1n467Kwh = s1n467Kwh;
		this.s1n468Kwh = s1n468Kwh;
		this.s1n469Kwh = s1n469Kwh;
		this.s1n470Kwh = s1n470Kwh;
		this.s1n471Kwh = s1n471Kwh;
		this.s1n472Kwh = s1n472Kwh;
		this.s1n473Kwh = s1n473Kwh;
		this.s1n474Kwh = s1n474Kwh;
		this.s1n475Kwh = s1n475Kwh;
		this.s1n476Kwh = s1n476Kwh;
		this.s1n477Kwh = s1n477Kwh;
		this.s1n478Kwh = s1n478Kwh;
		this.s1n479Kwh = s1n479Kwh;
		this.s1n480Kwh = s1n480Kwh;
	}

	
}
