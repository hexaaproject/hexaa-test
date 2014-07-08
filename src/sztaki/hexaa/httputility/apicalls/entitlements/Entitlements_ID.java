/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.entitlements;

import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;

/**
 *
 * @author Bana Tibor
 */
public class Entitlements_ID extends BasicCall {

    public Entitlements_ID() {
        this.setPath(Const.Api.ENTITLEMENTS_ID);
    }
}
