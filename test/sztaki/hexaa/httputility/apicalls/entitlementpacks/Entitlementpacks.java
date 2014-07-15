/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.entitlementpacks;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.Test;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 * @author Bana Tibor
 */
public class Entitlementpacks extends CleanTest {

    public static JSONArray entitlements = new JSONArray();

    /**
     * PUTs the existing entitlement specified by the entitlementId in the
     * entitlementpack specified by the packId
     *
     * @param entitlementId int: the id of the entitlement
     * @param packId int: id of the entitlementpack
     */
    public static void putEntitlementToPack(int entitlementId, int packId) {
        persistent.call(
                Const.Api.ENTITLEMENTPACKS_ID_ENTITLEMENTS_EID,
                BasicCall.REST.PUT,
                null,
                entitlementId, packId);
    }
    
    
}
