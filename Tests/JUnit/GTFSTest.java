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
    void verifyStopTimeHeader() {
    }

    @org.junit.jupiter.api.Test
    void verifyStopHeader() {
    }

    /**
     * @author Achuth Nair
     * This method tests the verifyTripHeader method in the GTFS class
     */
    @org.junit.jupiter.api.Test
    void verifyTripHeader() {
        String trueHeader = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
        assertTrue(gtfs.verifyTripHeader(trueHeader));

        String missingRouteId = "service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingRouteId));

        String missingServiceId = "route_id,,trip_id,trip_headsign,direction_id,block_id,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingServiceId));

        String missingTripId = "route_id,service_id,,trip_headsign,direction_id,block_id,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingTripId));

        String missingTripHeadsign = "route_id,service_id,trip_id,,direction_id,block_id,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingTripHeadsign));

        String missingDirectionId = "route_id,service_id,trip_id,trip_headsign,,block_id,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingDirectionId));

        String missingBlockId = "route_id,service_id,trip_id,trip_headsign,direction_id,,shape_id";
        assertFalse(gtfs.verifyTripHeader(missingBlockId));

        String missingShapeId = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,";
        assertFalse(gtfs.verifyTripHeader(missingShapeId));
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

    /**
     * @author Achuth Nair
     * This method tests the validateTripData method in the GTFS class
     */
    @org.junit.jupiter.api.Test
    void validateTripData() {
        String trueDataString = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23";
        String[] trueTripData = trueDataString.split(",");
        assertTrue(gtfs.validateTripData(trueTripData));

        String shortDataString = "64,17-SEP_SUN";
        String[] shortTripData = shortDataString.split(",");
        assertFalse(gtfs.validateTripData(shortTripData));

        String longDataString = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102," +
                "17-SEP_64_0_23 64,17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23";
        String[] longTripData = longDataString.split(",");
        assertFalse(gtfs.validateTripData(longTripData));

        String missingRouteId = ",17-SEP_SUN,21736564_2535,60TH-VLIET,0,64102,17-SEP_64_0_23";
        String[] missingRouteIdData = missingRouteId.split(",");
        assertFalse(gtfs.validateTripData(missingRouteIdData));

        String missingTripId = "64,17-SEP_SUN,,60TH-VLIET,0,64102,17-SEP_64_0_23";
        String[] missingTripIdData = missingTripId.split(",");
        assertFalse(gtfs.validateTripData(missingTripIdData));

        String trueDirectionId = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,1,64102,17-SEP_64_0_23";
        String[] trueDirectionIdData = trueDirectionId.split(",");
        assertTrue(gtfs.validateTripData(trueDirectionIdData));

        String trueMissingDirectionId = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,,64102,17-SEP_64_0_23";
        String[] trueMissingDirectionIdData = trueMissingDirectionId.split(",");
        assertTrue(gtfs.validateTripData(trueMissingDirectionIdData));

        String invalidDirectionId = "64,17-SEP_SUN,21736564_2535,60TH-VLIET,5,64102,17-SEP_64_0_23";
        String[] invalidDirectionIdData = invalidDirectionId.split(",");
        assertFalse(gtfs.validateTripData(invalidDirectionIdData));
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

        String missingID = ",08:51:00,08:51:00,9113,1,,0,0";
        String[] missingTripID = missingID.split(",");
        assertFalse(gtfs.validateStopTimeData(missingTripID));

        String trueArrivalTime = "21736564_2535,26:51:00,08:51:00,9113,1,,0,0";
        String[] trueStopTimeArrivalTime = trueArrivalTime.split(",");
        assertTrue(gtfs.validateStopTimeData(trueStopTimeArrivalTime));

        String invalidArrivalTimeMin = "21736564_2535,22:61:00,08:51:00,9113,1,,0,0";
        String[] invalidStopTimeArrivalTimeMin = invalidArrivalTimeMin.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeArrivalTimeMin));

        String invalidArrivalTimeSec = "21736564_2535,08:51:60,08:51:00,9113,1,,0,0";
        String[] invalidStopTimeArrivalTimeSec = invalidArrivalTimeSec.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeArrivalTimeSec));

        String invalidArrivalTimeFormat = "21736564_2535,6:51:30,08:51:00,9113,1,,0,0";
        String[] invalidStopTimeArrivalTimeFormat = invalidArrivalTimeFormat.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeArrivalTimeFormat));

        String trueDepartureTime = "21736564_2535,08:51:00,14:51:00,9113,1,,0,0";
        String[] trueStopTimeDepartureTime = trueDepartureTime.split(",");
        assertTrue(gtfs.validateStopTimeData(trueStopTimeDepartureTime));

        String invalidDepartureTimeMin = "21736564_2535,08:51:00,14:71:00,9113,1,,0,0";
        String[] invalidStopTimeDepartureTimeMin = invalidDepartureTimeMin.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeDepartureTimeMin));

        String invalidDepartureTimeSec = "21736564_2535,08:51:00,08:51:60,9113,1,,0,0";
        String[] invalidStopTimeDepartureTimeSec= invalidDepartureTimeSec.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeDepartureTimeSec));

        String invalidDepartureTimeFormat = "21736564_2535,08:51:00,6:51:30,9113,1,,0,0";
        String[] invalidStopTimeDepartureTimeFormat = invalidDepartureTimeFormat.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeDepartureTimeFormat));

        String missingStopSequence = "21736564_2535,08:51:00,08:51:00,9113,,,0,0";
        String[] missingStopTimeSequence = missingStopSequence.split(",");
        assertFalse(gtfs.validateStopTimeData(missingStopTimeSequence));

        String invalidStopSequence = "21736564_2535,08:51:00,08:51:00,9113,-50,,0,0";
        String[] invalidStopTimeSequence = invalidStopSequence.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeSequence));

        String validStopSequence = "21736564_2535,08:51:00,08:51:00,9113,500,,0,0";
        String[] validStopTimeSequence = validStopSequence.split(",");
        assertTrue(gtfs.validateStopTimeData(validStopTimeSequence));

        String validPickUpType = "21736564_2535,08:51:00,08:51:00,9113,500,,3,0";
        String[] validStopTimePickUpType = validPickUpType.split(",");
        assertTrue(gtfs.validateStopTimeData(validStopTimePickUpType));

        String invalidPickUpType = "21736564_2535,08:51:00,08:51:00,9113,500,,20,0";
        String[] invalidStopTimePickUpType = invalidPickUpType.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimePickUpType));

        String validDropOffType = "21736564_2535,08:51:00,08:51:00,9113,500,,0,2";
        String[] validStopTimeDropOffType = validDropOffType.split(",");
        assertTrue(gtfs.validateStopTimeData(validStopTimeDropOffType));

        String invalidDropOffType = "21736564_2535,08:51:00,08:51:00,9113,500,,0,50";
        String[] invalidStopTimeDropOffType = invalidDropOffType.split(",");
        assertFalse(gtfs.validateStopTimeData(invalidStopTimeDropOffType));
    }
}