package apis;

import config.Globals;
import busqueda.Buscador;
import java.io.File;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/se")
public class ApiBuscador {
    @Inject Buscador b;
    
    @GET
    @Path("/buscar/{consulta}/{R}")
    public Response buscar(@PathParam("consulta") String consulta, @PathParam("R") int R){
        System.out.println("--- Procesando consulta: " + consulta);
        return Response.ok(b.buscar(consulta, R)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/documento/{nombre}")
    public Response verDocumento(@PathParam("nombre") String nombre){
        System.out.println("--- Buscando documento: " + nombre);
        File archivo = new File(Globals.docs_path + "/" + nombre);
        return Response.ok(archivo, MediaType.APPLICATION_OCTET_STREAM).build();
    }
}
