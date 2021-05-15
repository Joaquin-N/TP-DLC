package main;

import busqueda.Buscador;
import indexacion.Indexador;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class ApiBuscador {
    @Inject Buscador b;
    @Inject Indexador indexador;
    
    @GET
    @Path("/buscar")
    public Response buscar(){
        return Response.ok(b.buscar("simple test of working")).build();
    }
    
    @GET
    @Path("/indexar")
    public Response indexar(){
        return Response.ok(indexador.generarIndice()).build();
    }
    
    @GET
    @Path("/nuevoarchivo")
    public Response indexarNuevo(){
        return Response.ok(indexador.agregarArchivo()).build();
    }
}
