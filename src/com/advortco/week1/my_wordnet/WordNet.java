package com.advortco.week1.my_wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.LinkedList;
import java.util.List;

/**
 * synset - синоним
 * hyponym (more specific synset)
 * hypernym (more general synset)
 * <p>
 * WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them.
 * One such relationship is the is-a relationship, which connects a hyponym (more specific synset)
 * to a hypernym (more general synset). For example, animal is a hypernym of both bird and fish;
 * bird is a hypernym of eagle, pigeon, and seagull.
 *
 * @author advortco
 */
public class WordNet {

    private final Digraph graph;
    private SAP sap;
    private ST<String, List<Integer>> nounTable;  // noun, list of noun ids (iterable)
    private ST<Integer, String> synsetTable;     // keep track of synset from id

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

        nounTable = new ST<String, List<Integer>>();
        synsetTable = new ST<Integer, String>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(synsets);
        String[] allSynsetsLines = in.readAllLines();

        for (String line : allSynsetsLines) {
            String[] row = line.split(","); //36 == AND_circuit AND_gate == a circuit in a computer that fires only when all of its inputs fire
            Integer synsetId = Integer.parseInt(row[0]);
            String synset = row[1];
            synsetTable.put(synsetId, synset);

            for (String noun : row[1].split(" ")) {
                if (nounTable.contains(noun)) {
                    nounTable.get(noun).add(synsetId);
                } else {
                    List<Integer> nounIds = new LinkedList<>();
                    nounIds.add(synsetId);
                    nounTable.put(noun, nounIds);
                }
            }
        }

        // second pass builds the digraph by connecting first vertex on each
        // line to all others
        graph = new Digraph(synsetTable.size());

        // synset_id          subsequent fields are the id numbers of the synset's hypernyms
        //  164,                                    21012, 56099
        in = new In(hypernyms);
        String[] allHypernymsLines = in.readAllLines();

        for (String line : allHypernymsLines) {
            String[] row = line.split(",");
            int synsetId = Integer.parseInt(row[0]); // first vertex id on each hypernym line

            for (int i = 1; i < row.length; i++) {
                int hypernymId = Integer.parseInt(row[i]);
                graph.addEdge(synsetId, hypernymId);
            }
        }

        sap = new SAP(graph);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounTable;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounTable.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Graph doesn't contain such nouns!");
        List<Integer> aIndexes = nounTable.get(nounA);
        List<Integer> bIndexes = nounTable.get(nounB);
        return sap.length(aIndexes, bIndexes);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // Shortest ancestral path - Кратчайший путь предков
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Graph doesn't contain such nouns!");
        }
        return synsetTable.get(sap.ancestor(nounTable.get(nounA), nounTable.get(nounB)));

    }

    // do unit testing of this class
    public static void main(String[] args) {


        WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        System.out.println(wordnet.distance("table", "bed"));

        for (int i = 0; i < 1000; i++)
            System.out.println(wordnet.sap("table", "bed"));

    }


}
