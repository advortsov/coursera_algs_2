package com.advortco.week2.weighted_graphs;

/**
 * @author aldvc
 * @date 02.04.2017.
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        return vertex == v ? w : v;
    }


    @Override
    public int compareTo(Edge anotherEdge) {
        if (this.weight > anotherEdge.weight) return 1;
        else if (this.weight < anotherEdge.weight) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
