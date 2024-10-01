import java.awt.Graphics;

public class Particle {

    private float mass;
    private float radius;
    private Vector2d position;
    private Vector2d velocity;

    public Particle(float x, float y, float radius, float mass){
        this.position = new Vector2d(x, y);
        this.radius = radius;
        this.mass = mass;
    }


    public void update(float dt){
        this.position = this.position.add(velocity.multiply(dt));
    }

    public void draw(Graphics g){
        g.fillOval((int)(position.x-radius), (int)(position.y-radius), (int)(radius*2), (int)(radius*2));
    }
}
