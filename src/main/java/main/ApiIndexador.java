
package main;

import indexacion.Indexador;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/index")
public class ApiIndexador {
    @Inject Indexador indexador;
    
    @GET
    @Path("/indexar")
    public Response indexar(){
        return Response.ok(indexador.generarIndice()).build();
    }
    
    @GET
    @Path("/test")
    public Response test(){
        System.out.println("OK");
        return Response.ok("OK").build();
    }
    
    @GET
    @Path("/agregararchivo/{path}")
    public Response agregarArchivo(@PathParam("path") String path){
        indexador.agregarArchivo(path);
        return Response.ok("OK").build();
    }
        
    @POST
    @Path("/nuevoarchivo")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response reciveFile(@Context HttpHeaders headers, InputStream fileInputStream) {
        MultivaluedMap<String, String> map = headers.getRequestHeaders();
          System.out.println("Nuevo archivo");
        //getFileName
        String fileName = getFileName(map);
        
        OutputStream out = null;

        String filePath = Globals.docs_path + "/" + fileName;
        try {
            out = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        indexador.agregarArchivo(new File(filePath));
             
        return Response.status(Response.Status.OK).build();
    }

    private String getFileName(MultivaluedMap<String, String> headers) {
        try {
           String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }  
        } catch (Exception e) {
            System.out.println("getFileName() "+e.getLocalizedMessage());
        }
       
        return "";
    }
}
