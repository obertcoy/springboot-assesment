package com.lacak.backend.assessment.utils;

import com.lacak.backend.assessment.enums.ScoreTypeEnum;

public class GeoDistanceUtils {

    private GeoDistanceUtils() {
    }

    private static final double EARTH_RADIUS = 6371;
    private static final double MAX_DISTANCE = 5000; // For normalizing distance result

    public static double calculateHaversineDistance(double latitudeQuery, double longitudeQuery, double latitudeTarget,
            double longitudeTarget, ScoreTypeEnum scoreTypeEnum) {

        double dLatitude = Math.toRadians(latitudeTarget - latitudeQuery);
        double dLongitude = Math.toRadians(longitudeTarget - longitudeQuery);

        latitudeQuery = Math.toRadians(latitudeQuery);
        latitudeTarget = Math.toRadians(latitudeTarget);

        // Haversine formula
        double a = Math.pow(Math.sin(dLatitude / 2), 2) +
                Math.pow(Math.sin(dLongitude / 2), 2) *
                        Math.cos(latitudeQuery) *
                        Math.cos(latitudeTarget);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        double distance = EARTH_RADIUS * c;

        switch (scoreTypeEnum) {
            case ScoreTypeEnum.RAW:
                
                break;
        
            case ScoreTypeEnum.NORMALIZED:

                distance = (1 - Math.min(distance / MAX_DISTANCE, 1.0));

                break;
        }

        return distance;
    }

}
