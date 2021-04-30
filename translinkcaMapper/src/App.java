import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class App {
    public static final Scanner input = new Scanner(System.in);

    // User interface menu - allows user to chose from avaible functions
    public static boolean menu(EdgeWeightedGraph graph, TST tree) {
        System.out.print("\nChoose from the options below or type 'exit' to quit the program:\n"
                + "1 - To find the shortest path between two bus stops type\n"
                + "2 - To find information on a specific stop type\n"
                + "3 - To find all trips with a given arrival time type\n" + "- ");
        var in = input.next();
        boolean isInt = false;
        int intValue = 0;
        try {
            intValue = Integer.parseInt(in);
            isInt = true;
        } catch (NumberFormatException e) {

        }
        if (in.equals("exit")) {
            return false;
        } else if (isInt) {
            if (intValue > 0 && intValue < 4) {
                switch (intValue) {
                    case 1:
                        int stop1, stop2;
                        System.out.print("\nEnter the first(starting) stop number: \n-");
                        if (input.hasNextInt()) {
                            stop1 = input.nextInt();
                            if ((graph.findStop(stop1) == -1)) {
                                System.out.println("That is not a valid stop");
                                break;
                            }
                        } else {
                            System.out.println("That is not a valid stop");
                            break;
                        }
                        System.out.print("\nEnter the second(destination) stop number: \n-");
                        if (input.hasNextInt()) {
                            stop2 = input.nextInt();
                            if ((graph.findStop(stop2) == -1)) {
                                System.out.println("That is not a valid stop");
                                break;
                            }
                        } else {
                            System.out.println("That is not a valid stop");
                            break;
                        }
                        findShortestPath(stop1, stop2, graph);
                        break;
                    case 2:
                        System.out.print("Enter the stop name, or just the first few characters: \n- ");
                        String searchTest = input.next();
                        busStopSearch(searchTest.toUpperCase(), tree); // input validation done within function
                        break;
                    case 3:
                        System.out.print("Enter arrival time in format - hh:mm:ss \n- ");
                        String inputTime = input.next();
                        if (isValidTime(inputTime)) { // check first if format is correct
                            tripArrivalTime(inputTime, graph);
                        } else {
                            System.out.println("You must enter the time in the format  - hh:mm:ss");
                        }
                        break;
                    default:
                        System.out.println("The menu options are between 1 - 3");
                        return false;
                }
            } else {
                System.out.println("The menu options are between 1 - 3");
            }
        } else {
            System.out.println("Please enter a value between 1 and 3 - or 'exit");
        }
        System.out.print("\nPress Enter to continue");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // adapted from geeksforgeeks.org
    private static boolean isValidTime(String time) {
        String regex = "(([0-9]:[0-5][0-9]:[0-5][0-9])|([2][0-3]:[0-5][0-9]:[0-5][0-9])|([0-1][0-9]:[0-5][0-9]:[0-5][0-9]))";
        Pattern p = Pattern.compile(regex);
        if (time == null) {
            return false;
        }
        Matcher m = p.matcher(time);
        return m.matches();
    }

    // Finding shortest paths between 2 bus stops (as input by the user), returning
    // the list of stops
    // en route as well as the associated “cost”.
    private static void findShortestPath(int stop1, int stop2, EdgeWeightedGraph graph) {
        DijkstraSPT shortestPath = new DijkstraSPT(graph, stop1, stop2);
        if (shortestPath.fail == false && stop1 != stop2) {
            ArrayList<Stop> route = shortestPath.shortestRoute;
            System.out.println("The Route from stop " + stop1 + " to stop " + stop2 + " is:");
            for (int i = 0; i < route.size(); i++) {
                Stop s = route.get(i);
                System.out.println(s.stopNumber + " - " + s.stopName);
            }
        } else if (stop1 == stop2) {
            System.out.println("The Route from stop " + stop1 + " to stop " + stop2 + " is:");
            System.out.println(graph.stops.get(graph.findStop(stop1)).stopNumber + " - "
                    + graph.stops.get(graph.findStop(stop1)).stopName);
        } else {
            System.out.println("Invalid Start or Destination Stop number entered");
        }
    }

    // Searching for a bus stop by full name or by the first few characters in the
    // name, using a
    // ternary search tree (TST), returning the full stop information for each stop
    // matching the
    // search criteria (which can be zero, one or more stops)
    private static void busStopSearch(String stopName, TST searchTree) {
        Iterable<String> validStops = searchTree.keysWithPrefix(stopName);
        if (validStops != null) {
            System.out.println("\n|NAME|\t\t\t\t\t|NUM|\t|STOP DESCRIPTION|");
            System.out.println("--------------------------------------------------------------------------");
            for (String key : validStops) {
                searchTree.get(key).printStopSingleLine();
            }
        } else {
            System.out.println("No matching stops were found");
        }

    }

    // Searching for all trips with a given arrival time, returning full details of
    // all trips matching the
    // criteria (zero, one or more), sorted by trip id
    private static void tripArrivalTime(String arrivalTime, EdgeWeightedGraph graph) {
        ArrayList<Trip> validTrips = new ArrayList<Trip>();
        for (int i = 0; i < graph.trips.size(); i++) {
            String time = graph.trips.get(i).time;
            if (time.charAt(0) == ' ') {
                time = time.substring(1);
            }
            if (arrivalTime.charAt(0) == ' ') {
                arrivalTime = arrivalTime.substring(1);
            }
            if (arrivalTime.equals(time)) {
                validTrips.add(graph.trips.get(i));
            }
        }
        for (int i = 0; i < validTrips.size(); i++) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Trip ID:" + validTrips.get(i).id + "\n" + "Stop Number:"
                    + validTrips.get(i).stop.stopNumber + "\n" + "Stop Name:" + validTrips.get(i).stop.stopName + "\n"
                    + "Arrival Time:" + validTrips.get(i).time + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Loading");
        EdgeWeightedGraph graph = new EdgeWeightedGraph();
        TST newTree = new TST("translinkcaMapper/src/stopsDescs.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Loaded in " + (endTime - startTime) + "ms");
        boolean run = false;
        do {
            run = menu(graph, newTree);
        } while (run);
        System.out.println("\nSucessfully quit");
    }
}
