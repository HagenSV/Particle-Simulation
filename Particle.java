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
        Vector2d addVec = velocity.copy();
        addVec.multiply(dt);
        this.position.add(addVec);
    }

    public void draw(Graphics g){
        g.fillOval((int)(position.getX()-radius), (int)(position.getY()-radius), (int)(radius*2), (int)(radius*2));
    }
}
