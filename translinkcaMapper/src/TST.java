import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Adapted from TST by Sedgwick and Wayne
 * https://algs4.cs.princeton.edu/52trie/TST.java.html
 */

public class TST {
    private int n;              // size
    private Node root;

    private static class Node {
        private char c;                     // character
        private Node left, mid, right;      // left, middle, and right subtries
        private Stop val;                   // value associated with string
    }

    public TST() {
    }

    public TST(String filename) {
        fileToTST(filename);
    }

    private void fileToTST(String filename) {
        int lineCount = 0;
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            lineCount++;
            while(scanner.hasNext()) {
                if(scanner.hasNextInt()) {
                    int stopNum = Integer.parseInt(scanner.next());
                    scanner.next(); // handling stop codes
                    String stopName = scanner.next();
                    String stopDesc = scanner.next();
                    Stop currStop = new Stop(stopNum, stopName, stopDesc);
                    put(currStop.stopName, currStop);
                    scanner.nextLine();
                    lineCount++;
                }
            }
            System.out.println(lineCount);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            root=null;
            n = 0;
        } catch (NumberFormatException e) {
            System.out.println(e + " on line " + lineCount);
        }

    }

    public int size() {
        return n;
    }

     public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    public Stop get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    public void put(String key, Stop val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) n++;
        else if(val == null) n--;       // delete existing key
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Stop val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d);
        else if (c > x.c)               x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }
     
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != null) queue.enqueue(prefix.toString() + x.c);
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    /* public static void main(String[] args) {
        TST searchTree = new TST("translinkcaMapper/src/stopsDescs.txt");
        // System.out.println("|KEY|\t|STOP NUM|\t|STOP NAME|\t|STOP DESCRIPTION|");
        // System.out.println("--------------------------------------------------------------------------");
        // for(String key : searchTree.keys()) {
        //     System.out.println(key + "|\t" + searchTree.get(key).printStop());
        // }
        System.out.println(searchTree.n);
        for(String key : searchTree.keysWithPrefix("HWY")){
            System.out.println(key);
        }
        System.out.println(searchTree.get(searchTree.keysWithPrefix("HWY").iterator().next()).printStop());
    } */
}