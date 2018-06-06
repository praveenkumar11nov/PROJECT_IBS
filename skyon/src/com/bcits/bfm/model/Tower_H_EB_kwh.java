package com.bcits.bfm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Tower_H_EB_kwh",schema="dbo",catalog="EMS")
public class Tower_H_EB_kwh {
	// Fields

	private Timestamp datestr;
	private Double s1n1Kwh;
	private Double s1n2Kwh;
	private Double s1n3Kwh;
	private Double s1n4Kwh;
	private Double s1n5Kwh;
	private Double s1n6Kwh;
	private Double s1n7Kwh;
	private Double s1n8Kwh;
	private Double s1n9Kwh;
	private Double s1n10Kwh;
	private Double s1n11Kwh;
	private Double s1n12Kwh;
	private Double s1n13Kwh;
	private Double s1n14Kwh;
	private Double s1n15Kwh;
	private Double s1n16Kwh;
	private Double s1n17Kwh;
	private Double s1n18Kwh;
	private Double s1n19Kwh;
	private Double s1n20Kwh;
	private Double s1n21Kwh;
	private Double s1n22Kwh;
	private Double s1n23Kwh;
	private Double s1n24Kwh;
	private Double s1n25Kwh;
	private Double s1n26Kwh;
	private Double s1n27Kwh;
	private Double s1n28Kwh;
	private Double s1n29Kwh;
	private Double s1n30Kwh;
	private Double s1n31Kwh;
	private Double s1n32Kwh;
	private Double s1n33Kwh;
	private Double s1n34Kwh;
	private Double s1n35Kwh;
	private Double s1n36Kwh;
	private Double s1n37Kwh;
	private Double s1n38Kwh;
	private Double s1n39Kwh;
	private Double s1n40Kwh;
	private Double s1n41Kwh;
	private Double s1n42Kwh;
	private Double s1n43Kwh;
	private Double s1n44Kwh;
	private Double s1n45Kwh;
	private Double s1n46Kwh;
	private Double s1n47Kwh;
	private Double s1n48Kwh;
	private Double s1n49Kwh;
	private Double s1n50Kwh;
	private Double s1n51Kwh;
	private Double s1n52Kwh;
	private Double s1n53Kwh;
	private Double s1n54Kwh;
	private Double s1n55Kwh;
	private Double s1n56Kwh;
	private Double s1n57Kwh;
	private Double s1n58Kwh;
	private Double s1n59Kwh;
	private Double s1n60Kwh;
	private Double s1n61Kwh;
	private Double s1n62Kwh;
	private Double s1n63Kwh;
	private Double s1n64Kwh;
	private Double s1n65Kwh;
	private Double s1n66Kwh;
	private Double s1n67Kwh;
	private Double s1n68Kwh;
	private Double s1n69Kwh;
	private Double s1n70Kwh;
	private Double s1n71Kwh;
	private Double s1n72Kwh;
	private Double s1n73Kwh;
	private Double s1n74Kwh;
	private Double s1n75Kwh;
	private Double s1n76Kwh;
	private Double s1n77Kwh;
	private Double s1n78Kwh;
	private Double s1n79Kwh;
	private Double s1n80Kwh;
	private Double s1n81Kwh;
	private Double s1n82Kwh;
	private Double s1n83Kwh;
	private Double s1n84Kwh;
	private Double s1n85Kwh;
	private Double s1n86Kwh;
	private Double s1n87Kwh;
	private Double s1n88Kwh;
	private Double s1n89Kwh;
	private Double s1n90Kwh;
	private Double s1n91Kwh;
	private Double s1n92Kwh;
	private Double s1n93Kwh;
	private Double s1n94Kwh;
	private Double s1n95Kwh;
	private Double s1n96Kwh;
	private Double s1n97Kwh;
	private Double s1n98Kwh;
	private Double s1n99Kwh;
	private Double s1n100Kwh;
	private Double s1n101Kwh;
	private Double s1n102Kwh;
	private Double s1n103Kwh;
	private Double s1n104Kwh;

	// Constructors

	/** default constructor */
	public Tower_H_EB_kwh() {
	}

	/** full constructor */
	public Tower_H_EB_kwh(Timestamp datestr, Double s1n1Kwh, Double s1n2Kwh,
			Double s1n3Kwh, Double s1n4Kwh, Double s1n5Kwh, Double s1n6Kwh,
			Double s1n7Kwh, Double s1n8Kwh, Double s1n9Kwh, Double s1n10Kwh,
			Double s1n11Kwh, Double s1n12Kwh, Double s1n13Kwh, Double s1n14Kwh,
			Double s1n15Kwh, Double s1n16Kwh, Double s1n17Kwh, Double s1n18Kwh,
			Double s1n19Kwh, Double s1n20Kwh, Double s1n21Kwh, Double s1n22Kwh,
			Double s1n23Kwh, Double s1n24Kwh, Double s1n25Kwh, Double s1n26Kwh,
			Double s1n27Kwh, Double s1n28Kwh, Double s1n29Kwh, Double s1n30Kwh,
			Double s1n31Kwh, Double s1n32Kwh, Double s1n33Kwh, Double s1n34Kwh,
			Double s1n35Kwh, Double s1n36Kwh, Double s1n37Kwh, Double s1n38Kwh,
			Double s1n39Kwh, Double s1n40Kwh, Double s1n41Kwh, Double s1n42Kwh,
			Double s1n43Kwh, Double s1n44Kwh, Double s1n45Kwh, Double s1n46Kwh,
			Double s1n47Kwh, Double s1n48Kwh, Double s1n49Kwh, Double s1n50Kwh,
			Double s1n51Kwh, Double s1n52Kwh, Double s1n53Kwh, Double s1n54Kwh,
			Double s1n55Kwh, Double s1n56Kwh, Double s1n57Kwh, Double s1n58Kwh,
			Double s1n59Kwh, Double s1n60Kwh, Double s1n61Kwh, Double s1n62Kwh,
			Double s1n63Kwh, Double s1n64Kwh, Double s1n65Kwh, Double s1n66Kwh,
			Double s1n67Kwh, Double s1n68Kwh, Double s1n69Kwh, Double s1n70Kwh,
			Double s1n71Kwh, Double s1n72Kwh, Double s1n73Kwh, Double s1n74Kwh,
			Double s1n75Kwh, Double s1n76Kwh, Double s1n77Kwh, Double s1n78Kwh,
			Double s1n79Kwh, Double s1n80Kwh, Double s1n81Kwh, Double s1n82Kwh,
			Double s1n83Kwh, Double s1n84Kwh, Double s1n85Kwh, Double s1n86Kwh,
			Double s1n87Kwh, Double s1n88Kwh, Double s1n89Kwh, Double s1n90Kwh,
			Double s1n91Kwh, Double s1n92Kwh, Double s1n93Kwh, Double s1n94Kwh,
			Double s1n95Kwh, Double s1n96Kwh, Double s1n97Kwh, Double s1n98Kwh,
			Double s1n99Kwh, Double s1n100Kwh, Double s1n101Kwh,
			Double s1n102Kwh, Double s1n103Kwh, Double s1n104Kwh) {
		this.datestr = datestr;
		this.s1n1Kwh = s1n1Kwh;
		this.s1n2Kwh = s1n2Kwh;
		this.s1n3Kwh = s1n3Kwh;
		this.s1n4Kwh = s1n4Kwh;
		this.s1n5Kwh = s1n5Kwh;
		this.s1n6Kwh = s1n6Kwh;
		this.s1n7Kwh = s1n7Kwh;
		this.s1n8Kwh = s1n8Kwh;
		this.s1n9Kwh = s1n9Kwh;
		this.s1n10Kwh = s1n10Kwh;
		this.s1n11Kwh = s1n11Kwh;
		this.s1n12Kwh = s1n12Kwh;
		this.s1n13Kwh = s1n13Kwh;
		this.s1n14Kwh = s1n14Kwh;
		this.s1n15Kwh = s1n15Kwh;
		this.s1n16Kwh = s1n16Kwh;
		this.s1n17Kwh = s1n17Kwh;
		this.s1n18Kwh = s1n18Kwh;
		this.s1n19Kwh = s1n19Kwh;
		this.s1n20Kwh = s1n20Kwh;
		this.s1n21Kwh = s1n21Kwh;
		this.s1n22Kwh = s1n22Kwh;
		this.s1n23Kwh = s1n23Kwh;
		this.s1n24Kwh = s1n24Kwh;
		this.s1n25Kwh = s1n25Kwh;
		this.s1n26Kwh = s1n26Kwh;
		this.s1n27Kwh = s1n27Kwh;
		this.s1n28Kwh = s1n28Kwh;
		this.s1n29Kwh = s1n29Kwh;
		this.s1n30Kwh = s1n30Kwh;
		this.s1n31Kwh = s1n31Kwh;
		this.s1n32Kwh = s1n32Kwh;
		this.s1n33Kwh = s1n33Kwh;
		this.s1n34Kwh = s1n34Kwh;
		this.s1n35Kwh = s1n35Kwh;
		this.s1n36Kwh = s1n36Kwh;
		this.s1n37Kwh = s1n37Kwh;
		this.s1n38Kwh = s1n38Kwh;
		this.s1n39Kwh = s1n39Kwh;
		this.s1n40Kwh = s1n40Kwh;
		this.s1n41Kwh = s1n41Kwh;
		this.s1n42Kwh = s1n42Kwh;
		this.s1n43Kwh = s1n43Kwh;
		this.s1n44Kwh = s1n44Kwh;
		this.s1n45Kwh = s1n45Kwh;
		this.s1n46Kwh = s1n46Kwh;
		this.s1n47Kwh = s1n47Kwh;
		this.s1n48Kwh = s1n48Kwh;
		this.s1n49Kwh = s1n49Kwh;
		this.s1n50Kwh = s1n50Kwh;
		this.s1n51Kwh = s1n51Kwh;
		this.s1n52Kwh = s1n52Kwh;
		this.s1n53Kwh = s1n53Kwh;
		this.s1n54Kwh = s1n54Kwh;
		this.s1n55Kwh = s1n55Kwh;
		this.s1n56Kwh = s1n56Kwh;
		this.s1n57Kwh = s1n57Kwh;
		this.s1n58Kwh = s1n58Kwh;
		this.s1n59Kwh = s1n59Kwh;
		this.s1n60Kwh = s1n60Kwh;
		this.s1n61Kwh = s1n61Kwh;
		this.s1n62Kwh = s1n62Kwh;
		this.s1n63Kwh = s1n63Kwh;
		this.s1n64Kwh = s1n64Kwh;
		this.s1n65Kwh = s1n65Kwh;
		this.s1n66Kwh = s1n66Kwh;
		this.s1n67Kwh = s1n67Kwh;
		this.s1n68Kwh = s1n68Kwh;
		this.s1n69Kwh = s1n69Kwh;
		this.s1n70Kwh = s1n70Kwh;
		this.s1n71Kwh = s1n71Kwh;
		this.s1n72Kwh = s1n72Kwh;
		this.s1n73Kwh = s1n73Kwh;
		this.s1n74Kwh = s1n74Kwh;
		this.s1n75Kwh = s1n75Kwh;
		this.s1n76Kwh = s1n76Kwh;
		this.s1n77Kwh = s1n77Kwh;
		this.s1n78Kwh = s1n78Kwh;
		this.s1n79Kwh = s1n79Kwh;
		this.s1n80Kwh = s1n80Kwh;
		this.s1n81Kwh = s1n81Kwh;
		this.s1n82Kwh = s1n82Kwh;
		this.s1n83Kwh = s1n83Kwh;
		this.s1n84Kwh = s1n84Kwh;
		this.s1n85Kwh = s1n85Kwh;
		this.s1n86Kwh = s1n86Kwh;
		this.s1n87Kwh = s1n87Kwh;
		this.s1n88Kwh = s1n88Kwh;
		this.s1n89Kwh = s1n89Kwh;
		this.s1n90Kwh = s1n90Kwh;
		this.s1n91Kwh = s1n91Kwh;
		this.s1n92Kwh = s1n92Kwh;
		this.s1n93Kwh = s1n93Kwh;
		this.s1n94Kwh = s1n94Kwh;
		this.s1n95Kwh = s1n95Kwh;
		this.s1n96Kwh = s1n96Kwh;
		this.s1n97Kwh = s1n97Kwh;
		this.s1n98Kwh = s1n98Kwh;
		this.s1n99Kwh = s1n99Kwh;
		this.s1n100Kwh = s1n100Kwh;
		this.s1n101Kwh = s1n101Kwh;
		this.s1n102Kwh = s1n102Kwh;
		this.s1n103Kwh = s1n103Kwh;
		this.s1n104Kwh = s1n104Kwh;
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

	@Column(name = "S1N1_KWH", precision = 53, scale = 0)
	public Double getS1n1Kwh() {
		return this.s1n1Kwh;
	}

	public void setS1n1Kwh(Double s1n1Kwh) {
		this.s1n1Kwh = s1n1Kwh;
	}

	@Column(name = "S1N2_KWH", precision = 53, scale = 0)
	public Double getS1n2Kwh() {
		return this.s1n2Kwh;
	}

	public void setS1n2Kwh(Double s1n2Kwh) {
		this.s1n2Kwh = s1n2Kwh;
	}

	@Column(name = "S1N3_KWH", precision = 53, scale = 0)
	public Double getS1n3Kwh() {
		return this.s1n3Kwh;
	}

	public void setS1n3Kwh(Double s1n3Kwh) {
		this.s1n3Kwh = s1n3Kwh;
	}

	@Column(name = "S1N4_KWH", precision = 53, scale = 0)
	public Double getS1n4Kwh() {
		return this.s1n4Kwh;
	}

	public void setS1n4Kwh(Double s1n4Kwh) {
		this.s1n4Kwh = s1n4Kwh;
	}

	@Column(name = "S1N5_KWH", precision = 53, scale = 0)
	public Double getS1n5Kwh() {
		return this.s1n5Kwh;
	}

	public void setS1n5Kwh(Double s1n5Kwh) {
		this.s1n5Kwh = s1n5Kwh;
	}

	@Column(name = "S1N6_KWH", precision = 53, scale = 0)
	public Double getS1n6Kwh() {
		return this.s1n6Kwh;
	}

	public void setS1n6Kwh(Double s1n6Kwh) {
		this.s1n6Kwh = s1n6Kwh;
	}

	@Column(name = "S1N7_KWH", precision = 53, scale = 0)
	public Double getS1n7Kwh() {
		return this.s1n7Kwh;
	}

	public void setS1n7Kwh(Double s1n7Kwh) {
		this.s1n7Kwh = s1n7Kwh;
	}

	@Column(name = "S1N8_KWH", precision = 53, scale = 0)
	public Double getS1n8Kwh() {
		return this.s1n8Kwh;
	}

	public void setS1n8Kwh(Double s1n8Kwh) {
		this.s1n8Kwh = s1n8Kwh;
	}

	@Column(name = "S1N9_KWH", precision = 53, scale = 0)
	public Double getS1n9Kwh() {
		return this.s1n9Kwh;
	}

	public void setS1n9Kwh(Double s1n9Kwh) {
		this.s1n9Kwh = s1n9Kwh;
	}

	@Column(name = "S1N10_KWH", precision = 53, scale = 0)
	public Double getS1n10Kwh() {
		return this.s1n10Kwh;
	}

	public void setS1n10Kwh(Double s1n10Kwh) {
		this.s1n10Kwh = s1n10Kwh;
	}

	@Column(name = "S1N11_KWH", precision = 53, scale = 0)
	public Double getS1n11Kwh() {
		return this.s1n11Kwh;
	}

	public void setS1n11Kwh(Double s1n11Kwh) {
		this.s1n11Kwh = s1n11Kwh;
	}

	@Column(name = "S1N12_KWH", precision = 53, scale = 0)
	public Double getS1n12Kwh() {
		return this.s1n12Kwh;
	}

	public void setS1n12Kwh(Double s1n12Kwh) {
		this.s1n12Kwh = s1n12Kwh;
	}

	@Column(name = "S1N13_KWH", precision = 53, scale = 0)
	public Double getS1n13Kwh() {
		return this.s1n13Kwh;
	}

	public void setS1n13Kwh(Double s1n13Kwh) {
		this.s1n13Kwh = s1n13Kwh;
	}

	@Column(name = "S1N14_KWH", precision = 53, scale = 0)
	public Double getS1n14Kwh() {
		return this.s1n14Kwh;
	}

	public void setS1n14Kwh(Double s1n14Kwh) {
		this.s1n14Kwh = s1n14Kwh;
	}

	@Column(name = "S1N15_KWH", precision = 53, scale = 0)
	public Double getS1n15Kwh() {
		return this.s1n15Kwh;
	}

	public void setS1n15Kwh(Double s1n15Kwh) {
		this.s1n15Kwh = s1n15Kwh;
	}

	@Column(name = "S1N16_KWH", precision = 53, scale = 0)
	public Double getS1n16Kwh() {
		return this.s1n16Kwh;
	}

	public void setS1n16Kwh(Double s1n16Kwh) {
		this.s1n16Kwh = s1n16Kwh;
	}

	@Column(name = "S1N17_KWH", precision = 53, scale = 0)
	public Double getS1n17Kwh() {
		return this.s1n17Kwh;
	}

	public void setS1n17Kwh(Double s1n17Kwh) {
		this.s1n17Kwh = s1n17Kwh;
	}

	@Column(name = "S1N18_KWH", precision = 53, scale = 0)
	public Double getS1n18Kwh() {
		return this.s1n18Kwh;
	}

	public void setS1n18Kwh(Double s1n18Kwh) {
		this.s1n18Kwh = s1n18Kwh;
	}

	@Column(name = "S1N19_KWH", precision = 53, scale = 0)
	public Double getS1n19Kwh() {
		return this.s1n19Kwh;
	}

	public void setS1n19Kwh(Double s1n19Kwh) {
		this.s1n19Kwh = s1n19Kwh;
	}

	@Column(name = "S1N20_KWH", precision = 53, scale = 0)
	public Double getS1n20Kwh() {
		return this.s1n20Kwh;
	}

	public void setS1n20Kwh(Double s1n20Kwh) {
		this.s1n20Kwh = s1n20Kwh;
	}

	@Column(name = "S1N21_KWH", precision = 53, scale = 0)
	public Double getS1n21Kwh() {
		return this.s1n21Kwh;
	}

	public void setS1n21Kwh(Double s1n21Kwh) {
		this.s1n21Kwh = s1n21Kwh;
	}

	@Column(name = "S1N22_KWH", precision = 53, scale = 0)
	public Double getS1n22Kwh() {
		return this.s1n22Kwh;
	}

	public void setS1n22Kwh(Double s1n22Kwh) {
		this.s1n22Kwh = s1n22Kwh;
	}

	@Column(name = "S1N23_KWH", precision = 53, scale = 0)
	public Double getS1n23Kwh() {
		return this.s1n23Kwh;
	}

	public void setS1n23Kwh(Double s1n23Kwh) {
		this.s1n23Kwh = s1n23Kwh;
	}

	@Column(name = "S1N24_KWH", precision = 53, scale = 0)
	public Double getS1n24Kwh() {
		return this.s1n24Kwh;
	}

	public void setS1n24Kwh(Double s1n24Kwh) {
		this.s1n24Kwh = s1n24Kwh;
	}

	@Column(name = "S1N25_KWH", precision = 53, scale = 0)
	public Double getS1n25Kwh() {
		return this.s1n25Kwh;
	}

	public void setS1n25Kwh(Double s1n25Kwh) {
		this.s1n25Kwh = s1n25Kwh;
	}

	@Column(name = "S1N26_KWH", precision = 53, scale = 0)
	public Double getS1n26Kwh() {
		return this.s1n26Kwh;
	}

	public void setS1n26Kwh(Double s1n26Kwh) {
		this.s1n26Kwh = s1n26Kwh;
	}

	@Column(name = "S1N27_KWH", precision = 53, scale = 0)
	public Double getS1n27Kwh() {
		return this.s1n27Kwh;
	}

	public void setS1n27Kwh(Double s1n27Kwh) {
		this.s1n27Kwh = s1n27Kwh;
	}

	@Column(name = "S1N28_KWH", precision = 53, scale = 0)
	public Double getS1n28Kwh() {
		return this.s1n28Kwh;
	}

	public void setS1n28Kwh(Double s1n28Kwh) {
		this.s1n28Kwh = s1n28Kwh;
	}

	@Column(name = "S1N29_KWH", precision = 53, scale = 0)
	public Double getS1n29Kwh() {
		return this.s1n29Kwh;
	}

	public void setS1n29Kwh(Double s1n29Kwh) {
		this.s1n29Kwh = s1n29Kwh;
	}

	@Column(name = "S1N30_KWH", precision = 53, scale = 0)
	public Double getS1n30Kwh() {
		return this.s1n30Kwh;
	}

	public void setS1n30Kwh(Double s1n30Kwh) {
		this.s1n30Kwh = s1n30Kwh;
	}

	@Column(name = "S1N31_KWH", precision = 53, scale = 0)
	public Double getS1n31Kwh() {
		return this.s1n31Kwh;
	}

	public void setS1n31Kwh(Double s1n31Kwh) {
		this.s1n31Kwh = s1n31Kwh;
	}

	@Column(name = "S1N32_KWH", precision = 53, scale = 0)
	public Double getS1n32Kwh() {
		return this.s1n32Kwh;
	}

	public void setS1n32Kwh(Double s1n32Kwh) {
		this.s1n32Kwh = s1n32Kwh;
	}

	@Column(name = "S1N33_KWH", precision = 53, scale = 0)
	public Double getS1n33Kwh() {
		return this.s1n33Kwh;
	}

	public void setS1n33Kwh(Double s1n33Kwh) {
		this.s1n33Kwh = s1n33Kwh;
	}

	@Column(name = "S1N34_KWH", precision = 53, scale = 0)
	public Double getS1n34Kwh() {
		return this.s1n34Kwh;
	}

	public void setS1n34Kwh(Double s1n34Kwh) {
		this.s1n34Kwh = s1n34Kwh;
	}

	@Column(name = "S1N35_KWH", precision = 53, scale = 0)
	public Double getS1n35Kwh() {
		return this.s1n35Kwh;
	}

	public void setS1n35Kwh(Double s1n35Kwh) {
		this.s1n35Kwh = s1n35Kwh;
	}

	@Column(name = "S1N36_KWH", precision = 53, scale = 0)
	public Double getS1n36Kwh() {
		return this.s1n36Kwh;
	}

	public void setS1n36Kwh(Double s1n36Kwh) {
		this.s1n36Kwh = s1n36Kwh;
	}

	@Column(name = "S1N37_KWH", precision = 53, scale = 0)
	public Double getS1n37Kwh() {
		return this.s1n37Kwh;
	}

	public void setS1n37Kwh(Double s1n37Kwh) {
		this.s1n37Kwh = s1n37Kwh;
	}

	@Column(name = "S1N38_KWH", precision = 53, scale = 0)
	public Double getS1n38Kwh() {
		return this.s1n38Kwh;
	}

	public void setS1n38Kwh(Double s1n38Kwh) {
		this.s1n38Kwh = s1n38Kwh;
	}

	@Column(name = "S1N39_KWH", precision = 53, scale = 0)
	public Double getS1n39Kwh() {
		return this.s1n39Kwh;
	}

	public void setS1n39Kwh(Double s1n39Kwh) {
		this.s1n39Kwh = s1n39Kwh;
	}

	@Column(name = "S1N40_KWH", precision = 53, scale = 0)
	public Double getS1n40Kwh() {
		return this.s1n40Kwh;
	}

	public void setS1n40Kwh(Double s1n40Kwh) {
		this.s1n40Kwh = s1n40Kwh;
	}

	@Column(name = "S1N41_KWH", precision = 53, scale = 0)
	public Double getS1n41Kwh() {
		return this.s1n41Kwh;
	}

	public void setS1n41Kwh(Double s1n41Kwh) {
		this.s1n41Kwh = s1n41Kwh;
	}

	@Column(name = "S1N42_KWH", precision = 53, scale = 0)
	public Double getS1n42Kwh() {
		return this.s1n42Kwh;
	}

	public void setS1n42Kwh(Double s1n42Kwh) {
		this.s1n42Kwh = s1n42Kwh;
	}

	@Column(name = "S1N43_KWH", precision = 53, scale = 0)
	public Double getS1n43Kwh() {
		return this.s1n43Kwh;
	}

	public void setS1n43Kwh(Double s1n43Kwh) {
		this.s1n43Kwh = s1n43Kwh;
	}

	@Column(name = "S1N44_KWH", precision = 53, scale = 0)
	public Double getS1n44Kwh() {
		return this.s1n44Kwh;
	}

	public void setS1n44Kwh(Double s1n44Kwh) {
		this.s1n44Kwh = s1n44Kwh;
	}

	@Column(name = "S1N45_KWH", precision = 53, scale = 0)
	public Double getS1n45Kwh() {
		return this.s1n45Kwh;
	}

	public void setS1n45Kwh(Double s1n45Kwh) {
		this.s1n45Kwh = s1n45Kwh;
	}

	@Column(name = "S1N46_KWH", precision = 53, scale = 0)
	public Double getS1n46Kwh() {
		return this.s1n46Kwh;
	}

	public void setS1n46Kwh(Double s1n46Kwh) {
		this.s1n46Kwh = s1n46Kwh;
	}

	@Column(name = "S1N47_KWH", precision = 53, scale = 0)
	public Double getS1n47Kwh() {
		return this.s1n47Kwh;
	}

	public void setS1n47Kwh(Double s1n47Kwh) {
		this.s1n47Kwh = s1n47Kwh;
	}

	@Column(name = "S1N48_KWH", precision = 53, scale = 0)
	public Double getS1n48Kwh() {
		return this.s1n48Kwh;
	}

	public void setS1n48Kwh(Double s1n48Kwh) {
		this.s1n48Kwh = s1n48Kwh;
	}

	@Column(name = "S1N49_KWH", precision = 53, scale = 0)
	public Double getS1n49Kwh() {
		return this.s1n49Kwh;
	}

	public void setS1n49Kwh(Double s1n49Kwh) {
		this.s1n49Kwh = s1n49Kwh;
	}

	@Column(name = "S1N50_KWH", precision = 53, scale = 0)
	public Double getS1n50Kwh() {
		return this.s1n50Kwh;
	}

	public void setS1n50Kwh(Double s1n50Kwh) {
		this.s1n50Kwh = s1n50Kwh;
	}

	@Column(name = "S1N51_KWH", precision = 53, scale = 0)
	public Double getS1n51Kwh() {
		return this.s1n51Kwh;
	}

	public void setS1n51Kwh(Double s1n51Kwh) {
		this.s1n51Kwh = s1n51Kwh;
	}

	@Column(name = "S1N52_KWH", precision = 53, scale = 0)
	public Double getS1n52Kwh() {
		return this.s1n52Kwh;
	}

	public void setS1n52Kwh(Double s1n52Kwh) {
		this.s1n52Kwh = s1n52Kwh;
	}

	@Column(name = "S1N53_KWH", precision = 53, scale = 0)
	public Double getS1n53Kwh() {
		return this.s1n53Kwh;
	}

	public void setS1n53Kwh(Double s1n53Kwh) {
		this.s1n53Kwh = s1n53Kwh;
	}

	@Column(name = "S1N54_KWH", precision = 53, scale = 0)
	public Double getS1n54Kwh() {
		return this.s1n54Kwh;
	}

	public void setS1n54Kwh(Double s1n54Kwh) {
		this.s1n54Kwh = s1n54Kwh;
	}

	@Column(name = "S1N55_KWH", precision = 53, scale = 0)
	public Double getS1n55Kwh() {
		return this.s1n55Kwh;
	}

	public void setS1n55Kwh(Double s1n55Kwh) {
		this.s1n55Kwh = s1n55Kwh;
	}

	@Column(name = "S1N56_KWH", precision = 53, scale = 0)
	public Double getS1n56Kwh() {
		return this.s1n56Kwh;
	}

	public void setS1n56Kwh(Double s1n56Kwh) {
		this.s1n56Kwh = s1n56Kwh;
	}

	@Column(name = "S1N57_KWH", precision = 53, scale = 0)
	public Double getS1n57Kwh() {
		return this.s1n57Kwh;
	}

	public void setS1n57Kwh(Double s1n57Kwh) {
		this.s1n57Kwh = s1n57Kwh;
	}

	@Column(name = "S1N58_KWH", precision = 53, scale = 0)
	public Double getS1n58Kwh() {
		return this.s1n58Kwh;
	}

	public void setS1n58Kwh(Double s1n58Kwh) {
		this.s1n58Kwh = s1n58Kwh;
	}

	@Column(name = "S1N59_KWH", precision = 53, scale = 0)
	public Double getS1n59Kwh() {
		return this.s1n59Kwh;
	}

	public void setS1n59Kwh(Double s1n59Kwh) {
		this.s1n59Kwh = s1n59Kwh;
	}

	@Column(name = "S1N60_KWH", precision = 53, scale = 0)
	public Double getS1n60Kwh() {
		return this.s1n60Kwh;
	}

	public void setS1n60Kwh(Double s1n60Kwh) {
		this.s1n60Kwh = s1n60Kwh;
	}

	@Column(name = "S1N61_KWH", precision = 53, scale = 0)
	public Double getS1n61Kwh() {
		return this.s1n61Kwh;
	}

	public void setS1n61Kwh(Double s1n61Kwh) {
		this.s1n61Kwh = s1n61Kwh;
	}

	@Column(name = "S1N62_KWH", precision = 53, scale = 0)
	public Double getS1n62Kwh() {
		return this.s1n62Kwh;
	}

	public void setS1n62Kwh(Double s1n62Kwh) {
		this.s1n62Kwh = s1n62Kwh;
	}

	@Column(name = "S1N63_KWH", precision = 53, scale = 0)
	public Double getS1n63Kwh() {
		return this.s1n63Kwh;
	}

	public void setS1n63Kwh(Double s1n63Kwh) {
		this.s1n63Kwh = s1n63Kwh;
	}

	@Column(name = "S1N64_KWH", precision = 53, scale = 0)
	public Double getS1n64Kwh() {
		return this.s1n64Kwh;
	}

	public void setS1n64Kwh(Double s1n64Kwh) {
		this.s1n64Kwh = s1n64Kwh;
	}

	@Column(name = "S1N65_KWH", precision = 53, scale = 0)
	public Double getS1n65Kwh() {
		return this.s1n65Kwh;
	}

	public void setS1n65Kwh(Double s1n65Kwh) {
		this.s1n65Kwh = s1n65Kwh;
	}

	@Column(name = "S1N66_KWH", precision = 53, scale = 0)
	public Double getS1n66Kwh() {
		return this.s1n66Kwh;
	}

	public void setS1n66Kwh(Double s1n66Kwh) {
		this.s1n66Kwh = s1n66Kwh;
	}

	@Column(name = "S1N67_KWH", precision = 53, scale = 0)
	public Double getS1n67Kwh() {
		return this.s1n67Kwh;
	}

	public void setS1n67Kwh(Double s1n67Kwh) {
		this.s1n67Kwh = s1n67Kwh;
	}

	@Column(name = "S1N68_KWH", precision = 53, scale = 0)
	public Double getS1n68Kwh() {
		return this.s1n68Kwh;
	}

	public void setS1n68Kwh(Double s1n68Kwh) {
		this.s1n68Kwh = s1n68Kwh;
	}

	@Column(name = "S1N69_KWH", precision = 53, scale = 0)
	public Double getS1n69Kwh() {
		return this.s1n69Kwh;
	}

	public void setS1n69Kwh(Double s1n69Kwh) {
		this.s1n69Kwh = s1n69Kwh;
	}

	@Column(name = "S1N70_KWH", precision = 53, scale = 0)
	public Double getS1n70Kwh() {
		return this.s1n70Kwh;
	}

	public void setS1n70Kwh(Double s1n70Kwh) {
		this.s1n70Kwh = s1n70Kwh;
	}

	@Column(name = "S1N71_KWH", precision = 53, scale = 0)
	public Double getS1n71Kwh() {
		return this.s1n71Kwh;
	}

	public void setS1n71Kwh(Double s1n71Kwh) {
		this.s1n71Kwh = s1n71Kwh;
	}

	@Column(name = "S1N72_KWH", precision = 53, scale = 0)
	public Double getS1n72Kwh() {
		return this.s1n72Kwh;
	}

	public void setS1n72Kwh(Double s1n72Kwh) {
		this.s1n72Kwh = s1n72Kwh;
	}

	@Column(name = "S1N73_KWH", precision = 53, scale = 0)
	public Double getS1n73Kwh() {
		return this.s1n73Kwh;
	}

	public void setS1n73Kwh(Double s1n73Kwh) {
		this.s1n73Kwh = s1n73Kwh;
	}

	@Column(name = "S1N74_KWH", precision = 53, scale = 0)
	public Double getS1n74Kwh() {
		return this.s1n74Kwh;
	}

	public void setS1n74Kwh(Double s1n74Kwh) {
		this.s1n74Kwh = s1n74Kwh;
	}

	@Column(name = "S1N75_KWH", precision = 53, scale = 0)
	public Double getS1n75Kwh() {
		return this.s1n75Kwh;
	}

	public void setS1n75Kwh(Double s1n75Kwh) {
		this.s1n75Kwh = s1n75Kwh;
	}

	@Column(name = "S1N76_KWH", precision = 53, scale = 0)
	public Double getS1n76Kwh() {
		return this.s1n76Kwh;
	}

	public void setS1n76Kwh(Double s1n76Kwh) {
		this.s1n76Kwh = s1n76Kwh;
	}

	@Column(name = "S1N77_KWH", precision = 53, scale = 0)
	public Double getS1n77Kwh() {
		return this.s1n77Kwh;
	}

	public void setS1n77Kwh(Double s1n77Kwh) {
		this.s1n77Kwh = s1n77Kwh;
	}

	@Column(name = "S1N78_KWH", precision = 53, scale = 0)
	public Double getS1n78Kwh() {
		return this.s1n78Kwh;
	}

	public void setS1n78Kwh(Double s1n78Kwh) {
		this.s1n78Kwh = s1n78Kwh;
	}

	@Column(name = "S1N79_KWH", precision = 53, scale = 0)
	public Double getS1n79Kwh() {
		return this.s1n79Kwh;
	}

	public void setS1n79Kwh(Double s1n79Kwh) {
		this.s1n79Kwh = s1n79Kwh;
	}

	@Column(name = "S1N80_KWH", precision = 53, scale = 0)
	public Double getS1n80Kwh() {
		return this.s1n80Kwh;
	}

	public void setS1n80Kwh(Double s1n80Kwh) {
		this.s1n80Kwh = s1n80Kwh;
	}

	@Column(name = "S1N81_KWH", precision = 53, scale = 0)
	public Double getS1n81Kwh() {
		return this.s1n81Kwh;
	}

	public void setS1n81Kwh(Double s1n81Kwh) {
		this.s1n81Kwh = s1n81Kwh;
	}

	@Column(name = "S1N82_KWH", precision = 53, scale = 0)
	public Double getS1n82Kwh() {
		return this.s1n82Kwh;
	}

	public void setS1n82Kwh(Double s1n82Kwh) {
		this.s1n82Kwh = s1n82Kwh;
	}

	@Column(name = "S1N83_KWH", precision = 53, scale = 0)
	public Double getS1n83Kwh() {
		return this.s1n83Kwh;
	}

	public void setS1n83Kwh(Double s1n83Kwh) {
		this.s1n83Kwh = s1n83Kwh;
	}

	@Column(name = "S1N84_KWH", precision = 53, scale = 0)
	public Double getS1n84Kwh() {
		return this.s1n84Kwh;
	}

	public void setS1n84Kwh(Double s1n84Kwh) {
		this.s1n84Kwh = s1n84Kwh;
	}

	@Column(name = "S1N85_KWH", precision = 53, scale = 0)
	public Double getS1n85Kwh() {
		return this.s1n85Kwh;
	}

	public void setS1n85Kwh(Double s1n85Kwh) {
		this.s1n85Kwh = s1n85Kwh;
	}

	@Column(name = "S1N86_KWH", precision = 53, scale = 0)
	public Double getS1n86Kwh() {
		return this.s1n86Kwh;
	}

	public void setS1n86Kwh(Double s1n86Kwh) {
		this.s1n86Kwh = s1n86Kwh;
	}

	@Column(name = "S1N87_KWH", precision = 53, scale = 0)
	public Double getS1n87Kwh() {
		return this.s1n87Kwh;
	}

	public void setS1n87Kwh(Double s1n87Kwh) {
		this.s1n87Kwh = s1n87Kwh;
	}

	@Column(name = "S1N88_KWH", precision = 53, scale = 0)
	public Double getS1n88Kwh() {
		return this.s1n88Kwh;
	}

	public void setS1n88Kwh(Double s1n88Kwh) {
		this.s1n88Kwh = s1n88Kwh;
	}

	@Column(name = "S1N89_KWH", precision = 53, scale = 0)
	public Double getS1n89Kwh() {
		return this.s1n89Kwh;
	}

	public void setS1n89Kwh(Double s1n89Kwh) {
		this.s1n89Kwh = s1n89Kwh;
	}

	@Column(name = "S1N90_KWH", precision = 53, scale = 0)
	public Double getS1n90Kwh() {
		return this.s1n90Kwh;
	}

	public void setS1n90Kwh(Double s1n90Kwh) {
		this.s1n90Kwh = s1n90Kwh;
	}

	@Column(name = "S1N91_KWH", precision = 53, scale = 0)
	public Double getS1n91Kwh() {
		return this.s1n91Kwh;
	}

	public void setS1n91Kwh(Double s1n91Kwh) {
		this.s1n91Kwh = s1n91Kwh;
	}

	@Column(name = "S1N92_KWH", precision = 53, scale = 0)
	public Double getS1n92Kwh() {
		return this.s1n92Kwh;
	}

	public void setS1n92Kwh(Double s1n92Kwh) {
		this.s1n92Kwh = s1n92Kwh;
	}

	@Column(name = "S1N93_KWH", precision = 53, scale = 0)
	public Double getS1n93Kwh() {
		return this.s1n93Kwh;
	}

	public void setS1n93Kwh(Double s1n93Kwh) {
		this.s1n93Kwh = s1n93Kwh;
	}

	@Column(name = "S1N94_KWH", precision = 53, scale = 0)
	public Double getS1n94Kwh() {
		return this.s1n94Kwh;
	}

	public void setS1n94Kwh(Double s1n94Kwh) {
		this.s1n94Kwh = s1n94Kwh;
	}

	@Column(name = "S1N95_KWH", precision = 53, scale = 0)
	public Double getS1n95Kwh() {
		return this.s1n95Kwh;
	}

	public void setS1n95Kwh(Double s1n95Kwh) {
		this.s1n95Kwh = s1n95Kwh;
	}

	@Column(name = "S1N96_KWH", precision = 53, scale = 0)
	public Double getS1n96Kwh() {
		return this.s1n96Kwh;
	}

	public void setS1n96Kwh(Double s1n96Kwh) {
		this.s1n96Kwh = s1n96Kwh;
	}

	@Column(name = "S1N97_KWH", precision = 53, scale = 0)
	public Double getS1n97Kwh() {
		return this.s1n97Kwh;
	}

	public void setS1n97Kwh(Double s1n97Kwh) {
		this.s1n97Kwh = s1n97Kwh;
	}

	@Column(name = "S1N98_KWH", precision = 53, scale = 0)
	public Double getS1n98Kwh() {
		return this.s1n98Kwh;
	}

	public void setS1n98Kwh(Double s1n98Kwh) {
		this.s1n98Kwh = s1n98Kwh;
	}

	@Column(name = "S1N99_KWH", precision = 53, scale = 0)
	public Double getS1n99Kwh() {
		return this.s1n99Kwh;
	}

	public void setS1n99Kwh(Double s1n99Kwh) {
		this.s1n99Kwh = s1n99Kwh;
	}

	@Column(name = "S1N100_KWH", precision = 53, scale = 0)
	public Double getS1n100Kwh() {
		return this.s1n100Kwh;
	}

	public void setS1n100Kwh(Double s1n100Kwh) {
		this.s1n100Kwh = s1n100Kwh;
	}

	@Column(name = "S1N101_KWH", precision = 53, scale = 0)
	public Double getS1n101Kwh() {
		return this.s1n101Kwh;
	}

	public void setS1n101Kwh(Double s1n101Kwh) {
		this.s1n101Kwh = s1n101Kwh;
	}

	@Column(name = "S1N102_KWH", precision = 53, scale = 0)
	public Double getS1n102Kwh() {
		return this.s1n102Kwh;
	}

	public void setS1n102Kwh(Double s1n102Kwh) {
		this.s1n102Kwh = s1n102Kwh;
	}

	@Column(name = "S1N103_KWH", precision = 53, scale = 0)
	public Double getS1n103Kwh() {
		return this.s1n103Kwh;
	}

	public void setS1n103Kwh(Double s1n103Kwh) {
		this.s1n103Kwh = s1n103Kwh;
	}

	@Column(name = "S1N104_KWH", precision = 53, scale = 0)
	public Double getS1n104Kwh() {
		return this.s1n104Kwh;
	}

	public void setS1n104Kwh(Double s1n104Kwh) {
		this.s1n104Kwh = s1n104Kwh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tower_H_EB_kwh))
			return false;
		Tower_H_EB_kwh castOther = (Tower_H_EB_kwh) other;

		return ((this.getDatestr() == castOther.getDatestr()) || (this
				.getDatestr() != null && castOther.getDatestr() != null && this
				.getDatestr().equals(castOther.getDatestr())))
				&& ((this.getS1n1Kwh() == castOther.getS1n1Kwh()) || (this
						.getS1n1Kwh() != null && castOther.getS1n1Kwh() != null && this
						.getS1n1Kwh().equals(castOther.getS1n1Kwh())))
				&& ((this.getS1n2Kwh() == castOther.getS1n2Kwh()) || (this
						.getS1n2Kwh() != null && castOther.getS1n2Kwh() != null && this
						.getS1n2Kwh().equals(castOther.getS1n2Kwh())))
				&& ((this.getS1n3Kwh() == castOther.getS1n3Kwh()) || (this
						.getS1n3Kwh() != null && castOther.getS1n3Kwh() != null && this
						.getS1n3Kwh().equals(castOther.getS1n3Kwh())))
				&& ((this.getS1n4Kwh() == castOther.getS1n4Kwh()) || (this
						.getS1n4Kwh() != null && castOther.getS1n4Kwh() != null && this
						.getS1n4Kwh().equals(castOther.getS1n4Kwh())))
				&& ((this.getS1n5Kwh() == castOther.getS1n5Kwh()) || (this
						.getS1n5Kwh() != null && castOther.getS1n5Kwh() != null && this
						.getS1n5Kwh().equals(castOther.getS1n5Kwh())))
				&& ((this.getS1n6Kwh() == castOther.getS1n6Kwh()) || (this
						.getS1n6Kwh() != null && castOther.getS1n6Kwh() != null && this
						.getS1n6Kwh().equals(castOther.getS1n6Kwh())))
				&& ((this.getS1n7Kwh() == castOther.getS1n7Kwh()) || (this
						.getS1n7Kwh() != null && castOther.getS1n7Kwh() != null && this
						.getS1n7Kwh().equals(castOther.getS1n7Kwh())))
				&& ((this.getS1n8Kwh() == castOther.getS1n8Kwh()) || (this
						.getS1n8Kwh() != null && castOther.getS1n8Kwh() != null && this
						.getS1n8Kwh().equals(castOther.getS1n8Kwh())))
				&& ((this.getS1n9Kwh() == castOther.getS1n9Kwh()) || (this
						.getS1n9Kwh() != null && castOther.getS1n9Kwh() != null && this
						.getS1n9Kwh().equals(castOther.getS1n9Kwh())))
				&& ((this.getS1n10Kwh() == castOther.getS1n10Kwh()) || (this
						.getS1n10Kwh() != null
						&& castOther.getS1n10Kwh() != null && this
						.getS1n10Kwh().equals(castOther.getS1n10Kwh())))
				&& ((this.getS1n11Kwh() == castOther.getS1n11Kwh()) || (this
						.getS1n11Kwh() != null
						&& castOther.getS1n11Kwh() != null && this
						.getS1n11Kwh().equals(castOther.getS1n11Kwh())))
				&& ((this.getS1n12Kwh() == castOther.getS1n12Kwh()) || (this
						.getS1n12Kwh() != null
						&& castOther.getS1n12Kwh() != null && this
						.getS1n12Kwh().equals(castOther.getS1n12Kwh())))
				&& ((this.getS1n13Kwh() == castOther.getS1n13Kwh()) || (this
						.getS1n13Kwh() != null
						&& castOther.getS1n13Kwh() != null && this
						.getS1n13Kwh().equals(castOther.getS1n13Kwh())))
				&& ((this.getS1n14Kwh() == castOther.getS1n14Kwh()) || (this
						.getS1n14Kwh() != null
						&& castOther.getS1n14Kwh() != null && this
						.getS1n14Kwh().equals(castOther.getS1n14Kwh())))
				&& ((this.getS1n15Kwh() == castOther.getS1n15Kwh()) || (this
						.getS1n15Kwh() != null
						&& castOther.getS1n15Kwh() != null && this
						.getS1n15Kwh().equals(castOther.getS1n15Kwh())))
				&& ((this.getS1n16Kwh() == castOther.getS1n16Kwh()) || (this
						.getS1n16Kwh() != null
						&& castOther.getS1n16Kwh() != null && this
						.getS1n16Kwh().equals(castOther.getS1n16Kwh())))
				&& ((this.getS1n17Kwh() == castOther.getS1n17Kwh()) || (this
						.getS1n17Kwh() != null
						&& castOther.getS1n17Kwh() != null && this
						.getS1n17Kwh().equals(castOther.getS1n17Kwh())))
				&& ((this.getS1n18Kwh() == castOther.getS1n18Kwh()) || (this
						.getS1n18Kwh() != null
						&& castOther.getS1n18Kwh() != null && this
						.getS1n18Kwh().equals(castOther.getS1n18Kwh())))
				&& ((this.getS1n19Kwh() == castOther.getS1n19Kwh()) || (this
						.getS1n19Kwh() != null
						&& castOther.getS1n19Kwh() != null && this
						.getS1n19Kwh().equals(castOther.getS1n19Kwh())))
				&& ((this.getS1n20Kwh() == castOther.getS1n20Kwh()) || (this
						.getS1n20Kwh() != null
						&& castOther.getS1n20Kwh() != null && this
						.getS1n20Kwh().equals(castOther.getS1n20Kwh())))
				&& ((this.getS1n21Kwh() == castOther.getS1n21Kwh()) || (this
						.getS1n21Kwh() != null
						&& castOther.getS1n21Kwh() != null && this
						.getS1n21Kwh().equals(castOther.getS1n21Kwh())))
				&& ((this.getS1n22Kwh() == castOther.getS1n22Kwh()) || (this
						.getS1n22Kwh() != null
						&& castOther.getS1n22Kwh() != null && this
						.getS1n22Kwh().equals(castOther.getS1n22Kwh())))
				&& ((this.getS1n23Kwh() == castOther.getS1n23Kwh()) || (this
						.getS1n23Kwh() != null
						&& castOther.getS1n23Kwh() != null && this
						.getS1n23Kwh().equals(castOther.getS1n23Kwh())))
				&& ((this.getS1n24Kwh() == castOther.getS1n24Kwh()) || (this
						.getS1n24Kwh() != null
						&& castOther.getS1n24Kwh() != null && this
						.getS1n24Kwh().equals(castOther.getS1n24Kwh())))
				&& ((this.getS1n25Kwh() == castOther.getS1n25Kwh()) || (this
						.getS1n25Kwh() != null
						&& castOther.getS1n25Kwh() != null && this
						.getS1n25Kwh().equals(castOther.getS1n25Kwh())))
				&& ((this.getS1n26Kwh() == castOther.getS1n26Kwh()) || (this
						.getS1n26Kwh() != null
						&& castOther.getS1n26Kwh() != null && this
						.getS1n26Kwh().equals(castOther.getS1n26Kwh())))
				&& ((this.getS1n27Kwh() == castOther.getS1n27Kwh()) || (this
						.getS1n27Kwh() != null
						&& castOther.getS1n27Kwh() != null && this
						.getS1n27Kwh().equals(castOther.getS1n27Kwh())))
				&& ((this.getS1n28Kwh() == castOther.getS1n28Kwh()) || (this
						.getS1n28Kwh() != null
						&& castOther.getS1n28Kwh() != null && this
						.getS1n28Kwh().equals(castOther.getS1n28Kwh())))
				&& ((this.getS1n29Kwh() == castOther.getS1n29Kwh()) || (this
						.getS1n29Kwh() != null
						&& castOther.getS1n29Kwh() != null && this
						.getS1n29Kwh().equals(castOther.getS1n29Kwh())))
				&& ((this.getS1n30Kwh() == castOther.getS1n30Kwh()) || (this
						.getS1n30Kwh() != null
						&& castOther.getS1n30Kwh() != null && this
						.getS1n30Kwh().equals(castOther.getS1n30Kwh())))
				&& ((this.getS1n31Kwh() == castOther.getS1n31Kwh()) || (this
						.getS1n31Kwh() != null
						&& castOther.getS1n31Kwh() != null && this
						.getS1n31Kwh().equals(castOther.getS1n31Kwh())))
				&& ((this.getS1n32Kwh() == castOther.getS1n32Kwh()) || (this
						.getS1n32Kwh() != null
						&& castOther.getS1n32Kwh() != null && this
						.getS1n32Kwh().equals(castOther.getS1n32Kwh())))
				&& ((this.getS1n33Kwh() == castOther.getS1n33Kwh()) || (this
						.getS1n33Kwh() != null
						&& castOther.getS1n33Kwh() != null && this
						.getS1n33Kwh().equals(castOther.getS1n33Kwh())))
				&& ((this.getS1n34Kwh() == castOther.getS1n34Kwh()) || (this
						.getS1n34Kwh() != null
						&& castOther.getS1n34Kwh() != null && this
						.getS1n34Kwh().equals(castOther.getS1n34Kwh())))
				&& ((this.getS1n35Kwh() == castOther.getS1n35Kwh()) || (this
						.getS1n35Kwh() != null
						&& castOther.getS1n35Kwh() != null && this
						.getS1n35Kwh().equals(castOther.getS1n35Kwh())))
				&& ((this.getS1n36Kwh() == castOther.getS1n36Kwh()) || (this
						.getS1n36Kwh() != null
						&& castOther.getS1n36Kwh() != null && this
						.getS1n36Kwh().equals(castOther.getS1n36Kwh())))
				&& ((this.getS1n37Kwh() == castOther.getS1n37Kwh()) || (this
						.getS1n37Kwh() != null
						&& castOther.getS1n37Kwh() != null && this
						.getS1n37Kwh().equals(castOther.getS1n37Kwh())))
				&& ((this.getS1n38Kwh() == castOther.getS1n38Kwh()) || (this
						.getS1n38Kwh() != null
						&& castOther.getS1n38Kwh() != null && this
						.getS1n38Kwh().equals(castOther.getS1n38Kwh())))
				&& ((this.getS1n39Kwh() == castOther.getS1n39Kwh()) || (this
						.getS1n39Kwh() != null
						&& castOther.getS1n39Kwh() != null && this
						.getS1n39Kwh().equals(castOther.getS1n39Kwh())))
				&& ((this.getS1n40Kwh() == castOther.getS1n40Kwh()) || (this
						.getS1n40Kwh() != null
						&& castOther.getS1n40Kwh() != null && this
						.getS1n40Kwh().equals(castOther.getS1n40Kwh())))
				&& ((this.getS1n41Kwh() == castOther.getS1n41Kwh()) || (this
						.getS1n41Kwh() != null
						&& castOther.getS1n41Kwh() != null && this
						.getS1n41Kwh().equals(castOther.getS1n41Kwh())))
				&& ((this.getS1n42Kwh() == castOther.getS1n42Kwh()) || (this
						.getS1n42Kwh() != null
						&& castOther.getS1n42Kwh() != null && this
						.getS1n42Kwh().equals(castOther.getS1n42Kwh())))
				&& ((this.getS1n43Kwh() == castOther.getS1n43Kwh()) || (this
						.getS1n43Kwh() != null
						&& castOther.getS1n43Kwh() != null && this
						.getS1n43Kwh().equals(castOther.getS1n43Kwh())))
				&& ((this.getS1n44Kwh() == castOther.getS1n44Kwh()) || (this
						.getS1n44Kwh() != null
						&& castOther.getS1n44Kwh() != null && this
						.getS1n44Kwh().equals(castOther.getS1n44Kwh())))
				&& ((this.getS1n45Kwh() == castOther.getS1n45Kwh()) || (this
						.getS1n45Kwh() != null
						&& castOther.getS1n45Kwh() != null && this
						.getS1n45Kwh().equals(castOther.getS1n45Kwh())))
				&& ((this.getS1n46Kwh() == castOther.getS1n46Kwh()) || (this
						.getS1n46Kwh() != null
						&& castOther.getS1n46Kwh() != null && this
						.getS1n46Kwh().equals(castOther.getS1n46Kwh())))
				&& ((this.getS1n47Kwh() == castOther.getS1n47Kwh()) || (this
						.getS1n47Kwh() != null
						&& castOther.getS1n47Kwh() != null && this
						.getS1n47Kwh().equals(castOther.getS1n47Kwh())))
				&& ((this.getS1n48Kwh() == castOther.getS1n48Kwh()) || (this
						.getS1n48Kwh() != null
						&& castOther.getS1n48Kwh() != null && this
						.getS1n48Kwh().equals(castOther.getS1n48Kwh())))
				&& ((this.getS1n49Kwh() == castOther.getS1n49Kwh()) || (this
						.getS1n49Kwh() != null
						&& castOther.getS1n49Kwh() != null && this
						.getS1n49Kwh().equals(castOther.getS1n49Kwh())))
				&& ((this.getS1n50Kwh() == castOther.getS1n50Kwh()) || (this
						.getS1n50Kwh() != null
						&& castOther.getS1n50Kwh() != null && this
						.getS1n50Kwh().equals(castOther.getS1n50Kwh())))
				&& ((this.getS1n51Kwh() == castOther.getS1n51Kwh()) || (this
						.getS1n51Kwh() != null
						&& castOther.getS1n51Kwh() != null && this
						.getS1n51Kwh().equals(castOther.getS1n51Kwh())))
				&& ((this.getS1n52Kwh() == castOther.getS1n52Kwh()) || (this
						.getS1n52Kwh() != null
						&& castOther.getS1n52Kwh() != null && this
						.getS1n52Kwh().equals(castOther.getS1n52Kwh())))
				&& ((this.getS1n53Kwh() == castOther.getS1n53Kwh()) || (this
						.getS1n53Kwh() != null
						&& castOther.getS1n53Kwh() != null && this
						.getS1n53Kwh().equals(castOther.getS1n53Kwh())))
				&& ((this.getS1n54Kwh() == castOther.getS1n54Kwh()) || (this
						.getS1n54Kwh() != null
						&& castOther.getS1n54Kwh() != null && this
						.getS1n54Kwh().equals(castOther.getS1n54Kwh())))
				&& ((this.getS1n55Kwh() == castOther.getS1n55Kwh()) || (this
						.getS1n55Kwh() != null
						&& castOther.getS1n55Kwh() != null && this
						.getS1n55Kwh().equals(castOther.getS1n55Kwh())))
				&& ((this.getS1n56Kwh() == castOther.getS1n56Kwh()) || (this
						.getS1n56Kwh() != null
						&& castOther.getS1n56Kwh() != null && this
						.getS1n56Kwh().equals(castOther.getS1n56Kwh())))
				&& ((this.getS1n57Kwh() == castOther.getS1n57Kwh()) || (this
						.getS1n57Kwh() != null
						&& castOther.getS1n57Kwh() != null && this
						.getS1n57Kwh().equals(castOther.getS1n57Kwh())))
				&& ((this.getS1n58Kwh() == castOther.getS1n58Kwh()) || (this
						.getS1n58Kwh() != null
						&& castOther.getS1n58Kwh() != null && this
						.getS1n58Kwh().equals(castOther.getS1n58Kwh())))
				&& ((this.getS1n59Kwh() == castOther.getS1n59Kwh()) || (this
						.getS1n59Kwh() != null
						&& castOther.getS1n59Kwh() != null && this
						.getS1n59Kwh().equals(castOther.getS1n59Kwh())))
				&& ((this.getS1n60Kwh() == castOther.getS1n60Kwh()) || (this
						.getS1n60Kwh() != null
						&& castOther.getS1n60Kwh() != null && this
						.getS1n60Kwh().equals(castOther.getS1n60Kwh())))
				&& ((this.getS1n61Kwh() == castOther.getS1n61Kwh()) || (this
						.getS1n61Kwh() != null
						&& castOther.getS1n61Kwh() != null && this
						.getS1n61Kwh().equals(castOther.getS1n61Kwh())))
				&& ((this.getS1n62Kwh() == castOther.getS1n62Kwh()) || (this
						.getS1n62Kwh() != null
						&& castOther.getS1n62Kwh() != null && this
						.getS1n62Kwh().equals(castOther.getS1n62Kwh())))
				&& ((this.getS1n63Kwh() == castOther.getS1n63Kwh()) || (this
						.getS1n63Kwh() != null
						&& castOther.getS1n63Kwh() != null && this
						.getS1n63Kwh().equals(castOther.getS1n63Kwh())))
				&& ((this.getS1n64Kwh() == castOther.getS1n64Kwh()) || (this
						.getS1n64Kwh() != null
						&& castOther.getS1n64Kwh() != null && this
						.getS1n64Kwh().equals(castOther.getS1n64Kwh())))
				&& ((this.getS1n65Kwh() == castOther.getS1n65Kwh()) || (this
						.getS1n65Kwh() != null
						&& castOther.getS1n65Kwh() != null && this
						.getS1n65Kwh().equals(castOther.getS1n65Kwh())))
				&& ((this.getS1n66Kwh() == castOther.getS1n66Kwh()) || (this
						.getS1n66Kwh() != null
						&& castOther.getS1n66Kwh() != null && this
						.getS1n66Kwh().equals(castOther.getS1n66Kwh())))
				&& ((this.getS1n67Kwh() == castOther.getS1n67Kwh()) || (this
						.getS1n67Kwh() != null
						&& castOther.getS1n67Kwh() != null && this
						.getS1n67Kwh().equals(castOther.getS1n67Kwh())))
				&& ((this.getS1n68Kwh() == castOther.getS1n68Kwh()) || (this
						.getS1n68Kwh() != null
						&& castOther.getS1n68Kwh() != null && this
						.getS1n68Kwh().equals(castOther.getS1n68Kwh())))
				&& ((this.getS1n69Kwh() == castOther.getS1n69Kwh()) || (this
						.getS1n69Kwh() != null
						&& castOther.getS1n69Kwh() != null && this
						.getS1n69Kwh().equals(castOther.getS1n69Kwh())))
				&& ((this.getS1n70Kwh() == castOther.getS1n70Kwh()) || (this
						.getS1n70Kwh() != null
						&& castOther.getS1n70Kwh() != null && this
						.getS1n70Kwh().equals(castOther.getS1n70Kwh())))
				&& ((this.getS1n71Kwh() == castOther.getS1n71Kwh()) || (this
						.getS1n71Kwh() != null
						&& castOther.getS1n71Kwh() != null && this
						.getS1n71Kwh().equals(castOther.getS1n71Kwh())))
				&& ((this.getS1n72Kwh() == castOther.getS1n72Kwh()) || (this
						.getS1n72Kwh() != null
						&& castOther.getS1n72Kwh() != null && this
						.getS1n72Kwh().equals(castOther.getS1n72Kwh())))
				&& ((this.getS1n73Kwh() == castOther.getS1n73Kwh()) || (this
						.getS1n73Kwh() != null
						&& castOther.getS1n73Kwh() != null && this
						.getS1n73Kwh().equals(castOther.getS1n73Kwh())))
				&& ((this.getS1n74Kwh() == castOther.getS1n74Kwh()) || (this
						.getS1n74Kwh() != null
						&& castOther.getS1n74Kwh() != null && this
						.getS1n74Kwh().equals(castOther.getS1n74Kwh())))
				&& ((this.getS1n75Kwh() == castOther.getS1n75Kwh()) || (this
						.getS1n75Kwh() != null
						&& castOther.getS1n75Kwh() != null && this
						.getS1n75Kwh().equals(castOther.getS1n75Kwh())))
				&& ((this.getS1n76Kwh() == castOther.getS1n76Kwh()) || (this
						.getS1n76Kwh() != null
						&& castOther.getS1n76Kwh() != null && this
						.getS1n76Kwh().equals(castOther.getS1n76Kwh())))
				&& ((this.getS1n77Kwh() == castOther.getS1n77Kwh()) || (this
						.getS1n77Kwh() != null
						&& castOther.getS1n77Kwh() != null && this
						.getS1n77Kwh().equals(castOther.getS1n77Kwh())))
				&& ((this.getS1n78Kwh() == castOther.getS1n78Kwh()) || (this
						.getS1n78Kwh() != null
						&& castOther.getS1n78Kwh() != null && this
						.getS1n78Kwh().equals(castOther.getS1n78Kwh())))
				&& ((this.getS1n79Kwh() == castOther.getS1n79Kwh()) || (this
						.getS1n79Kwh() != null
						&& castOther.getS1n79Kwh() != null && this
						.getS1n79Kwh().equals(castOther.getS1n79Kwh())))
				&& ((this.getS1n80Kwh() == castOther.getS1n80Kwh()) || (this
						.getS1n80Kwh() != null
						&& castOther.getS1n80Kwh() != null && this
						.getS1n80Kwh().equals(castOther.getS1n80Kwh())))
				&& ((this.getS1n81Kwh() == castOther.getS1n81Kwh()) || (this
						.getS1n81Kwh() != null
						&& castOther.getS1n81Kwh() != null && this
						.getS1n81Kwh().equals(castOther.getS1n81Kwh())))
				&& ((this.getS1n82Kwh() == castOther.getS1n82Kwh()) || (this
						.getS1n82Kwh() != null
						&& castOther.getS1n82Kwh() != null && this
						.getS1n82Kwh().equals(castOther.getS1n82Kwh())))
				&& ((this.getS1n83Kwh() == castOther.getS1n83Kwh()) || (this
						.getS1n83Kwh() != null
						&& castOther.getS1n83Kwh() != null && this
						.getS1n83Kwh().equals(castOther.getS1n83Kwh())))
				&& ((this.getS1n84Kwh() == castOther.getS1n84Kwh()) || (this
						.getS1n84Kwh() != null
						&& castOther.getS1n84Kwh() != null && this
						.getS1n84Kwh().equals(castOther.getS1n84Kwh())))
				&& ((this.getS1n85Kwh() == castOther.getS1n85Kwh()) || (this
						.getS1n85Kwh() != null
						&& castOther.getS1n85Kwh() != null && this
						.getS1n85Kwh().equals(castOther.getS1n85Kwh())))
				&& ((this.getS1n86Kwh() == castOther.getS1n86Kwh()) || (this
						.getS1n86Kwh() != null
						&& castOther.getS1n86Kwh() != null && this
						.getS1n86Kwh().equals(castOther.getS1n86Kwh())))
				&& ((this.getS1n87Kwh() == castOther.getS1n87Kwh()) || (this
						.getS1n87Kwh() != null
						&& castOther.getS1n87Kwh() != null && this
						.getS1n87Kwh().equals(castOther.getS1n87Kwh())))
				&& ((this.getS1n88Kwh() == castOther.getS1n88Kwh()) || (this
						.getS1n88Kwh() != null
						&& castOther.getS1n88Kwh() != null && this
						.getS1n88Kwh().equals(castOther.getS1n88Kwh())))
				&& ((this.getS1n89Kwh() == castOther.getS1n89Kwh()) || (this
						.getS1n89Kwh() != null
						&& castOther.getS1n89Kwh() != null && this
						.getS1n89Kwh().equals(castOther.getS1n89Kwh())))
				&& ((this.getS1n90Kwh() == castOther.getS1n90Kwh()) || (this
						.getS1n90Kwh() != null
						&& castOther.getS1n90Kwh() != null && this
						.getS1n90Kwh().equals(castOther.getS1n90Kwh())))
				&& ((this.getS1n91Kwh() == castOther.getS1n91Kwh()) || (this
						.getS1n91Kwh() != null
						&& castOther.getS1n91Kwh() != null && this
						.getS1n91Kwh().equals(castOther.getS1n91Kwh())))
				&& ((this.getS1n92Kwh() == castOther.getS1n92Kwh()) || (this
						.getS1n92Kwh() != null
						&& castOther.getS1n92Kwh() != null && this
						.getS1n92Kwh().equals(castOther.getS1n92Kwh())))
				&& ((this.getS1n93Kwh() == castOther.getS1n93Kwh()) || (this
						.getS1n93Kwh() != null
						&& castOther.getS1n93Kwh() != null && this
						.getS1n93Kwh().equals(castOther.getS1n93Kwh())))
				&& ((this.getS1n94Kwh() == castOther.getS1n94Kwh()) || (this
						.getS1n94Kwh() != null
						&& castOther.getS1n94Kwh() != null && this
						.getS1n94Kwh().equals(castOther.getS1n94Kwh())))
				&& ((this.getS1n95Kwh() == castOther.getS1n95Kwh()) || (this
						.getS1n95Kwh() != null
						&& castOther.getS1n95Kwh() != null && this
						.getS1n95Kwh().equals(castOther.getS1n95Kwh())))
				&& ((this.getS1n96Kwh() == castOther.getS1n96Kwh()) || (this
						.getS1n96Kwh() != null
						&& castOther.getS1n96Kwh() != null && this
						.getS1n96Kwh().equals(castOther.getS1n96Kwh())))
				&& ((this.getS1n97Kwh() == castOther.getS1n97Kwh()) || (this
						.getS1n97Kwh() != null
						&& castOther.getS1n97Kwh() != null && this
						.getS1n97Kwh().equals(castOther.getS1n97Kwh())))
				&& ((this.getS1n98Kwh() == castOther.getS1n98Kwh()) || (this
						.getS1n98Kwh() != null
						&& castOther.getS1n98Kwh() != null && this
						.getS1n98Kwh().equals(castOther.getS1n98Kwh())))
				&& ((this.getS1n99Kwh() == castOther.getS1n99Kwh()) || (this
						.getS1n99Kwh() != null
						&& castOther.getS1n99Kwh() != null && this
						.getS1n99Kwh().equals(castOther.getS1n99Kwh())))
				&& ((this.getS1n100Kwh() == castOther.getS1n100Kwh()) || (this
						.getS1n100Kwh() != null
						&& castOther.getS1n100Kwh() != null && this
						.getS1n100Kwh().equals(castOther.getS1n100Kwh())))
				&& ((this.getS1n101Kwh() == castOther.getS1n101Kwh()) || (this
						.getS1n101Kwh() != null
						&& castOther.getS1n101Kwh() != null && this
						.getS1n101Kwh().equals(castOther.getS1n101Kwh())))
				&& ((this.getS1n102Kwh() == castOther.getS1n102Kwh()) || (this
						.getS1n102Kwh() != null
						&& castOther.getS1n102Kwh() != null && this
						.getS1n102Kwh().equals(castOther.getS1n102Kwh())))
				&& ((this.getS1n103Kwh() == castOther.getS1n103Kwh()) || (this
						.getS1n103Kwh() != null
						&& castOther.getS1n103Kwh() != null && this
						.getS1n103Kwh().equals(castOther.getS1n103Kwh())))
				&& ((this.getS1n104Kwh() == castOther.getS1n104Kwh()) || (this
						.getS1n104Kwh() != null
						&& castOther.getS1n104Kwh() != null && this
						.getS1n104Kwh().equals(castOther.getS1n104Kwh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDatestr() == null ? 0 : this.getDatestr().hashCode());
		result = 37 * result
				+ (getS1n1Kwh() == null ? 0 : this.getS1n1Kwh().hashCode());
		result = 37 * result
				+ (getS1n2Kwh() == null ? 0 : this.getS1n2Kwh().hashCode());
		result = 37 * result
				+ (getS1n3Kwh() == null ? 0 : this.getS1n3Kwh().hashCode());
		result = 37 * result
				+ (getS1n4Kwh() == null ? 0 : this.getS1n4Kwh().hashCode());
		result = 37 * result
				+ (getS1n5Kwh() == null ? 0 : this.getS1n5Kwh().hashCode());
		result = 37 * result
				+ (getS1n6Kwh() == null ? 0 : this.getS1n6Kwh().hashCode());
		result = 37 * result
				+ (getS1n7Kwh() == null ? 0 : this.getS1n7Kwh().hashCode());
		result = 37 * result
				+ (getS1n8Kwh() == null ? 0 : this.getS1n8Kwh().hashCode());
		result = 37 * result
				+ (getS1n9Kwh() == null ? 0 : this.getS1n9Kwh().hashCode());
		result = 37 * result
				+ (getS1n10Kwh() == null ? 0 : this.getS1n10Kwh().hashCode());
		result = 37 * result
				+ (getS1n11Kwh() == null ? 0 : this.getS1n11Kwh().hashCode());
		result = 37 * result
				+ (getS1n12Kwh() == null ? 0 : this.getS1n12Kwh().hashCode());
		result = 37 * result
				+ (getS1n13Kwh() == null ? 0 : this.getS1n13Kwh().hashCode());
		result = 37 * result
				+ (getS1n14Kwh() == null ? 0 : this.getS1n14Kwh().hashCode());
		result = 37 * result
				+ (getS1n15Kwh() == null ? 0 : this.getS1n15Kwh().hashCode());
		result = 37 * result
				+ (getS1n16Kwh() == null ? 0 : this.getS1n16Kwh().hashCode());
		result = 37 * result
				+ (getS1n17Kwh() == null ? 0 : this.getS1n17Kwh().hashCode());
		result = 37 * result
				+ (getS1n18Kwh() == null ? 0 : this.getS1n18Kwh().hashCode());
		result = 37 * result
				+ (getS1n19Kwh() == null ? 0 : this.getS1n19Kwh().hashCode());
		result = 37 * result
				+ (getS1n20Kwh() == null ? 0 : this.getS1n20Kwh().hashCode());
		result = 37 * result
				+ (getS1n21Kwh() == null ? 0 : this.getS1n21Kwh().hashCode());
		result = 37 * result
				+ (getS1n22Kwh() == null ? 0 : this.getS1n22Kwh().hashCode());
		result = 37 * result
				+ (getS1n23Kwh() == null ? 0 : this.getS1n23Kwh().hashCode());
		result = 37 * result
				+ (getS1n24Kwh() == null ? 0 : this.getS1n24Kwh().hashCode());
		result = 37 * result
				+ (getS1n25Kwh() == null ? 0 : this.getS1n25Kwh().hashCode());
		result = 37 * result
				+ (getS1n26Kwh() == null ? 0 : this.getS1n26Kwh().hashCode());
		result = 37 * result
				+ (getS1n27Kwh() == null ? 0 : this.getS1n27Kwh().hashCode());
		result = 37 * result
				+ (getS1n28Kwh() == null ? 0 : this.getS1n28Kwh().hashCode());
		result = 37 * result
				+ (getS1n29Kwh() == null ? 0 : this.getS1n29Kwh().hashCode());
		result = 37 * result
				+ (getS1n30Kwh() == null ? 0 : this.getS1n30Kwh().hashCode());
		result = 37 * result
				+ (getS1n31Kwh() == null ? 0 : this.getS1n31Kwh().hashCode());
		result = 37 * result
				+ (getS1n32Kwh() == null ? 0 : this.getS1n32Kwh().hashCode());
		result = 37 * result
				+ (getS1n33Kwh() == null ? 0 : this.getS1n33Kwh().hashCode());
		result = 37 * result
				+ (getS1n34Kwh() == null ? 0 : this.getS1n34Kwh().hashCode());
		result = 37 * result
				+ (getS1n35Kwh() == null ? 0 : this.getS1n35Kwh().hashCode());
		result = 37 * result
				+ (getS1n36Kwh() == null ? 0 : this.getS1n36Kwh().hashCode());
		result = 37 * result
				+ (getS1n37Kwh() == null ? 0 : this.getS1n37Kwh().hashCode());
		result = 37 * result
				+ (getS1n38Kwh() == null ? 0 : this.getS1n38Kwh().hashCode());
		result = 37 * result
				+ (getS1n39Kwh() == null ? 0 : this.getS1n39Kwh().hashCode());
		result = 37 * result
				+ (getS1n40Kwh() == null ? 0 : this.getS1n40Kwh().hashCode());
		result = 37 * result
				+ (getS1n41Kwh() == null ? 0 : this.getS1n41Kwh().hashCode());
		result = 37 * result
				+ (getS1n42Kwh() == null ? 0 : this.getS1n42Kwh().hashCode());
		result = 37 * result
				+ (getS1n43Kwh() == null ? 0 : this.getS1n43Kwh().hashCode());
		result = 37 * result
				+ (getS1n44Kwh() == null ? 0 : this.getS1n44Kwh().hashCode());
		result = 37 * result
				+ (getS1n45Kwh() == null ? 0 : this.getS1n45Kwh().hashCode());
		result = 37 * result
				+ (getS1n46Kwh() == null ? 0 : this.getS1n46Kwh().hashCode());
		result = 37 * result
				+ (getS1n47Kwh() == null ? 0 : this.getS1n47Kwh().hashCode());
		result = 37 * result
				+ (getS1n48Kwh() == null ? 0 : this.getS1n48Kwh().hashCode());
		result = 37 * result
				+ (getS1n49Kwh() == null ? 0 : this.getS1n49Kwh().hashCode());
		result = 37 * result
				+ (getS1n50Kwh() == null ? 0 : this.getS1n50Kwh().hashCode());
		result = 37 * result
				+ (getS1n51Kwh() == null ? 0 : this.getS1n51Kwh().hashCode());
		result = 37 * result
				+ (getS1n52Kwh() == null ? 0 : this.getS1n52Kwh().hashCode());
		result = 37 * result
				+ (getS1n53Kwh() == null ? 0 : this.getS1n53Kwh().hashCode());
		result = 37 * result
				+ (getS1n54Kwh() == null ? 0 : this.getS1n54Kwh().hashCode());
		result = 37 * result
				+ (getS1n55Kwh() == null ? 0 : this.getS1n55Kwh().hashCode());
		result = 37 * result
				+ (getS1n56Kwh() == null ? 0 : this.getS1n56Kwh().hashCode());
		result = 37 * result
				+ (getS1n57Kwh() == null ? 0 : this.getS1n57Kwh().hashCode());
		result = 37 * result
				+ (getS1n58Kwh() == null ? 0 : this.getS1n58Kwh().hashCode());
		result = 37 * result
				+ (getS1n59Kwh() == null ? 0 : this.getS1n59Kwh().hashCode());
		result = 37 * result
				+ (getS1n60Kwh() == null ? 0 : this.getS1n60Kwh().hashCode());
		result = 37 * result
				+ (getS1n61Kwh() == null ? 0 : this.getS1n61Kwh().hashCode());
		result = 37 * result
				+ (getS1n62Kwh() == null ? 0 : this.getS1n62Kwh().hashCode());
		result = 37 * result
				+ (getS1n63Kwh() == null ? 0 : this.getS1n63Kwh().hashCode());
		result = 37 * result
				+ (getS1n64Kwh() == null ? 0 : this.getS1n64Kwh().hashCode());
		result = 37 * result
				+ (getS1n65Kwh() == null ? 0 : this.getS1n65Kwh().hashCode());
		result = 37 * result
				+ (getS1n66Kwh() == null ? 0 : this.getS1n66Kwh().hashCode());
		result = 37 * result
				+ (getS1n67Kwh() == null ? 0 : this.getS1n67Kwh().hashCode());
		result = 37 * result
				+ (getS1n68Kwh() == null ? 0 : this.getS1n68Kwh().hashCode());
		result = 37 * result
				+ (getS1n69Kwh() == null ? 0 : this.getS1n69Kwh().hashCode());
		result = 37 * result
				+ (getS1n70Kwh() == null ? 0 : this.getS1n70Kwh().hashCode());
		result = 37 * result
				+ (getS1n71Kwh() == null ? 0 : this.getS1n71Kwh().hashCode());
		result = 37 * result
				+ (getS1n72Kwh() == null ? 0 : this.getS1n72Kwh().hashCode());
		result = 37 * result
				+ (getS1n73Kwh() == null ? 0 : this.getS1n73Kwh().hashCode());
		result = 37 * result
				+ (getS1n74Kwh() == null ? 0 : this.getS1n74Kwh().hashCode());
		result = 37 * result
				+ (getS1n75Kwh() == null ? 0 : this.getS1n75Kwh().hashCode());
		result = 37 * result
				+ (getS1n76Kwh() == null ? 0 : this.getS1n76Kwh().hashCode());
		result = 37 * result
				+ (getS1n77Kwh() == null ? 0 : this.getS1n77Kwh().hashCode());
		result = 37 * result
				+ (getS1n78Kwh() == null ? 0 : this.getS1n78Kwh().hashCode());
		result = 37 * result
				+ (getS1n79Kwh() == null ? 0 : this.getS1n79Kwh().hashCode());
		result = 37 * result
				+ (getS1n80Kwh() == null ? 0 : this.getS1n80Kwh().hashCode());
		result = 37 * result
				+ (getS1n81Kwh() == null ? 0 : this.getS1n81Kwh().hashCode());
		result = 37 * result
				+ (getS1n82Kwh() == null ? 0 : this.getS1n82Kwh().hashCode());
		result = 37 * result
				+ (getS1n83Kwh() == null ? 0 : this.getS1n83Kwh().hashCode());
		result = 37 * result
				+ (getS1n84Kwh() == null ? 0 : this.getS1n84Kwh().hashCode());
		result = 37 * result
				+ (getS1n85Kwh() == null ? 0 : this.getS1n85Kwh().hashCode());
		result = 37 * result
				+ (getS1n86Kwh() == null ? 0 : this.getS1n86Kwh().hashCode());
		result = 37 * result
				+ (getS1n87Kwh() == null ? 0 : this.getS1n87Kwh().hashCode());
		result = 37 * result
				+ (getS1n88Kwh() == null ? 0 : this.getS1n88Kwh().hashCode());
		result = 37 * result
				+ (getS1n89Kwh() == null ? 0 : this.getS1n89Kwh().hashCode());
		result = 37 * result
				+ (getS1n90Kwh() == null ? 0 : this.getS1n90Kwh().hashCode());
		result = 37 * result
				+ (getS1n91Kwh() == null ? 0 : this.getS1n91Kwh().hashCode());
		result = 37 * result
				+ (getS1n92Kwh() == null ? 0 : this.getS1n92Kwh().hashCode());
		result = 37 * result
				+ (getS1n93Kwh() == null ? 0 : this.getS1n93Kwh().hashCode());
		result = 37 * result
				+ (getS1n94Kwh() == null ? 0 : this.getS1n94Kwh().hashCode());
		result = 37 * result
				+ (getS1n95Kwh() == null ? 0 : this.getS1n95Kwh().hashCode());
		result = 37 * result
				+ (getS1n96Kwh() == null ? 0 : this.getS1n96Kwh().hashCode());
		result = 37 * result
				+ (getS1n97Kwh() == null ? 0 : this.getS1n97Kwh().hashCode());
		result = 37 * result
				+ (getS1n98Kwh() == null ? 0 : this.getS1n98Kwh().hashCode());
		result = 37 * result
				+ (getS1n99Kwh() == null ? 0 : this.getS1n99Kwh().hashCode());
		result = 37 * result
				+ (getS1n100Kwh() == null ? 0 : this.getS1n100Kwh().hashCode());
		result = 37 * result
				+ (getS1n101Kwh() == null ? 0 : this.getS1n101Kwh().hashCode());
		result = 37 * result
				+ (getS1n102Kwh() == null ? 0 : this.getS1n102Kwh().hashCode());
		result = 37 * result
				+ (getS1n103Kwh() == null ? 0 : this.getS1n103Kwh().hashCode());
		result = 37 * result
				+ (getS1n104Kwh() == null ? 0 : this.getS1n104Kwh().hashCode());
		return result;
	}
}
