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
    public ArrayList<Stop> stops;
    public ArrayList<ArrayList<DirectedEdge>> adjEdges;
    public ArrayList<Trip> trips;

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
        initTripTimes();
    }

    private void initStops() {
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

    private void initStopEdges() {
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

    private void initTripTimes() {
        try {
            trips = new ArrayList<>();
            String route, time;
            int stopNumber;
            Stop stop;
            String filename = "translinkcaMapper/src/stop_times.txt";
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNext()) {
                route = scanner.next();
                time = scanner.next();
                scanner.next();
                stopNumber = scanner.nextInt();
                stop = stops.get(findStop(stopNumber));
                trips.add(new Trip(time, stop, route));
                scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {}
    }

    private void initTransferEdges() {
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

    public int findStop(int stopNum) {
        for(int i = 0; i < stops.size(); i++) {
            if(stops.get(i).stopNumber == stopNum) {
                return i;
            }
        }
        return -1;
    }
}