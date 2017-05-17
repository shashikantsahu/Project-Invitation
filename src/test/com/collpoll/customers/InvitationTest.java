package com.collpoll.customers;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by shashikant on १७-०५-२०१७.
 */
public class InvitationTest {
    @Test
    public void testMain() throws Exception {

    }

    @Test
    public void testShortlistCustomers() throws Exception {
        String filename = System.getProperty("user.dir") + File.separator + "input" + File.separator + "test.json";
        List<ShortListedCustomer> shortListedCustomers = Invitation.shortlistCustomers(filename);
        assertEquals(shortListedCustomers.size(),4); // 4 valid entries within 100 km radius in the "test.json" file
    }

    @Test
    public void testCalculateDistance() throws Exception {
        assertTrue(Invitation.calculateDistance(12.296541,76.639877) > 100); // coordinates of Mysore city
        assertTrue(Invitation.calculateDistance(13.372702,77.673909) < 100); // coordinates of Nandi Hills
        assertTrue(Invitation.calculateDistance(12.919703,77.286751) < 100); // coordinates of Savandurga state forest
        assertTrue(Invitation.calculateDistance(13.339861,77.109453) < 100); // coordinates of Tumkur
        assertTrue(Invitation.calculateDistance(12.935359,77.614167) < 100); // coordinates of Koramangala Social
        assertTrue(Invitation.calculateDistance(38.923375,-95.728571) > 100);// coordinates of Kansas, United States
    }
}