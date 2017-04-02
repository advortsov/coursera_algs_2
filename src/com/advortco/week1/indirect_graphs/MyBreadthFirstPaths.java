package com.advortco.week1.indirect_graphs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * @author aldvc
 * @date 19.03.2017.
 */
public class MyBreadthFirstPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private int V;

    private boolean[] visited;
    private int[] edgeTo;
    private int[] distTo;

    public MyBreadthFirstPaths(Graph graph, int source) {
        V = graph.V();
        visited = new boolean[V];
        edgeTo = new int[V];
        distTo = new int[V];

        bfs(graph, source);
    }

    private void bfs(Graph graph, int source) {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < V; v++) {
            distTo[v] = INFINITY;
        }

        distTo[source] = 0;
        visited[source] = true;
        q.enqueue(source);

        while (!q.isEmpty()) {
            int vertex = q.dequeue();
            for (int w : graph.adj(vertex)) {
                if (!visited[w]) {
                    edgeTo[w] = vertex;
                    distTo[w] = distTo[vertex] + 1;
                    visited[w] = true;
                    q.enqueue(w);
                }
            }
        }

    }


    public boolean hasPathTo(int vertex) {
        return visited[vertex];
    }

    public int distTo(int vertex) {
        return distTo[vertex];
    }

    public Iterable<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) return null;

        Stack<Integer> path = new Stack<>();
        int x;
        for (x = vertex; distTo[x] != 0; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }


}
