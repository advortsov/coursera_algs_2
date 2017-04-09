package com.advortco.week2.weighted_graphs;

import edu.princeton.cs.algs4.Bag;

/**
 * @author aldvc
 * @date 02.04.2017.
 */
public class MyEdgeWeightedGraph {
    private final int V;
    private final int E;
    private Bag<MyEdge>[] adj;

    public MyEdgeWeightedGraph(int V) {
        this.V = V;
        E = 0;
        this.adj = (Bag<MyEdge>[]) new Bag[V];
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

    public void addEdge(MyEdge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<MyEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<MyEdge> edges() {
        Bag<MyEdge> b = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (MyEdge e : adj[v]) {
                if (e.other(v) > v) b.add(e);
            }
        }
        return b;
    }
}
