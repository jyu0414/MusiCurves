package jp.ac.keio.sfc.oop.musicurves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {


    MainWindow(){
        setTitle("MusiCurves");
        setBounds(100,100,1300,800);
        getContentPane().setBackground(ThemeColour.background);
        addComponents();
    }

    void addComponents(){

        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 5;
        gbc.weightx = 1;
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(ThemeColour.background);
        layout.setConstraints(leftPanel,gbc);
        getContentPane().add(leftPanel);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weighty = 5;
        gbc.weightx = 1;
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(ThemeColour.background);
        GridBagLayout buttonLayout = new GridBagLayout();
        centerPanel.setLayout(buttonLayout);
        layout.setConstraints(centerPanel,gbc);
        getContentPane().add(centerPanel);

        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.weighty = 5;
        gbc.weightx = 1;
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(ThemeColour.background);
        layout.setConstraints(rightPanel,gbc);
        getContentPane().add(rightPanel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 95;
        JPanel panel = new JPanel();
        layout.setConstraints(panel,gbc);
        getContentPane().add(panel);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets= new Insets(0,0,0,0);
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 1;
        buttonConstraints.weighty = 1;


        JButton button = new JButton();
        button.setText("再生");
        button.addActionListener(this);
        buttonLayout.setConstraints(button,buttonConstraints);
        centerPanel.add(button);




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Melody melody = new Melody();
        melody.addPitch(770,3);
        melody.addPitch(500,3);

        MelodySequence[] melodies = new MelodySequence[1];
        melodies[0] = new MelodySequence(melody);

        SoundPlayer sp = new SoundPlayer(melodies);

        sp.play();
    }
}
