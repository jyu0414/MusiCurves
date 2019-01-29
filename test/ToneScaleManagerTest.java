import jp.ac.keio.sfc.oop.musicurves.ToneScaleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToneScaleManagerTest {

    ToneScaleManager tsm;

    @BeforeEach void setup(){
        tsm = new ToneScaleManager(1000,1000);
    }

    @Test
    @DisplayName("基準点が440ヘルツ")
    void referenceToneTest() {
        assertEquals(440,tsm.getFrequency(1000));
    }

    @Test
    @DisplayName("nオクターブの周波数")
    void octaveToneTest() {
        assertAll(
                ()-> assertEquals(110,tsm.getFrequency(3000)),
                ()-> assertEquals(220,tsm.getFrequency(2000)),
                ()-> assertEquals(440,tsm.getFrequency(1000)),
                ()-> assertEquals(880,tsm.getFrequency(0)),
                ()->assertEquals(1760,tsm.getFrequency(-1000))
                );
    }

    @Test
    @DisplayName("基準点と基準点の音程")
    void referenceToneIntervalTest() {
        assertEquals(0,tsm.getInterval(0));
    }

    @Test
    @DisplayName("オクターブの音程")
    void octaveIntervalTest() {
        assertAll(
                //さらにこまかく後ほど
                ()-> assertEquals(-12,tsm.getInterval(2000)),
                ()-> assertEquals(-6,tsm.getInterval(1000)),
                ()-> assertEquals(0,tsm.getInterval(0)),
                ()-> assertEquals(6, tsm.getInterval(-1000)),
                ()-> assertEquals(12,tsm.getInterval(-2000))
        );
    }

    @Test
    @DisplayName("一音ずれの全半")
    void scaleTest() {
        assertAll(
                ()-> assertEquals(1,tsm.getScale(-7)), // A
                ()-> assertEquals(0.5,tsm.getScale(-6)), // H
                ()-> assertEquals(1,tsm.getScale(-5)), // C
                ()-> assertEquals(1,tsm.getScale(-4)), // D
                ()-> assertEquals(0.5,tsm.getScale(-3)), // E
                ()-> assertEquals(1,tsm.getScale(-2)), // F
                ()-> assertEquals(1,tsm.getScale(-1)), // G
                ()-> assertEquals(0,tsm.getScale(0)), // A
                ()-> assertEquals(1,tsm.getScale(1)), // H
                ()-> assertEquals(0.5,tsm.getScale(2)), // C
                ()-> assertEquals(1,tsm.getScale(3)), // D
                ()-> assertEquals(1,tsm.getScale(4)), // E
                ()-> assertEquals(0.5,tsm.getScale(5)), // F
                ()-> assertEquals(1,tsm.getScale(6)), // G
                ()-> assertEquals(1,tsm.getScale(7))  // A
        );
    }

    @Test
    @DisplayName("一音ずれの音程")
    void scaleIntervalTest() {
        assertAll(
                ()-> assertEquals(6,tsm.getInterval(- 1000 / 7 * 7)), //高い
                ()-> assertEquals(5,tsm.getInterval(- 1000 / 7 * 6)),
                ()-> assertEquals(4,tsm.getInterval(- 1000 / 7 * 5)),
                ()-> assertEquals(3.5,tsm.getInterval(- 1000 / 7 * 4)),
                ()-> assertEquals(2.5,tsm.getInterval(- 1000 / 7 * 3)),
                ()-> assertEquals(1.5,tsm.getInterval(- 1000 / 7 * 2)),
                ()-> assertEquals(1,tsm.getInterval(- 1000 / 7 * 1)),
                ()-> assertEquals(0,tsm.getInterval(- 1000 / 7 * 0)),
                ()-> assertEquals(-1,tsm.getInterval( 1000 / 7 * 1)),
                ()-> assertEquals(-2,tsm.getInterval(1000 / 7 * 2)),
                ()-> assertEquals(-2.5,tsm.getInterval(1000 / 7 * 3)),
                ()-> assertEquals(-3.5,tsm.getInterval(1000 / 7 * 4)),
                ()-> assertEquals(-4.5,tsm.getInterval(1000 / 7 * 5)),
                ()-> assertEquals(-5,tsm.getInterval(1000 / 7 * 6)),
                ()-> assertEquals(-6,tsm.getInterval(1000 / 7 * 7)) //低い
        );
    }
}