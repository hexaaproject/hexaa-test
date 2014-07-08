package sztaki.hexaa.httputility.apicalls.attributes;

import org.json.simple.JSONObject;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;
import sztaki.hexaa.httputility.BasicCall;

public class AttributespecsInsertTest {

    @BeforeClass
    public static void setUpClass() {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    @Test
    public void testInserting() {

        JSONObject json = new JSONObject();
        json.put("oid", "1");
        json.put("friendly_name", "testName1");
        json.put("syntax", "noSyntax1");
        json.put("is_multivalue", false);

        assertEquals("", new Attributespecs().call(BasicCall.REST.POST, json.toJSONString()));

        json = new JSONObject();
        json.put("oid", "2");
        json.put("friendly_name", "testName2");
        json.put("syntax", "noSyntax2");
        json.put("is_multivalue", false);

        assertEquals("", new Attributespecs().call(BasicCall.REST.POST, json.toJSONString()));

    }

    @AfterClass
    public static void tearDown() {
        System.out.println(
                new Attributespecs_ID().call(BasicCall.REST.DELETE, 1, 0));
        System.out.println(
                new Attributespecs_ID().call(BasicCall.REST.DELETE, 2, 0));
    }
}
