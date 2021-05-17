package busqueda;

import entidades.Documento;
import java.util.ArrayList;
import java.util.List;
import persistencia.Persistencia;
import entidades.Termino;
import entidades.Vocabulario;
import entidades.Posteo;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class Buscador implements Serializable{  
    @Inject Persistencia p;
    
    Vocabulario v;
    int R = 3; //cantidad de docuemntos relevantes
    int N; //cantidad de documentos
    Ranking rank; 

    public Buscador() {
        
        //buscar("simple test of working");
    }  
    
    @PostConstruct
    private void initialize(){
        v = p.cargarVocabulario();
        N = p.obtenerCantidadDocumentos();
        rank = new Ranking(N);
    }
    
    public List<Documento> buscar(String querry){
        String[] palabras = querry.split(" ");
        
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
        
         // Buscamos los R primeros documentos de cada término y los ubicamos en el ranking
        for (Termino t : terminos) {
            List<Posteo> posteos = p.buscarPosteos(t.getPalabra(), R);
            posteos.forEach((p) -> rank.procesarDocumento(p.getDocumento(), p.getTf(), t.getNr()));
        }
        
        // Prints de testeo
        System.out.println(terminos);
        System.out.println(rank.getRanking());
        
        return rank.getRanking();
    }
    
}
