/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.enumjparest.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jayway.restassured.RestAssured;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.junit.jee.EJBContainerRule;
import org.apache.openejb.junit.jee.InjectRule;
import org.apache.openejb.junit.jee.config.PropertyFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.waastad.enumjparest.entity.Movie;
import org.waastad.enumjparest.model.Rating;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@PropertyFile("container.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RatingServiceIT {

    private static final String MOVIE = "KillBill";

    @ClassRule
    public static final EJBContainerRule CONTAINER_RULE = new EJBContainerRule();

    @Rule
    public final InjectRule injectRule = new InjectRule(this, CONTAINER_RULE);

    @PersistenceContext(unitName = "demoPU")
    EntityManager em;

    public RatingServiceIT() {
    }

    @Test
    public void test_00_SomeMethod() {
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        WebClient client = WebClient.create("http://127.0.0.1:4204/EnumJpaRest/rest", providers);
        ClientConfiguration config = WebClient.getConfig(client);
        config.getOutInterceptors().add(new LoggingOutInterceptor());
        config.getInInterceptors().add(new LoggingInInterceptor());
        Movie movie = new Movie();
        client.accept(MediaType.APPLICATION_JSON);
        client.type(MediaType.APPLICATION_JSON);
        Response post = client.post(movie);
    }

    @Test
    public void test_01_null_name() throws Exception {
        String json = " {\"name\":null,\"rating\":2}";
        RestAssured.baseURI = "http://127.0.0.1:4204/EnumJpaRest/rest";
        com.jayway.restassured.response.Response r = RestAssured.given()
                .contentType("application/json")
                .body(json).when().post();
    }

    @Test
    public void test_02_bad_rating() throws Exception {
        String json = " {\"name\":null,\"rating\":8}";
        RestAssured.baseURI = "http://127.0.0.1:4204/EnumJpaRest/rest";
        com.jayway.restassured.response.Response r = RestAssured.given()
                .contentType("application/json")
                .body(json).when().post();
    }

    @Test
    public void test_03_correct_insert() throws Exception {
        String json = String.format("{\"name\":\"%s\",\"rating\":\"%s\"}", MOVIE, Rating.UNRATED.getValue());
        RestAssured.baseURI = "http://127.0.0.1:4204/EnumJpaRest/rest";
        com.jayway.restassured.response.Response r = RestAssured.given()
                .contentType("application/json")
                .body(json).when().post();

        Query q = em.createQuery("SELECT t FROM Movie t");
        List resultList = q.getResultList();
        Assert.assertEquals(1, resultList.size());
        q = em.createQuery("SELECT t FROM Movie t WHERE t.rating=:rating").setParameter("rating", Rating.UNRATED);
        List resultList1 = q.getResultList();
        Assert.assertEquals(1, resultList1.size());
    }

    @Test
    public void test_04_correct_insert_webclient() throws Exception {
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        WebClient client = WebClient.create("http://127.0.0.1:4204/EnumJpaRest/rest", providers);
        ClientConfiguration config = WebClient.getConfig(client);
        config.getOutInterceptors().add(new LoggingOutInterceptor());
        config.getInInterceptors().add(new LoggingInInterceptor());
        Movie movie = new Movie("flaaklypa", Rating.G);
        client.accept(MediaType.APPLICATION_JSON);
        client.type(MediaType.APPLICATION_JSON);
        Response post = client.post(movie);

        Query q = em.createQuery("SELECT t FROM Movie t");
        List resultList = q.getResultList();
        Assert.assertEquals(2, resultList.size());
        q = em.createQuery("SELECT t FROM Movie t WHERE t.rating=:rating").setParameter("rating", Rating.G);
        List resultList1 = q.getResultList();
        Assert.assertEquals(1, resultList1.size());
    }

}
