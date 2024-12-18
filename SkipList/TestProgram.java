import java.io.*;
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
        this.levels = 2;
        this.alpha = alpha;
        this.rand = new Random();
        this.head = new Node(new MyEntry(Integer.MIN_VALUE, ""));
        Node currNode = this.head;
        Node newNode = new Node(new MyEntry(Integer.MIN_VALUE, ""));
        currNode.setBelow(newNode);
        newNode.setAbove(currNode);
    }

    public double getAlpha() { 
        return this.alpha;
    }

    public int size() {
	// TO BE COMPLETED   
        return this.size;    
    }

    public Node skipSearch(int k){
        int level = 0;
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
        Node currNode = this.head;
        while (currNode.getBelow() != null) {
            currNode = currNode.getBelow();          
        }

        if (currNode.getNext() == null)
            return currNode.getEntry();
        return currNode.getNext().getEntry();
    }

    public int insert(int key, String value) {
        int genNmb = this.generateEll(this.alpha, key);
        int olderLevels = this.levels;
        if (genNmb == 0 && this.levels == 1) {
            this.levels += genNmb + 1;
        }
        
        if (genNmb + 1 >= this.levels) {
            this.levels = genNmb + 2;
            generateNewHeads(olderLevels);
        }

        Node[] nodes = new Node[this.levels];
        nodes[0] = this.head;
        int level = 0;
        int nodeCnt = 1;
        Node currentNode = this.head;
        Node lastVisited = this.head;
        while (currentNode.getBelow() != null) {
            currentNode = currentNode.getBelow();
            level++;
            lastVisited = currentNode;
            nodeCnt++;
            while ((currentNode.getNext()) != null) {
                nodeCnt++;
                if ( key >= currentNode.getNext().getKey())
                {
                    lastVisited = currentNode.getNext();
                    currentNode = currentNode.getNext();
                }
                else
                    break;
            }
            nodes[level] = lastVisited;
        }

        // insertion
        Node[] newNodes = new Node[genNmb + 1];
        for (int i = 0; i < genNmb + 1; i++) {
            this.size++;
            Node newNode = new Node(new MyEntry(key, value));
            newNode.setPrev(nodes[levels - i - 1]);
            newNode.setNext(nodes[levels - i - 1].getNext());
            nodes[levels - i - 1].setNext(newNode);
            newNodes[i] = newNode;
            if (i > 0) {
                newNodes[i].setBelow(newNodes[i - 1]);
                newNodes[i - 1].setAbove(newNodes[i]);
            }
        }

        return nodeCnt - (this.levels - olderLevels);
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
        Node currNode = this.head;
        while (currNode.getBelow() != null) {
            currNode = currNode.getBelow();          
        }

        if (currNode.getNext() == null)
            return currNode.getEntry();

        Node prevNode;
        currNode = currNode.getNext();
        while (true) {
            this.size--;
            prevNode = currNode.getPrev();
            if (currNode.getNext() != null) {
                prevNode.setNext(currNode.getNext());
                currNode.getNext().setPrev(prevNode);
            }
            else {
                prevNode.setNext(null);
                if (this.levels != 2) {
                    this.levels--;
                    this.head = this.head.getBelow();
                }
            }

            if (currNode.getAbove() == null)
                break;

            currNode = currNode.getAbove();
        }   

        return currNode.getEntry();
    }

    public void print() {
        Node currentFirstNode = this.head;
        while (currentFirstNode.getBelow() != null)
        currentFirstNode = currentFirstNode.getBelow();

        while (true) { 
            if (currentFirstNode.getNext() == null)
                break;

            currentFirstNode = currentFirstNode.getNext();
            Node currNode = currentFirstNode;
            int cnt = 1;
            while (currNode.getAbove() != null) {
                cnt++;
                currNode = currNode.getAbove();
            }

            System.out.print(currNode.getEntry() + " " + cnt + ", ");
        }

        System.out.println("");
    }


    private void generateNewHeads(int olderLevels) {
        Node newHeadNode;
        for (int i = olderLevels; i < this.levels; i++) {
            newHeadNode = new Node(new MyEntry(Integer.MIN_VALUE, ""));
            newHeadNode.setBelow(this.head);
            this.head.setAbove(newHeadNode);
            this.head = newHeadNode;
        }
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

    public MyEntry getEntry() {
        return this.e;
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
        double tot = 0;
        double insertDone = 0;
        if (args.length != 1) {
            System.out.println("Usage: java TestProgram <file_path>");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            double alpha = Double.parseDouble(firstLine[1]);
            System.out.println(N + " " + alpha);

            SkipListPQ skipList = new SkipListPQ(alpha);

            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                int operation = Integer.parseInt(line[0]);

                switch (operation) {
                    case 0:
                        System.out.println(skipList.min());
                        break;
                    case 1:
                        skipList.removeMin();
                        break;
                    case 2:
                        int nodeCnt = skipList.insert(Integer.parseInt(line[1]), line[2]);
                        tot += nodeCnt;
                        insertDone++;
                        break;
                    case 3:
                        skipList.print();
                        break;
                    default:
                        System.out.println("Invalid operation code");
                        return;
                }
            }

            System.out.println(skipList.getAlpha() + ", " + skipList.size() + ", " + insertDone + ", " + (tot/insertDone) + "");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // SkipListPQ sl = new SkipListPQ(0.5);
        // sl.printBello();

        // tot += sl.insert(1, "asd");
        // insertDone++;
        // tot += sl.insert(12, "babbo");
        // insertDone++;
        // tot += sl.insert(12, "babbo2");
        // sl.printBello();
        // insertDone++;
        // tot += sl.insert(10, "a");
        // sl.printBello();
        // insertDone++;
        // tot += sl.insert(200, "v");
        // insertDone++;
        // tot += sl.insert(300, "b");
        // insertDone++;
        // tot += sl.insert(400, "c");
        // insertDone++;
        // sl.printBello();
        // sl.removeMin();
        // sl.removeMin();
        // sl.removeMin();
        // sl.printBello();
        // sl.print();

        // System.out.println(sl.getAlpha() + ", " + sl.size() + ", " + insertDone + ", " + ((double)tot/(double)insertDone) + "");
    }
}