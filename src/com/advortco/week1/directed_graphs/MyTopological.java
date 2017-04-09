package com.advortco.week1.directed_graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolDigraph;

/**
 * @author advortco
 */
public class MyTopological {

    private Iterable<Integer> order;


    public MyTopological(Digraph digraph) {
        MyDirectedCycle cycleFinder = new MyDirectedCycle(digraph);

        if (!cycleFinder.hasCycle()) {
            MyDepthFirstOrder dfs = new MyDepthFirstOrder(digraph);
            order = dfs.reversePost();
        }
    }


    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order == null;
    }

    public static void main(String[] args) {
        String filename = "wordnet/for_topological_jobs.txt";
        String separator = "=>";
        SymbolDigraph sg = new SymbolDigraph(filename, separator);


        MyTopological top = new MyTopological(sg.G());

        System.out.println(top.isDAG());

        for (int v : top.order())
            StdOut.println(sg.indexOf(sg.name(v)) + " " + sg.name(v));
    }

}
