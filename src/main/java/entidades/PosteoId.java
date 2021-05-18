package entidades;

import java.io.Serializable;

public class PosteoId implements Serializable{
    private int documento;
    private int palabra;  

    public PosteoId() {
    }
        
    public PosteoId(int id_documento, int id_palabra) {
        this.documento = id_documento;
        this.palabra = id_palabra;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public int getPalabra() {
        return palabra;
    }

    public void setPalabra(int palabra) {
        this.palabra = palabra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.documento;
        hash = 43 * hash + this.palabra;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PosteoId other = (PosteoId) obj;
        if (this.documento != other.documento) {
            return false;
        }
        if (this.palabra != other.palabra) {
            return false;
        }
        return true;
    }
   
    
    
}
