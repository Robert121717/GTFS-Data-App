package JUnit;

import GTFS.GTFS;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GTFSTest {

    private GTFS gtfs;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        gtfs = new GTFS();
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

    /**
     * @author Achuth Nair
     * This method tests the validateRouteData method in the GTFS class
     */
    @org.junit.jupiter.api.Test
    void validateRouteData() {

        String trueDataString = "12,MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[] trueRouteDataTest = trueDataString.split(",");
        assertTrue(gtfs.validateRouteData(trueRouteDataTest));


        String shortDataString = "12,MCTS,12,";
        String[]  falseShortRouteDataTest = shortDataString.split(",");
        assertFalse(gtfs.validateRouteData(falseShortRouteDataTest));

        String longDataString = "12,MCTS,12,Teutonia-Hampton,,3,,008345,12,MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[]  falseLongRouteDataTest = longDataString.split(",");
        assertFalse(gtfs.validateRouteData(falseLongRouteDataTest));

        String missingRouteTypeDataString = ",MCTS,12,Teutonia-Hampton,,3,,008345,";
        String[]  missingRouteTypeDataTest = missingRouteTypeDataString.split(",");
        assertFalse(gtfs.validateRouteData(missingRouteTypeDataTest));


        String missingRouteColorDataString = "12,MCTS,12,Teutonia-Hampton,,3,,,";
        String[]  missingRouteColorDataTest = missingRouteColorDataString.split(",");
        assertFalse(gtfs.validateRouteData(missingRouteColorDataTest));

        String invalidRouteColorDataString = "12,MCTS,12,Teutonia-Hampton,,3,,ZZZZZZ,";
        String[]  invalidRouteDataTest = invalidRouteColorDataString.split(",");
        assertFalse(gtfs.validateRouteData(invalidRouteDataTest));

        String invalidRouteTypeDataString = "12,MCTS,12,Teutonia-Hampton,,23,,008345,";
        String[] invalidRouteTypeDataTest = invalidRouteTypeDataString.split(",");
        assertFalse(gtfs.validateRouteData(invalidRouteTypeDataTest));

        String validRouteTextColor = "12,MCTS,12,Teutonia-Hampton,,3,,008345,FFFFFF";
        String[] validRouteTextColorDataTest = validRouteTextColor.split(",");
        assertTrue(gtfs.validateRouteData(validRouteTextColorDataTest));

        String invalidRouteTextColor = "12,MCTS,12,Teutonia-Hampton,,3,,008345,ZZZZZZ";
        String[] invalidRouteTextColorDataTest = invalidRouteTextColor.split(",");
        assertFalse(gtfs.validateRouteData(invalidRouteTextColorDataTest));


        String validRouteURLDataString = "12,MCTS,12,Teutonia-Hampton,,3,http://www.w3.org/albert/bertram/marie-claude,008345,";
        String[]  validRouteURLDataTest = validRouteURLDataString.split(",");
        assertTrue(gtfs.validateRouteData(validRouteURLDataTest));

        String invalidRouteURLDataString = "12,MCTS,12,Teutonia-Hampton,,3,fxqn:/us/va/reston/cnri/ietf/24/asdf%*.fred,008345,";
        String[]  invalidRouteURLDataTest = invalidRouteURLDataString.split(",");
        assertFalse(gtfs.validateRouteData(invalidRouteURLDataTest));
    }
    /**
     * @author Ryan Atkinson
     * This method tests the validateStopData method in the GTFS class
     */
    @org.junit.jupiter.api.Test
    void validateStopData() {
        String trueDataString = "6712,STATE & 5101 #6712,,43.0444475,-87.9779369";
        String[] trueStopDataTest = trueDataString.split(",");
        assertTrue(gtfs.validateStopData(trueStopDataTest));

        String shortDataString = "6712,STATE & 5101 #6712";
        String[] falseShortStopDataTest = shortDataString.split(",");
        assertFalse(gtfs.validateStopData(falseShortStopDataTest));

        String longDataString = "6712,STATE & 5101 #6712,,43.0444475,-87.9779369,33,44,55";
        String[] falseLongStopDataTest = longDataString.split(",");
        assertFalse(gtfs.validateStopData(falseLongStopDataTest));

        String missingStopID = ",STATE & 5101 #6712,,43.0444475,-87.9779369";
        String[] missingStopIDTest = missingStopID.split(",");
        assertFalse(gtfs.validateStopData(missingStopIDTest));

        String missingLat = ",STATE & 5101 #6712,,,-87.9779369";
        String[] missingStopLat = missingLat.split(",");
        assertFalse(gtfs.validateStopData(missingStopLat));

        String missingLon = ",STATE & 5101 #6712,,43.0444475,,";
        String[] missingStopLon = missingLon.split(",");
        assertFalse(gtfs.validateStopData(missingStopLon));

        String invalidLargeLat = ",STATE & 5101 #6712,,90.111,-87.9779369";
        String[] invalidStopLargeLat = invalidLargeLat.split(",");
        assertFalse(gtfs.validateStopData(invalidStopLargeLat));

        String invalidSmallLat = ",STATE & 5101 #6712,,-90.111,-87.9779369";
        String[] invalidStopSmallLat = invalidSmallLat.split(",");
        assertFalse(gtfs.validateStopData(invalidStopSmallLat));

        String invalidLargeLon = ",STATE & 5101 #6712,,43.0444475,181.2737";
        String[] invalidStopLargeLon = invalidLargeLon.split(",");
        assertFalse(gtfs.validateStopData(invalidStopLargeLon));

        String invalidSmallLon = ",STATE & 5101 #6712,,43.0444475,-181.2737";
        String[] invalidStopSmallLon = invalidSmallLon.split(",");
        assertFalse(gtfs.validateStopData(invalidStopSmallLon));


    }

    @org.junit.jupiter.api.Test
    void validateTripData() {

    }

    /**
     * @author Achuth Nair and Ryan Atkinson
     * This method tests the validateStopTimeData method in the GTFS class
     */
    @org.junit.jupiter.api.Test
    void validateStopTimeData() {
        String trueDataString = "21736564_2535,08:51:00,08:51:00,9113,1,,0,0";
        String[] trueStopTimeDataTest = trueDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(trueStopTimeDataTest));

        String shortDataString = "21736564_2535,08:51:00";
        String[] falseShortStopTimeDataTest = shortDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(falseShortStopTimeDataTest));

        String longDataString = "21736564_2535,08:51:00,08:51:00,9113,1,,0,021736564_2535,08:51:00,08:51:00,9113,1,,0,0";
        String[] falseLongStopTimeDataTest = longDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(falseLongStopTimeDataTest));

        String missingStopIDDataString = "21736564_2535,08:51:00,08:51:00,,1,,0,0";
        String[] missingStopIDDataTest = missingStopIDDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(missingStopIDDataTest));

        String missingTripIDDataString = ",08:51:00,08:51:00,9113,1,,0,0";
        String[] missingTripIDDataTest = missingTripIDDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(missingTripIDDataTest));

        String trueArrivalTimeDataString = "21736564_2535,26:51:00,08:51:00,9113,1,,0,0";
        String[] trueArrivalTimeDataTest = trueArrivalTimeDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(trueArrivalTimeDataTest));

        String invalidArrivalTimeMinDataString = "21736564_2535,22:61:00,08:51:00,9113,1,,0,0";
        String[] invalidArrivalTimeMinDataTest = invalidArrivalTimeMinDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidArrivalTimeMinDataTest));

        String invalidArrivalTimeSecDataString = "21736564_2535,08:51:60,08:51:00,9113,1,,0,0";
        String[] invalidArrivalTimeSecDataTest = invalidArrivalTimeSecDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidArrivalTimeSecDataTest));

        String invalidArrivalTimeFormatDataString = "21736564_2535,6:51:30,08:51:00,9113,1,,0,0";
        String[] invalidArrivalTimeFormatDataTest = invalidArrivalTimeFormatDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidArrivalTimeFormatDataTest));

        String trueDepartureTimeDataString = "21736564_2535,08:51:00,14:51:00,9113,1,,0,0";
        String[] trueDepartureTimeDataTest = trueDepartureTimeDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(trueDepartureTimeDataTest));

        String invalidDepartureTimeMinDataString = "21736564_2535,08:51:00,14:71:00,9113,1,,0,0";
        String[] invalidDepartureTimeMinDataTest = invalidDepartureTimeMinDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidDepartureTimeMinDataTest));

        String invalidDepartureTimeSecDataString = "21736564_2535,08:51:00,08:51:60,9113,1,,0,0";
        String[] invalidDepartureTimeSecDataTest = invalidDepartureTimeSecDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidDepartureTimeSecDataTest));

        String invalidDepartureTimeFormatDataString = "21736564_2535,08:51:00,6:51:30,9113,1,,0,0";
        String[] invalidDepartureTimeFormatDataTest = invalidDepartureTimeFormatDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidDepartureTimeFormatDataTest));

        String missingStopSequenceDataString = "21736564_2535,08:51:00,08:51:00,9113,,,0,0";
        String[] missingStopSequenceDataTest = missingStopSequenceDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(missingStopSequenceDataTest));

        String invalidStopSequenceDataString = "21736564_2535,08:51:00,08:51:00,9113,-50,,0,0";
        String[] invalidStopSequenceDataTest = invalidStopSequenceDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopSequenceDataTest));

        String validStopSequenceDataString = "21736564_2535,08:51:00,08:51:00,9113,500,,0,0";
        String[] validStopSequenceDataTest = validStopSequenceDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(validStopSequenceDataTest));

        String validPickUpTypeDataString = "21736564_2535,08:51:00,08:51:00,9113,500,,3,0";
        String[] validPickUpTypeDataTest = validPickUpTypeDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(validPickUpTypeDataTest));

        String invalidPickUpTypeDataString = "21736564_2535,08:51:00,08:51:00,9113,500,,20,0";
        String[] invalidPickUpTypeDataTest = invalidPickUpTypeDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidPickUpTypeDataTest));

        String validDropOffTypeDataString = "21736564_2535,08:51:00,08:51:00,9113,500,,0,2";
        String[] validDropOffTypeDataTest = validDropOffTypeDataString.split(",");
        assertTrue(gtfs.validateStopTimeData(validDropOffTypeDataTest));

        String invalidDropOffTypeDataString = "21736564_2535,08:51:00,08:51:00,9113,500,,0,50";
        String[] invalidDropOffTypeDataTest = invalidDropOffTypeDataString.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidDropOffTypeDataTest));
    }
}