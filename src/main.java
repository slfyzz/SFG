public class main {
    public static void main(String[] args) {

       /* SFG sfg = new SFG(6);
        Node src = new Node("g1");
        Node hidden = new Node("g2");
        Node hidden2 = new Node("g3");
        Node hidden3 = new Node("g4");
        Node hidden4 = new Node("g5");
        Node hidden5 = new Node("g6");
        Node hidden6 = new Node("g7");
        Node sink = new Node("g8");
        sfg.addNode(src);
        sfg.addNode(hidden);
        sfg.addNode(hidden2);
        sfg.addNode(hidden3);
        sfg.addNode(hidden4);
        sfg.addNode(hidden5);
        sfg.addNode(hidden6);
        sfg.addNode(sink);
        src.addEdge(hidden, 1);
        hidden.addEdge(hidden2, 2);
        hidden.addEdge(hidden6, 15);
        hidden2.addEdge(hidden, -2);
        hidden.addEdge(hidden3, 4);
        hidden2.addEdge(hidden3, 2);
        hidden3.addEdge(hidden2, -2);
        hidden3.addEdge(hidden4, 2);
        hidden4.addEdge(hidden3, -2);
        hidden4.addEdge(hidden5, 2);
        hidden5.addEdge(hidden6, 2);
        hidden6.addEdge(sink, 2);
        hidden6.addEdge(hidden6, 2);
        hidden6.addEdge(hidden4, 2);
        hidden6.addEdge(hidden5, -1);
        hidden5.addEdge(hidden4, -2);*/
        SFG sfg = new SFG(3);
        Node src = new Node("g1");
        Node hidden = new Node("g2");
        Node sink = new Node("g3");
        sfg.addNode(src);
        sfg.addNode(hidden);
        sfg.addNode(sink);
        src.addEdge(hidden, 2);
        hidden.addEdge(sink, 2);
        sink.addEdge(src, 2);
        hidden.addEdge(src, 2);
        sink.addEdge(hidden, 2);
        src.addEdge(sink, 1);
        Mason mason = new Mason(sfg);
        System.out.println(sfg.getForwardPaths());
        System.out.println(sfg.getCycles());
        System.out.println(mason.Calculate(sfg));
    //    System.out.println(mason.calculateDelta(sfg.getNodes(), sfg.getCycles(), sfg.getCyclesVals()));

        for (int i = 0; i < mason.allNonTouchedLoops.size(); i++)
        {
            System.out.println(i);
            for (int j = 0; j < mason.allNonTouchedLoops.get(i).size(); j++)
            {
                System.out.println(mason.allNonTouchedLoops.get(i).get(j));

            }
        }

    }
}
