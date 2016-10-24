package com.cmput301f16t11.a2b;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 Use Case: 7
 ID: US 01.07.01
 Description: A rider confirms that a request has been completed and officially transfer payment.
 Primary Actor: The casual rider
 Supporting Actor(s): The driver who has completed the rider's request
 Goal: For the rider to close a request as completed and complete payment to the driver
 Pre-conditions:

 Driver has accepted and completed the rider's request
 The rider had confirmed the acceptance
 Post-conditions:

 The request is closed as completed, and the rider's payment information is processed.
 Basic Flow:

 1 The (logged in) rider selects a confirm that they have arrived at the destination
 2 The rider selects the payment option
 Exceptions:

 2 Valid Payment information not found -> input payment information
 */
public class Story7UnitTest{
    String userName = "rider";
    String startLocation = "8210 108 St NW Edmonton, AB T6E 5T2";
    String endLocation = "10189 106 Street Northwest, Edmonton, AB T5J 1H3";
    Number fare = 10.00;

    User rider = UserController.loadUser(userName);
    User driver = UserController.loadUser("driver");
    UserRequest request;


    private void setUp()
    {
        rider.createRequest(startLocation,endLocation,fare);
        request = rider.getLatestActiveRequest();
        driver.addAcceptedRequest(request);
        request.setAcceptedStatus(true); // rider accepts ride
    }


    @Test
    public void checkPayment() {
        setUp();
        request.setPaymentReceived(true); // rider pays
        assertEquals(true,request.isPaymentRecived());
    }


}