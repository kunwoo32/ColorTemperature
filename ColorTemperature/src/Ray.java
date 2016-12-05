
public class Ray extends Line {
	private final Point initial;
	public final double angle;
	
	public Ray(Point p, double theta) {
		super(p, Math.tan(theta));
		this.initial = p;
		this.angle = theta;
	}
	
	public Ray(Point p, Point q) {
		super(p, q);
		this.initial = p;
		this.angle = q.subtract(p).angle();
	}
	
	public Point moveFromInitial(double distance) {
		return initial.addPolar(distance, angle);
	}
	
	public Point moveParallel(Point p, double distance) {
		return p.addPolar(distance, angle);
	}
}
