import java.util.ArrayList;
/*
 * Adapted from by DijkstraSP by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
 */
public class DijkstraSPT {
    public double[] distTo;
    public boolean[] visited;
    public int[] parent;
    public ArrayList<Stop> shortestRoute;
    public boolean fail = false;

    public DijkstraSPT(EdgeWeightedGraph graph, int sourceVertex, int finalVertex) {
        if(EdgeWeightedGraph.findStop(sourceVertex) != -1 && EdgeWeightedGraph.findStop(finalVertex) != -1) {
            distTo = new double[graph.numVert];
            visited = new boolean[graph.numVert];
            parent = new int[graph.numVert];
            parent[EdgeWeightedGraph.findStop(sourceVertex)] = -1;
            for(int i = 0; i < distTo.length; i++) {
                distTo[i] = Double.POSITIVE_INFINITY;
                visited[i] = false;
            }
            distTo[EdgeWeightedGraph.findStop(sourceVertex)] = 0;
            for(int i = 0; i < graph.numVert - 1; i++) {
                int vertex = minimumDistance(distTo, visited);
                if(vertex < 0) continue;
                visited[vertex] = true;
                for(DirectedEdge edge : EdgeWeightedGraph.adjEdges.get(vertex))
                    relax(edge);
            }
            shortestRoute = new ArrayList<Stop>();
            int temp = EdgeWeightedGraph.findStop(finalVertex);
            shortestRoute = findShortestPath(parent, temp);
        }
        else {
            fail = true;
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
        int initVert = EdgeWeightedGraph.findStop(edge.initVertex);
        int destVert = EdgeWeightedGraph.findStop(edge.destVertex);
        if(distTo[destVert] > (distTo[initVert] + edge.weight)) {
            distTo[destVert] = distTo[initVert] + edge.weight;
            parent[destVert] = initVert;
        }
    }

    public ArrayList<Stop> findShortestPath(int[] parent, int lastStop) {
        if(parent[lastStop] == -1) {
            shortestRoute.add(EdgeWeightedGraph.stops.get(lastStop));
            return shortestRoute;
        }
        findShortestPath(parent, parent[lastStop]);
        shortestRoute.add(EdgeWeightedGraph.stops.get(lastStop));
        return shortestRoute;
    }
}