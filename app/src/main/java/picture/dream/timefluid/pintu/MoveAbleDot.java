package picture.dream.timefluid.pintu;

public class MoveAbleDot {
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected float radius = 5;

    public MoveAbleDot(float x,float y,float dx,float dy) {
        this.y = y;
        this.x = x;
        this.dx = dx;
        this.dy = dy;
    }

    public void set(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float vel) {
        x += dx * vel;
        y += dy * vel;
    }

    public void setDir(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
