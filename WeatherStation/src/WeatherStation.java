public class WeatherStation {

    private int id;

//this class is used to form the proper list of weather staions

    //GPS positioning
    private double longitude;  // -180 -> 180
    private double latitude;  // -90 -> 90

    //specific data related to each of the connected weather stations
    private double temperature; // celsius degrees
    private double barometric_pressure; // mbar
    private double relative_humidity;  // %
    private double wind_force; //  km/h

    //constructors
    public WeatherStation(){


    }
    public WeatherStation(int id, double longitude, double latitude, double temperature, double barometric_pressure, double relative_humidity, double wind_force) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.barometric_pressure = barometric_pressure;
        this.relative_humidity = relative_humidity;
        this.wind_force = wind_force;
    }

    //setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getBarometric_pressure() {
        return barometric_pressure;
    }

    public void setBarometric_pressure(double barometric_pressure) {
        this.barometric_pressure = barometric_pressure;
    }

    public double getRelative_humidity() {
        return relative_humidity;
    }

    public void setRelative_humidity(double relative_humidity) {
        this.relative_humidity = relative_humidity;
    }

    public double getWind_force() {
        return wind_force;
    }

    public void setWind_force(double wind_force) {
        this.wind_force = wind_force;
    }
}
