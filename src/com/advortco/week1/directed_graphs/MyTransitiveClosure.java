package com.advortco.week1.directed_graphs;

import edu.princeton.cs.algs4.Digraph;

/**
 * @author advortco
 */
public class MyTransitiveClosure {
    private MyDirectedDFS[] all;

    public MyTransitiveClosure(Digraph G) {
        all = new MyDirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new MyDirectedDFS(G, v);
        }
    }

    public boolean reachable(int v, int w) {
        return all[v].visited(w);
    }

}
