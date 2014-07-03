/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.entitlements;

import sztaki.hexaa.httputility.HttpUtilityBasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * @author Bana Tibor
 */
public class Entitlementpacks_ID extends HttpUtilityBasicCall {

    public Entitlementpacks_ID() {
        this.setPath(ServerConstants.ApiCalls.ENTITLEMENTPACKS_ID);
    }
}
