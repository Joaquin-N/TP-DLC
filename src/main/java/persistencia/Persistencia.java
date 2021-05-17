package persistencia;

import entidades.Vocabulario;
import entidades.Termino;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import entidades.Documento;
import entidades.Palabra;
import entidades.Posteo;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Persistencia {
    EntityManagerFactory emf; 
    int batchSize = 100;

    @PostConstruct
    public void initialize(){
        emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
    }    
    
    public Vocabulario cargarVocabulario(){
        Vocabulario v = new Vocabulario();
        EntityManager em = emf.createEntityManager();
        em.createNativeQuery(                  
            "SELECT pa.palabra, COUNT(*) as nr, MAX(p.tf) as max_tf "
            + "FROM Posteo p JOIN Palabras pa ON pa.id = p.id_palabra "
            + "GROUP BY pa.palabra", Termino.class).getResultStream().forEach(
            (x)-> v.insertarTermino((Termino) x));
        em.close();
        return v;
    }
    
    public HashMap<String, Palabra> buscarDiccionario(){
        HashMap<String, Palabra> dic = new HashMap();
        EntityManager em = emf.createEntityManager();
        em.createNativeQuery("SELECT * FROM palabras", Palabra.class).getResultStream().forEach((p) -> dic.put(((Palabra)p).getPalabra(), (Palabra)p));
        em.close();
        return dic;
    }
    
    /*
    public void insertarPosteos(Collection<Posteo> values){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        for(Posteo p : values){
            em.merge(p);
        }
        t.commit();
        em.close();
    }
    */
    
    public void insertarPosteos(Object[] posteos){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        for(int i = 0; i < posteos.length; i++){
            if(i > 0 && i % batchSize == 0){
                t.commit();
                t.begin();
                em.clear();
            }
           
            em.merge(posteos[i]);
        }
        t.commit();
        em.close();
    }
    
    public void insertarDocumento(Documento d){
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(d);     
        t.commit();
        em.close();
    }
    
    public int obtenerCantidadDocumentos(){
        EntityManager em = emf.createEntityManager();         
        int N = (int) em.createNativeQuery("SELECT COUNT(*) FROM Documentos").getSingleResult();
        em.close();
        
        return N;
    }
    
    /*
    public void buscarPosteos(Termino termino){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        Stream<Posteo> posteos = em.createNativeQuery("SELECT TOP " + R + " p.id_palabra, p.id_documento, p.tf "
            + "FROM Posteo p "
            + "JOIN Documentos d ON p.id_documento = d.id "
            + "JOIN Palabras pa ON p.id_palabra = pa.id "
            + "WHERE pa.palabra = '" + termino.getPalabra() + "' "
            + "ORDER BY p.tf DESC", Posteo.class).getResultStream();
        
        posteos.forEach((p) -> rank.procesarDocumento(p.getDocumento(), p.getTf(), termino.getNr()));
        
        t.commit();
        em.close();
        emf.close();  
    }    
*/
    
    public List<Posteo> buscarPosteos(String palabra, int R){
        EntityManager em = emf.createEntityManager();
        List<Posteo> posteos = em.createNativeQuery("SELECT TOP " + R + " p.id_palabra, p.id_documento, p.tf "
            + "FROM Posteo p "
            + "JOIN Documentos d ON p.id_documento = d.id "
            + "JOIN Palabras pa ON p.id_palabra = pa.id "
            + "WHERE pa.palabra = '" + palabra + "' "
            + "ORDER BY p.tf DESC", Posteo.class).getResultList();       
        em.close(); 
        
        return posteos;
    }    
}
