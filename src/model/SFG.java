package model;

import java.util.*;

public class SFG {
    private boolean nodesChanged;

    private Node src,dist;
    private List<Node> nodes;
    private HashMap<String, Node> searchNodes;
    private int numOfNodes;
    private List<List<Node>> forwardPaths;
    private List<Double> forwardPathsVals;

    private List<List<Node>> cycles;
    private List<Double> cyclesVals;

    public SFG(int numOfNodes) {
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
        searchNodes = new HashMap<>();
        nodes = new ArrayList<>(numOfNodes);
        forwardPaths = new ArrayList<>();
        forwardPathsVals = new ArrayList<>();
        cycles = new ArrayList<>();
        cyclesVals = new ArrayList<>();
        nodesChanged = true;
    }

    public String addNode(Node node) {
        if (searchNodes.containsKey(node.toString()))
            node.changeName();
        nodes.add(node);
        searchNodes.put(node.toString(), node);
        nodesChanged = true;
        return node.toString();
    }

    public void removeNode (Node node) {
        if (searchNodes.containsKey(node.toString())) {
            node = searchNodes.get(node.toString());
            for (Node no : nodes){
                no.deleteEdge(node);
            }
            nodesChanged = true;
            nodes.remove(node);
            searchNodes.remove(node.toString());
            if (node.equals(src))
                src = null;
            else if (node.equals(dist))
                dist = null;
        }
        else
            throw new NullPointerException("Node " + node.toString() + "Not Found");
    }
    public Edge addEdge(String from, String to, double weight)
    {
        if (!searchNodes.containsKey(from))
            from = this.addNode(new Node(from));
        if (!searchNodes.containsKey(to))
            to = this.addNode(new Node(to));
        searchNodes.get(from).addEdge(searchNodes.get(to), weight);
        nodesChanged = true;
        return new Edge(from, to, searchNodes.get(from).getWeight(searchNodes.get(to)));
    }

    public void removeEdge(String from, String to)
    {
        if (!searchNodes.containsKey(from) || !searchNodes.containsKey(to))
            throw new NullPointerException("One of the nodes you entered doesn't exist");
        searchNodes.get(from).deleteEdge(searchNodes.get(to));
        nodesChanged = true;
    }
    private void clear () {
        forwardPaths.clear();
        cycles.clear();
        cyclesVals.clear();
        forwardPathsVals.clear();
    }

    public List<List<Node>> getForwardPaths() {
        CalcLoopsAndPaths();
        return forwardPaths;
    }

    public List<Double> getForwardPathsVals() {
        return forwardPathsVals;
    }

    public List<Double> getCyclesVals() {
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
            if (src == null || !searchNodes.containsKey(src.toString()))
                throw new NullPointerException("Specify The Input Node");
            if (dist == null || !searchNodes.containsKey(dist.toString()))
                throw new NullPointerException("Specify The Output Node");
            dfs(src , new ArrayList<>(), new ArrayList<>(), new HashSet<>());
            nodesChanged = false;
        }
    }

    private void dfs (Node root, List<Node> nodesSoFar, List<Double> gains, Set<List<Edge>> seenCycles) {

        if (nodesSoFar.size() == 0) {
            nodesSoFar.add(root);
            gains.add(1.0);
        }
        if (isSink(root)) {
            addForwardPath(nodesSoFar, gains.get(gains.size() - 1));
        }
        Set<Map.Entry<Node, Double>> adjNodes = root.getAdjacentNodes();
        for (Map.Entry<Node, Double> entry : adjNodes) {
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


    private void addCycle(List<Node> nodesSoFar, Node repeated, int indOfRepeated, double currGain, Set<List<Edge>> seenCycle) {
        List<Node> cycle = new ArrayList<>(nodesSoFar.subList(indOfRepeated, nodesSoFar.size()));
        cycle.add(repeated);

        List<Edge> edgesOfCycle = nodesToEdges(cycle);
        //System.out.println(edgesOfCycle);
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
            ls.add(new Edge(node.toString() , nodes.get(i + 1).toString(), node.getWeight(nodes.get(i + 1))));
        }
        return ls;
    }

    private void addForwardPath(List<Node> nodesSoFar, double currGain) {
        forwardPaths.add(new ArrayList<>(nodesSoFar));
        forwardPathsVals.add(currGain);
    }

    private boolean isSink(Node node) {
        return node.equals(dist);
    }

    private boolean isSrc(Node node) {
        return node.equals(src);
    }


    public void setSrc(Node src) {
        if (searchNodes.containsKey(src.toString())) {
            this.src = searchNodes.get(src.toString());
            this.nodesChanged = true;
        }
        else
            throw new NullPointerException("Input Node doesn't exist");
    }

    public void setDist(Node dist) {
        if (searchNodes.containsKey(dist.toString())) {
            this.dist = searchNodes.get(dist.toString());
            this.nodesChanged = true;
        }
        else
            throw new NullPointerException("Input Node doesn't exist");
    }

    public class Edge implements Comparable{
        private String Node1, Node2;
        private String name;
        private double weight;
        Edge(String n1 , String n2, double w) {
            this.name = n1 + n2;
            Node1 = n1;
            Node2 = n2;
            weight = w;
        }

        public double getWeight() {
            return weight;
        }

        public String getSrc() {
            return Node1;
        }

        public String getDest() {
            return Node2;
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

        @Override
        public String toString() {
            return name;
        }
    }

}
