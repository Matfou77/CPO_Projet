/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpo_miniprojet;
import java.util.Scanner;
/**
 *
 * @author foure
 */
public class Partie {
    private GrilleDeJeu grille;

    public void initialiserPartie(int par, int par1, int par2) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Choisissez un niveau de difficulte :");
    System.out.println("1. Facile (9x9, 10 bombes)");
    System.out.println("2. Intermediaire (16x16, 40 bombes)");
    System.out.println("3. Difficile (30x16, 99 bombes)");

    int niveau = scanner.nextInt();
    int lignes = 0, colonnes = 0, bombes = 0;

    switch (niveau) {
        case 1: // Niveau facile
            lignes = 9;
            colonnes = 9;
            bombes = 10;
            break;
        case 2: // Niveau intermédiaire
            lignes = 16;
            colonnes = 16;
            bombes = 40;
            break;
        case 3: // Niveau difficile
            lignes = 16;
            colonnes = 30;
            bombes = 99;
            break;
        default:
            System.out.println("Niveau invalide. Le niveau facile sera selectionne par defaut.");
            lignes = 9;
            colonnes = 9;
            bombes = 10;
    }

    grille = new GrilleDeJeu(lignes, colonnes, bombes);
}


    public void demarrerPartie() {
    Scanner scanner = new Scanner(System.in);
    boolean partieEnCours = true;
    boolean premierCoup = true;

    while (partieEnCours) {
        System.out.println("Grille actuelle :");
        System.out.println(grille);

        System.out.println("Entrez votre choix :");
        System.out.println("1. Reveler une cellule");
        System.out.println("2. Poser/retirer un drapeau");
        int choix = scanner.nextInt();

        System.out.print("Entrez la ligne et la colonne (ex : 0 1) : ");
        int ligne = scanner.nextInt();
        int colonne = scanner.nextInt();

        if (choix == 1) { // Révéler une cellule
            if (premierCoup) {
                grille.placerBombesAleatoirement(ligne, colonne);
                grille.calculerBombesAdjacentes();
                premierCoup = false;
            }

            if (grille.matriceCellules[ligne][colonne].getPresenceBombe()) {
                System.out.println("BOOM! Vous avez touché une bombe. Partie terminée.");
                grille.reveleBombe();
                partieEnCours = false;
            } else {
                grille.revelerCellule(ligne, colonne);

                if (grille.toutesCellulesRevelees()) {
                    System.out.println("Bravo! Vous avez gagne.");
                    partieEnCours = false;
                }
            }
        } else if (choix == 2) { // Poser ou retirer un drapeau
            grille.matriceCellules[ligne][colonne].basculerMarque();
        }
    }

    System.out.println("Grille finale :");
    System.out.println(grille);
}



    public static void main(String[] args) {
        Partie partie = new Partie();
        partie.initialiserPartie(9, 9, 10);
        partie.demarrerPartie();
    }

    GrilleDeJeu getGrille() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean verifierVictoire() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean tourDeJeu(int ligne, int colonne) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
