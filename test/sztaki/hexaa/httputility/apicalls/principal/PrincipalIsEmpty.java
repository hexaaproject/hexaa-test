/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sztaki.hexaa.httputility.apicalls.principal;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sztaki.hexaa.httputility.BasicCall;
import sztaki.hexaa.httputility.Const;
import sztaki.hexaa.httputility.apicalls.CleanTest;

/**
 *
 * @author Bana Tibor
 */
public class PrincipalIsEmpty extends CleanTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void isEmpty() {
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.MANAGER_ORGANIZATIONS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.MANAGER_SERVICES,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.MEMBER_ORGANIZATIONS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}",
                    new BasicCall().call(
                            Const.Api.PRINCIPAL,
                            BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("{\"code\":404,\"message\":\"Not Found\"}", new BasicCall().call(
                    Const.Api.PRINCIPAL_ATTRIBUTESPECS_ATTRIBUTEVALUEPRINCIPAL,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.PRINCIPAL_ATTRIBUTEVALUEPRINCIPAL,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.PRINCIPAL_EMAILINVITATIONS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[]", new BasicCall().call(
                    Const.Api.PRINCIPAL_URLINVITATIONS,
                    BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("[{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}]",
                    new BasicCall().call(
                            Const.Api.PRINCIPALS,
                            BasicCall.REST.GET));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }
        try {
            assertEquals("{\"fedid\":\"ede91bt@gmail.com@partners.sztaki.hu\",\"id\":1}",
                    new BasicCall().call(
                            Const.Api.PRINCIPALS_ID,
                            BasicCall.REST.GET,
                            null,
                            1, 0));
        } catch (AssertionError e) {
            collector.addError(e);
            System.out.println(e.getLocalizedMessage());
        }

    }
}
