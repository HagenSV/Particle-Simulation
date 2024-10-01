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
    private Thread drawThread;

    public GraphicsPanel(){
        particles = new ArrayList<>();
        particles.add(new Particle(50, 50, 10, 10));
        particles.get(0).velocity = new Vector2d(100,60);
        drawThread = new Thread(new UpdateLoop());
        drawThread.start();
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

    private class UpdateLoop implements Runnable {

        @Override
        public void run() {
            long startTime = 0, endTime = 0;
            float deltaTime = 0;
            
            while (drawThread != null){

                startTime = System.currentTimeMillis();
                repaint();
                endTime = System.currentTimeMillis();
                deltaTime = (endTime-startTime)*0.001f;
                for (Particle p : particles){
                    p.update(deltaTime);

                    //Update position
                    if (p.position.x-p.radius < 0 || p.position.x+p.radius > getWidth()){
                        p.velocity = new Vector2d(-p.velocity.x,p.velocity.y);
                    }
                    if (p.position.y-p.radius < 0 || p.position.y+p.radius > getHeight()){
                        p.velocity = new Vector2d(p.velocity.x,-p.velocity.y);
                    }
                }
            }
        }
    }
}