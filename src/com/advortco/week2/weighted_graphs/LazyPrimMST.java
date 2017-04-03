package com.advortco.week2.weighted_graphs;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;


/**
 * @author aldvc
 * @date 03.04.2017.
 */
public class LazyPrimMST {
    private boolean[] visited;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        visited = new boolean[G.V()];
        mst = new Queue<>();

        visit(G, 0);

        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (visited[v] && visited[w]) continue;
            mst.enqueue(e);
            if (!visited[v]) visit(G, v);
            if (!visited[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        visited[v] = true;

        for (Edge e : g.adj(v)) {
            if (!visited[e.other(v)]) {
                pq.insert(e);
            }
        }

    }


    public Iterable<Edge> edges() {
        return mst;
    }


    public double weight() {
        return 0;
    }


}
