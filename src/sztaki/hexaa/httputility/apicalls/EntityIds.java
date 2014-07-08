/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls;

import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class EntityIds extends BasicCall {

    public EntityIds() {
        super();
        this.setPath(ServerConstants.ApiCalls.ENTITYIDS);
    }
}
