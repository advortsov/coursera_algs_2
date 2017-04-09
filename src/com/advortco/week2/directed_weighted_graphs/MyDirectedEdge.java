package com.advortco.week2.directed_weighted_graphs;

/**
 * @author aldvc
 * @date 08.04.2017.
 */
public class MyDirectedEdge {
    private final int v;
    private final int w;
    private final double weight;


    public MyDirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }
}
