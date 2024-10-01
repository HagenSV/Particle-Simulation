import java.awt.Graphics;

public class Particle {

    private float mass;
    public final float radius;
    public Vector2d position;
    public Vector2d velocity;

    public Particle(float x, float y, float radius, float mass){
        this.position = new Vector2d(x, y);
        this.radius = radius;
        this.mass = mass;
        this.velocity = new Vector2d();
    }

    public void update(float dt){
        this.position = this.position.add(velocity.multiply(dt));
    }

    public void draw(Graphics g){
        g.fillOval((int)(position.x-radius), (int)(position.y-radius), (int)(radius*2), (int)(radius*2));
    }
}
