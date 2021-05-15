package main;

import busqueda.Buscador;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/buscador")
public class ApiBuscador {
    @Inject Buscador b;
    
    @GET
    @Path("/")
    public Response buscar(){
        return Response.ok(b.buscar("simple test of working")).build();
    }
    
}
