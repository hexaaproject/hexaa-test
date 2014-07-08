/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.organizations;

import sztaki.hexaa.httputility.apicalls.*;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Organizations_ID_Entitlementpacks_EPID extends BasicCall {

    public Organizations_ID_Entitlementpacks_EPID() {
        super();
        this.setPath(Const.Api.ORGANIZATIONS_ID_ENTITLEMENTPACKS_EPID);
//        this.getEnabled = true;
//        this.postEnabled = false;
//        this.putEnabled = false;
//        this.deleteEnabled = false;
    }
}
