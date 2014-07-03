/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.roles;

import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.HttpUtilityBasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Roles_ID_Principals_PID extends HttpUtilityBasicCall {

    public Roles_ID_Principals_PID() {
        super();
        this.setPath(ServerConstants.ApiCalls.ROLES_ID_PRINCIPALS_PID);
//        this.getEnabled = true;
//        this.postEnabled = false;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }
}
