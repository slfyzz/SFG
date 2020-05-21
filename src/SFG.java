import java.util.*;

public class SFG {
    private boolean nodesChanged;
    private List<Node> nodes;
    private int numOfNodes;
    private List<List<Node>> forwardPaths;
    private List<Integer> forwardPathsVals;

    private List<List<Node>> cycles;
    private List<Integer> cyclesVals;

    SFG (int numOfNodes) {
        this.numOfNodes = numOfNodes;
        init();
    }
    SFG(int numOfNodes ,List<Node> nodes) {
        init();
        this.numOfNodes = numOfNodes;
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    private void init() {
        nodes = new ArrayList<>(numOfNodes);
        forwardPaths = new ArrayList<>();
        forwardPathsVals = new ArrayList<>();
        cycles = new ArrayList<>();
        cyclesVals = new ArrayList<>();
        nodesChanged = true;
    }

    public void addNode(Node node) {
        nodes.add(node);
        nodesChanged = true;
    }
    private void clear ()
    {
        forwardPaths.clear();
        cycles.clear();
        cyclesVals.clear();
        forwardPathsVals.clear();
    }

    public List<List<Node>> getForwardPaths() {
        CalcLoopsAndPaths();
        return forwardPaths;
    }

    public List<Integer> getForwardPathsVals() {
        return forwardPathsVals;
    }

    public List<Integer> getCyclesVals() {
        return cyclesVals;
    }

    public List<List<Node>> getCycles() {
        CalcLoopsAndPaths();
        return cycles;
    }

    private void CalcLoopsAndPaths()
    {
        if (nodesChanged) {
            clear();
            dfs(nodes.get(0), new ArrayList<>(), new ArrayList<>(), new HashSet<>());
            nodesChanged = false;
        }
    }

    private void dfs (Node root, List<Node> nodesSoFar, List<Integer> gains, Set<List<Edge>> seenCycles) {

        if (nodesSoFar.size() == 0) {
            nodesSoFar.add(root);
            gains.add(1);
        }
        if (isSink(root)) {
            addForwardPath(nodesSoFar, gains.get(gains.size() - 1));
        }
        Set<Map.Entry<Node, Integer>> adjNodes = root.getAdjacentNodes();
        for (Map.Entry<Node, Integer> entry : adjNodes) {
            int ind = nodesSoFar.indexOf(entry.getKey());
            if (ind == -1) {
                nodesSoFar.add(entry.getKey());
                gains.add(gains.get(gains.size() - 1) * entry.getValue());
                dfs(entry.getKey(), nodesSoFar, gains, seenCycles);
                nodesSoFar.remove(nodesSoFar.size() - 1);
                gains.remove(gains.size() - 1);
            }
            else {
                addCycle(nodesSoFar, entry.getKey(), ind, entry.getValue() * gains.get(gains.size() - 1) / gains.get(ind), seenCycles);
            }
        }
    }

    private void addCycle(List<Node> nodesSoFar, Node repeated, int indOfRepeated, int currGain, Set<List<Edge>> seenCycle) {
        List<Node> cycle = new ArrayList<>(nodesSoFar.subList(indOfRepeated, nodesSoFar.size()));
        cycle.add(repeated);

        List<Edge> edgesOfCycle = nodesToEdges(cycle);
        Collections.sort(edgesOfCycle);
        if (seenCycle.contains(edgesOfCycle))
            return;
        seenCycle.add(edgesOfCycle);
        cycles.add(cycle);
        cyclesVals.add(currGain);
    }

    private List<Edge> nodesToEdges (List<Node> nodes)
    {
        List<Edge> ls = new ArrayList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            Node node = nodes.get(i);
            ls.add(new Edge(node.toString() + nodes.get(i + 1).toString()));
        }
        return ls;
    }

    private void addForwardPath(List<Node> nodesSoFar, int currGain) {
        forwardPaths.add(new ArrayList<>(nodesSoFar));
        forwardPathsVals.add(currGain);
    }

    private boolean isSink(Node node) {
        return nodes.indexOf(node) == nodes.size() - 1;
    }


    private class Edge implements Comparable{

        private String name;
        Edge(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            else if (!(obj instanceof Edge))
            {
                return false;
            }
            return this.name.equals(((Edge) obj).name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Edge))
                return 1;
            return name.compareTo(((Edge) o).name);
        }
    }

}
