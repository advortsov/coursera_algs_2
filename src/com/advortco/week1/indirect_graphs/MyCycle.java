package com.advortco.week1.indirect_graphs;

import edu.princeton.cs.algs4.Graph;

/**
 * @author aldvc
 * @date 20.03.2017.
 */
public class MyCycle {

    private boolean[] visited;
    private boolean hasCycle;

    public MyCycle(Graph graph) {
        visited = new boolean[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!visited[s]) {
                dfs(graph, s, s);
            }
        }
    }

    private void dfs(Graph graph, int v, int u) {
        visited[v] = true;
        for (int w : graph.adj(v)) {
            if (!visited[w]) {
                dfs(graph, w, v);
            } else if (w != u) {
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
