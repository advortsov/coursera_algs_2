package com.my_wordnet;

import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author advortco
 */
public class SAP {

    private Digraph digraph;
    private Map<Pair, Integer> cache = new HashMap<>();

    public Map<Pair, Integer> getCache() {
        return cache;
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        Pair p = new Pair(v, w);
        Integer sapLength = cache.get(p);
        if (sapLength != null) {
            return sapLength;
        }

        BreadthFirstDirectedPaths bfdpX = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdpY = new BreadthFirstDirectedPaths(digraph, w);

        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdpX.hasPathTo(i) && bfdpY.hasPathTo(i)) {
                int currDist = bfdpX.distTo(i) + bfdpY.distTo(i);
                if (minDist > currDist) {
                    minDist = currDist;
                }
            }
        }

        cache.put(p, minDist);
        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }


    /*
            The most straightforward way to find a shortest ancestral path is
            to run two breadth-first searches in G starting from x and from y, thereby computing
            the distance of each synset from x and from y.

             Then, for each synset reachable from x and from y, sum the two distances,

             Finally, take the  minimum of these sums and subtract two. (Or, instead of subtracting two, in each distance
        computation avoid counting the first edge.)

        An improvement is to run both searches
        concurrently, visiting vertices at distance one from either x or y, then vertices at distance two
        from x or y, and so on. Once some vertex is reached from both x and y, the sum of its distances
        from x and y gives an upper bound on the distance beyond which the searches need not go. The
        upper bound can be reduced each time a shorter pair of paths is found. This allows early
        termination, especially in the case of nearby words.

     */
    public int ancestor(int x, int y) {

        BreadthFirstDirectedPaths bfdpX = null;
        BreadthFirstDirectedPaths bfdpY = null;

        ExecutorService service = null;

        long start = System.currentTimeMillis();
        try {
            service = Executors.newFixedThreadPool(2);
            Callable<BreadthFirstDirectedPaths> callX = () -> new BreadthFirstDirectedPaths(digraph, x);
            Callable<BreadthFirstDirectedPaths> callY = () -> new BreadthFirstDirectedPaths(digraph, y);
            Future<?> result1 = service.submit(callX);
            Future<?> result2 = service.submit(callY);
            bfdpX = (BreadthFirstDirectedPaths) result1.get();
            bfdpY = (BreadthFirstDirectedPaths) result2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (service != null) service.shutdown();
        }


//        /*
//        to run two breadth-first searches in G starting from x and from y, thereby computing
//            the distance of each synset from x and from y.
//         */
//        bfdpX = new BreadthFirstDirectedPaths(digraph, x);
//        bfdpY = new BreadthFirstDirectedPaths(digraph, y);

//        int[] reachable from x and from y = new
        // Then, for each synset reachable from x and from y, sum the two distances,
        int minDist = Integer.MAX_VALUE;
        int ancestorIndx = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdpX.hasPathTo(i) && bfdpY.hasPathTo(i)) {
                int currDist = bfdpX.distTo(i) + bfdpY.distTo(i);
                if (minDist > currDist) {
                    minDist = currDist;
                    ancestorIndx = i;
                }
            }
        }

        /*
         Finally, take the  minimum of these sums and subtract two. (Or, instead of subtracting two, in each distance
        computation avoid counting the first edge.)
         */

        System.out.println("ancestor time, ms: " + (System.currentTimeMillis() - start));
        return ancestorIndx;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfdpX = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdpY = new BreadthFirstDirectedPaths(digraph, w);

        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdpX.hasPathTo(i) && bfdpY.hasPathTo(i)) {
                int currDist = bfdpX.distTo(i) + bfdpY.distTo(i);
                if (minDist > currDist) {
                    minDist = currDist;
                }
            }
        }

        return minDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        long start = System.currentTimeMillis();

        BreadthFirstDirectedPaths bfdpX = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdpY = new BreadthFirstDirectedPaths(digraph, w);

//        BreadthFirstDirectedPaths bfdpX = null;
//        BreadthFirstDirectedPaths bfdpY = null;
//
//        ExecutorService service = null;
//
//        try {
//            service = Executors.newFixedThreadPool(2);
//            Callable<BreadthFirstDirectedPaths> callX = () -> new BreadthFirstDirectedPaths(digraph, v);
//            Callable<BreadthFirstDirectedPaths> callY = () -> new BreadthFirstDirectedPaths(digraph, w);
//            Future<?> result1 = service.submit(callX);
//            Future<?> result2 = service.submit(callY);
//            bfdpX = (BreadthFirstDirectedPaths) result1.get();
//            bfdpY = (BreadthFirstDirectedPaths) result2.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            if (service != null) service.shutdown();
//        }


        int minDist = Integer.MAX_VALUE;
        int ancestorIndx = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdpX.hasPathTo(i) && bfdpY.hasPathTo(i)) {
                int currDist = bfdpX.distTo(i) + bfdpY.distTo(i);
                if (minDist > currDist) {
                    minDist = currDist;
                    ancestorIndx = i;
                }
            }
        }
        System.out.println("ancestor iterable time, ms: " + (System.currentTimeMillis() - start));

        return ancestorIndx;
    }

    /*
    Here is a sample execution:
            % more digraph1.txt             % java SAP digraph1.txt
            13                              3 11
            11                              length = 4, ancestor = 1
             7  3
             8  3                           9 12
             3  1                           length = 3, ancestor = 5
             4  1
             5  1                           7 2
             9  5                           length = 4, ancestor = 0
            10  5
            11 10                           1 6
            12 10                           length = -1, ancestor = -1
             1  0
             2  0
     */
    public static void main(String[] args) {
        In in = new In("wordnet/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }


//    public static void main(String[] args) {
//        Digraph G = new Digraph(new In("wordnet/sap_test_digraph_1.txt"));
//        SAP sap = new SAP(G);
//        printArray(sap.visited);
//
//        System.out.println(sap.ancestor(8, 2)); // 5
//        printArray(sap.visited);
//
//        System.out.println(sap.ancestor(11, 5)); // 9
//        printArray(sap.visited);
//
//        System.out.println(sap.ancestor(8, 11)); // 9
//        printArray(sap.visited);
//
//        System.out.println(sap.ancestor(8, 2)); // 5
//        printArray(sap.visited);
//
//        //=====
//
////        G = new Digraph(new In("wordnet/sap_test_digraph_2.txt"));
////        sap = new SAP(G);
////        printArray(sap.visited);
////
////        System.out.println(sap.ancestor(2, 5)); // 1
////        printArray(sap.visited);
////
//        G = new Digraph(new In("wordnet/digraph1.txt"));
//        sap = new SAP(G);
//        printArray(sap.visited);
//
//        System.out.println(sap.ancestor(3, 11)); // he shortest ancestral path between 3 and 11 has length 4 (with common ancestor 1).
//        printArray(sap.visited);
////
//        G = new Digraph(new In("wordnet/digraph2.txt"));
//        sap = new SAP(G);
//        printArray(sap.visited);
//
//        G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
//        sap = new SAP(G);
//        System.out.println("digraph-ambiguous-ancestor 2 10 = " + sap.ancestor(2, 10));
//
//        System.out.println("digraph-ambiguous-ancestor 10 10 = " + sap.ancestor(10, 10));
//
//        System.out.println(sap.ancestor(1, 5)); // one ancestral path between 1 and 5 has length 4 (with common ancestor 5),
//        System.out.println(sap.length(1, 5)); // 2
//        System.out.println(sap.getCache());
//        // but the shortest ancestral path has length 2 (with common ancestor 0).
//        printArray(sap.visited);
//
//    }


    private static void printArray(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] ? "+ " : "- ");
        }
        System.out.println();
    }

    private class Pair {
        private int v;
        private int w;

        public Pair(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (v != pair.v) return false;
            return w == pair.w;

        }

        @Override
        public int hashCode() {
            int result = v;
            result = 31 * result + w;
            return result;
        }

        @Override
        public String toString() {
            return "{" +
                    "v=" + v +
                    ", w=" + w +
                    '}';
        }
    }
}