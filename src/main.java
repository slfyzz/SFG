/**

it was for Testing



**/







/*import model.Mason;
import model.Node;
import model.SFG;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;


public class main {
    public static void main(String[] args) {
        Graph graph = new MultiGraph("model.SFG");
        graph.addAttribute("ui.antialias"); //this does something...
        graph.addAttribute("ui.quality"); //this sounds less important...
        graph.setAttribute("ui.stylesheet", "node:clicked {\n" +*/
             /*   "    fill-color: purple;\n" +
                "    text-size:    16;\n" +
                "    text-style:   bold;\n" +
                "    text-color:   #FFF;\n" +
                "    text-alignment: at-right; \n" +
                "    text-padding: 3px, 2px; \n" +
                "    text-background-mode: rounded-box; \n" +
                "    text-background-color: #A7CC; \n" +
                "    text-color: white; \n" +
                "    text-offset: 5px, 0px; \n" +
                "}\n" +
                "\n" +
                "node {\n" +
                "    size:         20px;\n" +
                "    shape:        circle;\n" +
                "    fill-color:   #8facb4;\n" +
                "    stroke-mode:  plain;\n" +
                "    stroke-color: black;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "edge {\n" +
                "    size:           4px;\n" +
                "    fill-mode:      plain;\n" +
                "    shape:          angle;\n" +
                "    text-size:      15px;\n" +
                "}"); //get some style

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.setStrict(false);
        graph.setAutoCreate( true );
        SFG sfg = new SFG(5);
        //model.Node src = new model.Node("R");
        model.Node hidden = new Node("g1");
        model.Node hidden2 = new model.Node("g2");
        model.Node hidden3 = new model.Node("g3");
        model.Node hidden4 = new Node("g4");
        model.Node hidden5 = new model.Node("g5");
        model.Node hidden6 = new model.Node("g6");
        model.Node hidden7 = new model.Node("C");
        //sfg.addNode(src);
        sfg.addNode(hidden);
        sfg.addNode(hidden2);
        sfg.addNode(hidden3);
        sfg.addNode(hidden4);
        sfg.addNode(hidden5);
        sfg.addNode(hidden6);
        sfg.addNode(hidden7);
        hidden.addEdge(hidden2, 1);
        hidden2.addEdge(hidden3, 5);
        hidden2.addEdge(hidden6, 10);
        hidden3.addEdge(hidden4, 10);
        hidden4.addEdge(hidden3, -1);
        hidden4.addEdge(hidden5, 2);
        hidden5.addEdge(hidden2, -1);
        hidden5.addEdge(hidden4, -2);
        hidden6.addEdge(hidden6, -1);
        hidden6.addEdge(hidden5, 2);
        hidden5.addEdge(hidden7, 1);
/*
          model.Node src = new model.Node("g1");
        model.Node hidden = new model.Node("g2");
        model.Node hidden2 = new model.Node("g3");
        model.Node hidden3 = new model.Node("g4");
        model.Node hidden4 = new model.Node("g5");
        model.Node hidden5 = new model.Node("g6");
        model.Node hidden6 = new model.Node("g7");
        model.Node sink = new model.Node("g8");
        sfg.addNode(src);
        sfg.addNode(hidden);
        sfg.addNode(hidden2);
        sfg.addNode(hidden3);
        sfg.addNode(hidden4);
        sfg.addNode(hidden5);
        sfg.addNode(hidden6);
        sfg.addNode(sink);
        src.addEdge(hidden, 1);
        graph.addEdge("G1G2", "G1", "G2", true).addAttribute("ui.label", "HEY");
        s.attachToEdge("G1G2");
        s.addAttribute("het");
        hidden.addEdge(hidden2, 2);
        graph.addEdge("G2G3", "G2", "G3", true);
        hidden.addEdge(hidden6, 15);
        graph.addEdge("G2G7", "G2", "G7", true);
        hidden2.addEdge(hidden, -2);
        graph.addEdge("G3G2", "G3", "G2", true);
        hidden.addEdge(hidden3, 4);
        graph.addEdge("G2G4", "G2", "G4", true);
        hidden2.addEdge(hidden3, 2);
        graph.addEdge("G3G4", "G3", "G4", true);
        hidden3.addEdge(hidden2, -2);
        graph.addEdge("G4G3", "G4", "G3", true);
        hidden3.addEdge(hidden4, 2);
        graph.addEdge("G4G5", "G4", "G5", true);
        hidden4.addEdge(hidden3, -2);
        graph.addEdge("G5G4", "G5", "G4", true);
        hidden4.addEdge(hidden5, 2);
        graph.addEdge("G5G6", "G5", "G6", true);
        hidden5.addEdge(hidden6, 2);
        graph.addEdge("G6G7", "G6", "G7", true);
        hidden6.addEdge(sink, 2);
        graph.addEdge("G7G8", "G7", "G8", true);
        hidden6.addEdge(hidden6, 2);
        graph.addEdge("G7G7", "G7", "G7", true);
        hidden6.addEdge(hidden4, 2);
        graph.addEdge("G7G5", "G7", "G5", true);
        hidden6.addEdge(hidden5, -1);
        graph.addEdge("G7G6", "G7", "G6", true);
        hidden5.addEdge(hidden4, -2);
        graph.addEdge("G6G5", "G6", "G5", true);
        graph.display();
        model.SFG sfg = new model.SFG(3);
        model.Node src = new model.Node("g1");
        model.Node hidden = new model.Node("g2");
        model.Node sink = new model.Node("g3");
        sfg.addNode(src);
        sfg.addNode(hidden);
        sfg.addNode(sink);
        src.addEdge(hidden, 2);
        hidden.addEdge(sink, 2);
        sink.addEdge(src, 2);
        hidden.addEdge(src, 2);
        sink.addEdge(hidden, 2);
        src.addEdge(sink, 1);*/
        //System.out.println(sfg.nodes.indexOf(new Node("g4")));
   /*     Mason mason = new Mason(sfg);
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
}*/
