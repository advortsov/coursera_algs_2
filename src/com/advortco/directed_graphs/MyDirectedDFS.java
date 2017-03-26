package com.advortco.directed_graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author advortco
 */
public class MyDirectedDFS {

    private boolean[] visited;

    public MyDirectedDFS(Digraph digraph, int s) {
        visited = new boolean[digraph.V()];
        dfs(digraph, s);
    }

    public MyDirectedDFS(Digraph digraph, Iterable<Integer> sources) {
        visited = new boolean[digraph.V()];
        for (Integer s : sources) {
            if (!visited[s]) {
                dfs(digraph, s);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        visited[v] = true;
        for (int w : digraph.adj(v)) {
            if (!visited[w]) {
                dfs(digraph, w);
            }
        }
    }

    public boolean visited(int v) {
        return visited[v];
    }

    public static void main(String[] args) {
        args = new String[]{"3"};
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        Bag<Integer> sources = new Bag<>();

        for (int i = 0; i < args.length; i++)
            sources.add(Integer.parseInt(args[i]));

        MyDirectedDFS reachable = new MyDirectedDFS(G, sources);

        for (int v = 0; v < G.V(); v++)
            if (reachable.visited(v)) StdOut.print(v + " ");

        StdOut.println();
    }
}
