
public class TemperatureCoordinate {
	public final double temperature; // measured in mireds
	public final double tint; // positive for values above the Planckian locus and negative for below 
	
	public TemperatureCoordinate(double temp, double tint) {
		this.temperature = temp;
		this.tint = tint;
	}
	
	public String toString() {
		return String.format("(temperature: %f, tint: %f)", temperature, tint);
	}
}
