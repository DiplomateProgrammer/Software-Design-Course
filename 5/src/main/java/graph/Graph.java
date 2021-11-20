package graph;

import drawing.DrawingApi;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Graph {
    private final DrawingApi drawingApi;
    private final Map<Integer, Map.Entry<Double, Double>> vertexCoordinates = new HashMap<>();
    private static double VERTEX_RADIUS = 10;
    private static double VERTEX_CIRCLE_MARGIN = 50;
    private double radius;
    private double centerX;
    private double centerY;
    private double curAngle;
    private double deltaAngle;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void readGraph();

    public abstract void drawGraph();

    protected void init(int countVertex) {
        long width = drawingApi.getDrawingAreaWidth();
        long height = drawingApi.getDrawingAreaHeight();

        radius = ((double) Math.min(width, height)) / 2.0 - VERTEX_CIRCLE_MARGIN;
        centerX = (double) width / 2;
        centerY = (double) height / 2;
        curAngle = 0;
        deltaAngle = 2 * Math.PI / countVertex;
    }

    protected void drawVertex(int v) {
        if (!vertexCoordinates.containsKey(v)) {
            double vertexX = centerX + radius * Math.cos(curAngle);
            double vertexY = centerY + radius * Math.sin(curAngle);

            drawingApi.drawCircle(vertexX, vertexY, VERTEX_RADIUS);
            vertexCoordinates.put(v, new AbstractMap.SimpleEntry<>(vertexX, vertexY));
            curAngle += deltaAngle;
        }
    }

    private void connectVertex(int v, int u) {
        Map.Entry<Double, Double> start = vertexCoordinates.get(v);
        Map.Entry<Double, Double> end = vertexCoordinates.get(u);
        drawingApi.drawLine(start.getKey(), start.getValue(), end.getKey(), end.getValue());
    }

    protected void drawEdge(int v, int u) {
        drawVertex(v);
        drawVertex(u);
        connectVertex(v, u);
    }
}
