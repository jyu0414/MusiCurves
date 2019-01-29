package jp.ac.keio.sfc.oop.musicurves;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class MelodySequence {
    Melody melody;
    double startOffsetTime; //offsetsec

    MelodySequence(Melody _melody, double _startPosition)
    {
        melody = _melody;
        startOffsetTime = _startPosition;
    }

    MelodySequence(Melody _melody)
    {
        melody = _melody;
        startOffsetTime = 0;
    }

    int getLength()
    {
        return (int)(startOffsetTime * melody.SAMPLE_RATE) + melody.getSize();
    }


    static byte[] mix(ArrayList<MelodySequence> sequences)
    {
        int length = 0;
        ArrayList<byte[]> data = new ArrayList<>();

        for (MelodySequence sequence: sequences) {
            int len = sequence.getLength();
            if(length < len)
            {
                length = len;
            }
            byte[] space = new byte[(int)(sequence.startOffsetTime * sequence.melody.SAMPLE_RATE)];
            Arrays.fill(space, (byte) 0);
            byte[] melodyData = sequence.melody.getData();
            ByteBuffer bb = ByteBuffer.allocate(space.length+melodyData.length);
            bb.put(space);
            bb.put(melodyData);
            data.add(bb.array());
        }

        byte[] result = new byte[length];

        for(int i = 0; i < length; i++)
        {
            result[i] = 0;
            for(byte[] d: data)
            {
                if(i < d.length)
                {
                    result[i] += d[i];
                }

            }
            result[i] /= data.size();

            if(result[i] > 120)
            {
                result[i] = 120;
            }
        }

        return result;

    }
    

}
