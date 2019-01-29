package jp.ac.keio.sfc.oop.musicurves;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ToneScaleManager {
    public double referenceTonePosition;
    public double octaveDistance;

    double totalTime = 5;

    private static final double[] SCALE = {1,0.5,1,1,0.5,1,1};

    public ToneScaleManager(int _referenceTonePosition, int _octaveDistance){
        referenceTonePosition = _referenceTonePosition;
        octaveDistance = _octaveDistance;
    }

    public double getFrequency(double position){

        double graphicDistance = position - referenceTonePosition; //positionが下にある、つまり全体がプラスの時低くなる

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

    public MelodySequence getFrequencyFromLine(ArrayList<Point2D> line)
    {

        Melody mel = new Melody();
        try {
            FileWriter fw = new FileWriter("test.csv");
            for(int i = 0; i <line.size(); i++) {



                int repeat = (int) Math.round(totalTime * mel.SAMPLE_RATE / (line.get(line.size() - 1).getX() - line.get(0).getX()));
                mel.addPitch(getFrequency((int) line.get(i).getY()),repeat);


                fw.write(line.get(i).getY() + ",1\n");

                if(i + 1 >= line.size()) continue;

                for(int j = 1; line.get(i).getX() + j < line.get(i+1).getX(); j++)
                {
                    double value = getPointOnLine(line.get(i).getX(),line.get(i).getY(),line.get(i+1).getX(),line.get(i+1).getY(),line.get(i).getX() + j);
                    mel.addPitch(getFrequency((int)Math.round(value)),repeat);
                    fw.write((int)Math.round(value) + "\n");
                }


            }
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new MelodySequence(mel, (float) line.get(0).getX());
    }

    double getPointOnLine(double x1,double y1,double x2,double y2,double x){
        double a = (y1 - y2) / (x1 - x2);
        double b = y1 - a * x1;
        return (a * x + b);
    }

    public double getInterval(double _graphicDistance){
        double graphicDistance = -1 * _graphicDistance;
        double naturalNoteDistance = octaveDistance / 7; //幹音の表示上の距離
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
