package busqueda;

import entidades.Documento;
import java.util.ArrayList;
import java.util.List;
import persistencia.Persistencia;
import entidades.Termino;
import entidades.Vocabulario;
import entidades.Posteo;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import main.Globals;

@SessionScoped
public class Buscador implements Serializable{  
    @Inject Persistencia p;
    
    Vocabulario v;
    int R = 3; //cantidad de docuemntos relevantes
    int N; //cantidad de documentos

    public Buscador() {
        
        //buscar("simple test of working");
    }  
    
    @PostConstruct
    private void initialize(){
        v = p.cargarVocabulario();
        N = p.obtenerCantidadDocumentos();
    }
    
    public List<Documento> buscar(String querry){
        String[] palabras = querry.split("\\+");
        
        ArrayList<Termino> terminos = new ArrayList(palabras.length);
        
        // Buscamos los términos asociados a cada palabra
        for (String p : palabras) {
            Termino t = v.getTermino(p);
            if(t != null){
                terminos.add(t);
            }
        }
        
        // Ordenamos de menor a mayor nr (cantidad de documentos en los que aparece el término)
        terminos.sort((t1, t2) -> t1.compareTo(t2));
        Ranking rank = new Ranking(N);
        
         // Buscamos los R primeros documentos de cada término y los ubicamos en el ranking
        for (Termino t : terminos) {
            List<Posteo> posteos = p.buscarPosteos(t.getPalabra(), R);
            posteos.forEach((p) -> rank.procesarDocumento(p.getDocumento(), p.getTf(), t.getNr()));
        }
        
        // Prints de testeo
        System.out.println(terminos);
        System.out.println(rank.getRanking(R));
        
        return rank.getRanking(R);
    }
    
    public File buscarArchivo(String nombre){
        File carpeta = new File(Globals.docs_path);
        File[] archivos = carpeta.listFiles();
        for(File f : archivos)
            if(f.getName().equals(nombre))
                return f;
        
        return null;
    }
    
}
