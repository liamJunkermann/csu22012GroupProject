import java.util.Scanner;
import java.util.ArrayList;
/*
 * Adapted from EdgeWeightedDigraph and AdjMatrixEdgeWeightedDiGraph by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
public class EdgeWeightedGraph {
    public int numVert;
    public int numEdge;
    public ArrayList<ArrayList<DirectedEdge>> adjEdges;
    public DirectedEdge[][] edgesAdj;

    public EdgeWeightedGraph(Scanner input, int type) {
        this.numVert = input.nextInt();
        this.numEdge = input.nextInt();
        if(type == 0) {
            adjEdges = new ArrayList<>(numVert);
            for (int i = 0; i < numVert; i++) {
                adjEdges.add(i, new ArrayList<DirectedEdge>());
            }
            for (int i = 0; i < numEdge; i++) {
                int initVertex = input.nextInt();
                int destVertex = input.nextInt();
                double weight = input.nextDouble();
                if(initVertex >= 0 && destVertex >= 0 && weight >= 0) {
                    adjEdges.get(initVertex).add(new DirectedEdge(initVertex, destVertex, weight));
                }
                else {
                    this.numVert = 0;
                    this.numEdge = 0;
                    break;
                }
            }
            for (ArrayList<DirectedEdge> adjEdge : adjEdges) {
                if (adjEdge.size() <= 0) {
                    this.numVert = 0;
                    this.numEdge = 0;
                    break;
                }
            }
        }
        else { //if(type == 1)
            edgesAdj = new DirectedEdge[numVert][numVert];
            for (int i = 0; i < numEdge; i++) {
                int vertexFrom = input.nextInt();
                int vertexTo = input.nextInt();
                double weight = input.nextDouble();
                if (vertexFrom >= 0 && vertexTo >= 0 && weight >= 0) {
                    edgesAdj[vertexFrom][vertexTo] = new DirectedEdge(vertexFrom, vertexTo, weight);
                }
            }

        }
    }
}