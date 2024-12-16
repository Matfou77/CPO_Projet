/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpo_miniprojet;

/**
 *
 * @author foure
 */
public class Cellule {
    private boolean presenceBombe;
    private boolean devoilee;
    private boolean marquee; // Nouveau : indique si la cellule est marquée
    private int nbBombesAdjacentes;

    public Cellule() {
        this.presenceBombe = false;
        this.devoilee = false;
        this.marquee = false;
        this.nbBombesAdjacentes = 0;
    }

    public boolean getPresenceBombe() {
        return presenceBombe;
    }

    public boolean estDevoilee() {
        return devoilee;
    }

    public boolean estMarquee() {
        return marquee;
    }

    public int getNbBombesAdjacentes() {
        return nbBombesAdjacentes;
    }

    public void setNbBombesAdjacentes(int nbBombesAdjacentes) {
        this.nbBombesAdjacentes = nbBombesAdjacentes;
    }

    public void placerBombe() {
        this.presenceBombe = true;
    }

    public void revelerCellule() {
        if (!marquee) {
            this.devoilee = true; // Une cellule marquée ne peut pas être révélée
        }
    }

    public void basculerMarque() {
        if (!devoilee) {
            this.marquee = !this.marquee; // Alterne entre marqué et non marqué
        }
    }

    @Override
    public String toString() {
        if (marquee) {
            return "🚩"; // D pour Drapeau
        } else if (!devoilee) {
            return "☁"; // Non dévoilée
        } else if (presenceBombe) {
            return "💣"; // Bombe dévoilée
        } else if (nbBombesAdjacentes > 0) {
            return String.valueOf(nbBombesAdjacentes); // Nombre de bombes adjacentes
        } else {
            return " "; // Cellule vide
        }
    }
}