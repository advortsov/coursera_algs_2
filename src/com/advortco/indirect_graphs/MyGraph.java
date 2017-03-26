package com.advortco.indirect_graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DepthFirstPaths;

/**
 * DFS - deep-first search
 *
 */
public class MyGraph {
    private final int V; // vertices (вершины)
    private Bag<Integer>[] adj;

    public MyGraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
//        edu.princeton.cs.algs4.Graph
//        DepthFirstPaths
//        BreadthFirstPaths
        return adj[v];

    }
}
