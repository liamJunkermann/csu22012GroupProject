/*
 * Adapted from DirectedEdge by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/DirectedEdge.java.html
 */

public class DirectedEdge {
    public int initVertex;
    public int destVertex;
    public double weight;

    public DirectedEdge(int initVert, int destVertex, double weight) {
        this.initVertex = initVert;
        this.destVertex = destVertex;
        this.weight = weight;
    }
}