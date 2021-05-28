package indexacion;

import entidades.Documento;
import entidades.Posteo;
import entidades.Palabra;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import persistencia.Persistencia;
import vocabulario.Vocabulario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import config.Globals;

@SessionScoped
public class Indexador implements Serializable{
    private int count = 0;

    @Inject Persistencia p;
    @Inject Vocabulario v;
    
    private HashMap<String, Palabra> diccionario = new HashMap<>();
    
    
    public String generarIndice(){
        try{
            count = 0;
            long startTime = System.currentTimeMillis();
            for(File a : obtenerArchivos())
                indexarArchivo(a);
            long endTime = System.currentTimeMillis();
            
            long minutos = (endTime - startTime) / 60000;
            long segundos = ((endTime - startTime) % 60000) / 1000;
            
            System.out.println("--- Indexación finalizada. Tiempo transcurrido: " + minutos + "' " + segundos + "''");
            
            v.recargar();
            
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
    
    public boolean agregarArchivo(File archivo){
        // Recupera las palabras existentes en la BD
        diccionario = p.buscarDiccionario();
        boolean result = indexarArchivo(archivo);      
        if(result) v.recargar();
        return result; 
    }
    
    // Lista los archivos del directorio
    private File[] obtenerArchivos() throws Exception{
        String path = Globals.docs_path;
        File carpeta = new File(path);
        File[] archivos = carpeta.listFiles();
        if (archivos.length == 0) throw new Exception("No se encontraron archivos en la ruta " + path);
        return archivos;
    }
        
    private boolean indexarArchivo(File archivo){
        HashMap<Palabra, Posteo> posteos = new HashMap<>();
        Documento doc = new Documento(archivo.getName());
        
        count++;
        System.out.println("--- Indexando archivo [" + count + "] " + doc.getDocumento());
        
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(archivo))))
        {
            // Delimitadores de palabras
            sc.useDelimiter("[^a-zA-Z']");        
            while(sc.hasNext()) {
                String palabra = sc.next();
                if (!palabra.isEmpty()){
                    palabra = palabra.toLowerCase();
                    agregarPosteo(posteos, palabra, doc);
                }
            }                
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        // Inserta todos los terminos del documento a la bd
        return p.insertarPosteos(posteos.values().toArray());
    }
    
    // Creo un nuevo posteo si la palabra no apareció todavia en el docuemento
    // En caso contrario aumento el contador de la palabra
    private void agregarPosteo(HashMap<Palabra, Posteo> posteos, String palabra, Documento doc){
        Palabra pal = obtenerPalabra(palabra);
        Posteo post = posteos.get(pal);
        if(post == null){
            post = new Posteo(pal, doc);
            posteos.put(pal, post);
        }
        post.incrementarContador();
    }

    // Gestiona el diccionario de palabras, para no tener entradas repetidas en la BD
    private Palabra obtenerPalabra(String pstr){
        Palabra pal = diccionario.get(pstr);
        if(pal == null){
            pal = new Palabra(pstr);
            diccionario.put(pstr, pal);
        }
        return pal;
    }   
}
