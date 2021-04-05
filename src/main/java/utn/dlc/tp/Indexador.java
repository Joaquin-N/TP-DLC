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
        try {
            Scanner sc = new Scanner(archivo);

            while(sc.hasNext()){
                String linea = sc.nextLine();

                for(String p : dividirPalabras(linea)){
                    v.insertarPalabra(p);
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
