package busqueda;

import java.util.ArrayList;
import java.util.List;
import persistencia.Persistencia;
import entidades.Termino;
import entidades.Vocabulario;
import entidades.Posteo;

public class Buscador {    
    Vocabulario v;
    int R = 3; //cantidad de docuemntos relevantes
    int N; //cantidad de documentos
    Ranking rank; 

    public Buscador() {
        v = Persistencia.cargarVocabulario();
        N = Persistencia.obtenerCantidadDocumentos();
        rank = new Ranking(N);
        buscar("simple test of working");
    }  
    
    private void buscar(String querry){
        String[] palabras = querry.split(" ");
        
        ArrayList<Termino> terminos = new ArrayList(palabras.length);
        
        for (String p : palabras) {
            Termino t = v.getTermino(p);
            if(t != null){
                terminos.add(t);
            }
        }
        
        terminos.sort((t1, t2) -> t1.compareTo(t2));
        
        for (Termino t : terminos) {
            List<Posteo> posteos = Persistencia.buscarPosteos(t.getPalabra(), R);
            posteos.forEach((p) -> rank.procesarDocumento(p.getDocumento(), p.getTf(), t.getNr()));
        }
        
        System.out.println(terminos);
        System.out.println(rank.getRanking());
        
    }
    
}
