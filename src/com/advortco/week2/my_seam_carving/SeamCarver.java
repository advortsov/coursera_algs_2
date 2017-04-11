package com.advortco.week2.my_seam_carving;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;

/**
 * @author aldvc
 * @date 09.04.2017.
 */
public class SeamCarver {
    private int[][] colorMatrix;
    private int width, height;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        colorMatrix = new int[width][height];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                colorMatrix[x][y] = picture.get(x, y).getRGB();
            }
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                pic.set(x, y, new Color(colorMatrix[x][y]));
            }
        return pic;
    }

    // width  of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }


    // sequence of indices for horizontal seam in current picture
    public int[] findHorizontalSeam() {
        // construct energy matrix by H x W
        double[][] energyMatrix = toEnergyMatrix(height, width, true);
        return findSeam(energyMatrix);
    }

    // sequence of indices for vertical   seam in current picture
    public int[] findVerticalSeam() {
        // construct energy matrix by W x H
        double[][] energyMatrix = toEnergyMatrix(width, height, false);
        return findSeam(energyMatrix);
    }

    public int[] findSeam(double[][] eMatrix) {
        int W = eMatrix.length;
        int H = eMatrix[0].length;

        double[][] energyTo = new double[W][H];
        int[][] edgeTo = new int[W][H];
        int[] seam = new int[H];

        // prepare energeTo:
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                energyTo[x][y] = (y == 0) ? 1000 : Double.POSITIVE_INFINITY;
            }
        }
//        AcyclicSP
        // relax all pixels in edgeTo:
        for (int y = 0; y < H - 1; y++) {
            for (int x = 0; x < W; x++) {
                for (int w = x - 1; w <= x + 1; w++) {
                    if (w >= 0 && w < W) {
                        if (energyTo[w][y + 1] > energyTo[x][y] + eMatrix[w][y + 1]) {
                            energyTo[w][y + 1] = energyTo[x][y] + eMatrix[w][y + 1];
                            edgeTo[w][y + 1] = y * W + x;
                        }
                    }
                }
            }
        }

        IndexMinPQ<Double> floorQ = new IndexMinPQ<>(W);
        // find the minimum index in last row
        for (int x = 0; x < W; x++)
            floorQ.insert(x, energyTo[x][H - 1]);

        seam[H - 1] = floorQ.minIndex();

        // back-track
        for (int y = H - 1; y > 0; y--) {
            seam[y - 1] = edgeTo[seam[y]][y] % W;
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] horSeam) {
        if (horSeam.length != width || height <= 1) throw new java.lang.IllegalArgumentException();

        int[][] copy = new int[width][height - 1];

        for (int x = 0; x < width; x++) {
            System.arraycopy(colorMatrix[x], 0, copy[x], 0, horSeam[x]);
            System.arraycopy(colorMatrix[x], horSeam[x] + 1, copy[x], horSeam[x], height - horSeam[x] - 1);
        }

        height--;
        colorMatrix = copy;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] vertSeam) {
        if (vertSeam.length != height || width <= 1)
            throw new java.lang.IllegalArgumentException();
        //checkSeam(a);

        int[][] copy = new int[width - 1][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x < vertSeam[y]) {
                    copy[x][y] = colorMatrix[x][y];
                } else if (x > vertSeam[y]) {
                    copy[x - 1][y] = colorMatrix[x][y];
                }
            }
        }

        width--;
        colorMatrix = copy;
    }

    private void checkSeam(int[] a) {
        for (int i = 1; i < a.length; ++i) {
            if (Math.abs(a[i - 1] - a[i]) > 1)
                throw new IllegalArgumentException(
                        "two adjacent entries differ by more than 1");
        }
    }

    public double energy(int col, int row) {
        if (col == 0 || col == width - 1 || row == 0 || row == height - 1) {
            return 1000;
        }
        double sumOfXDeltaSquares = sumOfPixelsDeltasSquares(new Color(colorMatrix[col + 1][row]), new Color(colorMatrix[col - 1][row]));
        double sumOfYDeltaSquares = sumOfPixelsDeltasSquares(new Color(colorMatrix[col][row + 1]), new Color(colorMatrix[col][row - 1]));
        return Math.sqrt(sumOfXDeltaSquares + sumOfYDeltaSquares);
    }

    private double sumOfPixelsDeltasSquares(Color col1, Color col2) {
        int deltaR = col1.getRed() - col2.getRed();
        int deltaG = col1.getGreen() - col2.getGreen();
        int deltaB = col1.getBlue() - col2.getBlue();
        return deltaR * deltaR + deltaG * deltaG + deltaB * deltaB;
    }


    private double[][] toEnergyMatrix(int W, int H, boolean needTranspose) {
        double[][] result = new double[W][H];
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++) {
                result[x][y] = needTranspose ? energy(y, x) : energy(x, y);
            }
        return result;
    }

}
