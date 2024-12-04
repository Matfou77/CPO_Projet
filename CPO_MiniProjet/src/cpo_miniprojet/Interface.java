package cpo_miniprojet;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author foure
 */
public class Interface extends JFrame {
    private JPanel panneauGrille;          // Panneau pour afficher la grille
    private JButton[][] boutons;          // Boutons de l'interface graphique
    private boolean[][] bombes;           // Grille logique des bombes
    private int[][] bombesAdjacentes;     // Nombre de bombes adjacentes par cellule
    private boolean[][] devoilees;        // État des cellules dévoilées
    private boolean[][] drapeaux;         // État des cellules avec drapeau
    private final int lignes = 9;         // Taille de la grille
    private final int colonnes = 9;
    private final int nombreBombes = 10;  // Nombre de bombes dans la grille
    private boolean premierCoup = true;   // Sécuriser le premier clic

    public Interface() {
        setTitle("Démineur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        initialiserGrille();
        creerInterface();
        setVisible(true);
    }

    /**
     * Initialise les grilles logiques pour les bombes, drapeaux et cellules dévoilées.
     */
    private void initialiserGrille() {
        bombes = new boolean[lignes][colonnes];
        bombesAdjacentes = new int[lignes][colonnes];
        devoilees = new boolean[lignes][colonnes];
        drapeaux = new boolean[lignes][colonnes];
    }

    /**
     * Place les bombes sur la grille tout en évitant la case initialement cliquée.
     */
    private void placerBombes(int exclureLigne, int exclureColonne) {
        Random random = new Random();
        int bombesPlacees = 0;

        while (bombesPlacees < nombreBombes) {
            int ligne = random.nextInt(lignes);
            int colonne = random.nextInt(colonnes);

            if (!bombes[ligne][colonne] && (ligne != exclureLigne || colonne != exclureColonne)) {
                bombes[ligne][colonne] = true;
                bombesPlacees++;
            }
        }

        calculerBombesAdjacentes();
    }

    /**
     * Calcule le nombre de bombes adjacentes pour chaque cellule.
     */
    private void calculerBombesAdjacentes() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (bombes[i][j]) {
                    bombesAdjacentes[i][j] = -1;
                } else {
                    int compte = 0;
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            int ni = i + di, nj = j + dj;
                            if (ni >= 0 && ni < lignes && nj >= 0 && nj < colonnes && bombes[ni][nj]) {
                                compte++;
                            }
                        }
                    }
                    bombesAdjacentes[i][j] = compte;
                }
            }
        }
    }

    /**
     * Crée l'interface utilisateur et initialise les boutons.
     */
    private void creerInterface() {
        panneauGrille = new JPanel();
        panneauGrille.setLayout(new GridLayout(lignes, colonnes));
        boutons = new JButton[lignes][colonnes];

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                boutons[i][j] = new JButton("?");
                boutons[i][j].setFont(new Font("Arial", Font.BOLD, 14));
                boutons[i][j].setBackground(Color.GRAY);

                final int ligne = i, colonne = j;

                // Ajouter des écouteurs pour les clics gauche et droit
                boutons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) { // Clic gauche
                            revelerCellule(ligne, colonne);
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // Clic droit
                            basculerDrapeau(ligne, colonne);
                        }
                    }
                });

                panneauGrille.add(boutons[i][j]);
            }
        }

        add(panneauGrille, BorderLayout.CENTER);
        pack();
    }

    /**
     * Révèle une cellule de la grille.
     */
    private void revelerCellule(int ligne, int colonne) {
        if (drapeaux[ligne][colonne] || devoilees[ligne][colonne]) return;

        if (premierCoup) {
            placerBombes(ligne, colonne);
            premierCoup = false;
        }

        devoilees[ligne][colonne] = true;

        if (bombes[ligne][colonne]) {
            boutons[ligne][colonne].setText("B");
            boutons[ligne][colonne].setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "BOOM! Vous avez perdu.");
            System.exit(0);
        } else if (bombesAdjacentes[ligne][colonne] > 0) {
            boutons[ligne][colonne].setText(String.valueOf(bombesAdjacentes[ligne][colonne]));
            boutons[ligne][colonne].setBackground(Color.LIGHT_GRAY);
        } else {
            boutons[ligne][colonne].setText(" ");
            boutons[ligne][colonne].setBackground(Color.LIGHT_GRAY);

            // Révéler les cellules adjacentes
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    int ni = ligne + di, nj = colonne + dj;
                    if (ni >= 0 && ni < lignes && nj >= 0 && nj < colonnes && !devoilees[ni][nj]) {
                        revelerCellule(ni, nj);
                    }
                }
            }
        }

        verifierVictoire();
    }

    /**
     * Basculer l'état d'un drapeau sur une cellule.
     */
    private void basculerDrapeau(int ligne, int colonne) {
        if (devoilees[ligne][colonne]) return;

        drapeaux[ligne][colonne] = !drapeaux[ligne][colonne];
        boutons[ligne][colonne].setText(drapeaux[ligne][colonne] ? "D" : "?");
        boutons[ligne][colonne].setForeground(drapeaux[ligne][colonne] ? Color.BLUE : Color.BLACK);
    }

    /**
     * Vérifie si le joueur a gagné la partie.
     */
    private void verifierVictoire() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (!bombes[i][j] && !devoilees[i][j]) {
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Félicitations! Vous avez gagné.");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interface::new);
    }
}
