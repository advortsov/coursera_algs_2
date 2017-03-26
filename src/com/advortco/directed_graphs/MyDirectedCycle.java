package com.advortco.directed_graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author advortco
 */
public class MyDirectedCycle {

    private boolean[] visited;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public MyDirectedCycle(Digraph digraph) {
//        DirectedCycle
        onStack = new boolean[digraph.V()];
        visited = new boolean[digraph.V()];
        edgeTo = new int[digraph.V()];
        for (int v = 0; v < digraph.V(); v++) {
            if (!visited[v]) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        onStack[v] = true;
        visited[v] = true;

        for (int w : digraph.adj(v)) {
            if (this.hasCycle()) {
                return;
            } else if (!visited[w]) {
                edgeTo[w] = v;
                dfs(digraph, w);
            } else if (onStack[w]) { // if vertex is already visited and existing in stack
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In("wordnet/cyclic_digraph_1.txt"));
        MyDirectedCycle myDirectedCycle = new MyDirectedCycle(G);
        if (myDirectedCycle.hasCycle()) {
            StdOut.print(myDirectedCycle.cycle());
        } else {
            StdOut.print("It's DAG!");
        }
    }
}
