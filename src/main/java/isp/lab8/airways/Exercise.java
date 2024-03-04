package isp.lab8.airways;

import java.io.File;
import java.io.IOException;


public class Exercise {

    public static void main(String[] args) throws IOException {
        RouteManagementSystem rms = new RouteManagementSystem();
        rms.addRoute("LRCL-LROP");
        rms.addWaypoint("LRCL-LROP","\"LRCL\" (Cluj-Napoca International Airport)",46.7852,23.6862,415 );
        //here we could add more waypoints
        rms.addWaypoint("LRCL-LROP","\"LROP\" (Henri Coandă International Airport)",44.5711,26.0858,106);

        rms.displayRoutes();
        rms.displayRoute("LRCL-LROP");
    }
}
