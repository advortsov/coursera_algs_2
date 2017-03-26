package com.advortco.indirect_graphs;

import edu.princeton.cs.algs4.Graph;

/**
 * Connected Components
 *
 * @author aldvc
 * @date 19.03.2017.
 */
public class CC {

    private boolean[] visited;
    private int[] componentId;
    private int count;


    public CC(Graph graph) {
        visited = new boolean[graph.V()];
        componentId = new int[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!visited[s]) {
                dfs(graph, s);
                count++;
            }
        }
    }

    private void dfs(Graph graph, int v) {
        visited[v] = true;
        componentId[v] = count;
        for (int w : graph.adj(v)) {
            if (!visited[w]) {
                dfs(graph, w);
            }
        }
    }

    public boolean connected(int v, int w) {
        return componentId[v] == componentId[w];
    }

    public int componentId(int v) {
        return componentId[v];
    }

    public int count() {
        return count;
    }
}
