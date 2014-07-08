/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.services;

import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.SERVICES} URI
 *
 * @author Bana Tibor
 */
public class Services_Entitlements extends BasicCall {

    public Services_Entitlements() {
        super();
        this.setPath(ServerConstants.ApiCalls.SERVICES_ENTITLEMENTS);
//        this.getEnabled = true;
//        this.postEnabled = true;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }

//    @Override
//    public String call(REST restCall) {
//        this.setString(null);
//        this.setId(0);
//
//        switch (restCall) {
//            case GET:
//                return callSwitch(restCall);
//            case POST:
//                return "JSON string is required, use call(restCall,json) instead";
//            default:
//                return callSwitch(restCall);
//        }
//    }
}
