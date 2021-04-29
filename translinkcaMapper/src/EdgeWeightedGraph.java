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
    public  static ArrayList<Stop> stops;
    public  static ArrayList<ArrayList<DirectedEdge>> adjEdges;

    public EdgeWeightedGraph() {
        stops = new ArrayList<>();
        initStops();
        this.numVert = stops.size();
        adjEdges = new ArrayList<>(stops.size());
        for (int i = 0; i < stops.size(); i++) {
            adjEdges.add(new ArrayList<>());
        }
        initStopEdges();
        initTransferEdges();
    }

    private static void initStops() {
        try{
            String filename = "translinkcaMapper/src/stops.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNext()) {
                if(scanner.hasNextInt()) {
                    int stopNum = scanner.nextInt();
                    scanner.next();
                    String stopName = scanner.next();
                    stops.add(new Stop(stopNum, stopName));
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
            String filename = "translinkcaMapper/src/stop_times.txt";
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
                    adjEdges.get(findStop(lastStop)).add(new DirectedEdge(lastStop, currStop, 1));
                    lastStop = currStop;
                    lastRoute = currRoute;
                }
                else {
                    lastRoute = currRoute;
                    lastStop = scanner.nextInt();
                }
                scanner.nextLine();
            }
            scanner.close();
        }
        catch(FileNotFoundException e){adjEdges = null;}
    }

    private static void initTransferEdges() {
        try {
            int initStop, destStop; 
            double weight;
            String filename = "translinkcaMapper/src/transfers.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",|\\n");
            scanner.nextLine();
            while(scanner.hasNext()) {
                initStop = scanner.nextInt();
                destStop = scanner.nextInt();
                if(scanner.hasNextInt()) {
                    scanner.nextInt();
                    String min = scanner.next();
                    min = min.substring(0,3);
                    double minimum = Double.parseDouble(min);
                    weight = minimum/100;
                }
                else {
                    weight = 2;
                    scanner.nextLine();
                }
                adjEdges.get(findStop(initStop)).add(new DirectedEdge(initStop, destStop, weight));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){adjEdges = null;}
    }

    public static int findStop(int stopNum) {
        for(int i = 0; i < stops.size(); i++) {
            if(stops.get(i).stopNumber == stopNum) {
                return i;
            }
        }
        return -1;
    }
}