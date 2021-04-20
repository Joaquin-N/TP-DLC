
package utn.dlc.tp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*@Entity
@Table(name="Posteo")
@OneToMany
@JoinTable(table=@Table(name="Documentos"),
joinColumns = @JoinColumn(name="id_documento"),
inverseJoinColumns = @JoinColumn(name="id"))*/
public class Palabra {
    private String palabra;
    private String documento;
    private int tf;

    public Palabra(String palabra, String documento) {
        this.palabra = palabra;
        this.documento = documento;
        tf = 1;
    }
    
    public void incrementarTf(){
        tf++;
    }
}
