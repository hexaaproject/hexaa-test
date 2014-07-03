/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.organizations;

import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Organizations_ID_Attributespecs extends HttpUtilityBasicCall {

    public Organizations_ID_Attributespecs() {
        super();
        this.setPath(ServerConstants.ApiCalls.ORGANIZATIONS_ID_ATTRIBUTESPECS);
//        this.getEnabled = true;
//        this.postEnabled = false;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }
}
