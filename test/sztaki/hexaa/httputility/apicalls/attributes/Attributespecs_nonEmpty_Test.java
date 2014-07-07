/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributes;

import org.junit.Test;
import static org.junit.Assert.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;

/**
 *
 * @author Bana Tibor
 */
public class Attributespecs_nonEmpty_Test {

    @Test
    public void testNotEmpty() {

        String notEmpty = new Attributespecs().call(HttpUtilityBasicCall.REST.GET);
        System.out.println(new Attributespecs_ID().call(HttpUtilityBasicCall.REST.GET, 1, 0));
        assertEquals(notEmpty, "[]", notEmpty);
    }
}
