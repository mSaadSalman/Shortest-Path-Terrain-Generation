package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayList;
import java.util.List;


public class node {
    private String city_name;
    private double elev;
    private int node_num;

    private ArrayList<edges> edge;


    public node (String city_name, double elev, int node_num){
        this.city_name = city_name;
        this.elev = elev;
        this.node_num =node_num;
    }

    public int getNode_num(){
        return node_num;
    }



    public String getCity_name(){
        return city_name;
    }




}