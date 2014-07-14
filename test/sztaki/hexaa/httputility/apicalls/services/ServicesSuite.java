/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.apicalls.CleanTest;
import sztaki.hexaa.httputility.apicalls.services.entitlements.*;
import sztaki.hexaa.httputility.apicalls.services.entitlementpacks.*;

/**
 *
 * @author Bana Tibor
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServicesGetTest.class,
    ServicesPostTest.class,
    ServicesIsEmptyTest.class,
    ServicesEntitlementsPostTest.class,
    ServicesEntitlementsGetTest.class,
    ServicesEntitlementpacksPostTest.class,
    ServicesEntitlementpacksGetTest.class,})
public class ServicesSuite {

    public static boolean CLEANUP_NEEDED = true;

    @BeforeClass
    public static void checkReachable() {
        try {
            InetAddress address;
            address = InetAddress.getByName(Const.HEXAA_HOST);
            Assume.assumeTrue(address.isReachable(5000));
        } catch (UnknownHostException ex) {
            Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CleanTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AssumptionViolatedException e) {
            CLEANUP_NEEDED = false;
            System.out.println(
                    "The host could not be reached, check the connection"
                    + " and/or the sserver constants at"
                    + " /sztaki/hexaa/httputility/Const");
            fail("Host unreachable.");
        }

    }

    @AfterClass
    public static void cleanUp() {
        if (CLEANUP_NEEDED) {
            new DatabaseManipulator().dropDatabase();
            new Authenticator().authenticate();
        }
    }

}
