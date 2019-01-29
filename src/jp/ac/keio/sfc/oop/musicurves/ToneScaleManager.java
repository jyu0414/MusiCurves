package jp.ac.keio.sfc.oop.musicurves;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ToneScaleManager {
    public int referenceTonePosition;
    public int octaveDistance;

    private static final double[] SCALE = {1,0.5,1,1,0.5,1,1};

    ToneScaleManager(int _referenceTonePosition, int _octaveDistance){
        referenceTonePosition = _referenceTonePosition;
        octaveDistance = _octaveDistance;
    }

    double getFrequency(int position){

        int graphicDistance = referenceTonePosition - position; //画面上の距離
        int toneDistance = graphicDistance / (octaveDistance / 7); //音階上の距離
        float toneDistancePlus = graphicDistance % (octaveDistance / 7); //音階上の距離のあまり

        double cent = 0; //何音分か
        for(int i = 1; i <= Math.abs(toneDistance); i++)
        {
            if(toneDistance < 0)
            {
                cent -= getScale(toneDistance - 1);
            }
            else {
                cent += getScale(toneDistance - 1);
            }

        }
        cent += getScale(toneDistance) * (toneDistancePlus / (octaveDistance / 7.0));

        if(cent == 0)
        {
            return 440;
        }
        else if(cent > 0)
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



    double getScale(int n){
        int number = n % SCALE.length;
        if(number < 0) number += 7;
        return SCALE[number];
    }

    public double[][] getfrequencyFromLine(ArrayList<Point2D> line)
    {

        double[][] freqLine = new double[line.size()][2];

        for(int i = 0; i <line.size(); i++) {
            freqLine[i][0] = getFrequency((int)line.get(i).getY());
            freqLine[i][1] = line.get(i).getX();
        }

        return freqLine;
    }

}
