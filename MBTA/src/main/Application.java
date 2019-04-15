package main;

import java.util.ArrayList;
import java.util.List;
import json.StopObject;

/**
 *
 * @author Eddy
 */
public class Application {
    
    //Store device's location: latitude, longitude
    public static List<String> location = new ArrayList();
    
    //Store nearby stops's full name and id
    public static List<StopObject> nearbyStops = new ArrayList<StopObject>();
    
    //Store special stop's full name and id
    public static List<StopObject> specialStops = new ArrayList<StopObject>();
    
    public static String[] colors = {"red", "red", "orange", "green", "green", "green", "green", "blue"};
    
    public static String[] icons = {"red", "mattapan", "orange", "green", "green", "green", "green", "blue"};
    
    //The table names in Mysql Database (Server)
    public static String[] tables = {"red", "mattapan", "orange", "greenb", "greenc", "greend", "greene", "blue"};
    
    //Train full name
    public static String[] names = {"Red Line", "Mattapan Trolley", "Orange Line", 
            "Green Line B", "Green Line C", "Green Line D", "Green Line E", "Blue Line"};
    
    //Train direction
    public static String[] directions = {"ALEWIFE - BRAINTREE", "ASHMONT - MATTAPAN", "OAK GROVE - FORSET HILLS", 
            "Park St - BOSTON COLLEGE", "NORTH STATION - CLEVELAND CIRCLE", "PARK St - RIVERSIDE",
            "LECHMERE - HEATH", "BOWDOIN - WONDERLAND"};
    
    //Train oppse direction
    public static String[] oppose_directions = {"BRAINTREE - ALEWIFE", "MATTAPAN - ASHMONT", "FORSET HILLS - OAK GROVE", 
            "BOSTON COLLEGE - Park St", "CLEVELAND CIRCLE - NORTH STATION", "RIVERSIDE - PARK St",
            "HEATH - LECHMERE", "WONDERLAND - BOWDOIN"};
    
    //Google Location API URL Request
    public static String googleAPI = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCEtfo79T22AAqtESXOzI6tDJIcsiWHMAI";
    
    //MBTA V3 API URL Request
    public static String nearby_start = "https://api-v3.mbta.com/stops?include=parent_station&filter[route_type]=0,1&filter[latitude]=";
    
    public static String nearby_end = "&filter[radius]=0.02";
    
    public static String prediction_start = "https://api-v3.mbta.com/predictions?filter[route]=Blue,Orange,Red,Mattapan,Green-B,Green-C,Green-D,Green-E&filter[stop]=";

    public static String schedule_start = "https://api-v3.mbta.com/schedules?filter[route]=Blue,Orange,Red,Mattapan,Green-B,Green-C,Green-D,Green-E&filter[date]=";
    
    public static String schedule_min_time = "&filter[min_time]=";
    
    public static String schedule_max_time = "&filter[max_time]=";
    
    public static String schedule_stop = "&filter[stop]=";
    
    public static String schedule_direction = "&filter[direction_id]=";
    
    public static String alert_url = "https://api-v3.mbta.com/alerts?filter[route_type]=0,1&filter[datetime]=NOW";
}
