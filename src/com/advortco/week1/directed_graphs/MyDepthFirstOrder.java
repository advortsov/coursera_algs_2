package com.advortco.week1.directed_graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * Листинг 4.2.4. У порядочение вершин в орграфе с помощью поиска в глубину.
 *
 * @author advortco
 */
public class MyDepthFirstOrder {
//    DepthFirstOrder

    private boolean[] visited;
    private Queue<Integer> pre; // preorder
    private Queue<Integer> post;
    private Stack<Integer> reversePost;

    public MyDepthFirstOrder(Digraph digraph) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        visited = new boolean[digraph.V()];

        for (int v = 0; v < digraph.V(); v++) {
            if (!visited[v]) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        pre.enqueue(v);
        visited[v] = true;
        for (int w : digraph.adj(v)) {
            if (!visited[w]) {
                dfs(digraph, w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }


    public Queue<Integer> getPre() {
        return pre;
    }

    public Queue<Integer> getPost() {
        return post;
    }

    public Stack<Integer> reversePost() {
        return reversePost;
    }
}
