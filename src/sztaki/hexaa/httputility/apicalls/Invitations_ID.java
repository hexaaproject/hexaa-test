/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls;

import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 *
 * Calls the {@value ServerConstants.ApiCalls.ENTITYIDS} URI
 *
 * @author Bana Tibor
 */
public class Invitations_ID extends BasicCall {

    public Invitations_ID() {
        super();
        this.setPath(Const.Api.INVITATIONS_ID);
    }
}
