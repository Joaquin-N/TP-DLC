package indexacion;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import entidades.Documento;
import entidades.Posteo;
import entidades.Palabra;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import persistencia.Persistencia;
import entidades.Vocabulario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import main.Globals;

@SessionScoped
public class Indexador implements Serializable{
    private int count = 0;

    @Inject Persistencia p;
    
    private Vocabulario v = new Vocabulario();
    private HashMap<String, Palabra> diccionario = new HashMap<>();
    
    
    public Vocabulario generarIndice(){
        try{
            count = 0;
            long startTime = System.currentTimeMillis();
            for(File a : obtenerArchivos())
                indexarArchivo(a);
            long endTime = System.currentTimeMillis();
            
            long minutos = (endTime - startTime) / 60000;
            long segundos = ((endTime - startTime) % 60000) / 1000;
            
            System.out.println("--- Indexación finalizada. Tiempo transcurrido: " + minutos + "' " + segundos + "''");
            
            startTime = System.currentTimeMillis();
            v = p.cargarVocabulario();            
            endTime = System.currentTimeMillis();      
            
            minutos = (endTime - startTime) / 60000;
            segundos = ((endTime - startTime) % 60000) / 1000;
            
            System.out.println("--- Carga de vocabulario finalizada. Tiempo transcurrido: " + minutos + "' " + segundos + "''");
            
            return v;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String agregarArchivo(File archivo){
        // Recupera las palabras existentes en la BD
        diccionario = p.buscarDiccionario();
        indexarArchivo(archivo);      
        return "OK"; 
    }
    
    public String agregarArchivo(String path){
        File archivo = new File(path);
        try{ 
            // Copia el archivo al directorio con los demás archivos
            File nuevo = new File(Globals.docs_path, archivo.getName());
            if(nuevo.exists()){
                return "Archivo ya existe";
            }
            Files.copy(Paths.get(archivo.getAbsolutePath()), Paths.get(nuevo.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

            // Recupera las palabras existentes en la BD
            diccionario = p.buscarDiccionario();
            indexarArchivo(archivo);      
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lista los archivos del directorio
    private File[] obtenerArchivos() throws Exception{
        String path = Globals.docs_path;
        File carpeta = new File(path);
        File[] archivos = carpeta.listFiles();
        if (archivos.length == 0) throw new Exception("No se encontraron archivos en la ruta " + path);
        return archivos;
    }
        
    private void indexarArchivo(File archivo){
        HashMap<Palabra, Posteo> posteos = new HashMap<>();
        Documento doc = new Documento(archivo.getName());
        
        count++;
        System.out.println("--- Indexando archivo [" + count + "] " + doc.getDocumento());
        
        //p.insertarDocumento(doc);
        
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
                               
            // Inserta todos los terminos del documento a la bd
            p.insertarPosteos(posteos.values().toArray());
                  
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    /*
    private void indexarArchivo2(File archivo){
        HashMap<Palabra, Posteo> posteos = new HashMap<>();
        Documento doc = new Documento(archivo.getName());
        
        Persistencia.insertarDocumento(doc);
        
        try (Scanner sc = new Scanner(archivo))
        {
            String palabraAux = "";
            String palabra = "";
            boolean palabraCortada = false;
            while(sc.hasNextLine())
            {
                String linea = sc.nextLine();
                if (!palabraCortada)
                {
                    palabra = "";
                }
                else
                {
                    palabra = palabraAux;
                    palabraAux = "";
                    palabraCortada = false;
                }
                int longitud = linea.length();
                for (int i = 0; i < longitud; i++)
                {
                    if(Character.isLetter(linea.charAt(i)) || Character.isDigit(linea.charAt(i)))
                        {
                            palabra = palabra + linea.charAt(i);
                            while(((i+1) < longitud) && (linea.charAt(i+1) == '-' || linea.charAt(i+1) == '\'' || Character.isLetter(linea.charAt(i+1)) || Character.isDigit(linea.charAt(i+1))))
                            {
                                i++;
                                if (Character.isLetter(linea.charAt(i)) || Character.isDigit(linea.charAt(i)) || linea.charAt(i) == '\'')
                                {
                                    if (linea.charAt(i) != '\'')
                                    {
                                        palabra = palabra + linea.charAt(i);
                                    }                                                        
                                }
                                else
                                {
                                    palabraCortada = true;
                                }

                            }
                            palabra = palabra.toLowerCase();
                            if (!palabraCortada)
                            {
                                agregarPosteo(posteos, palabra, doc);                                
                            }
                            else
                            {
                                palabraAux = palabra;
                            }
                        }
                    palabra = "";
                }
            }
                               
            Persistencia.insertarPosteos(posteos.values());
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    */
    
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
