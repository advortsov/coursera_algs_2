package com.advortco.week2.directed_weighted_graphs;

import edu.princeton.cs.algs4.*;

/**
 * p. 617
 *
 * @author aldvc
 * @date 09.04.2017.
 */
public class Arbitrage {
    public static void main(String[] args) {
        int V = StdIn.readInt();
        String[] name = new String[V];
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);

        for (int v = 0; v < V; v++) {
            name[v] = StdIn.readString();
            for (int w = 0; w < V; w++) {
                double rate = StdIn.readDouble();
                DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        BellmanFordSP spt = new BellmanFordSP(G, 0);

        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf(" = %10.5f %s\n", stake, name[e.to()]);
            }
        }


    }
}
