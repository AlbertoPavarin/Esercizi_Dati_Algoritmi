import java.util.*;

//Class my entry
class MyEntry {
    private Integer key;
    private String value;
    public MyEntry(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    @Override
    public String toString() {
        return key + " " + value;
    }
}

//Class SkipListPQ
class SkipListPQ {
    private Node head;
    private int levels;
    private double alpha;
    private Random rand;
    private int size;

    public SkipListPQ(double alpha) {
        this.size = 0;
        this.levels = 6;
        this.alpha = alpha;
        this.rand = new Random();
        this.head = new Node(new MyEntry(Integer.MIN_VALUE, ""));
        Node currNode = this.head;
        for (int i = 0; i < levels - 1; i++) {
            Node newNode = new Node(new MyEntry(Integer.MIN_VALUE + i + 1, ""));
            currNode.setBelow(newNode);
            newNode.setAbove(currNode);
            currNode = newNode;
        }
        // this.head.setBelow(new Node(new MyEntry(Integer.MIN_VALUE, "")));
        // Node n1_min = this.head.getBelow();
        // n1_min.setAbove(this.head);
        // n1_min.setNext(new Node(new MyEntry(10, "sdrogo")));
        // n1_min.setBelow(new Node(new MyEntry(Integer.MIN_VALUE, "")));
        // Node n1_10 = n1_min.getNext();
        // n1_10.setPrev(n1_min);
        // n1_10.setNext(new Node(new MyEntry(30, "sdrogo"))); 
        // Node n1_30 = n1_10.getNext();
        // n1_30.setPrev(n1_10);
        // Node n2_min = this.head.getBelow().getBelow();
        // n2_min.setAbove(this.head.getBelow().getBelow());
        // n2_min.setNext(new Node(new MyEntry(10, "sdrogo")));
        // Node n2_10 = n2_min.getNext();
        // n2_10.setPrev(n2_min);
        // n2_10.setAbove(n1_10);
        // n2_10.setNext(new Node(new MyEntry(20, "sdrogo")));
        // Node n2_20 = n2_10.getNext();
        // n2_20.setPrev(n2_10);
        // n2_20.setNext(new Node(new MyEntry(30, "sdrogo")));
        // Node n2_30 = n2_20.getNext();
        // n2_30.setAbove(n1_30);
        // n2_30.setPrev(n2_20);
        // n1_10.setBelow(n2_10);
        // levels = 3;
    }

    public int size() {
	// TO BE COMPLETED   
        return this.size;    
    }

    public Node skipSearch(int k){
        int level = 0;
        System.out.println("Search: ");
        Node currentNode = this.head;
        while (currentNode.getBelow() != null) {
            currentNode = currentNode.getBelow();
            level++;
            while ((currentNode.getNext()) != null) {
                if ( k >= currentNode.getNext().getKey())
                {
                    currentNode = currentNode.getNext();
                }
                else
                    break;
            }
        }
        return currentNode;
    }

    public MyEntry min() {
	// TO BE COMPLETED 
        return new MyEntry(0, "0");
    }

    public int insert(int key, String value) {
        // TO BE COMPLETED
        Node[] nodes = new Node[this.levels];
        nodes[0] = this.head;
        int level = 0;
        System.out.println("Search: ");
        Node currentNode = this.head;
        Node lastVisited = this.head;
        while (currentNode.getBelow() != null) {
            currentNode = currentNode.getBelow();
            level++;
            lastVisited = currentNode;
            while ((currentNode.getNext()) != null) {
                if ( key >= currentNode.getNext().getKey())
                {
                    lastVisited = currentNode;
                    currentNode = currentNode.getNext();
                }
                else
                    break;
            }
            System.out.println("Last visited of lvl "+ level + " : " + lastVisited);
            nodes[level] = lastVisited;
        }
        System.out.println("sdrogo: " + currentNode);
        return 0;
    }

    private int generateEll(double alpha_ , int key) {
        int level = 0;
        if (alpha_ >= 0. && alpha_< 1) {
          while (rand.nextDouble() < alpha_) {
              level += 1;
          }
        }
        else{
          while (key != 0 && key % 2 == 0){
            key = key / 2;
            level += 1;
          }
        }
        return level;
    }

    public MyEntry removeMin() {
	// TO BE COMPLETED 
        return new MyEntry(0, "0");
    }

    public void print() {
        Node currentNode = this.head;
        Node currentHead = this.head;
        currentNode.getNext();
        System.out.println("[ ");
        for (int i = 0; i < levels; i++) {
            System.out.print(" S" + (levels - 1 - i) + " [ ");
            System.out.print(currentNode.toString());
            while ((currentNode = currentNode.getNext()) != null)
                System.out.print(currentNode.toString());
            currentNode = currentHead.getBelow();
            currentHead = currentHead.getBelow();
            System.out.println(" ]");
        }
        System.out.println("]");
    }
}

class Node{
    private static final int NEXT = 0;
    private static final int PREV = 1;
    private static final int ABOVE = 2;
    private static final int BELOW = 3;
    private  MyEntry e;
    private Node[] next; // postion 0, the one on the right, 1 left, 2 up, 3, down

    public Node(){}

    public Node(MyEntry en) {
        this.next = new Node[4];
        this.e = en;
    }

    public int getKey() {
        return this.e.getKey();
    }

    public void setNext(Node n) {
        this.next[NEXT] = n;
    }

    public void setPrev(Node n) {
        this.next[PREV] = n;
    }

    public void setAbove(Node n) {
        this.next[ABOVE] = n;
    }

    public void setBelow(Node n) {
        this.next[BELOW] = n;
    }

    public Node getNext() {
        return this.next[NEXT];
    }

    public Node getPrev() {
        return this.next[PREV];
    }

    public Node getAbove() {
        return this.next[ABOVE];
    }

    public Node getBelow() {
        return this.next[BELOW];
    }

    public String toString(){
        return "( " + this.e.toString() + " ) ";
    }
}

//TestProgram

public class TestProgram {
    public static void main(String[] args) {
        // if (args.length != 1) {
        //     System.out.println("Usage: java TestProgram <file_path>");
        //     return;
        // }

        // try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
        //     String[] firstLine = br.readLine().split(" ");
        //     int N = Integer.parseInt(firstLine[0]);
        //     double alpha = Double.parseDouble(firstLine[1]);
        //     System.out.println(N + " " + alpha);

        //     SkipListPQ skipList = new SkipListPQ(alpha);

        //     for (int i = 0; i < N; i++) {
        //         String[] line = br.readLine().split(" ");
        //         int operation = Integer.parseInt(line[0]);

        //         switch (operation) {
        //             case 0:
		// 	// TO BE COMPLETED 
        //                 break;
        //             case 1:
		// 	// TO BE COMPLETED 
        //                 break;
        //             case 2:
		// 	// TO BE COMPLETED 
        //                 break;
        //             case 3:
		// 	// TO BE COMPLETED 
        //                 break;
        //             default:
        //                 System.out.println("Invalid operation code");
        //                 return;
        //         }
        //     }
        // } catch (IOException e) {
        //     System.out.println("Error reading file: " + e.getMessage());
        // }
        SkipListPQ sl = new SkipListPQ(0.5);
        sl.print();

        sl.insert(1, "asd");
        // Node n = sl.skipSearch(30);
        // System.out.println(n);
    }
}