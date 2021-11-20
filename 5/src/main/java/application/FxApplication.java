package application;

import drawing.DrawingApi;
import drawing.DrawingApiFx;
import graph.Graph;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxApplication extends Application implements GraphDrawingApplication {
    @Override
    public void start(Stage primaryStage) {
        int width = ApplicationConfig.WIDTH;
        int height = ApplicationConfig.HEIGHT;

        primaryStage.setTitle("Hello graphs!");

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        DrawingApi drawingApi = new DrawingApiFx(gc, width, height);
        Graph graph = ApplicationConfig.createGraph(drawingApi);
        graph.readGraph();
        graph.drawGraph();

        Group root = new Group();
        root.getChildren().add(canvas);

        primaryStage.setScene(new Scene(root, Color.WHITE));
        primaryStage.show();
    }

    public void startApplication() {
        Application.launch(this.getClass());
    }
}
