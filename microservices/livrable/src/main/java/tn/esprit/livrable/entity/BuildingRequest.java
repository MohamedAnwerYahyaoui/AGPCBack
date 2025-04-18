package tn.esprit.livrable.entity;

public class BuildingRequest {


    private String type;
    private double surface;
    private double longueur;
    private double largeur;
    private int nombreChambres;

    // Getters et Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getSurface() { return surface; }
    public void setSurface(double surface) { this.surface = surface; }

    public double getLongueur() { return longueur; }
    public void setLongueur(double longueur) { this.longueur = longueur; }

    public double getLargeur() { return largeur; }
    public void setLargeur(double largeur) { this.largeur = largeur; }

    public int getNombreChambres() { return nombreChambres; }
    public void setNombreChambres(int nombreChambres) { this.nombreChambres = nombreChambres; }
}





