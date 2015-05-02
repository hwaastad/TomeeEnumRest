/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.enumjparest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;
import org.waastad.enumjparest.entity.Movie;
import org.waastad.enumjparest.model.Rating;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
public class RatingServiceTest {
    
    ObjectMapper mapper = new ObjectMapper();
    
    private static final String MOVIE = "KillBill";
    
    public RatingServiceTest() {
    }

    @Test
    public void testSomeMethod() throws Exception {
        Movie movie = new Movie("flaaklypa", Rating.UNRATED);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movie));
    }
    
    @Test
    public void testSomeMetho2() throws Exception {
        String json = String.format("{\"name\":\"%s\",\"rating\":%d}", MOVIE, Rating.UNRATED.getValue());
        System.out.println(json);
        Movie convertValue = mapper.readValue(json, Movie.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(convertValue));
    }
    
}
