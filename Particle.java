import java.awt.Graphics;

public class Particle {

    private float mass;
    private float radius;
    private float x;
    private float y;
    private float velX;
    private float velY;

    public Particle(float x, float y, float radius, float mass){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
    }


    public void update(float dt){
        this.x += velX*dt;
        this.y += velY*dt;
    }

    public void draw(Graphics g){
        g.fillOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
    }
}
