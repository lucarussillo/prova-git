package it.unibas.mediapesata.modello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Esame {
    
    private static final Logger logger = LoggerFactory.getLogger(Esame.class.getName());
    
    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    
    public Esame (String insegnamento, int voto, boolean lode, int crediti) {
        setInsegnamento(insegnamento);
        setVoto(voto);
        setLode(lode);
        setCrediti(crediti);
    }
    
    public String getInsegnamento() {
        return this.insegnamento;
    }
        
    public int getVoto() {
        return this.voto;
    }
        
    public int getCrediti() {
        return this.crediti;
    }
        
    public boolean isLode() {
        return this.lode;
    }
    
    private void setInsegnamento(String insegnamento) {
        this.insegnamento = insegnamento;
    }

    private void setVoto(int voto) {
        if (voto < 18 || voto > 30) {
            throw new IllegalArgumentException("Valore scorretto del voto");
        }
        this.voto = voto;
    }

    private void setCrediti(int crediti) {
        if (crediti <= 0) {
            throw new IllegalArgumentException("Valore scorretto dei crediti");
        }
        this.crediti = crediti;
    }

    private void setLode(boolean lode) {
        if (lode == true && this.voto != 30) {
            throw new IllegalArgumentException("La lode e' possibile solo con il 30");
        }
        this.lode = lode;
    }
    
    public String toString() {
        String risultato = "Esame di " + this.insegnamento + " (" + this.crediti + " CFU) - voto: " + this.voto;
        if (this.lode) {
            risultato = risultato + " e lode";
        }
        return risultato;
    }

    public String toSaveString() {
        return this.insegnamento + " | " + this.crediti + " | " 
             + this.voto + " | " + this.lode;
    }

    public void prova() {
        return "Notebook";
    }
}
