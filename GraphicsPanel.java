import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main
 */
public class GraphicsPanel extends JPanel {

    private List<Particle> particles;

    public GraphicsPanel(){
        particles = new ArrayList<>();
        particles.add(new Particle(50, 50, 10, 10));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle Simulation");
        frame.add(new GraphicsPanel(),BorderLayout.CENTER);
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle p : particles){
            p.draw(g);
        }
    }
}