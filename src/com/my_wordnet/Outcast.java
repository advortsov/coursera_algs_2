package com.my_wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Outcast detection. Given a list of wordnet nouns A1, A2, ..., An,
 * which noun is the least related to the others? To identify an outcast,
 * compute the sum of the distances between each noun and every other one:
 * <p>
 * di   =   dist(Ai, A1)   +   dist(Ai, A2)   +   ...   +   dist(Ai, An)
 * and return a noun At for which dt is maximum.
 */
public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }


    public String outcast(String[] nouns) {
        int[] sumOfDist = new int[nouns.length];
        int nounWithMaxDistIndex = 0;

        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue;
                int currDist = wordNet.distance(nouns[i], nouns[j]);
                if (currDist > 0) {
                    sumOfDist[i] += currDist;
                }
            }

            if (sumOfDist[nounWithMaxDistIndex] < sumOfDist[i]) {
                nounWithMaxDistIndex = i;
            }
        }


        System.out.println(Arrays.toString(sumOfDist));
        return nouns[nounWithMaxDistIndex];
    }

    public static void main(String[] args) {
/*
% java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
outcast5.txt: table
outcast8.txt: bed
outcast11.txt: potato

 */
        args = new String[]{"wordnet/synsets.txt", "wordnet/hypernyms.txt", "wordnet/outcast5.txt",
                "wordnet/outcast8.txt", "wordnet/outcast11.txt"};

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}