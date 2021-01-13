/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WestPanel extends JPanel {

    private final MainFrame view;

    private final int width;
    private final int height;

    private JLabel lblActions;
    private JButton btnAIGuess;
    private JButton btnAIFinish;
    private JButton btnResetGame;
    private JList<String> lstHighScores;

    public WestPanel(MainFrame view, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;

        setupPanel();
    }
    private void setupPanel() {

        setSize(width, height);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        lblActions = new JLabel();
        lblActions.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblActions.add(Box.createRigidArea(new Dimension(100,50)));
        lblActions.setText("Actions: " + view.getActions());
        add(lblActions);
        add(Box.createRigidArea(new Dimension(0, 10)));

        btnAIGuess = new JButton("AI guess");
        btnAIGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAIGuess.add(Box.createRigidArea(new Dimension(100,50)));
        btnAIGuess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.letAIPressButton();
            }
        });
        add(btnAIGuess);
        add(Box.createRigidArea(new Dimension(0, 10)));

        btnAIFinish = new JButton("Let AI do rest");
        btnAIFinish.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAIFinish.add(Box.createRigidArea(new Dimension(100,50)));
        btnAIFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.letAIFinish();
            }
        });
        add(btnAIFinish);
        add(Box.createRigidArea(new Dimension(0, 10)));

        btnResetGame = new JButton("Reset Board");
        btnResetGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResetGame.add(Box.createRigidArea(new Dimension(100,50)));
        btnResetGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.resetGame();
                enableAIButton();
            }
        });
        add(btnResetGame);
        add(Box.createRigidArea(new Dimension(0, 10)));

        lstHighScores = new JList<>();
        lstHighScores.setAlignmentX(Component.CENTER_ALIGNMENT);
        lstHighScores.setFixedCellWidth(130);
        String highScoreTitle = String.format("*Best Scores %dx%d*", view.getRows(), view.getRows());
        lstHighScores.setBorder(BorderFactory.createTitledBorder(highScoreTitle));
        add(lstHighScores);

    }

    public void updateActions() {
        lblActions.setText("Current score: " + view.getActions());
    }
    public void updateHighScores(String[] highScores) {

        lstHighScores.setListData(highScores);
    }

    public void disableAIButton() {
        btnAIGuess.setEnabled(false);
        btnAIFinish.setEnabled(false);
    }
    public void enableAIButton() {
        btnAIGuess.setEnabled(true);
        btnAIFinish.setEnabled(true);

    }

}
