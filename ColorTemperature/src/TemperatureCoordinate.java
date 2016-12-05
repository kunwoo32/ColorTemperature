
public class TemperatureCoordinate {
	public final double temperature;
	public final double tint;
	
	public TemperatureCoordinate(double temp, double tint) {
		this.temperature = temp;
		this.tint = tint;
	}
	
	public String toString() {
		return String.format("(temperature: %f, tint: %f)", temperature, tint);
	}
}
