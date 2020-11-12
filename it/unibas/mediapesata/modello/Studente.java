package it.unibas.mediapesata.modello;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Studente {
    
    private static final Logger logger = LoggerFactory.getLogger(Studente.class.getName());

    private String nome;
    private String cognome;
    private int matricola;
    private List<Esame> listaEsami = new ArrayList<Esame>();
    //private java.util.LinkedList listaEsami = new java.util.LinkedList();
    
    public Studente() {}
    
    public Studente (String nome, String cognome, int matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
    }
    
    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public int getMatricola() {
        return this.matricola;
    }

    public void addEsame(String insegnamento, int voto, boolean lode, int crediti) {
        Esame esame = new Esame(insegnamento, voto, lode, crediti);
        this.listaEsami.add (esame);
    }

    public void addEsame(Esame esame) {
        this.listaEsami.add(esame);
    }

    public Esame getEsame(int i) {
        if (i < 0 || i >= this.listaEsami.size()) {
            throw new IndexOutOfBoundsException("Esame inesistente");
        }
        return this.listaEsami.get(i);
    }

    public int getNumeroEsami() {
        return this.listaEsami.size();
    }

    public double getMediaPesata() {
        if (this.listaEsami.isEmpty()) {
            throw new IllegalArgumentException("Non e' possibile calcolare la media di 0 esami");
        }
        int sommaVotiPesati = 0;
        int sommaCrediti = 0;
        for (Esame esame : this.listaEsami) {
            sommaVotiPesati += esame.getVoto() * esame.getCrediti();
            sommaCrediti += esame.getCrediti();
        }
        return ((double)sommaVotiPesati)/sommaCrediti;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Cognome: ").append(getCognome()).append(" - Nome: ").append(getNome()).append(" - Matricola: ").append(getMatricola());
        return result.toString();
    }
    
    public String toSaveString() {
        return getCognome() + " | " + getNome() + " | " + getMatricola();
    }


}


