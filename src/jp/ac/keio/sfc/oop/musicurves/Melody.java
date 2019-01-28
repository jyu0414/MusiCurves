package jp.ac.keio.sfc.oop.musicurves;

import javax.sound.sampled.*;
import java.util.*;

public class Melody {

    private static final float SAMPLE_RATE = 44100;
    private static final double VOLUME = 0.9;
    private ArrayList<Byte> data = new ArrayList<>();

    void addPitch(double frequency, double sec) {

        double angularVelocity = 2 * Math.PI / SAMPLE_RATE * frequency; //角速度
        for(int i = 0; i < sec * SAMPLE_RATE; i++)
        {
            data.add((byte) (128 * VOLUME * Math.sin(data.size() * angularVelocity)));
        }


    }

    AudioFormat getFormat(){
        return new AudioFormat(SAMPLE_RATE, 8, 2, true, false);
    }

    byte[] getData()
    {
        byte[] result = new byte[data.size()];

        for(int i = 0; i < data.size(); i++)
        {
            result[i] = data.get(i).byteValue();
        }
        return result;
    }

    int size()
    {
        return data.size();
    }


    //ref https://jprogramer.com/java/2272

}
