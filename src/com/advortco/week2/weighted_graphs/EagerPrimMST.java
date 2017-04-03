package com.advortco.week2.weighted_graphs;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * @author aldvc
 * @date 03.04.2017.
 */
public class EagerPrimMST {
    private Edge[] edgeTo; // кратчайшее ребро из вершины дерева
    private double[] distTo; // distTo[w] = edgeTo[w].weight()
    private boolean[] included; // true, если v включена в дерево
    private IndexMinPQ<Double> pq; // пригодные перекрестные ребра

    public EagerPrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        included = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[0] = 0;
        pq.insert(0, 0.0);
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());
        }

    }

    private void visit(EdgeWeightedGraph G, int v) {
        included[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (included[w]) continue;
            if (e.getWeight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.getWeight();
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    // 4.3.21
    public Iterable<Edge> edges() {
        return null;
    }

    // 4.3.31
    public double weight() {
        return 0;
    }
}
