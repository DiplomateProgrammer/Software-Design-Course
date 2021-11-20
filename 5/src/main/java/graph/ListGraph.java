package graph;

import drawing.DrawingApi;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ListGraph extends Graph{
    private int numVertices;
    private int[][] edges;

    public ListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter number of edges: ");
        int numEdges = in.nextInt();

        System.out.println("Enter edges as pairs of connected vertices: ");
        edges = new int[numEdges][2];
        Set<Integer> vertices = new HashSet<>();
        for (int i = 0; i < numEdges; i++) {
            edges[i][0] = in.nextInt();
            edges[i][1] = in.nextInt();
            vertices.add(edges[i][0]);
            vertices.add(edges[i][1]);
        }

        numVertices = vertices.size();
    }

    @Override
    public void drawGraph() {
        init(numVertices);
        for (int[] pair : edges) {
            drawEdge(pair[0], pair[1]);
        }
    }
}
