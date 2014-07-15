/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.organizations;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 * @author Bana Tibor
 */
public class OrganizationIsEmptyTest extends CleanTest {

    /**
     * Tests several uris with GET calls to check the proper empty strings and
     * 404 errors.
     */
    @Test
    public void testOrganizationIsEmptyGet() {
        try {
            assertEquals(
                    "[]",
                    persistent.call(
                            Const.Api.ORGANIZATIONS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 200 OK", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ATTRIBUTESPECS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ATTRIBUTEVALUEORGANIZATIONS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MANAGER,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MEMBERS,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ROLES,
                            BasicCall.REST.GET));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }

    @Test
    public void testOrganizationIsEmptyPut() {
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID_ACCEPT,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_TOKEN,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MANAGER_PID,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
                            BasicCall.REST.PUT));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
    
    @Test
    public void testOrganizationIsEmptyDelete() {
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID,
                            BasicCall.REST.DELETE));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID,
                            BasicCall.REST.DELETE));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MANAGER_PID,
                            BasicCall.REST.DELETE));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
        try {
            assertEquals(
                    "{\"code\":404,\"message\":\"Not Found\"}",
                    persistent.call(
                            Const.Api.ORGANIZATIONS_ID_MEMBERS_PID,
                            BasicCall.REST.DELETE));
            assertEquals("HTTP/1.1 404 Not Found", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
