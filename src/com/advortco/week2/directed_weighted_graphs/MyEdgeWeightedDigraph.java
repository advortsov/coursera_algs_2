package com.advortco.week2.directed_weighted_graphs;

import edu.princeton.cs.algs4.Bag;

/**
 * @author aldvc
 * @date 08.04.2017.
 */
public class MyEdgeWeightedDigraph {
    private final int V;
    private int E;
    private Bag<MyDirectedEdge>[] adj;


    public MyEdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<MyDirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(MyDirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<MyDirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<MyDirectedEdge> edges() {
        Bag<MyDirectedEdge> bag = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (MyDirectedEdge e : adj(v)) {
                bag.add(e);
            }
        }

        return bag;
    }
}
