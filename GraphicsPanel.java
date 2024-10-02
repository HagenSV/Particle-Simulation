import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main
 */
public class GraphicsPanel extends JPanel {

    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int NUM_PARTICLES = 10;

    private List<Particle> particles;
    private Thread drawThread;

    private Comparator<Particle> particleSort = new SortParticlesX();

    public GraphicsPanel(){
        setSize(WIDTH,HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        particles = new ArrayList<>();

        for (int i = 0; i < NUM_PARTICLES; i++){
            Particle p = new Particle((float)Math.random()*WIDTH+10, (float)Math.random()*HEIGHT+10, 5, 10);
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
        frame.setAlwaysOnTop(true);
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

                //Sort particles along the x axis for more efficient collision detection
                List<Particle> sortedParticles = new ArrayList<>(particles);
                sortedParticles.sort(particleSort);                         

                for (int i = 0; i < sortedParticles.size(); i++){
                    Particle current = sortedParticles.get(i);
                    for (int j = i + 1; j < sortedParticles.size(); j++){
                        Particle check = sortedParticles.get(j);

                        Vector2d posA = current.position;
                        Vector2d posB = check.position;

                        //If there is no overlap on the x-axis, stop checking.
                        //Every subsequent particle will be further right and cannot collide with the current particle
                        if (posA.getX()+current.radius < posB.getX()-check.radius){ break; }

                        resolveCollision(current,check);
                    }
                }

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


        private void resolveCollision(Particle p1, Particle p2){

            //Calculate distance
            Vector2d distVec = Vector2d.subtract(p2.position, p1.position); 

            float d = distVec.getMagnitude();
            
            //Objects are too far to collide
            if (d > p1.radius+p2.radius){ return; }


            Vector2d dir = distVec.copy();
            dir.setMagnitude(0.5f*(d - (p1.radius + p2.radius)));
            p1.position.add(dir);
            p2.position.subtract(dir);

            d = p1.radius + p2.radius;

            
            Vector2d velDiff = Vector2d.subtract(p2.velocity,p1.velocity);

            float num = velDiff.dotProduct(distVec);
            float den = (p1.mass+p2.mass) * d * d;

            Vector2d deltaVA = distVec.copy();
            deltaVA.multiply(2 * p2.mass * num / den);
            p1.velocity.add(deltaVA);
            
            Vector2d deltaVB = distVec.copy();
            deltaVB.multiply(-2 * p1.mass * num / den);
            p2.velocity.add(deltaVB);
        }
    }

    private class SortParticlesX implements Comparator<Particle> {

        @Override
        public int compare(Particle o1, Particle o2) {
            return Float.compare(o1.position.getX(), o2.position.getX());
        }
            
    };
}