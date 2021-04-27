import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
/*
 * Adapted from EdgeWeightedDigraph and AdjMatrixEdgeWeightedDiGraph by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
public class EdgeWeightedGraph {
    public static int numVert;
    public static ArrayList<Stop> stops;
    public static ArrayList<ArrayList<DirectedEdge>> adjEdges;

    public EdgeWeightedGraph() {
            numVert = stops.size();
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

    private static void initStops() {
        try{
            String filename = "stops.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNext()) {
                if(scanner.hasNextInt()) {
                    stops.add(new Stop(scanner.nextInt()));
                    scanner.nextLine();
                }
            }
            scanner.close();
        }   
        catch(FileNotFoundException e){stops = null;}
    }

    public static void main(String[] args) {
        stops = new ArrayList<>();
        initStops();
    }
}