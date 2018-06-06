<%-- <%@ page language=java contentType=text/html; charset=ISO-8859-1
    pageEncoding=ISO-8859-1%> --%>
<!DOCTYPE html PUBLIC -//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd>
<html>
<head>
<meta http-equiv=Content-Type content=text/html; charset=ISO-8859-1>
<title>Insert title here</title>
</head>
<body>
<div id='myTab'>
<table id='tabs' style='width: 100%; background: white; padding: 21px 23px; border: 2px solid black; font-size: 12px;border-collapse: collapse;'>
<tr style='border-bottom:1px solid black;'><td style='padding: 0.77em;'>&nbsp;</td><td style='padding: 0.77em;'><b><u>ELECTRICITY BILL</u></b></td></tr>
<tr style='border-bottom:1px solid black;'><td style='width:80px;height:100px; border-bottom:1px solid black;vertical-align:'> <img src='http://www.ireoprojects.co.in/gallery/ireo-grandarch.jpg' style='width:160px;height:100px'/></td>

<td style='padding: 0.77em; border-bottom:1px solid black;border-right:1px solid black;vertical-align: top;border-top:1px solid black;border-right:1px solid black;top;width: 33%;'><b style='color:#FF9900;font-size:15px;'>Grand Arch Resident Welfare Association</b><br/><b style='color:#90EE90;font-size:13px;'>Sector-58, Gram-Behrampur Gurgaon - 122002</b><br/></td>
<td style='padding: 0.77em; border-bottom:1px solid black;vertical-align: top;border-top:1px solid black;'><b>Amount Payable -+electricityBillEntity.getNetAmount()</b><br/><b>Due Date -+DateFormatUtils.format(electricityBillEntity.getBillDueDate(), dd MMM yyyy)</b></td></tr>
<tr>
				<td style='width:400px;vertical-align: top;border-right: 1pt solid black;'>
				    <b style='font-size:15px;margin-left: 10px;'><u>Customer Details</u></b>
					<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>
                    <tr>
					<td style='' ><p>Name </p></td><td style='padding:  0.2em;text-align: left'>+electricityBillEntity.getAccountObj().getPerson().getFirstName()</td> 
					
					</tr>
							<tr>		
						
					<td style=''><p>Address </p></td> <td style='padding:  0.2em;'>+address1</td> 
					
						</tr>
							<tr>	
							
					<td style=''><p>City</p></td> <td style='padding:  0.2em;'>+city</td> 
					
						</tr>
							<tr>	
							
					<td style=''><p>Mobile No </p></td> <td style='padding:  0.2em;'>+mobile</td> 
					
						</tr>
							<tr>	
							
					<td style=' '><p>E-Mail </p></td> <td style='padding:  0.2em;'>+email</td> 
					
							
						</tr>
						<tr><td style='padding: 0.77em;'><b style='font-size:15px;'><u>Account Details</u></b></td></tr>
				
<tr>	
					<td style=''><p>CA No </p></td> <td style='padding:  0.2em;'>+electricityBillEntity.getAccountObj().getAccountNo()</td> 
						
							
						</tr>
							<tr>	
					<td style=''><p>Service Type</p></td> <td style='padding:  0.2em;'>+electricityBillEntity.getTypeOfService()</td> 
						
							
						</tr>
							<tr>	
					<td style=''><p>Tariff Category</p></td> <td style='padding:  0.2em;'>+tariffName</td> 
						
						</tr>
						<tr>	
					<td style=''><p>Bill Basis</p></td> <td style='padding:  0.2em;'>+electricityBillEntity.getBillType()</td> 
						
						</tr>
						</table>
					</td>
					
					<td style='width:400px;vertical-align: top; border-right: 1pt solid black;'>
					<table style='width: 100%;border-collapse: collapse;margin-left: 2px;' >
					<tr><td style='padding: 0.77em;'><b style='font-size:15px;margin-left: 10px;'><u>Connection Details</u></b></td></tr>
				
<tr>	
					<td style=''><p>Sanctioned (Utility) </p></td> <td style='padding:  0.2em;'>+sanctionLoad</td> 
						
							
						</tr>
							<tr>	
					<td style=''><p>Sanctioned (DG)</p></td> <td style='padding:  0.2em;'>+sanctionLoadDG</td> 
						
							
						</tr>
							<tr>	
					<td style=''><p>Voltage Level</p></td> <td style='padding:  0.2em;'>+voltageLevel</td> 
						
						</tr>
						<tr>	
					<td style=''><p>Meter Type</p></td> <td style='padding:  0.2em;'>+meterType</td> 
						
						</tr>
						<tr>	
					<td style=''><p>Connection Type</p></td> <td style='padding:  0.2em;'>+connectionType</td> 
						
						</tr>
						<tr>	
					<td style=''><p>Connection Security</p></td> <td style='padding:  0.2em;'>+connectionSecurity</td> 
						
						</tr>
						<tr>	
					<td style=''><p>PF</p></td> <td style='padding:  0.2em;'>+pfValue</td> 
						
						</tr>
						</table>
					</td>

					<td style='width:400px;vertical-align: top;'>
					<table style='width: 100%;border-collapse: collapse;margin-left: 2px;'>
					<tr>	
					<td style=''><p>&nbsp;</p></td> <td style='padding:  0.2em;'>&nbsp;</td> 
						
						</tr>
					
				<tr>
					<td style=' '><p>Surcharge </p></td><td style='padding:  0.2em;'>+new BigDecimal((latePaymentAmount)).setScale(2, RoundingMode.HALF_UP)</td> 
					
                   </tr>
							<tr>		
						
					<td style=''><p>Payable after Due Date - </p></td> <td style='padding:  0.2em;'>+ new BigDecimal((latePaymentAmount+electricityBillEntity.getNetAmount())).setScale(2, RoundingMode.HALF_UP)</td>
					
						</tr>
							<tr>	
							
					<td style=''><p>Bill No</p></td> <td style='padding:  0.2em;'>+electricityBillEntity.getBillNo()</td> 
					
						</tr>
							<tr>	
							
					<td style=''><p>Bill Date</p></td><td style='padding:  0.2em;'>+DateFormatUtils.format(electricityBillEntity.getBillDate(),dd MMM yyyy)</td>
					
						</tr>
							<tr>	
							
					<td style=''><p>Billing Period </p></td>  <td style='padding:  0.2em;'>+DateFormatUtils.format(electricityBillEntity.getFromDate(),dd MMM yyyy)  To +DateFormatUtils.format(electricityBillEntity.getBillDate(),dd MMM yyyy) </td>
					
							
						</tr>
						<tr><td style='padding: 0.77em;'><b style='font-size:15px;'><u>Meter Details</u></b></td></tr>
				
                 <tr>	
					<td style=''><p>Meter Make </p></td> <td style='padding:  0.2em;'>+meterMake</td> 
						
							
						</tr>
						<tr>	
                    <td style=''><p>Meter Sr No</p></td> <td style='padding:  0.2em;'>+meterSrNo</td>	 
						
							
						</tr>	
							<tr>		
					<td style=''><p>Meter Status</p></td> <td style='padding:  0.2em;'>+meterStatus</td> 	
						
						</tr>
						<tr>		
						<td style=''><p>MF</p></td> <td style='padding:  0.2em;'>+meterConstant</td> 	
							</tr>	
						</table>	
					</td>	
					
					</tr>	

					 <tr style='background-color: black'>	
					<td style='backgound: black; color: white; font-weight: bolder;' colspan=3>Particulars</td>	
				</tr>	
				<tr>	
					<td colspan='3'>	
						<table style='width: 100%; text-align: center;'>	
							<tr>	
								<td width='10%' style=' border: 1px solid #808080;'>	
									<table style='width: 100%; text-align: center;vertical-align: top;'>	
									<tr>	
									<th style='font-weight: bold; border: 1px solid #808080;'>Energy Type / Meter Sr No</th></tr>	
	
									<tr><td style=' border: 1px solid #808080;'>+electricityBillEntity.getTypeOfService()/+meterSrNo</td></tr>	
									
								</table>	
								</td>	

								<td width='10%' style=' border: 1px solid #808080;'>	
									<table style='width: 100%; text-align: center;vertical-align: top;'>	
									<tr>	
									<th style='font-weight: bold; border: 1px solid #808080;'>MDI</th></tr>	
									<tr><td style=' border: 1px solid #808080;'>+mdiValue</td></tr>	
									
								</table>	
								</td>	

								<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>	
									<table style='width: 100%; text-align: center;'>	
									<tr>	
									
										
											<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Meter Reading date</th>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>New</td>	

										<td style=' border: 1px solid #808080;' width='50%' >Old</td>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>+electricityBillEntity.getBillDate()</td>	

										<td style=' border: 1px solid #808080;' width='50%' >+electricityBillEntity.getFromDate()</td>	
										</tr>	
										
								</table>	
								</td>	

								<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>	
									<table style='width: 100%; text-align: center;'>	
									<tr>	
									
									
											<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Meter Reading (kWh)</th>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>New</td>	
										<td style=' border: 1px solid #808080;' width='50%' >Old</td>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>+presentReading</td>	

										<td style=' border: 1px solid #808080;' width='50%' >+previousReading</td>	
										</tr>	
										
								</table>	
								</td>	

								<td width='10%' style=' border: 1px solid #808080;vertical-align: top;'>	
									<table style='width: 100%; text-align: center;'>	
									<tr>	
									<th style='font-weight: bold; border: 1px solid #808080;'>MF</th></tr>	
									<tr><td style=' border: 1px solid #808080;'>+meterConstant</td></tr>	
									
									</table>	
								</td>	

								<td width='30%' style=' border: 1px solid #808080;vertical-align: top;'>	
									<table style='width: 100%; text-align: center;'>	
									<tr>	
										<th style='font-weight: bold; border: 1px solid #808080;' colspan='2'>Current Consumption</th>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>Billed Units</td>	
										<td style=' border: 1px solid #808080;' width='50%' >Days</td>	
										</tr>	
										<tr>	
										<td style=' border: 1px solid #808080;' width='50%'>+uomValue</td>	

										<td style=' border: 1px solid #808080;' width='50%' >+numberOfDays</td>	
										</tr>	
										
								</table>	
								</td>	
								</tr>	
								</table>	
								</td></tr>	

								 <tr><td style='background-color: black; color: white; font-weight: bolder; text-align: center;'>Bill Details</td><td style='background-color: #808000; color: white; font-weight: bolder; text-align: center;'>Slab Details</td><td style='background-color: #87CEEB; color: white; font-weight: bolder; text-align: center;'>Arrear Details</td></tr>	
						<tr>	

				   <td style='width:400px;vertical-align: top;'>	
					<table style='width: 100%;margin-left: 2px;'>	
	                +bliStr
	                 <tr>
	                 <td style=''><b>Total Charges Payable by Due Date</b></td> <td style='padding:  0.2em;'>+electricityBillEntity.getBillAmount()</td> 
	                 </tr>
	                + </table>	
					</td>	
					
					<td style='width:400px; vertical-align: top;'>	
					<table style='width: 100%;margin-left: 2px;'>	
				<tr>	
					<td style='' ><p>Units</p></td> 	
					<td style='' ><p>Rates </p></td> 	
					<td style='' ><p>Amount (Rs.) </p></td> 	
					</tr>	
					+slabString	
					<tr style='background-color: #808000; color: white; font-weight: bolder; text-align: center;width: 100%;'><td colspan='3'>DG Details</td></tr>	
					<tr>	
					<td style='' ><p>Units</p></td>	 
					<td style='' ><p>Rates </p></td>	 
					<td style='' ><p>Amount (Rs.) </p></td>	 
					</tr>	
					<tr>	
					+dgSlabString					
					</tr>	
					</table>	
					</td>	
				<td style='width:400px; vertical-align: top;'>	
					<table style='width: 100%;margin-left: 2px;'>
				<tr>
				<td style='' ><p>arrears</p></td>
				</tr>
			<tr>			
                    +arrearString                   
                    </tr>
						</table>	
					</td>	
					</tr>	
				<tr><td colspan='2' style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>Consumption Graph</td><td  style='background-color: #808000; color: black; font-weight: bolder; text-align: center;'>Previous Consumption Pattern</td></tr>
				<td colspan='2' style='width:400px; vertical-align: top;'>	
				    /*<div id='container' style='height: 200px; max-height: 200px; width: 350px; max-width: 350px'>
					</div>*/
					 <img id='chart' src='file://D:/Bills/graphs/abc.png'  />
					/* <canvas id='canvas'></canvas>*/
				</td>	
					<td  style='width:400px;vertical-align: top;'>	
					<table style='width: 100%;margin-left: 2px;'>	
				    <tr>	
					<td style='' ><p>Bill Month</p></td>	 
					<td style='' ><p>Units</p></td>	 
					<td style='' ><p>Amount</p></td> 	
					</tr>	
					+lastSixBills
					<tr style='background-color: #808000; color: black; font-weight: bolder; text-align: center;width: 100%;'><td colspan='3'>Last Payment Status</td></tr>	
					+paymentString
					</table>	
					</td>	
					</tr>	
					<tr style='background-color: #808000'>	
					<td colspan='3' style='backgound: black; color: black; font-weight: bolder;'><u>Notes : Grand Arch Resident Welfare Association</u></td>	
				</tr>
					<tr>
					<td colspan='3' style='width:400px; vertical-align: top;'>	
					<table style='width: 100%;margin-left: 10px;'>	
				<tr>	
					<td style='' ><p>1 Payment Can be made by crossed cheque/DD in favour of Grand Arch Resident Welfare Association (A/C No...xxxxxx)payable at Gurgaon</p></td>	
					</tr>	
					<tr>	
					<td style='' ><p>2 Resident can pay through RTGS/NEFT as per details :- Grand Arch Resident Welfare Association. A/C No xxxxxx, kotak bank Golf course road, Sector-53 Gurgaon </p></td>	
					</tr>	
					<tr>	
					<td style='' ><p>3 Add Rs 50/- for outstation cheques and bank charge of 150/- shall be levied on dishonour cheques </p></td>	 
					</tr>	
					<tr>	
					<td style='' ><p>4 Cheque should not be post dated and write your Name, Flat no, Contact no on the reverse of the cheque.</p></td>	 
					</tr>	
					<tr>	
					<td style='' ><p>5 For more details kindly login on <b style='font-size:13px;color: blue;'><u>www.icommunity.in</u></b> and follow the given instructions </p></td> 	
					</tr>	
					</table>	
					</td>
					</tr>
					
						
					</table>	
           </div>
</body>
</html>