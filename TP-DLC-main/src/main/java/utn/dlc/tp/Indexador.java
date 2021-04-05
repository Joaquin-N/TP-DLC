package utn.dlc.tp;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Indexador {
    private final String path = FileSystemView.getFileSystemView().getHomeDirectory() + "\\DLC";

    private Vocabulario v = new Vocabulario();
    
    public Vocabulario generarIndice(){
        try{
            for(File a : obtenerArchivos())
                indexarArchivo(a);

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
                            while(((i+1) < longitud) && (linea.charAt(i+1) == '-' || Character.isLetter(linea.charAt(i+1)) || Character.isDigit(linea.charAt(i+1))))
                            {
                                i++;
                                if (Character.isLetter(linea.charAt(i)) || Character.isDigit(linea.charAt(i)))
                                {
                                    palabra = palabra + linea.charAt(i);
                                }
                                else
                                {
                                    palabraCortada = true;
                                }

                            }
                            if (!palabraCortada)
                            {
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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String[] dividirPalabras(String linea){
        String[] palabras = linea.split(" ");
        return palabras;
    }
    
    
    
}
