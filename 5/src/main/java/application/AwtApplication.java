package application;

import drawing.DrawingApiAwt;
import graph.Graph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

public class AwtApplication implements GraphDrawingApplication {
    class AwtFrame extends Frame {
        private List<Shape> shapes;
        public AwtFrame(List<Shape> shapes) {
            this.shapes = shapes;
        }
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.blue);
            for (Shape shape : shapes) {
                g2d.draw(shape);
            }
        }
    }

    @Override
    public void startApplication() {
        int width = ApplicationConfig.WIDTH;
        int height = ApplicationConfig.HEIGHT;

        DrawingApiAwt drawingApi = new DrawingApiAwt(width, height);
        Graph graph = ApplicationConfig.createGraph(drawingApi);

        graph.readGraph();
        graph.drawGraph();

        Frame frame = new AwtFrame(drawingApi.getShapes());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
