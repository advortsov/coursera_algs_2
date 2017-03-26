package com.advortco.indirect_graphs;

import edu.princeton.cs.algs4.Graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author aldvc
 * @date 20.03.2017.
 */
public class MyTwoColor {

    private boolean[] visited;
    private Integer[] ii;
    private boolean[] color;
    private boolean isTwoColorable = true;

    public MyTwoColor(Graph graph) {
        visited = new boolean[graph.V()];
        color = new boolean[graph.V()];


//        Arrays.stream(ii).map(() -> "")

        for (int s = 0; s < graph.V(); s++) {
            if (!visited[s]) dfs(graph, s);
        }

    }

    private void dfs(Graph graph, int v) {
        visited[v] = true;
        for (int w : graph.adj(v)) {
            if (!visited[w]) {
                color[w] = !color[v];
                dfs(graph, v);
            } else if (color[w] == color[v]) {
                isTwoColorable = false;
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }

}
