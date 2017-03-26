package com.advortco.indirect_graphs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
        // write your code here
        In in = new In("/tinyG.txt");
        Graph graph = new Graph(in);
        for (int v = 0; v < graph.V(); v++) {
            for (int w : graph.adj(v)) { // adjacent - смежный
                StdOut.println(v + " -> " + w);
            }
        }

        System.out.println(graph);
        


    }

    public static int degree(Graph g, int v){ // Степень вершины - https://ru.wikipedia.org/wiki/%D0%A1%D1%82%D0%B5%D0%BF%D0%B5%D0%BD%D1%8C_%D0%B2%D0%B5%D1%80%D1%88%D0%B8%D0%BD%D1%8B_(%D1%82%D0%B5%D0%BE%D1%80%D0%B8%D1%8F_%D0%B3%D1%80%D0%B0%D1%84%D0%BE%D0%B2)
        int degree = 0;
        for (int w : g.adj(v)) degree++;
        return degree;
    }
}
