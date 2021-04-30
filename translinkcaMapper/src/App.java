import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class App {

    //User interface menu - allows user to chose from avaible functions
    public static boolean menu(EdgeWeightedGraph graph){
        System.out.print("Choose from the options below or type 'exit' to quit the program:\n"
        + "To find the shortest path between two bus stops type: 1\n"
        + "To find information on a specific stop type: 2\n"
        + "To find all trips with a given arrival time type: 3\n"
        );
        Scanner input = new Scanner(System.in);
        if(input.next().equals("exit")) {
            input.close();
            return false;
        }
        else if(input.hasNextInt()) {
            int value = input.nextInt();
            if(value > 0 && value < 4) {
                switch(value) {
                    case 1:
                        int stop1, stop2;
                        System.out.println("Enter the first(starting) stop number:");
                        if(input.hasNextInt()) {
                            stop1 = input.nextInt();
                        }
                        else{
                            System.out.println("Please enter a vaild input");
                            break;
                        }
                        System.out.println("Enter the second(destination) stop number:");
                        if(input.hasNextInt()) {
                            stop2 = input.nextInt();
                        }
                        else{
                            System.out.println("Please enter a vaild input");
                            break;
                        }
                        findShortestPath(stop1, stop2, graph);     
                        break;
                    case 2:
                        System.out.println("Enter the stop name, or just the first few characters:");
                        busStopSearch(input.nextLine());    //input validation done within function
                        break;
                    case 3:
                        System.out.println("Enter arrival time in format - hh:mm:ss");
                        String inputTime = input.nextLine();
                        if(isValidTime(inputTime)) {    //check first if format is correct
                            tripArrivalTime(inputTime);
                        } else {
                            System.out.println("Please enter a vaild input");
                        }
                        break;
                    default:
                        System.out.println("Please enter a vaild input");
                        input.close();
                        return false;
                }
            }
        }
        else {
            System.out.println("Please enter a vaild input");
        }
        return true;
    }

    //adapted from geeksforgeeks.org
    private static boolean isValidTime(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        Pattern p = Pattern.compile(regex);
        if (time == null) {
            return false;
        }
        Matcher m = p.matcher(time);
        return m.matches();
    }

    //Finding shortest paths between 2 bus stops (as input by the user), returning the list of stops
    //en route as well as the associated “cost”.
    private static void findShortestPath(int stop1, int stop2, EdgeWeightedGraph graph) {
        DijkstraSPT shortestPath = new DijkstraSPT(graph, stop1, stop2);
        if(shortestPath.fail == false) {
            ArrayList<Stop> route = shortestPath.shortestRoute;
            System.out.println("The Route from stop " + stop1 + " to stop " + stop2 + " is:");
            for(int i = 0; i < route.size(); i++) {
                Stop s = route.get(i);
                System.out.println(s.stopNumber + " - " + s.stopName);
            }
        }
        else {
            System.out.println("Invalid Start or Destination Stop number entered");
        }
    } 
    //Searching for a bus stop by full name or by the first few characters in the name, using a
    //ternary search tree (TST), returning the full stop information for each stop matching the
    //search criteria (which can be zero, one or more stops)
    private static void busStopSearch(String stopName) {
        TST searchTree = new TST("translinkcaMapper/src/stopsDescs.txt");
        Iterable<String> validStops = searchTree.keysWithPrefix(stopName);
        if(validStops != null) {
            System.out.println("|KEY|\t|STOP NUM|\t|STOP NAME|\t|STOP DESCRIPTION|");
             System.out.println("--------------------------------------------------------------------------");
            for(String key : validStops){
                System.out.println(key + "|\t" + searchTree.get(key).printStop());
            }
        } else {
            System.out.println("No matching stops were found");
        }
        
    }
    // Searching for all trips with a given arrival time, returning full details of all trips matching the
    //criteria (zero, one or more), sorted by trip id
    private static String tripArrivalTime(String arrivalTime) {
        return "";
    }

    public static void main(String[] args) throws Exception {
        EdgeWeightedGraph graph = new EdgeWeightedGraph();
        boolean run = false;
        do {
            run = menu(graph);
        } while(run);
    }
}
