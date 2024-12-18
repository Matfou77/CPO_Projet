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
    private boolean premierClic; // Variable pour vérifier si c'est le premier clic

    public GrilleDeJeu(int nbLignes, int nbColonnes, int nbBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        this.matriceCellules = new Cellule[nbLignes][nbColonnes];
        this.premierClic = true; // Au départ, on n'a pas encore cliqué
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

        while (bombesPlacees < 10) {
            int ligne = random.nextInt(10);
            int colonne = random.nextInt(10);

            // Assurez-vous que les bombes ne sont pas placées sur ou autour du premier clic
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

                // Attribue le nombre de bombes adjacentes
                matriceCellules[i][j].setNbBombesAdjacentes(bombes);
            }
        }
    }

    public void revelerCellule(int ligne, int colonne) {
        if (ligne < 0 || ligne >= 10 || colonne < 0 || colonne >= 10) return;
        Cellule cellule = matriceCellules[ligne][colonne];

        // Si la cellule est déjà révélée, on arrête
        if (cellule.estDevoilee()) return;

        cellule.revelerCellule();

        if (cellule.getNbBombesAdjacentes() == 0) {
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di != 0 || dj != 0) {
                        revelerCellule(ligne + di, colonne + dj); // Révéler les cases adjacentes
                    }
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
                
    public void clickSurCellule(int i, int j) {
        if (premierClic) {
            premierClic = false;
            calculerBombesAdjacentes();
            
            // Vérifier que la cellule cliquée est bien vide, et révèler sa zone
            while (matriceCellules[i][j].getNbBombesAdjacentes() > 0) {
                // Si la cellule cliquée a des bombes adjacentes, refaire le placement des bombes
                placerBombesAleatoirement(i, j);
                calculerBombesAdjacentes();
            }
        }

        // Si la cellule contient une bombe, montrer toutes les bombes et afficher un message de fin
        if (matriceCellules[i][j].getPresenceBombe()) {
            reveleBombe();  // Révèle toutes les bombes
            JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Game Over", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Ferme le jeu si une bombe est révélée
        }

        // Révéler la cellule et vérifier si la victoire est atteinte
        revelerCellule(i, j);

        if (toutesCellulesRevelees()) {
            JOptionPane.showMessageDialog(null, "Félicitations ! Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void reveleBombe() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (matriceCellules[i][j].getPresenceBombe()) {
                    matriceCellules[i][j].revelerCellule();  // Révèle la cellule contenant une bombe
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
