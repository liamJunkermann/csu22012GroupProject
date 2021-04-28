import java.util.Scanner;

public class App {

    //User interface menu - allows user to chose from avaible functions
    public static boolean menu(){
        System.out.print("Choose from the options below or type 'exit' to quit the program:\n"
        + "To find the shortest path between two bus stops type: 1\n"
        + "To find information on a specific stop type: 2\n"
        + "To find all trips wiht a given arrival time type: 3\n"
        );
        Scanner input = new Scanner(System.in);
        if(input.next().equals("exit")) {
            input.close();
            return false;
        }
        else if(input.hasNextInt() && input.nextInt() > 0 && input.nextInt() < 4) {
            switch(input.nextInt()) {
                case 1:
                    System.out.println("Enter the first(starting) stop:");
                    String stop1 = input.next();
                    System.out.println("Enter the second(destination) stop:");
                    String stop2 = input.next();
                    findShortestPath(stop1, stop2);
                    break;
                case 2:
                    System.out.println("Enter the stop name, or just the first few characters:");
                    busStopSearch(input.next());
                    break;
                case 3:
                    System.out.println("Enter arrival time in format - hh:mm:ss");
                    tripArrivalTime(input.next());
                    break;
                default:
                    input.close();
                    return false;
            }


        }
        else {
            System.out.println("Please enter a vaild input");
        }
        input.close();
        return true;
    }

    //Finding shortest paths between 2 bus stops (as input by the user), returning the list of stops
    //en route as well as the associated “cost”.
    private static String findShortestPath(String stop1, String stop2) {
        return "";
    } 
    //Searching for a bus stop by full name or by the first few characters in the name, using a
    //ternary search tree (TST), returning the full stop information for each stop matching the
    //search criteria (which can be zero, one or more stops)
    private static String busStopSearch(String stopName) {
        return "";
    }
    // Searching for all trips with a given arrival time, returning full details of all trips matching the
    //criteria (zero, one or more), sorted by trip id
    private static String tripArrivalTime(String arrivalTime) {
        return "";
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        boolean run = false;
        do {
            run = menu();
        } while(run);
    }
}
