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
import entidades.Posteo;

public class Persistencia {
    
    public static Vocabulario cargarVocabulario(){
        Vocabulario v = new Vocabulario();
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
        return v;
    }
    
    public static void insertarPosteos(Collection<Posteo> values){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        for(Posteo p : values){
            em.merge(p);
        }
        t.commit();
        em.close();
        emf.close();
    }
    
    public static void insertarDocumento(Documento d){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        
        em.persist(d);
        
        t.commit();
        em.close();
        emf.close();
    }
    
    public static int obtenerCantidadDocumentos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        
        int N = (int) em.createNativeQuery("SELECT COUNT(*) FROM Documentos").getSingleResult();
        
        t.commit();
        em.close();
        emf.close();
        
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
    
    public static List<Posteo> buscarPosteos(String palabra, int R){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-SearchEngine");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        List<Posteo> posteos = em.createNativeQuery("SELECT TOP " + R + " p.id_palabra, p.id_documento, p.tf "
            + "FROM Posteo p "
            + "JOIN Documentos d ON p.id_documento = d.id "
            + "JOIN Palabras pa ON p.id_palabra = pa.id "
            + "WHERE pa.palabra = '" + palabra + "' "
            + "ORDER BY p.tf DESC", Posteo.class).getResultList();       
        t.commit();
        em.close();
        emf.close();
        
        return posteos;
    }    
}
