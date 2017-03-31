package com.balancer.service.model.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
