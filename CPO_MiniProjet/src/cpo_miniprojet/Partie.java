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

    public void initialiserPartie() {
    grille = new GrilleDeJeu(10, 10, 10);
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
}