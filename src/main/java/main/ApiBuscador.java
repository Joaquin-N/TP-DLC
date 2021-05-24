package main;

import busqueda.Buscador;
import indexacion.Indexador;
import java.io.File;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/se")
public class ApiBuscador {
    @Inject Buscador b;
    
    @GET
    @Path("/buscar/{consulta}")
    public Response buscar(@PathParam("consulta") String consulta){
        System.out.println("Procesando consulta: " + consulta);
        return Response.ok(b.buscar(consulta)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/documento/{nombre}")
    public Response verDocumento(@PathParam("nombre") String nombre){
        return Response.ok(b.buscarArchivo(nombre), MediaType.APPLICATION_OCTET_STREAM).build();
    }
    
    /*
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile() {
    File file = ... // Initialize this to the File path you want to serve.
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
            .build();
      }
    */
}
