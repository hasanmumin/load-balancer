package com.balancer.service.controller;

import com.balancer.service.LoadBalancerApplication;
import com.balancer.service.RequestEntityBuilder;
import com.balancer.service.TestApiConfig;
import com.balancer.service.domain.Machine;
import com.balancer.service.model.json.request.AuthenticationRequest;
import com.balancer.service.model.json.response.AuthenticationResponse;
import com.balancer.service.model.json.response.ResponseMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoadBalancerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoadBalancerControllerTest {

    private static RestTemplate client;
    private static AuthenticationRequest authenticationRequest;
    private static String authenticationToken;
    private static String authenticationRoute = "auth";
    private static boolean setUpIsDone = false;


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
    public void triggerServer() {
        this.createMachineRequest();

        try {
            List<String> machines = Arrays.asList("Machine-1", "Machine-2");

            ResponseEntity<ResponseMessage[]> machineResponseEntity = client.exchange(
                    TestApiConfig.getAbsolutePath("balances"),
                    HttpMethod.POST,
                    buildRequestEntity(machines),
                    ResponseMessage[].class
            );

            ResponseMessage[] response = machineResponseEntity.getBody();

            assertTrue(response[0].getStatus().equals(ResponseMessage.Status.FAILURE));
            assertTrue(response[0].getMachine().equals("Machine-1"));

            assertTrue(response[1].getMachine().equals("Machine-2"));
            assertTrue(response[1].getStatus().equals(ResponseMessage.Status.FAILURE));
            assertTrue(response[1].getMessage().equals("No match with any Machine"));

        } catch (Exception e) {
            fail("Should have returned an HTTP 200 without any Exception");
        }
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

            return machineResponseEntity.getBody();

        } catch (Exception e) {
            fail("Should have returned an HTTP 200 without any Exception");
        }
        return null;
    }

    private HttpEntity<Object> buildRequestEntity(Object body) {
        return RequestEntityBuilder.buildRequestEntity(authenticationToken, body);
    }
}
