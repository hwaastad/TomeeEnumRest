/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.enumjparest.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
public enum Rating {

    UNRATED(0), G(1), PG(2);
    private final int value;

    private Rating(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

}
