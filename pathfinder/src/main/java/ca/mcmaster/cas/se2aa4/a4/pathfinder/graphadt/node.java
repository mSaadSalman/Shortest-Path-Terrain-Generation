package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayList;
import java.util.List;


public class node {
    private String city_name;
    private double elev;

    private ArrayList<edges> edge;


    public node (String city_name, double elev){
        this.city_name = city_name;
        this.elev = elev;
    }

    public String getCity_name(){
        return city_name;
    }




}