package com.example.helbhotel;

public class ReservationRequest {
    public String nom;
    public String prenom;
    public int nombreDePersonnes;
    public boolean fumeur;
    public String motifSejour;
    public int nombreEnfants;

    public ReservationRequest(String nom, String prenom, int nombreDePersonnes, boolean fumeur, String motifSejour, int nombreEnfants) {
        this.nom = nom;
        this.prenom = prenom;
        this.nombreDePersonnes = nombreDePersonnes;
        this.fumeur = fumeur;
        this.motifSejour = motifSejour;
        this.nombreEnfants = nombreEnfants;
    }
}
