
package entidades;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Palabras")
public class Palabra implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "palabra")
    private String palabra;

    public Palabra(){
    }
    
    public Palabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }
    
    
}
