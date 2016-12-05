
public class Point {
	public final double x;
	public final double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static Point unit(double theta) {
		return new Point(Math.cos(theta), Math.sin(theta));
	}
	
	public Point add(Point p) {
		return new Point(this.x + p.x, this.y + p.y);
	}
	
	public Point subtract(Point p) {
		return new Point(this.x-p.x, this.y-p.y);
	}
	
	public Point multiply(double f) {
		return new Point(f * this.x, f * this.y);
	}
	
	public double distance(Point p) {
		return Math.sqrt(Math.pow(this.x-p.x, 2) + Math.pow(this.y-p.y, 2));
	}
	
	public double slope() {
		return this.y / this.x;
	}
	
	public double slope2(Point p) {
		return this.subtract(p).slope();
	}
	
	public double angle() {
		return Math.atan2(this.y, this.x);
	}
	
	public Point addPolar(double r, double theta) {
		return this.add(unit(theta).multiply(r));
	}
	
	public Point lerp(Point p, double percentage) {
		return p.subtract(this).multiply(percentage).add(this);
	}
	
	public String toString() {
		return String.format("(%f,%f)", this.x, this.y);
	}
}
