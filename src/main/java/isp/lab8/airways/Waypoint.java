package isp.lab8.airways;

/**
 * Example waypoint class which can be extended to be used in implementation of the exercise. Add constructor, getters, setters, etc.
 */
public class Waypoint implements Comparable<Waypoint>{
    private int index;
    private String name;
    private double latitude;
    private double longitude;
    private int altitude;

    public Waypoint(int index, String name, double latitude, double longitude, int altitude) {
        this.index = index;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    public Waypoint( String name, double latitude, double longitude, int altitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    public Waypoint() {}

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(Waypoint o) {
        int com = ((Waypoint)o).getIndex();
        return this.index - com;
    }
}
