package graph;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		new support.graph.GraphVisualizer(stage, null,
				"/course/cs0160/lib/graph-images/Dragon.jpg");

		/*
		 * EXTRA CREDIT ONLY: Uncomment the line below and comment out the line
		 * above to use the visualizer with your extra credit graph with the
		 * visualizer. *
		 */
//		 String extraCreditGraph = "EdgeSetGraph";
//		 new support.graph.GraphVisualizer(stage, extraCreditGraph,
//		 "/course/cs0160/lib/graph-images/Dragon.jpg");

	}

	public static void main(String[] args) {
		launch(args);
	}

}
