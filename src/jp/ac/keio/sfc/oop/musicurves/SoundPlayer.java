package jp.ac.keio.sfc.oop.musicurves;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SoundPlayer {

    ArrayList<MelodySequence> sequences;

    SoundPlayer(ArrayList<MelodySequence> ml) {
        sequences = ml;
    }

    void play() {

        if(sequences == null || sequences.size() == 0)
        {
            return;
        }

        //formatは同じで無ければならない
        //byte[] data = MelodySequence.mix(sequences);
        byte[] data = sequences.get(0).melody.getData();

        try {
            DataLine.Info info = new DataLine.Info(Clip.class, sequences.get(0).melody.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);

            if(data.length % 2 != 0)
            {
                data = Arrays.copyOfRange(data,0,data.length - 1);
            }

            clip.open(sequences.get(0).melody.getFormat(), data , 0, data.length);
            clip.start();
            while (clip.isRunning()) {
                Thread.sleep(100);
            }


        } catch (Exception er) {
            System.out.println("error:" + er);
        }
    }

}
