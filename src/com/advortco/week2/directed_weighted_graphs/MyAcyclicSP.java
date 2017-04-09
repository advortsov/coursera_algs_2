package com.advortco.week2.directed_weighted_graphs;

import com.advortco.week2.weighted_graphs.MyEdge;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;

/**
 * p.596
 *
 * @author aldvc
 * @date 08.04.2017.
 */
public class MyAcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public MyAcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0;
        Topological topological = new Topological(G);


        for (int v : topological.order()) {
            relax(G, v);
        }


    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }


    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return edgeTo[v] != null;
    }


    public Iterable<MyEdge> pathTo(int t) {
        return null;
    }

}
