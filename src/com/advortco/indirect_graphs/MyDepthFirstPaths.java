package com.advortco.indirect_graphs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

/**
 * @author aldvc
 * @date 19.03.2017.
 */
public class MyDepthFirstPaths {

    private boolean[] visited;
    private int[] edgeTo;
    private final int source;

    public MyDepthFirstPaths(Graph graph, int source) {
        this.source = source;
        edgeTo = new int[graph.V()];
        visited = new boolean[graph.V()];
        dfs(graph, source);
    }

    private void dfs(Graph graph, int vertex) {
        visited[vertex] = true;
        for (int w : graph.adj(vertex)) {
            if (!visited[w]) {
                edgeTo[w] = vertex;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int vertex) {
        return visited[vertex];
    }

    public Iterable<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) return null;

        Stack<Integer> path = new Stack<>();
        for (int i = vertex; i != source; i = edgeTo[i]) {
            path.push(i);
        }
        path.push(source);
        return path;

    }



}
