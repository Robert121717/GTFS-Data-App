package JUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GTFSTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void verifyRouteHeader() {
    }

    @org.junit.jupiter.api.Test
    void verifyStopHeader() {
    }

    @org.junit.jupiter.api.Test
    void verifyTripHeader() {
    }

    @org.junit.jupiter.api.Test
    void verifyStopTimeHeader() {
    }

    @org.junit.jupiter.api.Test
    void validateRouteData() {

        String trueDataString = "12,MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[] trueRouteDataTest = trueDataString.split(",");
        assertTrue("Data was true",validateRouteData(trueRouteDataTest));


        String shortDataString = "12,MCTS,12,";
        String[]  falseShortRouteDataTest = shortDataString.split(",");
        assertFalse(validateRouteData(falseShortRouteDataTest));

        String longDataString = "12,MCTS,12,Teutonia-Hampton,,3,,008345,12,MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[]  falseLongRouteDataTest = longDataString.split(",");
        assertFalse(validateRouteData(falseLongRouteDataTest));

        String missingRouteTypeDataString = ",MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[]  missingRouteTypeDataTest = missingRouteTypeDataString.split(",");
        assertFalse(validateRouteData(missingRouteTypeDataTest));


        String missingRouteColorDataString = "12,MCTS,12,Teutonia-Hampton,,3,,,";
        String[]  missingRouteColorDataTest = missingRouteColorDataString.split(",");
        assertFalse(validateRouteData(missingRouteColorDataTest););

        String invalidRouteColorDataString = "12,MCTS,12,Teutonia-Hampton,,3,,ZZZZZZ,";
        String[]  invalidRouteDataTest = invalidRouteColorDataString.split(",");
        assertFalse(validateRouteData(invalidRouteDataTest));

        String validRouteURLDataString = "12,MCTS,12,Teutonia-Hampton,,3,http://www.w3.org/albert/bertram/marie-claude,ZZZZZZ,";
        String[]  validRouteURLDataTest = validRouteURLDataString.split(",");
        assertTrue(validateRouteData(validRouteURLDataTest));

        String invalidRouteURLDataString = "12,MCTS,12,Teutonia-Hampton,,3,fxqn:/us/va/reston/cnri/ietf/24/asdf%*.fred,ZZZZZZ,";
        String[]  invalidRouteURLDataTest = invalidRouteURLDataString.split(",");
        assertTrue(validateRouteData(invalidRouteURLDataTest););
    }

    @org.junit.jupiter.api.Test
    void validateStopData() {
    }

    @org.junit.jupiter.api.Test
    void validateTripData() {
    }

    @org.junit.jupiter.api.Test
    void validateStopTimeData() {
    }
}