/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpo_miniprojet;
import java.util.Random;
/**
 *
 * @author foure
 */
public class GrilleDeJeu {
    
    Cellule[][] matriceCellules;
    private int nbLignes;
    private int nbColonnes;
    private int nbBombes;

    public GrilleDeJeu(int nbLignes, int nbColonnes, int nbBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        this.matriceCellules = new Cellule[nbLignes][nbColonnes];
        initialiserGrille();
    }

    private void initialiserGrille() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                matriceCellules[i][j] = new Cellule();
            }
        }
    }

    public void placerBombesAleatoirement(int premierLigne, int premierColonne) {
        Random random = new Random();
        int bombesPlacees = 0;

        while (bombesPlacees < nbBombes) {
            int ligne = random.nextInt(nbLignes);
            int colonne = random.nextInt(nbColonnes);

            if (!matriceCellules[ligne][colonne].getPresenceBombe() &&
            (Math.abs(ligne - premierLigne) > 1 || Math.abs(colonne - premierColonne) > 1)) {
            matriceCellules[ligne][colonne].placerBombe();
            bombesPlacees++;
            }
        }
    }

    public void calculerBombesAdjacentes() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (!matriceCellules[i][j].getPresenceBombe()) {
                    int bombes = 0;
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            int ni = i + di, nj = j + dj;
                            if (ni >= 0 && ni < nbLignes && nj >= 0 && nj < nbColonnes &&
                                    matriceCellules[ni][nj].getPresenceBombe()) {
                                bombes++;
                            }
                        }
                    }
                    matriceCellules[i][j].setNbBombesAdjacentes(bombes);
                }
            }
        }
    }

    public void revelerCellule(int ligne, int colonne) {
        if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes || matriceCellules[ligne][colonne].estDevoilee()) {
            return;
        }

        matriceCellules[ligne][colonne].revelerCellule();
        matriceCellules[ligne][colonne].estDevoilee();
        
        if (matriceCellules[ligne][colonne].getNbBombesAdjacentes() == 0 && !matriceCellules[ligne][colonne].getPresenceBombe()) {
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    revelerCellule(ligne + di, colonne + dj);
                }
            }
        }
    }

    public boolean toutesCellulesRevelees() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (!matriceCellules[i][j].estDevoilee() && !matriceCellules[i][j].getPresenceBombe()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void reveleBombe(){
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (matriceCellules[i][j].getPresenceBombe()) {
                        matriceCellules[i][j].revelerCellule();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                sb.append(matriceCellules[i][j].toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

