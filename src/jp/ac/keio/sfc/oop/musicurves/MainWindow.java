package jp.ac.keio.sfc.oop.musicurves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindow extends JFrame implements ActionListener {

    Sketch sketchBoard;

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
        leftPanel.setBackground(ThemeColour.purple);
        layout.setConstraints(leftPanel,gbc);
        getContentPane().add(leftPanel);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weighty = 5;
        gbc.weightx = 1;
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(ThemeColour.purple);
        GridBagLayout buttonLayout = new GridBagLayout();
        centerPanel.setLayout(buttonLayout);
        layout.setConstraints(centerPanel,gbc);
        getContentPane().add(centerPanel);

        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.weighty = 5;
        gbc.weightx = 1;
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(ThemeColour.purple);
        layout.setConstraints(rightPanel,gbc);
        getContentPane().add(rightPanel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 95;
        sketchBoard = new Sketch();
        sketchBoard.setBackground(ThemeColour.background);
        layout.setConstraints(sketchBoard,gbc);
        getContentPane().add(sketchBoard);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets= new Insets(0,0,0,0);
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 1;
        buttonConstraints.weighty = 1;


        JButton button = new JButton();
        button.setText("Play");
        button.addActionListener(this);
        buttonLayout.setConstraints(button,buttonConstraints);
        centerPanel.add(button);

        JButton button2 = new JButton();
        button2.setText("Eraser");
        button2.addActionListener(this);
        buttonLayout.setConstraints(button2,buttonConstraints);
        centerPanel.add(button2);

        JButton button3 = new JButton();
        button3.setText("Pen");
        button3.addActionListener(this);
        buttonLayout.setConstraints(button3,buttonConstraints);
        centerPanel.add(button3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Play" )
        {
            Lock lock = new ReentrantLock();
            ArrayList<double[][]> lines = sketchBoard.getFrequencyLines();
            MelodySequence[] melodies = new MelodySequence[lines.size()];

            double secMultiple = 10.0 / (double)(sketchBoard.getWidth());

            for (int i = 0; i < lines.size(); i++) {
                Melody melody = new Melody();
                for (int j = 0; j < lines.get(i).length; j++) {
                    double prev = 0;
                    if (j > 0) prev = lines.get(i)[j - 1][1];
                    melody.addPitch(lines.get(i)[j][0], (lines.get(i)[j][1] - prev) * secMultiple);
                }
                melodies[i] = new MelodySequence(melody, (float) (lines.get(i)[0][1] * secMultiple));
            }

            if(melodies.length > 0)
            {
                SoundPlayer sp = new SoundPlayer(melodies);
                sp.play();
            }

        }
        else if(e.getActionCommand() == "Eraser")
        {
            sketchBoard.mode = Sketch.MouseMode.Eraser;
        }
        else if(e.getActionCommand() == "Pen")
        {
            sketchBoard.mode = Sketch.MouseMode.Pen;
        }

    }
}
