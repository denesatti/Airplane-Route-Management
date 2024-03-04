package isp.lab8.airways;

import com.fasterxml.jackson.databind.ObjectMapper;
import examples.files.FileReadUtil;
import examples.serializableJson.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RouteManagementSystem {
    //checks if /routes directory exists, if not it creates one
    private void mainFileHandler(){
        File routes = new File("src/main/java/isp/lab8/airways/routes");
        if(!routes.exists()){
            routes.mkdir();
        }
    }

    //checks if a route with a specific name exists, it returns true if it does, else false
    private boolean doesRouteExists(String route){
        boolean doesExist = false;
        File db = new File("src/main/java/isp/lab8/airways/routes");
        File[] routes = db.listFiles();
        for(File r: routes){
            if(r.getName().equals(route)){
                doesExist = true;
            }
        }

        return doesExist;
    }

    //ads a route
    public void addRoute(String route){
        this.mainFileHandler();
        if(!doesRouteExists(route)){
            File newRoute = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            newRoute.mkdir();
            System.out.println("The route: " + route + " was created.");
        }else{
            System.out.println("The route: " + route + " already exists.");
        }
    }

    //deletes a route
    public void deleteRoute(String route){
        this.mainFileHandler();
        if(doesRouteExists(route)){
            File newRoute = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            newRoute.delete();
            System.out.println("The route: " + route + " was deletes.");
        }else{
            System.out.println("The route: " + route + " does not exists.");
        }
    }


    //adds a waypoint to the end of a route
    public void addWaypoint(String route,String name, double latitude, double longitude, int altitude) throws IOException {
        if(doesRouteExists(route)){
            File r = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            File[] waypoints = r.listFiles();
            String[] sTemp = name.split("\"",-2);
            int index = waypoints.length + 1;
            ObjectMapper om = new ObjectMapper();

            Waypoint wp = new Waypoint(index,name,latitude,longitude,altitude);
            om.writeValue(new FileWriter(r + "/" + sTemp[1] + ".json"), wp);

            System.out.println("The waypoint: " + wp.getName() + " was succesfully created.");

        }else{
            System.out.println("The route does not exists.");
        }
    }

    //adds a waypoint in the route on place of a specific index
    public void addWaypoint(String route,int index, String name, double latitude, double longitude, int altitude) throws IOException {
        if(doesRouteExists(route)){
            File r = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            File[] waypoints = r.listFiles();
            String[] sTemp = name.split("\"",-2);
            Waypoint[] temp = new Waypoint[waypoints.length];
            int i = 0;
            for(File f:waypoints) {
                ObjectMapper om = new ObjectMapper();
                String jsonContent = FileReadUtil.readAllFileLines(f.getPath()).stream().collect(Collectors.joining("\n"));
                temp[i] = om.readValue(jsonContent,Waypoint.class);
                i++;
            }

            for(Waypoint k: temp){
                if(k.getIndex() >= index){
                    k.setIndex(k.getIndex() + 1);
                }
            }

            for(File f: waypoints){
                f.delete();
            }

            for (Waypoint k: temp){
                ObjectMapper om = new ObjectMapper();
                sTemp =k.getName().split("\"",-2);
                om.writeValue(new FileWriter(r + "/" + sTemp[1] + ".json"), k);
            }

            ObjectMapper om = new ObjectMapper();
            Waypoint wp = new Waypoint(index,name,latitude,longitude,altitude);
            sTemp = wp.getName().split("\"", -2);
            om.writeValue(new FileWriter(r + "/" + sTemp[1] + ".json"), wp);

            System.out.println("The waypoint: " + wp.getName() + " was succesfully created.");

        }else{
            System.out.println("The route does not exists.");
        }
    }

    public void displayRoutes(){
        File r = new File("src/main/java/isp/lab8/airways/routes");
        File[] routes = r.listFiles();

        for(File route: routes){
            System.out.println(route.getName());
        }
    }
    public void displayRoute(String route) throws IOException {
        System.out.println("The route: " + route + " has a total distance of:" + this.calculateTotalDistance(route) + ".");
        this.displayWaypoints(route);
    }

    public void displayWaypoints(String route) throws IOException {
        if(doesRouteExists(route)){
            File r = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            File[] waypoints = r.listFiles();
            for(File f: waypoints) {
                ObjectMapper om = new ObjectMapper();
                String jsonContent = FileReadUtil.readAllFileLines(f.getPath()).stream().collect(Collectors.joining("\n"));
                Waypoint wp = om.readValue(jsonContent,Waypoint.class);

                System.out.println(wp.getName() +": "+ wp.getLatitude() + "° N, "+ wp.getLongitude() + "° E, " + wp.getAltitude()+ " meters altitude.");
            }

        }else{
            System.out.println("The route does not exists.");
        }
    }


    public double calculateTotalDistance(String route) throws IOException {
        double distance = -1;
        if(doesRouteExists(route)){
            File r = new File("src/main/java/isp/lab8/airways/routes/"+ route);
            File[] waypoints = r.listFiles();
            Waypoint[] temp = new Waypoint[waypoints.length];
            int i = 0;
            for(File f:waypoints) {
                ObjectMapper om = new ObjectMapper();
                String jsonContent = FileReadUtil.readAllFileLines(f.getPath()).stream().collect(Collectors.joining("\n"));
                temp[i] = om.readValue(jsonContent,Waypoint.class);
                i++;
            }


            WaypointDistanceCalculator calc = new WaypointDistanceCalculator();
            //System.out.println(temp.length);

            Arrays.sort(temp);
            //System.out.println(temp.length);
            for(int j = 0; j < temp.length - 1; j++){
                distance += calc.calculateDistance(temp[j].getLatitude(),temp[j].getLongitude(),temp[j+1].getLatitude(),temp[j+1].getLongitude());
            }

        }else{
            System.out.println("The route does not exists.");
        }

        return distance;
    }


}
