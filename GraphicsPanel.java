import java.awt.BorderLayout;
import java.awt.Dimension;
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
        setSize(900,600);
        setPreferredSize(new Dimension(900, 600));

        particles = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Particle p = new Particle((float)Math.random()*800+10, (float)Math.random()*500+10, 10, 10);
            p.velocity = new Vector2d((float)Math.random()*1000-500, (float)Math.random()*1000-500);
            particles.add(p);
        }
        drawThread = new Thread(new UpdateLoop());
        drawThread.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle Simulation");
        frame.add(new GraphicsPanel(),BorderLayout.CENTER);
        frame.pack();
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
                    if (p.position.getX()-p.radius < 0){
                        p.velocity.setX(-p.velocity.getX());
                        p.position.setX(p.radius);
                    }
                    if (p.position.getY()-p.radius < 0){
                        p.velocity.setY(-p.velocity.getY());
                        p.position.setY(p.radius);
                    }
                    if (p.position.getX()+p.radius > getWidth()){
                        p.velocity.setX(-p.velocity.getX());
                        p.position.setX(getWidth()-p.radius);
                    }
                    if (p.position.getY()+p.radius > getHeight()){
                        p.velocity.setY(-p.velocity.getY());
                        p.position.setY(getHeight()-p.radius);
                    }
                }
            }
        }
    }
}