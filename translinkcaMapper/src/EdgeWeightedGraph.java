import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
/*
 * Adapted from EdgeWeightedDigraph and AdjMatrixEdgeWeightedDiGraph by Sedgewick and Wayne
 * https://algs4.cs.princeton.edu/44sp/EdgeWeightedDigraph.java.html
 */
public class EdgeWeightedGraph {
    public int numVert;
    public static ArrayList<Stop> stops;
    public static ArrayList<ArrayList<DirectedEdge>> adjEdges;

    public EdgeWeightedGraph() {
            // numVert = stops.size();
            // adjEdges = new ArrayList<>(numVert*25);
            // for (int i = 0; i < numVert; i++) {
            //     adjEdges.add(stops.get(i).stopNumber, new ArrayList<DirectedEdge>());
            // }
            // for (int i = 0; i < numEdge; i++) {
            //     int initVertex = input.nextInt();
            //     int destVertex = input.nextInt();
            //     double weight = input.nextDouble();
            //     if(initVertex >= 0 && destVertex >= 0 && weight >= 0) {
            //         adjEdges.get(initVertex).add(new DirectedEdge(initVertex, destVertex, weight));
            //     }
            //     else {
            //         this.numVert = 0;
            //         this.numEdge = 0;
            //         break;
            //     }
            // }
            // for (ArrayList<DirectedEdge> adjEdge : adjEdges) {
            //     if (adjEdge.size() <= 0) {
            //         this.numVert = 0;
            //         this.numEdge = 0;
            //         break;
            //     }
            // }
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

    private static void initStopEdges() {
        try {
            int lastRoute, currRoute, lastStop, currStop; 
            String filename = "stop_times.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            lastRoute = scanner.nextInt();
            scanner.next();
            scanner.next();
            lastStop = scanner.nextInt();
            scanner.nextLine();
            while(scanner.hasNext()) {
                currRoute = scanner.nextInt();
                scanner.next();
                scanner.next();
                if(lastRoute==currRoute){
                    currStop = scanner.nextInt();
                    adjEdges.get(lastStop).add(new DirectedEdge(lastStop, currStop, 1));
                    scanner.nextLine();
                }
                else {
                    lastRoute = scanner.nextInt();
                    scanner.next();
                    scanner.next();
                    lastStop = scanner.nextInt();
                    scanner.nextLine();
                }
            }
            scanner.close();
        }
        catch(FileNotFoundException e){adjEdges = null;}
    }

    private static void initTransferEdges() {
        try {
            int initStop, destStop, weight;
            String filename = "transfers.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNext()) {
                initStop = scanner.nextInt();
                destStop = scanner.nextInt();
                if(scanner.hasNextInt()) {
                    scanner.nextInt();
                    int min = scanner.nextInt();
                    weight = min/100;
                }
                else {
                    weight = 2;
                }
                adjEdges.get(initStop).add(new DirectedEdge(initStop, destStop, weight));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){adjEdges = null;}
    }

    public static void main(String[] args) {
        stops = new ArrayList<>();
        initStops();
        adjEdges = new ArrayList<>(stops.size()*15);
        for (int i = 0; i < stops.size()*15; i++) {
            adjEdges.add(new ArrayList<>());
        }
        initStopEdges();
        initTransferEdges();
    }
}