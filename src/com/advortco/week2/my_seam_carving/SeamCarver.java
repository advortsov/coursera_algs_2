package com.advortco.week2.my_seam_carving;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

/**
 * @author aldvc
 * @date 09.04.2017.
 */
public class SeamCarver {
    private Picture picture;
    private double[][] gradient;


    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.gradient = new double[picture.height()][picture.width()];

        //
        System.out.println("Gradient table:");
        for (int r = 0; r < picture.height(); r++) {
            for (int c = 0; c < picture.width(); c++) {
                gradient[r][c] = energy(c, r);
                StdOut.printf("[%.2f] ", gradient[r][c]);
            }
            System.out.println();
        }
        System.out.println();

        int definition = picture.height() * picture.width();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(definition);
        for (int pixel = 0; pixel < definition; pixel++) {

            DirectedEdge left = null;
            DirectedEdge middle = null;
            DirectedEdge right = null;

            int r = pixel / width();
            int c = pixel % width();
            double weight = gradient[r][c];

            if ((pixel + width() + 1 < definition) && (pixel + width() - 1) >= width()) { // DOESNT situated in the current row
                left = new DirectedEdge(
                        pixel,
                        pixel + width() - 1,
                        weight);
            }

            if ((pixel + width()) < definition) { // has next row
                middle = new DirectedEdge(
                        pixel,
                        pixel + width(),
                        weight);
            }

            if ((pixel + width() + 1 < definition) && (pixel == 0 || pixel % (width() - 1) != 0)) { // last pixel in the row
                right = new DirectedEdge(
                        pixel,
                        pixel + width() + 1,
                        weight);
            }

            if (left != null) {
                G.addEdge(left);
            }


            if (middle != null) {
                G.addEdge(middle);
            }


            if (right != null) {
                G.addEdge(right);
            }

        }


        System.out.println(G);

    }


    public int height() {
        return picture.height();
    }

    public int width() {
        return picture.width();
    }


    public double energy(int col, int row) {
        if (col == 0 || col == picture.width() - 1 || row == 0 || row == picture.height() - 1) {
            return 1000;
        }
        double sumOfXDeltaSquares = sumOfPixelsDeltasSquares(picture.get(col + 1, row), picture.get(col - 1, row));
        double sumOfYDeltaSquares = sumOfPixelsDeltasSquares(picture.get(col, row + 1), picture.get(col, row - 1));
        return Math.sqrt(sumOfXDeltaSquares + sumOfYDeltaSquares);
    }

    private double sumOfPixelsDeltasSquares(Color col1, Color col2) {
        int deltaR = col1.getRed() - col2.getRed();
        int deltaG = col1.getGreen() - col2.getGreen();
        int deltaB = col1.getBlue() - col2.getBlue();
        return Math.pow(deltaR, 2) + Math.pow(deltaG, 2) + Math.pow(deltaB, 2);
    }

    public int[] findVerticalSeam() {
        return new int[0];
    }


    public int[] findHorizontalSeam() {
        return new int[0];
    }

    public void removeHorizontalSeam(int[] horizontalSeam) {

    }

    public void removeVerticalSeam(int[] verticalSeam) {

    }

    public Picture picture() {
        return picture;
    }
}
