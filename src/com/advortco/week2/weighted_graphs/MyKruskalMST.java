package com.advortco.week2.weighted_graphs;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

/**
 * @author aldvc
 * @date 02.04.2017.
 */
public class MyKruskalMST {
    private Queue<MyEdge> mst = new Queue<>();

    public MyKruskalMST(MyEdgeWeightedGraph G) {
        MinPQ<MyEdge> pq = new MinPQ<>();
        for (MyEdge e : G.edges()) {
            pq.insert(e);
        }

        UF uf = new UF(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            MyEdge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
            }

        }
    }

    public Iterable<MyEdge> edges() {
        return mst;
    }
}
