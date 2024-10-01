public class Vector2d {
    public final float x;
    public final float y;

    public Vector2d(){
        x = 0;
        y = 0;
    }

    public Vector2d(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getMagnitude(){
        return (float)Math.sqrt((x*x)+(y*y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x,this.y+other.y);
    }

    public Vector2d multiply(float scalar){
        return new Vector2d(this.x*scalar,this.y*scalar);
    }
}
