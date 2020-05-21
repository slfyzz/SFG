import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Node implements Comparable{
    // represents adjacent nodes
    private Map<Node, Integer> adjNodes;
    private String name;
    private static int counter = 0;

    Node(String nodeName) {
        adjNodes = new HashMap<>();
        this.name = nodeName;
        if (this.name == null || this.name.length() == 0)
            name = "g" + counter++;
    }

    public void addEdge(Node adjNode, int edgeValue) {
        adjNodes.put(adjNode, adjNodes.containsKey(adjNode) ? adjNodes.get(adjNode) + edgeValue : edgeValue);
    }

    public Set<Map.Entry<Node, Integer>> getAdjacentNodes() {
        return adjNodes.entrySet();
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
        return this.name.equals(((Node)obj).name);
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
