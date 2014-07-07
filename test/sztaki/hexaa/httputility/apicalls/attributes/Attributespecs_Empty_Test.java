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
public class Attributespecs_Empty_Test {

    @Test
    public void testIsEmpty() {
        assertEquals("[]", new Attributespecs().call(HttpUtilityBasicCall.REST.GET));
    }

}
