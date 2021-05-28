package vocabulario;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Termino implements Serializable, Comparable<Termino>{
    @Id
    private String palabra;
    private int nr;
    private int max_tf;

    public String getPalabra() {
        return palabra;
    }

    public int getNr() {
        return nr;
    }

    public int getMax_tf() {
        return max_tf;
    }

    public Termino(){
    }
    
    public Termino(String palabra) {
        this.palabra = palabra;
        this.nr = 1;
        this.max_tf = 0;
    }
    
    public Termino(String palabra, int nr, int max_tf) {
        this.palabra = palabra;
        this.nr = nr;
        this.max_tf = max_tf;
    }

    public void incrementarNr(){
        nr++;
    }
    
    @Override
    // Odenamiento menor a mayor
    public int compareTo(Termino that) {
        return this.nr - that.nr;
    }

    @Override
    public String toString() {
        return " nr=" + nr + " - palabra=" + palabra; // + "<br>";
    }
}
