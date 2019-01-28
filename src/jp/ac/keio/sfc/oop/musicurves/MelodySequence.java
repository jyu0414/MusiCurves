package jp.ac.keio.sfc.oop.musicurves;

public class MelodySequence {
    Melody melody;
    float startPosition; //sec

    MelodySequence(Melody _melody, float _startPosition)
    {
        melody = _melody;
        startPosition = _startPosition;
    }

    MelodySequence(Melody _melody)
    {
        melody = _melody;
        startPosition = 0;
    }


}
