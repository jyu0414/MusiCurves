package jp.ac.keio.sfc.oop.musicurves;

import javax.sound.sampled.*;

public class SoundPlayer {

    MelodySequence sequence;

    SoundPlayer(MelodySequence[] ml) {
        sequence = ml[0];
    }

    void play() {

        try {
            DataLine.Info info = new DataLine.Info(Clip.class, sequence.melody.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sequence.melody.getFormat(), sequence.melody.getData() , 0, sequence.melody.size());
            clip.start();
            while (clip.isRunning()) {
                Thread.sleep(100);
            }

        } catch (Exception er) {
            System.out.println("error:" + er);
        }
    }

}
