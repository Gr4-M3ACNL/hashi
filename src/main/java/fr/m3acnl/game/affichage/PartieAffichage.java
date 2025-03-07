package fr.m3acnl.game.affichage;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import fr.m3acnl.game.logique.Jeu;

public class PartieAffichage extends JFrame {

    private Jeu jeu;
    private JLabel timeLabel;
    private JButton[][] buttons;

    public PartieAffichage(Jeu jeu) {
        this.jeu = jeu;
        setTitle("Jeu Interface");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fond d'écran
        JLabel backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/ressources/META-INF/assetsGraphiques/hashiWallpaper.jpeg")));
        backgroundLabel.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(7, 7));
        buttons = new JButton[7][7];

        // Remplir les boutons avec des images selon les nœuds
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j] = new JButton();
                int x = i;
                int y = j;
                buttons[i][j].addActionListener(e -> activerElement(x, y));
                gridPanel.add(buttons[i][j]);

                // Définir l'image en fonction du chiffre du nœud
                //String nodeValue = String.valueOf(jeu.getPlateau().getElement(i, j).;
                //String imagePath = "../../../../../ressources/META-INF/assetsGraphiques/pie/pie" + nodeValue + ".png";
                String imagePath = jeu.getPlateau().getElement(i, j).draw();
                buttons[i][j].setIcon(new ImageIcon(getClass().getResource(imagePath)));
            }
        }

        backgroundLabel.add(gridPanel, BorderLayout.CENTER);
        add(backgroundLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton undoButton = new JButton("Retour");
        undoButton.addActionListener(e -> jeu.retour());
        controlPanel.add(undoButton);

        JButton redoButton = new JButton("Avancer");
        redoButton.addActionListener(e -> jeu.avancer());
        controlPanel.add(redoButton);

        JButton checkWinButton = new JButton("Vérifier victoire");
        checkWinButton.addActionListener(e -> checkWin());
        controlPanel.add(checkWinButton);

        timeLabel = new JLabel("Temps: 0s");
        controlPanel.add(timeLabel);

        add(controlPanel, BorderLayout.SOUTH);

        new Timer(1000, e -> updateTime()).start();
    }

    private void activerElement(int x, int y) {
        jeu.activeElemJeu(x, y, null);
        buttons[x][y].setText("X");
    }

    private void checkWin() {
        if (jeu.gagner()) {
            JOptionPane.showMessageDialog(this, "Vous avez gagné!");
        }
    }

    private void updateTime() {
        timeLabel.setText("Temps: " + jeu.getTempsEcouler() + "s");
    }

    public static void main(String[] args) {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Jeu jeu = new Jeu(0, mat);
        SwingUtilities.invokeLater(() -> new PartieAffichage(jeu).setVisible(true));
    }
}
