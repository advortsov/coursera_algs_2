package com.advortco.week1.directed_graphs;

import edu.princeton.cs.algs4.Digraph;

/**
 * @author advortco
 */
public class MyKosarajuSCC {
    private boolean[] visited;
    private int[] id;
    private int currComponentId;

    public MyKosarajuSCC(Digraph G) {
        visited = new boolean[G.V()];
        id = new int[G.V()];
        MyDepthFirstOrder order = new MyDepthFirstOrder(G);
        for (int s : order.reversePost()) {
            if (!visited[s]) {
                dfs(G, s);
                currComponentId++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        visited[v] = true;
        id[v] = currComponentId;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }


    public int id(int v) {
        return id[v];
    }

    public int count() {
        return currComponentId;
    }


}
