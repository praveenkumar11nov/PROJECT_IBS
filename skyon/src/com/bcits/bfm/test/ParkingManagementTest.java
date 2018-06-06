package com.bcits.bfm.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcits.bfm.service.facilityManagement.ParkingSlotsAllotmentService;
import com.bcits.bfm.service.facilityManagement.ParkingSlotsService;
import com.bcits.bfm.service.facilityManagement.VehicleService;
import com.bcits.bfm.service.facilityManagement.WrongParkingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/config/web-application-config.xml")
public class ParkingManagementTest {

	@Autowired
	private ParkingSlotsService parkingSlotsService;
	@Autowired
	private ParkingSlotsAllotmentService parkingSlotsAllotmentService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private WrongParkingService wrongParkingService;

	@Test
	public void findAll() {
		assertNotNull(parkingSlotsService.findAll());
		assertNotNull(parkingSlotsAllotmentService.findAll());
		assertNotNull(vehicleService.findAll());
		assertNotNull(wrongParkingService.findAll());
	}

	@Test
	public void insert() {

	}

}
