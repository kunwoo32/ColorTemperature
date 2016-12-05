
public class XYInterpolationPoint {
	
	public final double WAVELENGTH;
	
	public final double TEMPERATURE; // measured in mirads
	
	public final Point PLANCKIAN_XY;
	
	public final Point MAGENTA_XY;

	public XYInterpolationPoint(double wavelength, double temperature, Point planckianXY, Point magentaXY) {
		this.WAVELENGTH = wavelength;
		this.TEMPERATURE = temperature;
		this.PLANCKIAN_XY = planckianXY;
		this.MAGENTA_XY = magentaXY;
	}
	
	public String toString() {
		return String.format("XYInterpolationPoint(wavelength: %f, temperature: %f, planckian xy: %s, magenta xy: %s",
		                     WAVELENGTH, TEMPERATURE, PLANCKIAN_XY.toString(), MAGENTA_XY.toString());
	}
}
