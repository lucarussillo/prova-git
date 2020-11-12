package it.unibas.mediapesata.controllo;

import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.persistenza.DAOStudente;
import it.unibas.utilita.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Principale {

    private static final Logger logger = LoggerFactory.getLogger(Principale.class.getName());

    public void esegui() {
        Studente studente = null;
        boolean continua = true;
        while (continua) {
            int scelta = schermoMenu();
            if (scelta == 0) {
                continua = false;
            } else if (scelta == 1) {
                studente = schermoLeggiDatiStudente();
            } else if (scelta == 2) {
                if (studente != null) {
                    schermoLeggiDatiEsami(studente);
                } else {
                    System.out.println("ERRORE: studente nullo");
                }
            } else if (scelta == 3) {
                studente = schermoCaricaDati();
            } else if (scelta == 4) {
                if (studente != null) {
                    schermoSalvaDati(studente);
                } else {
                    System.out.println("ERRORE: studente nullo");
                }
            } else if (scelta == 5) {
                if (studente != null && studente.getNumeroEsami() > 0) {
                    schermoMediaPesata(studente);
                } else {
                    System.out.println("ERRORE: non e' possibile calcolare la media");
                }
            }
        }
        System.out.println();
        System.out.println("Arrivederci");
    }

    private int schermoMenu() {
        int scelta;
        System.out.println("-----------------------------------------");
        System.out.println("               Media  Pesata         ");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("   1. Inserisci dati nuovo studente");
        System.out.println("   2. Inserisci dati esami");
        System.out.println("   3. Carica dati studente ed esami da file");
        System.out.println("   4. Salva dati studente ed esami su file");
        System.out.println("   5. Calcola media pesata");
        System.out.println();
        System.out.println("   0. Esci");
        System.out.println();
        System.out.print("   Scelta ----> ");
        scelta = Console.leggiIntero();
        while ((scelta < 0) || (scelta > 5)) {
            System.out.print("   Errore. Ripeti ----> ");
            scelta = Console.leggiIntero();
        }
        return scelta;
    }

    private Studente schermoLeggiDatiStudente() {
        System.out.println("-------------------------------------------");
        System.out.println("      Calcolo della Media Pesata");
        System.out.println("-------------------------------------------");
        System.out.println("Nome dello studente:");
        System.out.print("---> ");
        String nome = this.leggiStringaNonVuota();
        System.out.println("Cognome dello studente:");
        System.out.print("---> ");
        String cognome = this.leggiStringaNonVuota();
        System.out.println("Matricola dello studente:");
        System.out.print("---> ");
        int matricola = Console.leggiIntero();
        while (matricola < 0) {
            System.out.println("Errore. Numero scorretto.");
            System.out.print("Ripeti. ---> ");
            matricola = Console.leggiIntero();
        }
        Studente studente = new Studente(nome, cognome, matricola);
        return studente;
    }

    private int schermoLeggiNumeroEsami() {
        System.out.println("Quanti esami ha sostenuto lo studente ?");
        System.out.print("---> ");
        int numeroEsami = Console.leggiIntero();
        while (numeroEsami < 0) {
            System.out.println("Errore. Numero scorretto.");
            System.out.print("Ripeti. ---> ");
            numeroEsami = Console.leggiIntero();
        }
        return numeroEsami;
    }

    private void schermoLeggiDatiEsami(Studente studente) {
        int numeroEsami = schermoLeggiNumeroEsami();
        for (int i = 0; i < numeroEsami; i++) {
            String insegnamento = schermoLeggiInsegnamento(i);
            int voto = schermoLeggiVoto();
            char lodeSN;
            if (voto == 30) {
                lodeSN = schermoLeggiLode();
            } else {
                lodeSN = 'n';
            }
            boolean lode = false;
            if (lodeSN == 's') {
                lode = true;
            }
            int crediti = schermoLeggiCrediti();
            studente.addEsame(insegnamento, voto, lode, crediti);
        }
    }

    private String schermoLeggiInsegnamento(int i) {
        System.out.println();
        System.out.println("Immetti i dati dell'esame n.: " + ++i);
        System.out.println("----------------------------------------");
        System.out.println("Insegnamento:");
        System.out.print("---> ");
        String insegnamento = this.leggiStringaNonVuota();
        return insegnamento;
    }

    private int schermoLeggiVoto() {
        System.out.println("Voto:");
        System.out.print("---> ");
        int voto = Console.leggiIntero();
        while (voto < 18 || voto > 30) {
            System.out.println("Errore. Numero scorretto.");
            System.out.print("Ripeti. ---> ");
            voto = Console.leggiIntero();
        }
        return voto;
    }

    private char schermoLeggiLode() {
        char lode;
        System.out.println("Lode (s/n):");
        System.out.print("---> ");
        lode = Console.leggiCarattere();
        while (lode != 's' && lode != 'n') {
            System.out.println("Errore. Valore scorretto.");
            System.out.print("Ripeti (s/n) ---> ");
            lode = Console.leggiCarattere();
        }
        return lode;
    }

    private int schermoLeggiCrediti() {
        System.out.println("Crediti:");
        System.out.print("---> ");
        int crediti = Console.leggiIntero();
        while (crediti <= 0) {
            System.out.println("Errore. Numero scorretto.");
            System.out.print("Ripeti. ---> ");
            crediti = Console.leggiIntero();
        }
        return crediti;
    }

    private Studente schermoCaricaDati() {
        System.out.println("---------------------------------");
        System.out.println("       Caricamento Dati");
        System.out.println("---------------------------------");
        System.out.print("Inserisci il nome del file --> ");
        String nomeFile = this.leggiStringaNonVuota();
        Studente studente = null;
        try {
            studente = DAOStudente.carica(nomeFile);
            System.out.println(" -- Caricamento effettuato -- ");
        } catch (DAOException ioe) {
            System.out.println("ERRORE: " + ioe);
        }
        return studente;
    }

    private void schermoSalvaDati(Studente studente) {
        System.out.println("---------------------------------");
        System.out.println("       Salvataggio Agenda");
        System.out.println("---------------------------------");
        System.out.print("Inserisci il nome del file --> ");
        String nomeFile = this.leggiStringaNonVuota();
        try {
            DAOStudente.salva(studente, nomeFile);
            System.out.println(" -- Salvataggio effettuato -- ");
        } catch (DAOException ioe) {
            System.out.println("ERRORE: " + ioe);
            System.out.println("--------------------------------");
        }
    }

    private void schermoMediaPesata(Studente studente) {
        System.out.println();
        System.out.println("Dati dello studente\n");
        System.out.println(studente);
        System.out.println();
        System.out.println("Elenco degli esami sostenuti:\n");
        for (int i = 0; i < studente.getNumeroEsami(); i++) {
            System.out.println(studente.getEsame(i));
        }
        System.out.println("Media pesata : " + studente.getMediaPesata());
    }

    private String leggiStringaNonVuota() {
        String stringa = Console.leggiStringa();
        while (stringa.length() == 0) {
            System.out.println("ERRORE: La stringa non puo' essere nulla. Riprova.");
            System.out.print("---> ");
            stringa = Console.leggiStringa();
        }
        return stringa;
    }

    public static void main(String[] args) {
        Principale p = new Principale();
        p.esegui();
    }

}
