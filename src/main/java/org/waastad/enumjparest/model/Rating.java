/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.enumjparest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
public enum Rating {

    UNRATED(1), G(2), PG(3);
    private final Integer value;

    private Rating(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static Rating create(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (Rating r : values()) {
            if (value.equals(r.getValue())) {
                return r;
            }
        }
        throw new IllegalArgumentException();
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

}
