/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.enumjparest.service;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.waastad.enumjparest.entity.Movie;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Singleton
@Path("rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingService {

    @PersistenceContext(unitName = "demoPU")
    private EntityManager em;

    @POST
    public Response addMovie(@Valid @NotNull Movie movie) {
        em.persist(movie);
        return Response.ok().build();
    }
}
