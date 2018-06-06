package com.bcits.bfm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="Tower_F_EB_kwh.findALL" ,query="Select tower from Tower_F_EB_kwh tower")
})
@Entity
@Table(name="Tower_F_EB_kwh",schema="dbo",catalog="EMS")
public class Tower_F_EB_kwh {
	// Fields

	private Timestamp datestr;	
	private Double s1n480Kwh;
	private Double s1n481Kwh;
	private Double s1n482Kwh;
	private Double s1n483Kwh;
	private Double s1n484Kwh;
	private Double s1n485Kwh;
	private Double s1n486Kwh;
	private Double s1n487Kwh;
	private Double s1n488Kwh;
	private Double s1n489Kwh;
	private Double s1n490Kwh;
	private Double s1n491Kwh;
	private Double s1n492Kwh;
	private Double s1n493Kwh;
	private Double s1n494Kwh;
	private Double s1n495Kwh;
	private Double s1n496Kwh;
	private Double s1n497Kwh;
	private Double s1n498Kwh;
	private Double s1n499Kwh;
	private Double s1n500Kwh;
	private Double s1n501Kwh;
	private Double s1n502Kwh;
	private Double s1n503Kwh;
	private Double s1n504Kwh;
	private Double s1n505Kwh;
	private Double s1n506Kwh;
	private Double s1n507Kwh;
	private Double s1n508Kwh;
	private Double s1n509Kwh;
	private Double s1n510Kwh;
	private Double s1n511Kwh;
	private Double s1n512Kwh;
	private Double s1n513Kwh;
	private Double s1n514Kwh;
	private Double s1n515Kwh;
	private Double s1n516Kwh;
	private Double s1n517Kwh;
	private Double s1n518Kwh;
	private Double s1n519Kwh;
	private Double s1n520Kwh;
	private Double s1n521Kwh;
	private Double s1n522Kwh;
	private Double s1n523Kwh;
	private Double s1n524Kwh;
	private Double s1n525Kwh;
	private Double s1n526Kwh;
	private Double s1n527Kwh;
	private Double s1n528Kwh;
	private Double s1n529Kwh;
	private Double s1n530Kwh;
	private Double s1n531Kwh;
	private Double s1n532Kwh;
	private Double s1n533Kwh;
	private Double s1n534Kwh;
	private Double s1n535Kwh;
	private Double s1n536Kwh;
	private Double s1n537Kwh;
	private Double s1n538Kwh;
	private Double s1n539Kwh;
	private Double s1n540Kwh;
	private Double s1n541Kwh;
	private Double s1n542Kwh;
	private Double s1n543Kwh;
	private Double s1n544Kwh;
	private Double s1n545Kwh;
	private Double s1n546Kwh;
	private Double s1n547Kwh;
	private Double s1n548Kwh;
	private Double s1n549Kwh;
	private Double s1n550Kwh;
	private Double s1n551Kwh;
	private Double s1n552Kwh;
	private Double s1n553Kwh;
	private Double s1n554Kwh;
	private Double s1n555Kwh;
	private Double s1n556Kwh;
	private Double s1n557Kwh;
	private Double s1n558Kwh;
	private Double s1n559Kwh;
	private Double s1n560Kwh;
	private Double s1n561Kwh;
	private Double s1n562Kwh;
	private Double s1n563Kwh;
	private Double s1n564Kwh;
	private Double s1n565Kwh;
	private Double s1n566Kwh;
	private Double s1n567Kwh;
	private Double s1n568Kwh;
	private Double s1n569Kwh;
	private Double s1n570Kwh;
	private Double s1n571Kwh;
	private Double s1n572Kwh;
	private Double s1n573Kwh;
	private Double s1n574Kwh;
	private Double s1n575Kwh;
	private Double s1n576Kwh;
	private Double s1n577Kwh;
	private Double s1n578Kwh;
	private Double s1n579Kwh;
	private Double s1n580Kwh;
	private Double s1n581Kwh;
	private Double s1n582Kwh;
	private Double s1n583Kwh;
	private Double s1n584Kwh;
	private Double s1n585Kwh;
	private Double s1n586Kwh;
	private Double s1n587Kwh;
	private Double s1n588Kwh;
	private Double s1n589Kwh;
	private Double s1n590Kwh;
	private Double s1n591Kwh;
	private Double s1n592Kwh;
	private Double s1n593Kwh;
	
	@Id
	@Column(name = "Datestr", length = 23)
	public Timestamp getDatestr() {
		return datestr;
	}
	public void setDatestr(Timestamp datestr) {
		this.datestr = datestr;
	}
	@Column(name = "S1N480_KWH", precision = 53, scale = 0)
	public Double getS1n480Kwh() {
		return s1n480Kwh;
	}
	public void setS1n480Kwh(Double s1n480Kwh) {
		this.s1n480Kwh = s1n480Kwh;
	}
	@Column(name = "S1N481_KWH", precision = 53, scale = 0)
	public Double getS1n481Kwh() {
		return s1n481Kwh;
	}
	public void setS1n481Kwh(Double s1n481Kwh) {
		this.s1n481Kwh = s1n481Kwh;
	}
	@Column(name = "S1N482_KWH", precision = 53, scale = 0)
	public Double getS1n482Kwh() {
		return s1n482Kwh;
	}
	public void setS1n482Kwh(Double s1n482Kwh) {
		this.s1n482Kwh = s1n482Kwh;
	}
	@Column(name = "S1N483_KWH", precision = 53, scale = 0)
	public Double getS1n483Kwh() {
		return s1n483Kwh;
	}
	public void setS1n483Kwh(Double s1n483Kwh) {
		this.s1n483Kwh = s1n483Kwh;
	}
	@Column(name = "S1N484_KWH", precision = 53, scale = 0)
	public Double getS1n484Kwh() {
		return s1n484Kwh;
	}
	public void setS1n484Kwh(Double s1n484Kwh) {
		this.s1n484Kwh = s1n484Kwh;
	}
	@Column(name = "S1N485_KWH", precision = 53, scale = 0)
	public Double getS1n485Kwh() {
		return s1n485Kwh;
	}
	public void setS1n485Kwh(Double s1n485Kwh) {
		this.s1n485Kwh = s1n485Kwh;
	}
	@Column(name = "S1N486_KWH", precision = 53, scale = 0)
	public Double getS1n486Kwh() {
		return s1n486Kwh;
	}
	public void setS1n486Kwh(Double s1n486Kwh) {
		this.s1n486Kwh = s1n486Kwh;
	}
	@Column(name = "S1N487_KWH", precision = 53, scale = 0)
	public Double getS1n487Kwh() {
		return s1n487Kwh;
	}
	public void setS1n487Kwh(Double s1n487Kwh) {
		this.s1n487Kwh = s1n487Kwh;
	}
	@Column(name = "S1N488_KWH", precision = 53, scale = 0)
	public Double getS1n488Kwh() {
		return s1n488Kwh;
	}
	public void setS1n488Kwh(Double s1n488Kwh) {
		this.s1n488Kwh = s1n488Kwh;
	}
	@Column(name = "S1N489_KWH", precision = 53, scale = 0)
	public Double getS1n489Kwh() {
		return s1n489Kwh;
	}
	public void setS1n489Kwh(Double s1n489Kwh) {
		this.s1n489Kwh = s1n489Kwh;
	}
	@Column(name = "S1N490_KWH", precision = 53, scale = 0)
	public Double getS1n490Kwh() {
		return s1n490Kwh;
	}
	public void setS1n490Kwh(Double s1n490Kwh) {
		this.s1n490Kwh = s1n490Kwh;
	}
	@Column(name = "S1N491_KWH", precision = 53, scale = 0)
	public Double getS1n491Kwh() {
		return s1n491Kwh;
	}
	public void setS1n491Kwh(Double s1n491Kwh) {
		this.s1n491Kwh = s1n491Kwh;
	}
	@Column(name = "S1N492_KWH", precision = 53, scale = 0)
	public Double getS1n492Kwh() {
		return s1n492Kwh;
	}
	public void setS1n492Kwh(Double s1n492Kwh) {
		this.s1n492Kwh = s1n492Kwh;
	}
	@Column(name = "S1N493_KWH", precision = 53, scale = 0)
	public Double getS1n493Kwh() {
		return s1n493Kwh;
	}
	public void setS1n493Kwh(Double s1n493Kwh) {
		this.s1n493Kwh = s1n493Kwh;
	}
	@Column(name = "S1N494_KWH", precision = 53, scale = 0)
	public Double getS1n494Kwh() {
		return s1n494Kwh;
	}
	public void setS1n494Kwh(Double s1n494Kwh) {
		this.s1n494Kwh = s1n494Kwh;
	}
	@Column(name = "S1N495_KWH", precision = 53, scale = 0)
	public Double getS1n495Kwh() {
		return s1n495Kwh;
	}
	public void setS1n495Kwh(Double s1n495Kwh) {
		this.s1n495Kwh = s1n495Kwh;
	}
	public Double getS1n496Kwh() {
		return s1n496Kwh;
	}
	@Column(name = "S1N496_KWH", precision = 53, scale = 0)
	public void setS1n496Kwh(Double s1n496Kwh) {
		this.s1n496Kwh = s1n496Kwh;
	}
	public Double getS1n497Kwh() {
		return s1n497Kwh;
	}
	@Column(name = "S1N497_KWH", precision = 53, scale = 0)
	public void setS1n497Kwh(Double s1n497Kwh) {
		this.s1n497Kwh = s1n497Kwh;
	}
	@Column(name = "S1N498_KWH", precision = 53, scale = 0)
	public Double getS1n498Kwh() {
		return s1n498Kwh;
	}
	
	public void setS1n498Kwh(Double s1n498Kwh) {
		this.s1n498Kwh = s1n498Kwh;
	}
	@Column(name = "S1N499_KWH", precision = 53, scale = 0)
	public Double getS1n499Kwh() {
		return s1n499Kwh;
	}
	public void setS1n499Kwh(Double s1n499Kwh) {
		this.s1n499Kwh = s1n499Kwh;
	}
	@Column(name = "S1N500_KWH", precision = 53, scale = 0)
	public Double getS1n500Kwh() {
		return s1n500Kwh;
	}
	public void setS1n500Kwh(Double s1n500Kwh) {
		this.s1n500Kwh = s1n500Kwh;
	}
	@Column(name = "S1N501_KWH", precision = 53, scale = 0)
	public Double getS1n501Kwh() {
		return s1n501Kwh;
	}
	public void setS1n501Kwh(Double s1n501Kwh) {
		this.s1n501Kwh = s1n501Kwh;
	}
	@Column(name = "S1N502_KWH", precision = 53, scale = 0)
	public Double getS1n502Kwh() {
		return s1n502Kwh;
	}
	public void setS1n502Kwh(Double s1n502Kwh) {
		this.s1n502Kwh = s1n502Kwh;
	}
	@Column(name = "S1N503_KWH", precision = 53, scale = 0)
	public Double getS1n503Kwh() {
		return s1n503Kwh;
	}
	public void setS1n503Kwh(Double s1n503Kwh) {
		this.s1n503Kwh = s1n503Kwh;
	}
	@Column(name = "S1N504_KWH", precision = 53, scale = 0)
	public Double getS1n504Kwh() {
		return s1n504Kwh;
	}
	
	
	public void setS1n504Kwh(Double s1n504Kwh) {
		this.s1n504Kwh = s1n504Kwh;
	}
	@Column(name = "S1N505_KWH", precision = 53, scale = 0)
	public Double getS1n505Kwh() {
		return s1n505Kwh;
	}
	
	public void setS1n505Kwh(Double s1n505Kwh) {
		this.s1n505Kwh = s1n505Kwh;
	}
	@Column(name = "S1N506_KWH", precision = 53, scale = 0)
	public Double getS1n506Kwh() {
		return s1n506Kwh;
	}
	public void setS1n506Kwh(Double s1n506Kwh) {
		this.s1n506Kwh = s1n506Kwh;
	}
	@Column(name = "S1N507_KWH", precision = 53, scale = 0)
	public Double getS1n507Kwh() {
		return s1n507Kwh;
	}
	public void setS1n507Kwh(Double s1n507Kwh) {
		this.s1n507Kwh = s1n507Kwh;
	}
	@Column(name = "S1N508_KWH", precision = 53, scale = 0)
	public Double getS1n508Kwh() {
		return s1n508Kwh;
	}
	public void setS1n508Kwh(Double s1n508Kwh) {
		this.s1n508Kwh = s1n508Kwh;
	}
	@Column(name = "S1N509_KWH", precision = 53, scale = 0)
	public Double getS1n509Kwh() {
		return s1n509Kwh;
	}
	public void setS1n509Kwh(Double s1n509Kwh) {
		this.s1n509Kwh = s1n509Kwh;
	}
	@Column(name = "S1N510_KWH", precision = 53, scale = 0)
	public Double getS1n510Kwh() {
		return s1n510Kwh;
	}
	public void setS1n510Kwh(Double s1n510Kwh) {
		this.s1n510Kwh = s1n510Kwh;
	}
	@Column(name = "S1N511_KWH", precision = 53, scale = 0)
	public Double getS1n511Kwh() {
		return s1n511Kwh;
	}
	
	public void setS1n511Kwh(Double s1n511Kwh) {
		this.s1n511Kwh = s1n511Kwh;
	}
	@Column(name = "S1N512_KWH", precision = 53, scale = 0)
	public Double getS1n512Kwh() {
		return s1n512Kwh;
	}
	public void setS1n512Kwh(Double s1n512Kwh) {
		this.s1n512Kwh = s1n512Kwh;
	}
	@Column(name = "S1N513_KWH", precision = 53, scale = 0)
	public Double getS1n513Kwh() {
		return s1n513Kwh;
	}
	public void setS1n513Kwh(Double s1n513Kwh) {
		this.s1n513Kwh = s1n513Kwh;
	}
	@Column(name = "S1N514_KWH", precision = 53, scale = 0)
	public Double getS1n514Kwh() {
		return s1n514Kwh;
	}
	public void setS1n514Kwh(Double s1n514Kwh) {
		this.s1n514Kwh = s1n514Kwh;
	}
	@Column(name = "S1N515_KWH", precision = 53, scale = 0)
	public Double getS1n515Kwh() {
		return s1n515Kwh;
	}
	public void setS1n515Kwh(Double s1n515Kwh) {
		this.s1n515Kwh = s1n515Kwh;
	}
	@Column(name = "S1N516_KWH", precision = 53, scale = 0)
	public Double getS1n516Kwh() {
		return s1n516Kwh;
	}
	public void setS1n516Kwh(Double s1n516Kwh) {
		this.s1n516Kwh = s1n516Kwh;
	}
	@Column(name = "S1N517_KWH", precision = 53, scale = 0)
	public Double getS1n517Kwh() {
		return s1n517Kwh;
	}
	public void setS1n517Kwh(Double s1n517Kwh) {
		this.s1n517Kwh = s1n517Kwh;
	}
	@Column(name = "S1N518_KWH", precision = 53, scale = 0)
	public Double getS1n518Kwh() {
		return s1n518Kwh;
	}
	public void setS1n518Kwh(Double s1n518Kwh) {
		this.s1n518Kwh = s1n518Kwh;
	}
	@Column(name = "S1N519_KWH", precision = 53, scale = 0)
	public Double getS1n519Kwh() {
		return s1n519Kwh;
	}
	public void setS1n519Kwh(Double s1n519Kwh) {
		this.s1n519Kwh = s1n519Kwh;
	}
	@Column(name = "S1N520_KWH", precision = 53, scale = 0)
	public Double getS1n520Kwh() {
		return s1n520Kwh;
	}
	public void setS1n520Kwh(Double s1n520Kwh) {
		this.s1n520Kwh = s1n520Kwh;
	}
	@Column(name = "S1N521_KWH", precision = 53, scale = 0)
	public Double getS1n521Kwh() {
		return s1n521Kwh;
	}
	public void setS1n521Kwh(Double s1n521Kwh) {
		this.s1n521Kwh = s1n521Kwh;
	}
	@Column(name = "S1N522_KWH", precision = 53, scale = 0)
	public Double getS1n522Kwh() {
		return s1n522Kwh;
	}
	public void setS1n522Kwh(Double s1n522Kwh) {
		this.s1n522Kwh = s1n522Kwh;
	}
	@Column(name = "S1N523_KWH", precision = 53, scale = 0)
	public Double getS1n523Kwh() {
		return s1n523Kwh;
	}
	public void setS1n523Kwh(Double s1n523Kwh) {
		this.s1n523Kwh = s1n523Kwh;
	}
	@Column(name = "S1N524_KWH", precision = 53, scale = 0)
	public Double getS1n524Kwh() {
		return s1n524Kwh;
	}
	public void setS1n524Kwh(Double s1n524Kwh) {
		this.s1n524Kwh = s1n524Kwh;
	}
	@Column(name = "S1N525_KWH", precision = 53, scale = 0)
	public Double getS1n525Kwh() {
		return s1n525Kwh;
	}
	public void setS1n525Kwh(Double s1n525Kwh) {
		this.s1n525Kwh = s1n525Kwh;
	}
	@Column(name = "S1N526_KWH", precision = 53, scale = 0)
	public Double getS1n526Kwh() {
		return s1n526Kwh;
	}
	public void setS1n526Kwh(Double s1n526Kwh) {
		this.s1n526Kwh = s1n526Kwh;
	}
	@Column(name = "S1N527_KWH", precision = 53, scale = 0)
	public Double getS1n527Kwh() {
		return s1n527Kwh;
	}
	public void setS1n527Kwh(Double s1n527Kwh) {
		this.s1n527Kwh = s1n527Kwh;
	}
	@Column(name = "S1N528_KWH", precision = 53, scale = 0)
	public Double getS1n528Kwh() {
		return s1n528Kwh;
	}
	public void setS1n528Kwh(Double s1n528Kwh) {
		this.s1n528Kwh = s1n528Kwh;
	}
	@Column(name = "S1N529_KWH", precision = 53, scale = 0)
	public Double getS1n529Kwh() {
		return s1n529Kwh;
	}
	public void setS1n529Kwh(Double s1n529Kwh) {
		this.s1n529Kwh = s1n529Kwh;
	}
	@Column(name = "S1N530_KWH", precision = 53, scale = 0)
	public Double getS1n530Kwh() {
		return s1n530Kwh;
	}
	public void setS1n530Kwh(Double s1n530Kwh) {
		this.s1n530Kwh = s1n530Kwh;
	}
	@Column(name = "S1N531_KWH", precision = 53, scale = 0)
	public Double getS1n531Kwh() {
		return s1n531Kwh;
	}
	public void setS1n531Kwh(Double s1n531Kwh) {
		this.s1n531Kwh = s1n531Kwh;
	}
	@Column(name = "S1N532_KWH", precision = 53, scale = 0)
	public Double getS1n532Kwh() {
		return s1n532Kwh;
	}
	public void setS1n532Kwh(Double s1n532Kwh) {
		this.s1n532Kwh = s1n532Kwh;
	}
	@Column(name = "S1N533_KWH", precision = 53, scale = 0)
	public Double getS1n533Kwh() {
		return s1n533Kwh;
	}
	public void setS1n533Kwh(Double s1n533Kwh) {
		this.s1n533Kwh = s1n533Kwh;
	}
	@Column(name = "S1N534_KWH", precision = 53, scale = 0)
	public Double getS1n534Kwh() {
		return s1n534Kwh;
	}
	public void setS1n534Kwh(Double s1n534Kwh) {
		this.s1n534Kwh = s1n534Kwh;
	}
	@Column(name = "S1N535_KWH", precision = 53, scale = 0)
	public Double getS1n535Kwh() {
		return s1n535Kwh;
	}
	public void setS1n535Kwh(Double s1n535Kwh) {
		this.s1n535Kwh = s1n535Kwh;
	}
	@Column(name = "S1N536_KWH", precision = 53, scale = 0)
	public Double getS1n536Kwh() {
		return s1n536Kwh;
	}
	public void setS1n536Kwh(Double s1n536Kwh) {
		this.s1n536Kwh = s1n536Kwh;
	}
	@Column(name = "S1N537_KWH", precision = 53, scale = 0)
	public Double getS1n537Kwh() {
		return s1n537Kwh;
	}
	public void setS1n537Kwh(Double s1n537Kwh) {
		this.s1n537Kwh = s1n537Kwh;
	}
	@Column(name = "S1N538_KWH", precision = 53, scale = 0)
	public Double getS1n538Kwh() {
		return s1n538Kwh;
	}
	public void setS1n538Kwh(Double s1n538Kwh) {
		this.s1n538Kwh = s1n538Kwh;
	}
	@Column(name = "S1N539_KWH", precision = 53, scale = 0)
	public Double getS1n539Kwh() {
		return s1n539Kwh;
	}
	public void setS1n539Kwh(Double s1n539Kwh) {
		this.s1n539Kwh = s1n539Kwh;
	}
	@Column(name = "S1N540_KWH", precision = 53, scale = 0)
	public Double getS1n540Kwh() {
		return s1n540Kwh;
	}
	public void setS1n540Kwh(Double s1n540Kwh) {
		this.s1n540Kwh = s1n540Kwh;
	}
	@Column(name = "S1N541_KWH", precision = 53, scale = 0)
	public Double getS1n541Kwh() {
		return s1n541Kwh;
	}
	public void setS1n541Kwh(Double s1n541Kwh) {
		this.s1n541Kwh = s1n541Kwh;
	}
	@Column(name = "S1N542_KWH", precision = 53, scale = 0)
	public Double getS1n542Kwh() {
		return s1n542Kwh;
	}
	public void setS1n542Kwh(Double s1n542Kwh) {
		this.s1n542Kwh = s1n542Kwh;
	}
	@Column(name = "S1N543_KWH", precision = 53, scale = 0)
	public Double getS1n543Kwh() {
		return s1n543Kwh;
	}
	public void setS1n543Kwh(Double s1n543Kwh) {
		this.s1n543Kwh = s1n543Kwh;
	}
	@Column(name = "S1N544_KWH", precision = 53, scale = 0)
	public Double getS1n544Kwh() {
		return s1n544Kwh;
	}
	public void setS1n544Kwh(Double s1n544Kwh) {
		this.s1n544Kwh = s1n544Kwh;
	}
	@Column(name = "S1N545_KWH", precision = 53, scale = 0)
	public Double getS1n545Kwh() {
		return s1n545Kwh;
	}
	public void setS1n545Kwh(Double s1n545Kwh) {
		this.s1n545Kwh = s1n545Kwh;
	}
	@Column(name = "S1N546_KWH", precision = 53, scale = 0)
	public Double getS1n546Kwh() {
		return s1n546Kwh;
	}
	public void setS1n546Kwh(Double s1n546Kwh) {
		this.s1n546Kwh = s1n546Kwh;
	}
	@Column(name = "S1N547_KWH", precision = 53, scale = 0)
	public Double getS1n547Kwh() {
		return s1n547Kwh;
	}
	public void setS1n547Kwh(Double s1n547Kwh) {
		this.s1n547Kwh = s1n547Kwh;
	}
	@Column(name = "S1N548_KWH", precision = 53, scale = 0)
	public Double getS1n548Kwh() {
		return s1n548Kwh;
	}
	public void setS1n548Kwh(Double s1n548Kwh) {
		this.s1n548Kwh = s1n548Kwh;
	}
	@Column(name = "S1N549_KWH", precision = 53, scale = 0)
	public Double getS1n549Kwh() {
		return s1n549Kwh;
	}
	public void setS1n549Kwh(Double s1n549Kwh) {
		this.s1n549Kwh = s1n549Kwh;
	}
	@Column(name = "S1N550_KWH", precision = 53, scale = 0)
	public Double getS1n550Kwh() {
		return s1n550Kwh;
	}
	public void setS1n550Kwh(Double s1n550Kwh) {
		this.s1n550Kwh = s1n550Kwh;
	}
	@Column(name = "S1N551_KWH", precision = 53, scale = 0)
	public Double getS1n551Kwh() {
		return s1n551Kwh;
	}
	public void setS1n551Kwh(Double s1n551Kwh) {
		this.s1n551Kwh = s1n551Kwh;
	}
	@Column(name = "S1N552_KWH", precision = 53, scale = 0)
	public Double getS1n552Kwh() {
		return s1n552Kwh;
	}
	public void setS1n552Kwh(Double s1n552Kwh) {
		this.s1n552Kwh = s1n552Kwh;
	}
	@Column(name = "S1N553_KWH", precision = 53, scale = 0)
	public Double getS1n553Kwh() {
		return s1n553Kwh;
	}
	public void setS1n553Kwh(Double s1n553Kwh) {
		this.s1n553Kwh = s1n553Kwh;
	}
	@Column(name = "S1N554_KWH", precision = 53, scale = 0)
	public Double getS1n554Kwh() {
		return s1n554Kwh;
	}
	public void setS1n554Kwh(Double s1n554Kwh) {
		this.s1n554Kwh = s1n554Kwh;
	}
	@Column(name = "S1N555_KWH", precision = 53, scale = 0)
	public Double getS1n555Kwh() {
		return s1n555Kwh;
	}
	public void setS1n555Kwh(Double s1n555Kwh) {
		this.s1n555Kwh = s1n555Kwh;
	}
	@Column(name = "S1N556_KWH", precision = 53, scale = 0)
	public Double getS1n556Kwh() {
		return s1n556Kwh;
	}
	public void setS1n556Kwh(Double s1n556Kwh) {
		this.s1n556Kwh = s1n556Kwh;
	}
	@Column(name = "S1N557_KWH", precision = 53, scale = 0)
	public Double getS1n557Kwh() {
		return s1n557Kwh;
	}
	public void setS1n557Kwh(Double s1n557Kwh) {
		this.s1n557Kwh = s1n557Kwh;
	}
	@Column(name = "S1N558_KWH", precision = 53, scale = 0)
	public Double getS1n558Kwh() {
		return s1n558Kwh;
	}
	public void setS1n558Kwh(Double s1n558Kwh) {
		this.s1n558Kwh = s1n558Kwh;
	}
	@Column(name = "S1N559_KWH", precision = 53, scale = 0)
	public Double getS1n559Kwh() {
		return s1n559Kwh;
	}
	public void setS1n559Kwh(Double s1n559Kwh) {
		this.s1n559Kwh = s1n559Kwh;
	}
	@Column(name = "S1N560_KWH", precision = 53, scale = 0)
	public Double getS1n560Kwh() {
		return s1n560Kwh;
	}
	public void setS1n560Kwh(Double s1n560Kwh) {
		this.s1n560Kwh = s1n560Kwh;
	}
	@Column(name = "S1N561_KWH", precision = 53, scale = 0)
	public Double getS1n561Kwh() {
		return s1n561Kwh;
	}
	public void setS1n561Kwh(Double s1n561Kwh) {
		this.s1n561Kwh = s1n561Kwh;
	}
	@Column(name = "S1N562_KWH", precision = 53, scale = 0)
	public Double getS1n562Kwh() {
		return s1n562Kwh;
	}
	public void setS1n562Kwh(Double s1n562Kwh) {
		this.s1n562Kwh = s1n562Kwh;
	}
	@Column(name = "S1N563_KWH", precision = 53, scale = 0)
	public Double getS1n563Kwh() {
		return s1n563Kwh;
	}
	public void setS1n563Kwh(Double s1n563Kwh) {
		this.s1n563Kwh = s1n563Kwh;
	}
	@Column(name = "S1N564_KWH", precision = 53, scale = 0)
	public Double getS1n564Kwh() {
		return s1n564Kwh;
	}
	public void setS1n564Kwh(Double s1n564Kwh) {
		this.s1n564Kwh = s1n564Kwh;
	}
	@Column(name = "S1N565_KWH", precision = 53, scale = 0)
	public Double getS1n565Kwh() {
		return s1n565Kwh;
	}
	public void setS1n565Kwh(Double s1n565Kwh) {
		this.s1n565Kwh = s1n565Kwh;
	}
	@Column(name = "S1N566_KWH", precision = 53, scale = 0)
	public Double getS1n566Kwh() {
		return s1n566Kwh;
	}
	public void setS1n566Kwh(Double s1n566Kwh) {
		this.s1n566Kwh = s1n566Kwh;
	}
	@Column(name = "S1N567_KWH", precision = 53, scale = 0)
	public Double getS1n567Kwh() {
		return s1n567Kwh;
	}
	public void setS1n567Kwh(Double s1n567Kwh) {
		this.s1n567Kwh = s1n567Kwh;
	}
	@Column(name = "S1N568_KWH", precision = 53, scale = 0)
	public Double getS1n568Kwh() {
		return s1n568Kwh;
	}
	public void setS1n568Kwh(Double s1n568Kwh) {
		this.s1n568Kwh = s1n568Kwh;
	}
	@Column(name = "S1N569_KWH", precision = 53, scale = 0)
	public Double getS1n569Kwh() {
		return s1n569Kwh;
	}
	public void setS1n569Kwh(Double s1n569Kwh) {
		this.s1n569Kwh = s1n569Kwh;
	}
	@Column(name = "S1N570_KWH", precision = 53, scale = 0)
	public Double getS1n570Kwh() {
		return s1n570Kwh;
	}
	public void setS1n570Kwh(Double s1n570Kwh) {
		this.s1n570Kwh = s1n570Kwh;
	}
	@Column(name = "S1N571_KWH", precision = 53, scale = 0)
	public Double getS1n571Kwh() {
		return s1n571Kwh;
	}
	public void setS1n571Kwh(Double s1n571Kwh) {
		this.s1n571Kwh = s1n571Kwh;
	}
	@Column(name = "S1N572_KWH", precision = 53, scale = 0)
	public Double getS1n572Kwh() {
		return s1n572Kwh;
	}
	public void setS1n572Kwh(Double s1n572Kwh) {
		this.s1n572Kwh = s1n572Kwh;
	}
	@Column(name = "S1N573_KWH", precision = 53, scale = 0)
	public Double getS1n573Kwh() {
		return s1n573Kwh;
	}
	public void setS1n573Kwh(Double s1n573Kwh) {
		this.s1n573Kwh = s1n573Kwh;
	}
	@Column(name = "S1N574_KWH", precision = 53, scale = 0)
	public Double getS1n574Kwh() {
		return s1n574Kwh;
	}
	public void setS1n574Kwh(Double s1n574Kwh) {
		this.s1n574Kwh = s1n574Kwh;
	}
	@Column(name = "S1N575_KWH", precision = 53, scale = 0)
	public Double getS1n575Kwh() {
		return s1n575Kwh;
	}
	public void setS1n575Kwh(Double s1n575Kwh) {
		this.s1n575Kwh = s1n575Kwh;
	}
	@Column(name = "S1N576_KWH", precision = 53, scale = 0)
	public Double getS1n576Kwh() {
		return s1n576Kwh;
	}
	public void setS1n576Kwh(Double s1n576Kwh) {
		this.s1n576Kwh = s1n576Kwh;
	}
	@Column(name = "S1N577_KWH", precision = 53, scale = 0)
	public Double getS1n577Kwh() {
		return s1n577Kwh;
	}
	public void setS1n577Kwh(Double s1n577Kwh) {
		this.s1n577Kwh = s1n577Kwh;
	}
	@Column(name = "S1N578_KWH", precision = 53, scale = 0)
	public Double getS1n578Kwh() {
		return s1n578Kwh;
	}
	public void setS1n578Kwh(Double s1n578Kwh) {
		this.s1n578Kwh = s1n578Kwh;
	}
	@Column(name = "S1N579_KWH", precision = 53, scale = 0)
	public Double getS1n579Kwh() {
		return s1n579Kwh;
	}
	public void setS1n579Kwh(Double s1n579Kwh) {
		this.s1n579Kwh = s1n579Kwh;
	}
	@Column(name = "S1N580_KWH", precision = 53, scale = 0)
	public Double getS1n580Kwh() {
		return s1n580Kwh;
	}
	public void setS1n580Kwh(Double s1n580Kwh) {
		this.s1n580Kwh = s1n580Kwh;
	}
	@Column(name = "S1N581_KWH", precision = 53, scale = 0)
	public Double getS1n581Kwh() {
		return s1n581Kwh;
	}
	public void setS1n581Kwh(Double s1n581Kwh) {
		this.s1n581Kwh = s1n581Kwh;
	}
	@Column(name = "S1N582_KWH", precision = 53, scale = 0)
	public Double getS1n582Kwh() {
		return s1n582Kwh;
	}
	public void setS1n582Kwh(Double s1n582Kwh) {
		this.s1n582Kwh = s1n582Kwh;
	}
	@Column(name = "S1N583_KWH", precision = 53, scale = 0)
	public Double getS1n583Kwh() {
		return s1n583Kwh;
	}
	public void setS1n583Kwh(Double s1n583Kwh) {
		this.s1n583Kwh = s1n583Kwh;
	}
	@Column(name = "S1N584_KWH", precision = 53, scale = 0)
	public Double getS1n584Kwh() {
		return s1n584Kwh;
	}
	public void setS1n584Kwh(Double s1n584Kwh) {
		this.s1n584Kwh = s1n584Kwh;
	}
	@Column(name = "S1N585_KWH", precision = 53, scale = 0)
	public Double getS1n585Kwh() {
		return s1n585Kwh;
	}
	public void setS1n585Kwh(Double s1n585Kwh) {
		this.s1n585Kwh = s1n585Kwh;
	}
	@Column(name = "S1N586_KWH", precision = 53, scale = 0)
	public Double getS1n586Kwh() {
		return s1n586Kwh;
	}
	public void setS1n586Kwh(Double s1n586Kwh) {
		this.s1n586Kwh = s1n586Kwh;
	}
	@Column(name = "S1N587_KWH", precision = 53, scale = 0)
	public Double getS1n587Kwh() {
		return s1n587Kwh;
	}
	public void setS1n587Kwh(Double s1n587Kwh) {
		this.s1n587Kwh = s1n587Kwh;
	}
	@Column(name = "S1N588_KWH", precision = 53, scale = 0)
	public Double getS1n588Kwh() {
		return s1n588Kwh;
	}
	public void setS1n588Kwh(Double s1n588Kwh) {
		this.s1n588Kwh = s1n588Kwh;
	}
	@Column(name = "S1N589_KWH", precision = 53, scale = 0)
	public Double getS1n589Kwh() {
		return s1n589Kwh;
	}
	public void setS1n589Kwh(Double s1n589Kwh) {
		this.s1n589Kwh = s1n589Kwh;
	}
	@Column(name = "S1N590_KWH", precision = 53, scale = 0)
	public Double getS1n590Kwh() {
		return s1n590Kwh;
	}
	public void setS1n590Kwh(Double s1n590Kwh) {
		this.s1n590Kwh = s1n590Kwh;
	}
	@Column(name = "S1N591_KWH", precision = 53, scale = 0)
	public Double getS1n591Kwh() {
		return s1n591Kwh;
	}
	public void setS1n591Kwh(Double s1n591Kwh) {
		this.s1n591Kwh = s1n591Kwh;
	}
	@Column(name = "S1N592_KWH", precision = 53, scale = 0)
	public Double getS1n592Kwh() {
		return s1n592Kwh;
	}
	public void setS1n592Kwh(Double s1n592Kwh) {
		this.s1n592Kwh = s1n592Kwh;
	}
	@Column(name = "S1N593_KWH", precision = 53, scale = 0)
	public Double getS1n593Kwh() {
		return s1n593Kwh;
	}
	public void setS1n593Kwh(Double s1n593Kwh) {
		this.s1n593Kwh = s1n593Kwh;
	}
	
	// Constructors
	
	
}