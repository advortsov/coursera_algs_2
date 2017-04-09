package com.advortco.week2.directed_weighted_graphs;

import com.advortco.week2.weighted_graphs.MyEdge;

/**
 * @author aldvc
 * @date 08.04.2017.
 */
public class MyDijkstraAllPairsSP {

    private MyDijkstraSP[] all;

    public MyDijkstraAllPairsSP(MyEdgeWeightedDigraph G) {
        all = new MyDijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new MyDijkstraSP(G, v);
        }
    }

    public Iterable<MyEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }

    public double dist(int s, int t) {
        return all[s].distTo(t);
    }
}
