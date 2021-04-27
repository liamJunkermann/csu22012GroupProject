/*
 * Adapted from by DijkstraSP by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
 */
public class DijkstraSPT {
    public double[] distTo;
    public DirectedEdge[] edgeTo;
    public boolean[] visited;

    public DijkstraSPT(EdgeWeightedGraph graph, int sourceVertex) {
        distTo = new double[graph.numVert];
        edgeTo = new DirectedEdge[graph.numVert];
        visited = new boolean[graph.numVert];
        for(int i = 0; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }
        distTo[sourceVertex] = 0;
        for(int i = 0; i < graph.numVert - 1; i++) {
            int vertex = minimumDistance(distTo, visited);
            if(vertex < 0) continue;
            visited[vertex] = true;
            for(DirectedEdge edge : graph.adjEdges.get(vertex))
                relax(edge);
        }


    }
    //Adapted from minDistance algorithm from www.geeksforgeeks.org
    public int minimumDistance(double[] distTo, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < visited.length; i++) {
            if(!visited[i] && distTo[i] <= min) {
                min = distTo[i];
                index = i;
            }
        }
        return index;
    }

    //Adapted from relax() method by Sedgewick and Wayne
    public void relax(DirectedEdge edge) {
        int initVert = edge.initVertex;
        int destVert = edge.destVertex;
        if(distTo[destVert] > (distTo[initVert] + edge.weight)) {
            distTo[destVert] = distTo[initVert] + edge.weight;
            edgeTo[destVert] = edge;
        }
    }
}