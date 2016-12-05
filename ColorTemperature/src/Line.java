
public class Line {
	public final double slope;
	private final double yIntercept;
	
	public Line(Point p, double slope) {
		this.slope = slope;
		this.yIntercept = p.y - p.x*slope;
	}
	
	public Line(Point p, Point q) {
		this.slope = p.slope2(q);
		this.yIntercept = p.y - p.x*this.slope;
	}
	
	public double findY(double x) {
		return x*this.slope + this.yIntercept;
	}
	
	public double findX(double y) {
		return (y-this.yIntercept) / this.slope;
	}
	
	public double shortestDistance(Point p) {
		Point a = new Point(0,yIntercept);
		double theta = p.subtract(a).angle() - Math.atan(this.slope);
		return Math.abs(a.distance(p) * Math.sin(theta));
	}

	public Point perpendicularIntersect(Point p) {
		Point a = new Point(0,yIntercept);
		double lineAngle = Math.atan(this.slope);
		double theta = p.subtract(a).angle() - lineAngle;
		return a.add(Point.unit(lineAngle).multiply(a.distance(p)*Math.cos(theta)));
	}
	
	public boolean isBelow(Point p) {
		return findY(p.x) < p.y;
	}
	
	public boolean isAbove(Point p) {
		return findY(p.x) > p.y;
	}
	
	public boolean isLeftOf(Point p) {
		return findX(p.y) < p.x;
	}
	
	public boolean isRightOf(Point p) {
		return findX(p.y) > p.x;
	}
	
	public boolean passesThrough(Point p) {
		return findY(p.x) == p.y;
	}
}
