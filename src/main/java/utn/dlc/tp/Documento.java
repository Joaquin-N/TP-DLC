package utn.dlc.tp;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Documentos")
public class Documento implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "documento")
    private String documento;

    public Documento() {
    }

    public Documento(String documento) {
        this.documento = documento;
    }
    
    
}
