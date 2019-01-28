package jp.ac.keio.sfc.oop.musicurves;

import javax.sound.sampled.*;
import java.util.*;

public class Melody {

    private static final float SAMPLE_RATE = 44100;
    private static final double VOLUME = 0.9;
    private ArrayList<Byte> data = new ArrayList<>();
    private ArrayList<Harmonic> harmonics = new ArrayList<>();

    Melody(ArrayList<Harmonic> _harmonics){
        harmonics = _harmonics;
    }

    Melody(){
        harmonics.add(new Harmonic(1,1));
        harmonics.add(new Harmonic(2,1.0/3.0));
        harmonics.add(new Harmonic(3,1.0/5.0));
        harmonics.add(new Harmonic(4,1.0/7.0));
        harmonics.add(new Harmonic(5,1.0/9.0));
    }

    void addPitch(double frequency, double sec) {
        for(int i = 0; i < sec * SAMPLE_RATE; i++) {
            data.add(additiveSynthesis(frequency));
        }
    }

    private byte additiveSynthesis(double frequency)
    {
        double result = 0;

        for(int i = 0; i < harmonics.size(); i++) {
            double angularVelocity = 2 * Math.PI / SAMPLE_RATE * frequency * harmonics.get(i).multiple; //角速度
            result += (128 * VOLUME * harmonics.get(i).amplitude * Math.sin(data.size() * angularVelocity));
        }


        return (byte)result;
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
