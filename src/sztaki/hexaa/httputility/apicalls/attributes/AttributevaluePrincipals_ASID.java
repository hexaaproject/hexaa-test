/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.attributes;

import sztaki.hexaa.httputility.HttpUtilityBasicCall;
import sztaki.hexaa.httputility.ServerConstants;

/**
 *
 * @author Bana Tibor
 */
public class AttributevaluePrincipals_ASID extends HttpUtilityBasicCall {

    public AttributevaluePrincipals_ASID() {
        super();
        this.setPath(ServerConstants.ApiCalls.ATTRIBUTEVALUEPRINCIPALS_ASID);
    }
}
