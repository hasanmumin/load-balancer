package com.balancer.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class RequestEntityBuilder {

    public static HttpEntity<Object> buildRequestEntity(String authenticationToken, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", authenticationToken);
        headers.add("Content-Type", "application/json");
        return new HttpEntity<Object>(
                body,
                headers
        );
    }

    public static HttpEntity<Object> buildRequestEntityWithoutBody(String authenticationToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", authenticationToken);
        headers.add("Content-Type", "application/json");
        return new HttpEntity<Object>(headers);
    }

    public static HttpEntity<Object> buildRequestEntityWithoutAuthenticationToken(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<Object>(
                body,
                headers
        );
    }

    public static HttpEntity<Object> buildRequestEntityWithoutBodyOrAuthenticationToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<Object>(headers);
    }

}
