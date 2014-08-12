package sztaki.hexaa.httputility.apicalls.roles;

import java.time.LocalDate;
import java.time.ZoneId;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.Utility;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 * Tests the POST method on the /api/organization/{id}/role call.
 */
public class RolesPostTest extends CleanTest {

    /**
     * Print the class name on the output.
     */
    @BeforeClass
    public static void classInformation() {
        System.out.println("***\t " + RolesPostTest.class.getSimpleName() + " ***");
    }

    /**
     * Creates one organization.
     */
    @BeforeClass
    public static void setUpClass() {
        Utility.Create.organization(new String[]{"testOrgForRole1"});
    }

    /**
     * POSTs a new role.
     */
    @Test
    public void testRolesPost() {
        // Creates the JSON object
        JSONObject json = new JSONObject();
        json.put("name", "testPrimeLeader1");
        json.put("start_date", LocalDate.now(ZoneId.of("UTC")).toString());
        // POST the role
        persistent.call(
                Const.Api.ORGANIZATIONS_ID_ROLES,
                BasicCall.REST.POST,
                json.toString(),
                1, 1);

        try {
            assertEquals("HTTP/1.1 201 Created", persistent.getStatusLine());
        } catch (AssertionError e) {
            AssertErrorHandler(e);
        }
    }
}
