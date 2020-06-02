package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Mason;
import model.Node;
import model.SFG;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GUI {
    private static GUI single = null;
    private GridPane paneForm;
    private Pane result;
    private Label resultPanel;
    private SFG sfg;
    private Mason mason;
    private Graph graph;
    private GUI(int height, int width)
    {
        graph = new MultiGraph("model.SFG");
        graph.addAttribute("ui.antialias"); //this does something...
        graph.addAttribute("ui.quality"); //this sounds less important...
        graph.setAttribute("ui.stylesheet", "node:clicked {\n" +
                "    fill-color: purple;\n" +
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
        graph.setAutoCreate(true);
        paneForm = new GridPane();
        result = new Pane();
        resultPanel = new Label("Results :  ............");
        result.getChildren().add(resultPanel);
        sfg = new SFG(15);
        mason = new Mason(sfg);
        initialize(paneForm, height, width);

    }

    private void initialize(GridPane form, int Height, int Width)
    {
        // specify the number of Columns
        //form.setPrefSize(Width, Height);
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(25);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(25);
        ColumnConstraints col3Constraints = new ColumnConstraints();
        col3Constraints.setPercentWidth(25);
        ColumnConstraints col4Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(25);
        form.getColumnConstraints().addAll(col1Constraints,col2Constraints,col3Constraints, col4Constraints);
        form.setStyle("-fx-background-color: #ffffff");

        // some params
        List<String> ls = new ArrayList<>();

        int row = 2;
        int col = 0;
        int offset = 5;


        // create Node
        ls.add("Node");
        createForm("Insert", ls, "Insert a new Node", form, row, col);
        row += ls.size() + offset;


        //remove Node
        createForm("Remove", ls, "Remove a Node", form, row, col);
        row += ls.size() + offset;

        //Create an edge
        ls.clear();
        ls = new ArrayList<>(Arrays.asList("From", "To", "Weight"));
        createForm("Insert", ls, "Insert an Edge", form, row, col);
        row += ls.size() + offset;


        //remove an edge
        ls.clear();
        ls = new ArrayList<>(Arrays.asList("From", "To"));
        createForm("Remove", ls, "Remove an Edge", form, row, col);
        row += ls.size() + offset;

        // src and dest
        ls.clear();
        ls = new ArrayList<>(Arrays.asList("input Node", "Output Node"));
        createForm("Enter", ls, "I/O", form, row, col);
        row += ls.size() + offset;


        // Show Graph button
        Button showGraph = new Button("ShowGraph");
        buttonSetting(showGraph);
        showGraph.setOnMouseClicked(e->graph.display());
        form.add(showGraph, 0, row + 1, 2, 2);

        // Calculate Button
        Button Calc = new Button("Calculate");
        buttonSetting(Calc);
        form.add(Calc, 2, row + 1, 2, 2);
        Calc.setOnMouseClicked(e->{
            resultPanel.setText(resultMason());
        });

    }

    // Print in the right of screen the result String
    private String resultMason()
    {
        StringBuilder ans = new StringBuilder();
        try {
            ans.append(mason.Calculate(sfg)).append("\n");
        }
        catch (NullPointerException e) {
            return "Make Sure you entered The Output/Input Nodes correctly";
        }

        List<List<Node>> forwardPaths = sfg.getForwardPaths();
        for (int i = 0; i < forwardPaths.size(); i++) {
            List<Node> ls = forwardPaths.get(i);
            ans.append("ForwardPath ").append(i + 1).append(": ").append(ls.toString()).append("\n");
        }
        ans.append("\n");
        List<List<Node>> cycles = sfg.getCycles();
        for (int i = 0; i < cycles.size(); i++) {
            List<Node> ls = cycles.get(i);
            ans.append("Cycle ").append(i + 1).append(": ").append(ls.toString()).append("\n");
        }

        List<List<List<Integer>>> allNonTouchedLoops = mason.allNonTouchedLoops;
        for (int i = 0; i < allNonTouchedLoops.size(); i++) {
            List<List<Integer>> ls = allNonTouchedLoops.get(i);
            if (ls.size() == 0)
                break;
            ans.append(i + 1).append(" Non Touched Loops  ");
            for (int j = 0; j < ls.size(); j++) {
                ans.append(" ").append(j + 1).append(": ");
                for (int z = 0; z < ls.get(j).size(); z++)
                {
                    ans.append("Cycle ").append(ls.get(j).get(z) + 1).append(" ");
                }
                ans.append("\n");
            }
        }
        return ans.toString();
    }

    // common Setting for a button
    private void buttonSetting(Button button)
    {
        button.setBackground(new Background(new BackgroundFill(Color.gray(0.1,0.5),null, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setWrapText(true);
        button.setTextAlignment(TextAlignment.JUSTIFY);
    }


    // create a form Settings
    private void createForm (String buttonsName, List<String> TextNames, String title, GridPane gridPane, int row, int col)
    {

        Label Title = new Label(title);
        Pane spring = new Pane();
        spring.minHeightProperty().bind(Title.heightProperty());
        gridPane.add(spring, col, row++, 4, 1);

        Title.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        Title.setWrapText(true);
        Title.setTextAlignment(TextAlignment.JUSTIFY);
        Title.setStyle("-fx-font: 24 arial;");
        Title.setMaxHeight(Double.MAX_VALUE);

        spring = new Pane();
        spring.minHeightProperty().bind(Title.heightProperty());
        gridPane.add(Title, col, row++, 4, 1);
        gridPane.add(spring, col, row++, 4, 1);

        List<TextField> fields = new ArrayList<>();

        for (int i = 0; i < TextNames.size(); i++) {
            String textfield = TextNames.get(i);
            TextField field = new TextField();
            fields.add(field);
            field.setPromptText(textfield);
            field.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
            field.setStyle("-fx-font: 18 arial;");
            field.setMaxHeight(Double.MAX_VALUE);
            gridPane.add(field, col, row + i, 3, 1);
        }

        Button push = new Button(buttonsName);
        buttonSetting(push);

        gridPane.add(push, col + 3, row + TextNames.size() - 1, 1, 1);

        push.setOnMouseClicked(event -> setButtonJob(push, Title, fields));
    }

    // Button property (on action)
    private void setButtonJob(Button button, Label label, List<TextField> ls)
    {

        if (button.getText().equalsIgnoreCase("insert"))
        {
            if (label.getText().contains("Node")) {
                String nodeName = sfg.addNode(new Node(ls.get(0).getText()));
                graph.addNode(nodeName).addAttribute("ui.label", nodeName);
            }
            else {
                try {
                    SFG.Edge edge = sfg.addEdge(ls.get(0).getText(), ls.get(1).getText(), Double.valueOf(ls.get(2).getText()));
                    if (graph.getNode(edge.getSrc()) == null)
                        graph.addNode(edge.getSrc()).addAttribute("ui.label", edge.getSrc());
                    if (graph.getNode(edge.getDest()) == null)
                        graph.addNode(edge.getDest()).addAttribute("ui.label", edge.getDest());
                    System.out.println(edge.getSrc() + " " + edge.getDest() + " " + edge.getWeight() + " " + edge.toString());
                    graph.addEdge(edge.toString(), edge.getSrc(), edge.getDest(), true).addAttribute("ui.label", edge.getWeight());
                }
                catch (Exception e) {
                    resultPanel.setText("Check your input again !!");
                }
            }
        }
        else if (button.getText().equalsIgnoreCase("Enter"))
        {
            try {
                sfg.setSrc(new Node(ls.get(0).getText()));
                sfg.setDist(new Node(ls.get(1).getText()));
            }
            catch (NullPointerException e) {
                resultPanel.setText(e.getMessage());
            }
        }
        else {
            try {
                if (label.getText().contains("Node")) {
                    sfg.removeNode(new Node(ls.get(0).getText()));
                    graph.removeNode(ls.get(0).getText());

                } else {
                    sfg.removeEdge(ls.get(0).getText(), ls.get(1).getText());
                    graph.removeEdge(ls.get(0).getText() , ls.get(1).getText());

                }
            }
            catch (NullPointerException e) {
                System.out.println(e.getMessage());
                resultPanel.setText(e.getMessage());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                resultPanel.setText("Internal Error, Try Again");
            }
        }
        clearText(ls);
    }
    public static Pane getGUIform(int height, int width) {
        if (single == null) {
            single = new GUI(height, width);
        }
        return single.paneForm;
    }
    public static Pane getResult(int height, int width) {
        if (single == null) {
            single = new GUI(height, width);
        }
        return single.result;
    }

    private void clearText (List<TextField> ls) {
        for (TextField textField : ls)
            textField.setText("");
    }

}
