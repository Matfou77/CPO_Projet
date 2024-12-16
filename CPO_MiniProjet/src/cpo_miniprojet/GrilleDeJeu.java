/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpo_miniprojet;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 *
 * @author foure
 */
public class GrilleDeJeu {
    
    Cellule[][] matriceCellules;
    private final int nbLignes;
    private final int nbColonnes;
    private final int nbBombes;

    public GrilleDeJeu(int nbLignes, int nbColonnes, int nbBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        this.matriceCellules = new Cellule[nbLignes][nbColonnes];
        initialiserGrille();
    }

    void initialiserGrille() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                matriceCellules[i][j] = new Cellule();
            }
        }
    }

    public void placerBombesAleatoirement(int premierLigne, int premierColonne) {
    Random random = new Random();
    int bombesPlacees = 0;

    // Placer les bombes en excluant la cellule cliquée et les cellules adjacentes
    while (bombesPlacees < nbBombes) {
        int ligne = random.nextInt(nbLignes);
        int colonne = random.nextInt(nbColonnes);

        // Vérifier que la cellule n'est pas déjà une bombe et qu'elle est à une distance sécuritaire
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
            int bombes = 0;

            // Parcourt un carré de 3×3 autour de la cellule [i][j]
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    int ni = i + di, nj = j + dj;

                    // Vérifie que les indices restent dans les limites de la grille
                    if (ni >= 0 && ni < nbLignes && nj >= 0 && nj < nbColonnes &&
                        matriceCellules[ni][nj].getPresenceBombe()) {
                        bombes++;
                    }
                }
            }

            // Attribue le nombre de bombes (adjacentes ou dans la case elle-même)
            matriceCellules[i][j].setNbBombesAdjacentes(bombes);
            }
        }
    }

public void revelerCellule(int ligne, int colonne) {
    if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes || matriceCellules[ligne][colonne].estDevoilee()) {
        return; // Si la cellule est hors limites ou déjà révélée, on ne fait rien
    }

    matriceCellules[ligne][colonne].revelerCellule();

    // Si la cellule n'a aucune bombe adjacente, révéler toutes ses voisines
    if (matriceCellules[ligne][colonne].getNbBombesAdjacentes() == 0) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                int ni = ligne + di;
                int nj = colonne + dj;
                revelerCellule(ni, nj);
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

    public boolean premierClicEstValide(int i, int j) {
    // Vérifier que la case cliquée et ses voisines ne contiennent aucune bombe
    for (int di = -1; di <= 1; di++) {
        for (int dj = -1; dj <= 1; dj++) {
            int ni = i + di;
            int nj = j + dj;

            // Vérifie si les indices sont dans les limites de la grille
            if (ni >= 0 && ni < nbLignes && nj >= 0 && nj < nbColonnes) {
                // Si une cellule voisine contient une bombe, le premier clic est invalide
                if (matriceCellules[ni][nj].getPresenceBombe()) {
                    return false;
                }
            }
        }
    }
    return true;
}

    boolean premierClic = false;
    
    public void clickSurCellule(int i, int j) {
    // Si c'est le premier clic
    if (!premierClic) {
        premierClic = true;
        
        // Vérifier que la case cliquée est valide (sans bombe et sans bombes autour)
        while (!premierClicEstValide(i, j)) {
            // Si la case n'est pas valide, on demande un autre clic
            JOptionPane.showMessageDialog(null, "Veuillez cliquer sur une cellule sans bombe autour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Empêche de continuer si le clic est invalide
        }
        
        // Placer les bombes après le premier clic (en excluant les voisins)
        placerBombesAleatoirement(i, j);
        calculerBombesAdjacentes();  // Calculer les bombes adjacentes à chaque cellule
    }

    // Vérifier si la cellule contient une bombe
    if (matriceCellules[i][j].getPresenceBombe()) {
        JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Game Over", JOptionPane.ERROR_MESSAGE);
        System.exit(0); // Ferme le jeu si une bombe est révélée
    }

    // Révéler la cellule
    revelerCellule(i, j);

    // Vérifier si toutes les cellules sans bombe sont révélées
    if (toutesCellulesRevelees()) {
        JOptionPane.showMessageDialog(null, "Félicitations ! Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
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


