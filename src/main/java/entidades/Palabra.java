
package entidades;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Palabras")
public class Palabra implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "palabra")
    private String palabra;

    public Palabra(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Palabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }
    
    
}
