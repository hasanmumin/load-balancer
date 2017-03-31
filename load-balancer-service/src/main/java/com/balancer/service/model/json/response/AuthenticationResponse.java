package com.balancer.service.model.json.response;

import com.balancer.service.model.base.BaseModel;

public class AuthenticationResponse extends BaseModel {

    private static final long serialVersionUID = -6624726180748515507L;
    private String token;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String token) {
        this.setToken(token);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
