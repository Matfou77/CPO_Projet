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
        int taille = 9;
        int bombes = 10;
        grille = new GrilleDeJeu(taille, taille, bombes);
        grille.placerBombesAleatoirement();
        grille.calculerBombesAdjacentes();
    }

    public void demarrerPartie() {
        Scanner scanner = new Scanner(System.in);
        boolean partieEnCours = true;

        while (partieEnCours) {
            System.out.println("Grille actuelle :");
            System.out.println(grille);

            System.out.print("Entrez la ligne et la colonne a reveler (ex : 0 1 ) Max 8 et min 0 : ");
            int ligne = scanner.nextInt();
            int colonne = scanner.nextInt();

            if (grille.matriceCellules[ligne][colonne].getPresenceBombe()) {
                System.out.println("BOOM! Vous avez touché une bombe. Partie terminée.");
                grille.reveleBombe();
                partieEnCours = false;
            } else {
                grille.revelerCellule(ligne, colonne);

                if (grille.toutesCellulesRevelees()) {
                    System.out.println("Bravo! Vous avez gagné.");
                    partieEnCours = false;
                }
            }
        }

        System.out.println("Grille finale :");
        System.out.println(grille);
    }

    public static void main(String[] args) {
        Partie partie = new Partie();
        partie.initialiserPartie();
        partie.demarrerPartie();
    }
}
