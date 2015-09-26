package com.simipro.serialization;

import java.io.Serializable;

/**
 * Created by Simi on 25.09.2015.
 */
public class Message implements Serializable {
    private String hello;

    public Message(String hellow) {
        this.hello = hellow;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
