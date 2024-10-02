public class Vector2d {
    private float x;
    private float y;

    public Vector2d(){
        x = 0;
        y = 0;
    }

    public Vector2d(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){ return x; }
    public float getY(){ return y; }

    public void setX(float x){ this.x = x; }
    public void setY(float y){ this.y = y; }

    public void set(float x, float y){ this.x = x; this.y = y; }

    public float getMagnitude(){
        return (float)Math.sqrt((x*x)+(y*y));
    }

    public void add(Vector2d other){
        this.x += other.x;
        this.y += other.y;
    }

    public void multiply(float scalar){
        this.x *= scalar;
        this.y *= scalar;
    }

    public Vector2d copy(){
        return new Vector2d(this.x,this.y);
    }
}
