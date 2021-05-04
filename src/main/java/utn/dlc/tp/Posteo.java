package utn.dlc.tp;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Posteo")
@IdClass(PosteoId.class)
public class Posteo implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_palabra")
    private Palabra palabra;
    
    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_documento")
    private Documento documento;
    
    @Column(name = "tf")
    private int contador;

    public Posteo() {
    }

    public Posteo(Palabra palabra, Documento documento) {
        this.palabra = palabra;
        this.documento = documento;
        this.contador = 0;
    }
    
    public void incrementarContador(){
        contador++;
    }
    
    
}
