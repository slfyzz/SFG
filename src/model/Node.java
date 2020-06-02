package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Node implements Comparable{
    // represents adjacent nodes
    private Map<Node, Double> adjNodes;
    private String name;
    private static int counter = 0;

    public Node(String nodeName) {
        adjNodes = new HashMap<>();
        this.name = nodeName;
        if (this.name == null || this.name.length() == 0)
            name = "g" + counter++;
    }
    public void deleteEdge(Node node) {
        adjNodes.remove(node);
    }
    public double getWeight(Node node)
    {
        if (adjNodes.containsKey(node))
            return adjNodes.get(node);
        return Double.MAX_VALUE;
    }

    public void addEdge(Node adjNode, double edgeValue) {
        adjNodes.put(adjNode, adjNodes.containsKey(adjNode) ? adjNodes.get(adjNode) + edgeValue : edgeValue);
    }

    public Set<Map.Entry<Node, Double>> getAdjacentNodes() {
        return adjNodes.entrySet();
    }

    public void changeName() {
        name = counter + name + counter;
        counter++;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Node))
            return false;
        return this.name.equals(((Node) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(o.toString());
    }
}
