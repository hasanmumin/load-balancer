package com.balancer.service.controller;

import com.balancer.service.LoadBalancerApplication;
import com.balancer.service.RequestEntityBuilder;
import com.balancer.service.TestApiConfig;
import com.balancer.service.domain.Machine;
import com.balancer.service.model.json.request.AuthenticationRequest;
import com.balancer.service.model.json.response.AuthenticationResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoadBalancerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MachineControllerTest {

    private static RestTemplate client;
    private static AuthenticationRequest authenticationRequest;
    private static String authenticationToken;
    private static String authenticationRoute = "auth";
    private static boolean setUpIsDone = false;
    private static Machine RESPONSE = null;

    private static void login() {
        initializeStateForMakingValidAuthenticationRequest();
        ResponseEntity<AuthenticationResponse> authenticationResponse = client.postForEntity(
                TestApiConfig.getAbsolutePath(authenticationRoute),
                authenticationRequest,
                AuthenticationResponse.class
        );

        authenticationToken = authenticationResponse.getBody().getToken();
    }

    private static void initializeStateForMakingValidAuthenticationRequest() {
        authenticationRequest = TestApiConfig.ADMIN_AUTHENTICATION_REQUEST;
    }

    @Before
    public void setUp() throws Exception {
        if (setUpIsDone) {
            return;
        }
        client = new RestTemplate();
        login();
        setUpIsDone = true;
    }

    @Test
    public void test1_create() {
        RESPONSE = this.createMachineRequest();
    }

    @Test
    public void test2_update() {
        RESPONSE.setName("Machine-2");
        Machine response = this.updateMachineRequest(RESPONSE);
        assertTrue(RESPONSE.equals(response));
    }

    @Test
    public void test3_delete() {
        Machine response = this.deleteMachineRequest(RESPONSE.getOid());
        assertTrue(RESPONSE.equals(response));
    }

    private Machine updateMachineRequest(Machine machine) {
        try {
            ResponseEntity<Machine> machineResponseEntity = client.exchange(
                    TestApiConfig.getAbsolutePath(String.format("%s/%s", "machines", machine.getOid())),
                    HttpMethod.PUT,
                    buildRequestEntity(machine),
                    Machine.class
            );

            return machineResponseEntity.getBody();

        } catch (Exception e) {
            fail("Should have returned an HTTP 200 without any Exception");
        }
        return null;
    }

    private Machine deleteMachineRequest(String oid) {
        try {
            ResponseEntity<Machine> machineResponseEntity = client.exchange(
                    TestApiConfig.getAbsolutePath(String.format("%s/%s", "machines", oid)),
                    HttpMethod.DELETE,
                    buildRequestEntityWithoutBody(),
                    Machine.class
            );

            return machineResponseEntity.getBody();

        } catch (Exception e) {
            fail("Should have returned an HTTP 200 without any Exception");
        }
        return null;
    }

    private Machine createMachineRequest() {
        Machine machine = new Machine();
        machine.setName("Machine-1");
        machine.setUrl("http://localhost:8091/api/dummy");
        try {
            ResponseEntity<Machine> machineResponseEntity = client.exchange(
                    TestApiConfig.getAbsolutePath("machines"),
                    HttpMethod.POST,
                    buildRequestEntity(machine),
                    Machine.class
            );

            Machine response = machineResponseEntity.getBody();

            assertTrue(response.getName().equals(machine.getName()));
            assertTrue(response.getUrl().equals(machine.getUrl()));

            return response;

        } catch (Exception e) {
            fail("Should have returned an HTTP 200 without any Exception");
        }
        return null;
    }

    private HttpEntity<Object> buildRequestEntity(Object body) {
        return RequestEntityBuilder.buildRequestEntity(authenticationToken, body);
    }

    private HttpEntity<Object> buildRequestEntityWithoutBody() {
        return RequestEntityBuilder.buildRequestEntityWithoutBody(authenticationToken);
    }

}
