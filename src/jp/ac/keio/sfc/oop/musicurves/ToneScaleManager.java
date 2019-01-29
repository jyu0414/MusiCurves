package jp.ac.keio.sfc.oop.musicurves;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ToneScaleManager {
    public int referenceTonePosition;
    public int octaveDistance;

    private static final double[] SCALE = {1,0.5,1,1,0.5,1,1};

    public ToneScaleManager(int _referenceTonePosition, int _octaveDistance){
        referenceTonePosition = _referenceTonePosition;
        octaveDistance = _octaveDistance;
    }

    public double getFrequency(int position){

        int graphicDistance = position - referenceTonePosition; //positionが下にある、つまり全体がプラスの時低くなる

        double cent = getInterval(graphicDistance); //プラスが高い

        if(cent >= 0)
        {

            double result = 440 * Math.pow(2,(int)(cent / 7));
            double remain = ((cent - (int)(cent / 7)) / 7);
            result *= Math.pow(2.0, remain);

            return result;

        }
        else {
            cent = Math.abs(cent);
            double result = 440 / Math.pow(2,(int)(cent / 7));
            double remain = ((cent - (int)(cent / 7)) / 7);
            result /= Math.pow(2.0, remain);
            return result;
        }


    }



    public double getScale(int n){
        if(n == 0) return 0;
        int number = n % SCALE.length;
        if(number < 0) number += 7;
        else if (number > 0) number -= 1;
        else number = 6;
        return SCALE[number];
    }

    public double[][] getFrequencyFromLine(ArrayList<Point2D> line)
    {

        double[][] freqLine = new double[line.size()][2];

        for(int i = 0; i <line.size(); i++) {
            freqLine[i][0] = getFrequency((int)line.get(i).getY());
            freqLine[i][1] = line.get(i).getX();
        }

        return freqLine;
    }

    public double getInterval(int _graphicDistance){
        int graphicDistance = -1 * _graphicDistance;
        int naturalNoteDistance = octaveDistance / 7; //幹音の表示上の距離
        Boolean isUpper = graphicDistance >= 0;

        int i = 0;
        double interval = 0;

        while (naturalNoteDistance * i <= Math.abs(graphicDistance))
        {
            if(isUpper)
            {
                interval += getScale(i);
            }
            else {
                interval -= getScale(-i);
            }

            i++;
        }

        if(isUpper)
        {
            interval +=  getScale(i) * (graphicDistance - naturalNoteDistance * (i - 1)) / naturalNoteDistance;
        }
        else
        {
            interval += getScale(i) * (graphicDistance + naturalNoteDistance * (i - 1)) / naturalNoteDistance ;
        }

        return interval;

    }

}
