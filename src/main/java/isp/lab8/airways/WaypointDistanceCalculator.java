package isp.lab8.airways;

import java.lang.Math;

/**
 * Example how to calculate distance between 2 geographical points. Reuse part of this code in your application.
 */
public class WaypointDistanceCalculator {


    // Method to calculate the distance between two waypoints using the haversine formula
    public  double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int earthRadius = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance;
    }
}

