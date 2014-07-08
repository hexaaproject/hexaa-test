/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.roles;

import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Roles_ID_Principals extends BasicCall {

    public Roles_ID_Principals() {
        super();
        this.setPath(Const.Api.ROLES_ID_PRINCIPALS);
//        this.getEnabled = true;
//        this.postEnabled = false;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }
}
