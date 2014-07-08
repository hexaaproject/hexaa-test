/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.principals;

import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Principal_FEDID extends BasicCall {

    public Principal_FEDID() {
        super();
        this.setPath(ServerConstants.ApiCalls.PRINCIPAL_FEDID);
//        this.getEnabled = true;
//        this.postEnabled = false;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }
}
