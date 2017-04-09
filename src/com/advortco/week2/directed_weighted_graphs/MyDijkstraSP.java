package com.advortco.week2.directed_weighted_graphs;

import com.advortco.week2.weighted_graphs.MyEdge;
import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * p. 591
 *
 * @author aldvc
 * @date 08.04.2017.
 */
public class MyDijkstraSP {

    private MyDirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public MyDijkstraSP(MyEdgeWeightedDigraph G, int s) {
        edgeTo = new MyDirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        pq.insert(s, 0.0);

        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
    }


    // relaxing of all adj edges
    private void relax(MyEdgeWeightedDigraph G, int v) {
        for (MyDirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }

            if (pq.contains(w)) {
                pq.change(w, distTo[w]);
            } else {
                pq.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        return edgeTo[v] != null;
    }


    public Iterable<MyEdge> pathTo(int t) {
        return null;
    }
}
