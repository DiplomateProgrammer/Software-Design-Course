package graph;

import drawing.DrawingApi;

import java.util.Scanner;

public class MatrixGraph extends Graph {
    private boolean[][] matrix;
    private int numVertices;

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        numVertices = in.nextInt();
        matrix = new boolean[numVertices][numVertices];

        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrix[i][j] = in.nextInt() == 1;
            }
        }
    }

    @Override
    public void drawGraph() {
        init(numVertices);
        for (int i = 0; i < numVertices - 1; i++) {
            drawVertex(i);
            for (int j = i + 1; j < numVertices; j++) {
                if (matrix[i][j]) drawEdge(i, j);
            }
        }
    }
}