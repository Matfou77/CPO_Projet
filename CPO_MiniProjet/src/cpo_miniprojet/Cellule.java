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
    private int nbBombesAdjacentes;
    private boolean marquee;
    

    public Cellule() {
        this.presenceBombe = false;
        this.devoilee = false;
        this.nbBombesAdjacentes = 0;
        this.marquee = false;

    }

    public boolean getPresenceBombe() {
        return presenceBombe;
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
        if(!marquee){
            this.devoilee = true;
        }
    }

    public boolean estDevoilee() {
        return devoilee;
    }
    public boolean estMarquee() {
        return marquee;
    }
    public void basculerMarque() {
        if (!devoilee) {
            this.marquee = !this.marquee; // Alterne entre marqué et non marqué
        }
    }
    

    @Override
    public String toString() {
        if(marquee){
            return "D";
        }else if (!devoilee) {
            return "?";
        } else if (presenceBombe) {
            return "B";
        } else if (nbBombesAdjacentes > 0) {
            return String.valueOf(nbBombesAdjacentes);
        } else {
            return " ";
        }
    }
}
