package it.unibas.mediapesata.test.modello;

import junit.framework.*;

import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.persistenza.DAOStudente;

public class TestDAOStudente extends TestCase {

    private static final String PREFISSO = "/Users/donatello/Dropbox (Informatica)/Didattica/POO I/codice progetti/java/mediaPesata/test/dati/";
//    private static String PREFISSO = "/Users/mecca/Dropbox/codice/java/aaa - codice progetti POOI/mediaPesata/test/dati/";
    private static String NOMEFILE1 = PREFISSO + "studente1-test.txt";
    private static String NOMEFILE2 = PREFISSO + "studente2-test.txt";
    private static String NOMEFILE3 = PREFISSO + "studente3-test.txt";
    private static String NOMEFILE4 = PREFISSO + "studente4-test.txt";

    private Studente studente;
    private Esame esame1;
    private Esame esame2;

    public void setUp() {
        studente = new Studente("Mario", "Rossi", 1234);
        esame1 = new Esame("Analisi", 24, false, 3);
        esame2 = new Esame("Fisica", 30, true, 9);
    }

    public void testCarica1() throws Exception {
        studente = DAOStudente.carica(NOMEFILE1);
        verificaStudente1(studente);
    }

    public void testCarica2() throws Exception {
        studente = DAOStudente.carica(NOMEFILE2);
        verificaStudente2(studente);
    }

    public void testSalva1() throws Exception {
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        DAOStudente.salva(studente, NOMEFILE3);
        Studente studente1 = DAOStudente.carica(NOMEFILE3);
        verificaStudente1(studente1);
    }

    public void testSalva2() throws Exception {
        DAOStudente.salva(studente, NOMEFILE4);
        Studente studente2 = DAOStudente.carica(NOMEFILE4);
        verificaStudente2(studente2);
    }

    private void verificaStudente1(Studente studente) {
        assertEquals("Rossi", studente.getCognome());
        assertEquals("Mario", studente.getNome());
        assertEquals(1234, studente.getMatricola());
        assertEquals(2, studente.getNumeroEsami());
        assertEquals("Analisi", studente.getEsame(0).getInsegnamento());
        assertEquals(3, studente.getEsame(0).getCrediti());
        assertEquals(24, studente.getEsame(0).getVoto());
        assertEquals(false, studente.getEsame(0).isLode());
        assertEquals("Fisica", studente.getEsame(1).getInsegnamento());
        assertEquals(9, studente.getEsame(1).getCrediti());
        assertEquals(30, studente.getEsame(1).getVoto());
        assertEquals(true, studente.getEsame(1).isLode());
    }

    private void verificaStudente2(Studente studente) {
        assertEquals("Rossi", studente.getCognome());
        assertEquals("Mario", studente.getNome());
        assertEquals(1234, studente.getMatricola());
        assertEquals(0, studente.getNumeroEsami());
    }
}
