package application;

import drawing.DrawingApi;
import graph.Graph;
import graph.ListGraph;
import graph.MatrixGraph;

public class ApplicationConfig {
    public enum GraphType {
        LIST,
        MATRIX
    }
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static GraphType graphType;

    public static Graph createGraph(DrawingApi drawingApi){
        return switch(graphType) {
            case LIST -> new ListGraph(drawingApi);
            case MATRIX -> new MatrixGraph(drawingApi);
        };
    }
}
