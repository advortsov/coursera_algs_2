package com.advortco.week2.weighted_graphs;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;


/**
 * @author aldvc
 * @date 03.04.2017.
 */
public class MyLazyPrimMST {
    private boolean[] visited;
    private Queue<MyEdge> mst;
    private MinPQ<MyEdge> pq;

    public MyLazyPrimMST(MyEdgeWeightedGraph G) {
        pq = new MinPQ<>();
        visited = new boolean[G.V()];
        mst = new Queue<>();

        visit(G, 0);

        while (!pq.isEmpty()) {
            MyEdge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (visited[v] && visited[w]) continue;
            mst.enqueue(e);
            if (!visited[v]) visit(G, v);
            if (!visited[w]) visit(G, w);
        }
    }

    private void visit(MyEdgeWeightedGraph g, int v) {
        visited[v] = true;

        for (MyEdge e : g.adj(v)) {
            if (!visited[e.other(v)]) {
                pq.insert(e);
            }
        }

    }


    public Iterable<MyEdge> edges() {
        return mst;
    }


    public double weight() {
        return 0;
    }


}
