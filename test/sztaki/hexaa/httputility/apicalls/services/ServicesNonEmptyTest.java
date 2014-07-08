/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sztaki.hexaa.httputility.apicalls.services;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;

/**
 *
 * @author Bana Tibor
 */
public class ServicesNonEmptyTest {
    
    @BeforeClass
    public static void setUpClass() {
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }
}
