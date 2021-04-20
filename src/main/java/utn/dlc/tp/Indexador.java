package utn.dlc.tp;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
/*import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;*/

public class Indexador {
    private final String path = FileSystemView.getFileSystemView().getHomeDirectory() + "\\DLC";

    private Vocabulario v = new Vocabulario();
    /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
    EntityManager em = emf.createEntityManager();*/
    
    public Vocabulario generarIndice(){
        try{
            for(File a : obtenerArchivos())
                indexarArchivo(a);

            /*EntityTransaction t = em.getTransaction();
            t.begin();
            
            em.createNativeQuery(
            "SELECT t.palabra, COUNT(*) as nr, MAX(p.tf) as max_tf "
            + "FROM Posteo p JOIN Terminos t ON t.id = p.id_termino "
            + "GROUP BY t.palabra", Termino.class).getResultStream().forEach(
            (x)-> v.insertarTermino((Termino) x));
            t.commit();*/
            
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
    
    private void indexarArchivo(File archivo){
        HashMap<String, Palabra> hm = new HashMap<>();
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
                                /*Palabra p = hm.get(palabra);
                                if(p == null){
                                String file = archivo.getName();
                                hm.put(palabra, new Palabra(palabra, file));
                                }
                                else{
                                p.incrementarTf();
                                }*/
                                v.insertarPalabra(palabra);
                            }
                            else
                            {
                                palabraAux = palabra;
                            }
                        }
                    palabra = "";
                }
            }
                               
            /*for(Palabra p : hm.values()){
            em.persist(p);
            }*/
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String[] dividirPalabras(String linea){
        String[] palabras = linea.split(" ");
        return palabras;
    }
    
    
    
}
