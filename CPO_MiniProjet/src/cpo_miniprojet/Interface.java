package cpo_miniprojet;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author foure
 */
public class Interface extends javax.swing.JFrame {

    GrilleDeJeu grille;
    public void initialiserPartie() {
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

    public class CelluleGraphique extends JButton {
        int largeur;
        int hauteur;
        Cellule cellule;
    

    public CelluleGraphique(Cellule cellule, int l, int h) {
        this.largeur = l;
        this.hauteur = h;
        this.cellule = cellule;

    }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setText(cellule.toString());
        }
   
    }

    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
        int nbLignes = 10;
        int nbColonnes = 10;
        this.grille = new GrilleDeJeu(10,10, 10);
        PanneauGrille.setLayout(new GridLayout(nbLignes, nbColonnes)); 
        
        this.grille.revelerCellule(0,0); 
        repaint(); 
        
        for (int i=0; i < nbLignes; i++) { 
            for (int j=0; j < nbColonnes; j++ ) { 
                CelluleGraphique bouton_cellule = new CelluleGraphique( grille.matriceCellules[i][j], 36,36);
                PanneauGrille.add(bouton_cellule); // ajout au Jpanel PanneauGrille 
            } 
        } 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauGrille = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(new java.awt.CardLayout(100, 100));

        PanneauGrille.setBackground(new java.awt.Color(80, 80, 80));
        PanneauGrille.setPreferredSize(new java.awt.Dimension(360, 360));

        javax.swing.GroupLayout PanneauGrilleLayout = new javax.swing.GroupLayout(PanneauGrille);
        PanneauGrille.setLayout(PanneauGrilleLayout);
        PanneauGrilleLayout.setHorizontalGroup(
            PanneauGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );
        PanneauGrilleLayout.setVerticalGroup(
            PanneauGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 587, Short.MAX_VALUE)
        );

        getContentPane().add(PanneauGrille, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanneauGrille;
    // End of variables declaration//GEN-END:variables
}
