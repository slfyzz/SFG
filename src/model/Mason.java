package model;

import java.util.*;

public class Mason {
    private SFG graph;
    public List<List<List<Integer>>> allNonTouchedLoops;
    private List<List<Double>> allNonTouchedLoopsGain;
    public Mason(SFG givenGraph) {
        graph = givenGraph;
        allNonTouchedLoops = new ArrayList<>();
        allNonTouchedLoopsGain = new ArrayList<>();
    }

    public double calculateDelta(List<Node> nodes, List<List<Node>> cycles, List<Double> cyclesValue)  {
        Map<Integer, Set<Integer>> nonTouchedLoops = new HashMap<>();
        List<List<Integer>> twosList = new ArrayList<>();
        List<Double> twosListGain = new ArrayList<>();
        allNonTouchedLoops.add(twosList);
        allNonTouchedLoopsGain.add(twosListGain);
        double delta = 1;
        for (int i = 0; i < cycles.size(); i++) {
            delta -= cyclesValue.get(i);
            List<Integer> twos = new ArrayList<>();
            twos.add(i);
            for (int j = i + 1; j < cycles.size(); j++) {
                if (!Touched(cycles.get(i), cycles.get(j))) {
                    double gain = cyclesValue.get(i) * cyclesValue.get(j);
                    twosListGain.add(gain);
                    delta += (gain);
                    insertIntoMap(nonTouchedLoops, i, j);
                    insertIntoMap(nonTouchedLoops, j, i);
                    twos.add(j);
                    twosList.add(new ArrayList<>(twos));
                    twos.remove(twos.size() - 1);
                }
            }
        }
        delta += createTheSegmentsOfNonTouchedLoops(nonTouchedLoops, cycles, cyclesValue);
        return delta;
    }

    // create from the 2 non-touched loops till nodesSize - 1
    private double createTheSegmentsOfNonTouchedLoops(Map<Integer, Set<Integer>> nonTouchedLoops,
                                                      List<List<Node>> cycles,
                                                      List<Double> cyclesValue) {
        double deltaDash = 0;
        int sign = -1;
        for (int i = 0; i < cycles.size() - 1; i++) {
            List<List<Integer>> ithList = new ArrayList<>();
            List<Double> ithListGain = new ArrayList<>();
            for (int j = 0; j < allNonTouchedLoops.get(i).size(); j++)
            {
                int mx = Collections.max(allNonTouchedLoops.get(i).get(j));
                for (int z = mx + 1; z < cycles.size(); z++)
                {
                    if (shouldBeAdded(z, allNonTouchedLoops.get(i).get(j), nonTouchedLoops))
                    {
                        double gain = allNonTouchedLoopsGain.get(i).get(j) * cyclesValue.get(z) * sign;
                        ithListGain.add(gain);
                        deltaDash += gain;

                        List<Integer> ls = new ArrayList<>(allNonTouchedLoops.get(i).get(j));
                        ls.add(z);
                        ithList.add(ls);
                    }
                }
            }
            if (ithList.size() == 0)
                break;
            sign *= -1;
            allNonTouchedLoops.add(ithList);
            allNonTouchedLoopsGain.add(ithListGain);
        }
        return deltaDash;
    }



    private boolean shouldBeAdded(int newCycle, List<Integer> nonNthTouchedCycles,  Map<Integer, Set<Integer>> nonTouchedLoops) {
        if (!nonTouchedLoops.containsKey(newCycle))
            return false;
        Set<Integer> newCycleSet = nonTouchedLoops.get(newCycle);
        for (Integer nonNthTouchedCycle : nonNthTouchedCycles) {
            if (!newCycleSet.contains(nonNthTouchedCycle))
                return false;
        }
        return true;
    }

    private void insertIntoMap(Map<Integer, Set<Integer>> map, int key, int val) {
        if (map.containsKey(key))
            map.get(key).add(val);
        else {
            Set<Integer> st = new HashSet<>();
            st.add(val);
            map.put(key, st);
        }
    }
    private boolean Touched(List<Node> cycle1, List<Node> cycle2) {
        return !Collections.disjoint(cycle1, cycle2);
    }

    private List<Integer> cycleNotTouchingIthPath(List<Node> forwardPath, List<List<Node>> cycles)
    {
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i < cycles.size(); i++) {
            List<Node> cylce = cycles.get(i);
            if (!Touched(forwardPath, cylce)) {
                ls.add(i);
            }
        }
        return ls;
    }
    private double calculateithDelta(List<Node> forwardPath, List<List<Node>> cycles, List<Double>cyclesValue) {
        List<Integer> loopsNonTouchingPath = cycleNotTouchingIthPath(forwardPath, cycles);
        double deltaI = 1;
        if (loopsNonTouchingPath.size() == 0)
            return 1;

        for (Integer loop : loopsNonTouchingPath) {
            deltaI -= cyclesValue.get(loop);
        }
        int sign = 1;
        for (int i = 0; i < allNonTouchedLoops.size() && i + 2 <= loopsNonTouchingPath.size(); i++) {
            List<List<Integer>> segment = allNonTouchedLoops.get(i);
            for (int j = 0; j < segment.size(); j++) {
                if (loopsNonTouchingPath.containsAll(segment.get(j))) {
                     deltaI += (sign * allNonTouchedLoopsGain.get(i).get(j));
                }
            }
            sign *= -1;
        }
        return deltaI;
    }

    public double Calculate(SFG graph) {
        this.allNonTouchedLoops.clear();
        this.allNonTouchedLoopsGain.clear();
        double totalDelta = calculateDelta(graph.getNodes(), graph.getCycles(), graph.getCyclesVals());
        double sigma = 0;
        for (int i = 0; i < graph.getForwardPaths().size(); i++) {
            sigma += (graph.getForwardPathsVals().get(i) * calculateithDelta(graph.getForwardPaths().get(i), graph.getCycles(), graph.getCyclesVals()));
        }
        return sigma / totalDelta;
    }
}
