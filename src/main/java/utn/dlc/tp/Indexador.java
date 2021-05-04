package utn.dlc.tp;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Indexador {
    private final String path = FileSystemView.getFileSystemView().getHomeDirectory() + "\\DLC";

    private Vocabulario v = new Vocabulario();
    private HashMap<String, Palabra> diccionario = new HashMap<>();
    
    
    public Vocabulario generarIndice(){
        try{
            
            for(File a : obtenerArchivos())
                indexarArchivo(a);
            
            cargarVocabulario();            
       
            return v;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File[] obtenerArchivos() throws Exception{
        File carpeta = new File(path);
        File[] archivos = carpeta.listFiles();
        if (archivos.length == 0) throw new Exception("No se encontraron archivos en la ruta " + path);
        return archivos;
    }
    
    private void cargarVocabulario(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
            EntityManager em = emf.createEntityManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.createNativeQuery(                  
                "SELECT pa.palabra, COUNT(*) as nr, MAX(p.tf) as max_tf "
                + "FROM Posteo p JOIN Palabras pa ON pa.id = p.id_palabra "
                + "GROUP BY pa.palabra", Termino.class).getResultStream().forEach(
                (x)-> v.insertarTermino((Termino) x));
            t.commit();
            em.close();
            emf.close();
    }
    
    private void indexarArchivo(File archivo){
        HashMap<Palabra, Posteo> posteos = new HashMap<>();
        Documento doc = new Documento(archivo.getName());
        
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
                               
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
            EntityManager em = emf.createEntityManager();
            EntityTransaction t = em.getTransaction();
            t.begin();
            for(Posteo p : posteos.values()){
                em.merge(p);
            }
            t.commit();
            em.close();
            emf.close();
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private void agregarPosteo(HashMap<Palabra, Posteo> posteos, String palabra, Documento doc){
        Palabra pal = obtenerPalabra(palabra);
        Posteo post = posteos.get(pal);
        if(post == null){
            post = new Posteo(pal, doc);
            posteos.put(pal, post);
        }
        post.incrementarContador();
    }

    private Palabra obtenerPalabra(String pstr){
        Palabra p = diccionario.get(pstr);
        if(p == null){
            p = new Palabra(pstr);
            diccionario.put(pstr, p);
        }
        return p;
    }
    
    private String[] dividirPalabras(String linea){
        String[] palabras = linea.split(" ");
        return palabras;
    }
    
    
    
}
