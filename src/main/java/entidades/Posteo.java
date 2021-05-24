package entidades;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Posteo")
@IdClass(PosteoId.class)
public class Posteo implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_palabra")
    private Palabra palabra;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "id_documento")
    private Documento documento;
    
    @Column(name = "tf")
    private int tf;

    public Posteo() {
    }

    public Posteo(Palabra palabra, Documento documento) {
        this.palabra = palabra;
        this.documento = documento;
        this.tf = 0;
    }
    
    public Posteo(Palabra palabra, Documento documento, int tf) {
        this.palabra = palabra;
        this.documento = documento;
        this.tf = tf;
    }

    public Palabra getPalabra() {
        return palabra;
    }

    public void setPalabra(Palabra palabra) {
        this.palabra = palabra;
    }

    public Documento getDocumento() {
        return documento;
    }

    public int getTf() {
        return tf;
    }
        
    public void incrementarContador(){
        tf++;
    }
    
    
}
