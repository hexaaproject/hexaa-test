/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributes;

import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ATTRIBUTESPECS} URI
 *
 * @author Bana Tibor
 */
public class Attributespecs extends BasicCall {

    public Attributespecs() {
        super();
        this.setPath(ServerConstants.ApiCalls.ATTRIBUTESPECS);
    }
}
