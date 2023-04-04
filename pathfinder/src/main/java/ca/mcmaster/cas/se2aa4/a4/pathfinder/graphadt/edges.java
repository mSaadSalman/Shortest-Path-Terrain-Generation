package ca.mcmaster.cas.se2aa4.a4.pathfinder.graphadt;

import java.util.ArrayList;

public class edges {

    private node source_node;
    private node target_node;
    private int weight;


    public edges(node source_node, node target_node, int weight){
        this.source_node = source_node;
        this.target_node = target_node;
        this.weight = weight;
    }

    public node getTarget_node(){
        return target_node;
    }

    public node getSource_node(){
        return source_node;
    }

    public int get_weight(){
        return weight;
    }






}
