package tools;

import javax.swing.*;
import java.awt.*;

public class SwingPlot extends JFrame {

    public static void main(String[] args) {
        IQueue<Double> x = new LinkedListQueue<>();
        IQueue<Double> y = new LinkedListQueue<>();
        for (int i = 0; i < 100; i++) {
            x.enqueue( (double) i);
            y.enqueue( Math.pow(i, 2) );
        }
        new SwingPlot("f(x) = 2^x", 10, x, y);
    }

    private final int width = 600;
    private final int height = 600;
    private final int plotWidth = 500;
    private final int plotHeight = 500;
    private final int originX = 50;
    private final int originY = 550;
    private final String title;
    private final double scale;
    private final int pointSize = 4;
    private final IQueue<Double> vectorX_Copy;
    private final IQueue<Double> vectorY_Copy;

    public SwingPlot(String title, double scale, IQueue<Double> vectorX, IQueue<Double> vectorY) {

        this.title = title;
        this.scale = scale;
        this.vectorX_Copy = new LinkedListQueue<>();
        this.vectorY_Copy = new LinkedListQueue<>();
        for (double d : vectorX) vectorX_Copy.enqueue(d);
        for (double d : vectorY) vectorY_Copy.enqueue(d);
        SwingUtilities.invokeLater(this::setWindow);

    }

    private void setWindow() {
        this.setTitle(this.title);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.requestFocusInWindow(true);
        this.setFocusable(true);
        this.setContentPane(new PlotLabel());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private class PlotLabel extends JLabel {

        private PlotLabel() {
            this.setBackground(Color.WHITE);
            this.setDoubleBuffered(true);
            this.setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g;
            drawAxis(g2D);
            drawFunction(g2D);
            g2D.dispose();
        }

        private void drawPoint(Graphics2D g2D, int x, int y) {
            g2D.setColor(Color.RED);
            g2D.fillRect(
                    (int) ((x * scale + originX) - pointSize / 2),
                    (int) ((originY - y * scale) - pointSize / 2),
                    pointSize, pointSize);
        }

        private void drawFunction(Graphics2D g2D) {
            int size = vectorX_Copy.size();
            for (int i = 0; i < size; i++) {
                int x = vectorX_Copy.dequeue().intValue();
                int y = vectorY_Copy.dequeue().intValue();
                drawPoint(g2D, x, y);
            }
        }

        private void drawAxis(Graphics2D g2D) {
            g2D.drawLine(originX, originY, originX + plotWidth, originY);
            g2D.drawLine(originX, originY, originX, originY - plotHeight);
            int baseGradSize = 3;
            int largeGradSize = 6;
            int gradStep = 5;
            int gradStrStep = 5;
            if (scale <= 0.5) {
                gradStep = 500;
                gradStrStep = 1000;
            }
            // x-axis
            int count = 0;
            int offset = 30;
            g2D.setFont(new Font("arial", Font.PLAIN, 10));
            for (double i = originX; i <= width - originX; i += scale) {
                if (count % gradStep == 0) {
                    g2D.drawLine((int) i, originY - baseGradSize, (int) i, originY + baseGradSize);
                }
                if (count % gradStrStep == 0) {
                    g2D.drawLine((int) i, originY - largeGradSize, (int) i, originY + largeGradSize);
                    g2D.drawString(String.valueOf(count), (int) i, height - originX + offset);
                }
                count++;
            }
            // y-axis
            count = 0;
            for (double i = originY; i >= height - originY; i-= scale) {
                if (count % gradStep == 0) {
                    g2D.drawLine(originX - baseGradSize, (int) i, originX + baseGradSize, (int) i);
                }
                if (count % gradStrStep == 0) {
                    g2D.drawLine(originX - largeGradSize, (int) i, originX + largeGradSize, (int) i);
                    g2D.drawString(String.valueOf(count), originX - offset, (int) i);
                }
                count++;
            }

        }

    }


}
