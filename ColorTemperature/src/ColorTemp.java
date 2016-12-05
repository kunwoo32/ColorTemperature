import java.math.*;

public class ColorTemp {

	// CIE standard observer, 360nm to 830nm, 5nm increments
	public static final double[][] CIE_STANDARD_OBSERVER = {{0.0001299, 0.000003917, 0.0006061},
		    {0.0002321, 0.000006965, 0.001086}, {0.0004149, 0.00001239, 0.001946}, {0.0007416, 0.00002202, 0.003486},
		    {0.001368, 0.000039, 0.006450001}, {0.002236, 0.000064, 0.01054999}, {0.004243, 0.000120, 0.02005001},
		    {0.007650, 0.000217, 0.036210}, {0.014310, 0.000396, 0.06785001}, {0.023190, 0.000640, 0.110200},
		    {0.043510, 0.001210, 0.207400}, {0.077630, 0.002180, 0.371300}, {0.134380, 0.004000, 0.645600},
		    {0.214770, 0.007300, 1.0390501}, {0.283900, 0.011600, 1.385600}, {0.328500, 0.016840, 1.622960},
		    {0.348280, 0.023000, 1.747060}, {0.348060, 0.029800, 1.782600}, {0.336200, 0.038000, 1.772110},
		    {0.318700, 0.048000, 1.744100}, {0.290800, 0.060000, 1.669200}, {0.251100, 0.073900, 1.528100},
		    {0.195360, 0.090980, 1.287640}, {0.142100, 0.112600, 1.041900}, {0.095640, 0.139020, 0.8129501},
		    {0.05795001, 0.169300, 0.616200}, {0.032010, 0.208020, 0.465180}, {0.014700, 0.258600, 0.353300},
		    {0.004900, 0.323000, 0.272000}, {0.002400, 0.407300, 0.212300}, {0.009300, 0.503000, 0.158200},
		    {0.029100, 0.608200, 0.111700}, {0.063270, 0.710000, 0.07824999}, {0.109600, 0.793200, 0.05725001},
		    {0.165500, 0.862000, 0.042160}, {0.2257499, 0.9148501, 0.029840}, {0.290400, 0.954000, 0.020300},
		    {0.359700, 0.980300, 0.013400}, {0.43344990, 0.9949501, 0.008749999}, {0.5120501, 1.000000, 0.005749999},
		    {0.594500, 0.995000, 0.003900}, {0.678400, 0.978600, 0.002749999}, {0.762100, 0.952000, 0.002100},
		    {0.842500, 0.915400, 0.001800}, {0.916300, 0.870000, 0.001650001}, {0.978600, 0.816300, 0.001400},
		    {1.026300, 0.757000, 0.001100}, {1.056700, 0.694900, 0.001000}, {1.062200, 0.631000, 0.000800},
		    {1.045600, 0.566800, 0.000600}, {1.002600, 0.503000, 0.000340}, {0.938400, 0.441200, 0.000240},
		    {0.8544499, 0.381000, 0.000190}, {0.751400, 0.321000, 0.000100}, {0.642400, 0.265000, 0.00004999999},
		    {0.541900, 0.217000, 0.000030}, {0.447900, 0.175000, 0.000020}, {0.360800, 0.138200, 0.000010},
		    {0.283500, 0.107000, 0.000000}, {0.218700, 0.081600, 0.000000}, {0.164900, 0.061000, 0.000000},
		    {0.121200, 0.044580, 0.000000}, {0.087400, 0.032000, 0.000000}, {0.063600, 0.023200, 0.000000},
		    {0.046770, 0.017000, 0.000000}, {0.032900, 0.011920, 0.000000}, {0.022700, 0.008210, 0.000000},
		    {0.015840, 0.005723, 0.000000}, {0.01135916, 0.004102, 0.000000}, {0.008110916, 0.002929, 0.000000},
		    {0.005790346, 0.002091, 0.000000}, {0.004109457, 0.001484, 0.000000}, {0.002899327, 0.001047, 0.000000},
		    {0.00204919, 0.000740, 0.000000}, {0.001439971, 0.000520, 0.000000}, {0.0009999493, 0.0003611, 0.000000},
		    {0.0006900786, 0.0002492, 0.000000}, {0.0004760213, 0.0001719, 0.000000}, {0.0003323011, 0.000120, 0.000000},
		    {0.0002348261, 0.0000848, 0.000000}, {0.0001661505, 0.000060, 0.000000}, {0.000117413, 0.0000424, 0.000000},
		    {0.00008307527, 0.000030, 0.000000}, {0.00005870652, 0.0000212, 0.000000}, {0.00004150994, 0.00001499, 0.000000},
		    {0.00002935326, 0.0000106, 0.000000}, {0.00002067383, 0.0000074657, 0.000000}, {0.00001455977, 0.0000052578, 0.000000},
		    {0.00001025398, 0.0000037029, 0.000000}, {0.000007221456, 0.00000260778, 0.000000}, {0.000005085868, 0.0000018366, 0.000000},
		    {0.000003581652, 0.0000012934, 0.000000}, {0.000002522525, 0.00000091093, 0.000000}, {0.000001776509, 0.00000064153, 0.000000},
		    {0.000001251141, 0.00000045181, 0.000000}};

	public static final Point INFINTE_TEMP_XY = infinitelyHotXY();
	public static final Point BLUEST_POINT = monochromeToXY(415);
	public static final Point REDDEST_POINT = monochromeToXY(700);
	public static final Line NEGATIVE_TEMP_LINE = new Line(BLUEST_POINT, INFINTE_TEMP_XY);
	public static final Line MAGENTA_LINE = new Line(BLUEST_POINT, REDDEST_POINT);
	
	public static final XYInterpolationPoint[] INTERP_TABLE = createInterpolationTable();
	
	// yields a blackbody radiator sprectrum; lambda is measured in nanometers; temp is measured in Kelvin
	public static double plancksLaw(double temp, double lambda) {
		double h = 6.62607004e-34;
		double c = 299792458;
		double k = 1.38064852e-23;
		double wl = lambda / 1e9;
		
		return 2*h*c*c/(wl*wl*wl*wl*wl)/(Math.exp(h*c/(k*wl*temp))-1);
	}
	
	public static Point infinitelyHotXY() {
		double X = 0;
		double Y = 0;
		double Z = 0;
		
		for(int i=0; i<95; i++) {
			double lambda = i*5 + 360;
			double spectral = 1 / (lambda*lambda*lambda*lambda);
			X += CIE_STANDARD_OBSERVER[i][0] * spectral;
			Y += CIE_STANDARD_OBSERVER[i][1] * spectral;
			Z += CIE_STANDARD_OBSERVER[i][2] * spectral;
		}
		
		return new Point(X/(X+Y+Z), Y/(X+Y+Z));
	}
	
	
	// input is in Kelvin
	public static Point tempToXY(double temp) {
		double X = 0;
		double Y = 0;
		double Z = 0;
		
		for(int i=0; i<95; i++) {
			double lambda = i*5 + 360;
			double spectral = plancksLaw(temp, lambda);
			X += CIE_STANDARD_OBSERVER[i][0] * spectral;
			Y += CIE_STANDARD_OBSERVER[i][1] * spectral;
			Z += CIE_STANDARD_OBSERVER[i][2] * spectral;
		}
		
		return new Point(X/(X+Y+Z), Y/(X+Y+Z));
	}
	
	//input is in nanometers
	public static Point monochromeToXY(double wavelength) {
		if(wavelength < 360 || wavelength > 830) {
			throw new IllegalArgumentException("Wavelength is not between 560nm and 830nm. Wavelength is " + wavelength);
		} else if(wavelength==830) {
			double x = CIE_STANDARD_OBSERVER[94][0]
				       / (CIE_STANDARD_OBSERVER[94][0] + CIE_STANDARD_OBSERVER[94][1] + CIE_STANDARD_OBSERVER[94][2]);
			double y = CIE_STANDARD_OBSERVER[94][1]
			           / (CIE_STANDARD_OBSERVER[94][0] + CIE_STANDARD_OBSERVER[94][1] + CIE_STANDARD_OBSERVER[94][2]);
			return new Point(x,y);
		} else {
			int nearestTabular = ((int) wavelength) / 5 * 5;
			double remainder = wavelength - nearestTabular;
			int i = (nearestTabular-360) / 5;
			
			double x1 = CIE_STANDARD_OBSERVER[i][0]
					    / (CIE_STANDARD_OBSERVER[i][0] + CIE_STANDARD_OBSERVER[i][1] + CIE_STANDARD_OBSERVER[i][2]);
			double y1 = CIE_STANDARD_OBSERVER[i][1]
				        / (CIE_STANDARD_OBSERVER[i][0] + CIE_STANDARD_OBSERVER[i][1] + CIE_STANDARD_OBSERVER[i][2]);
			
			double x2 = CIE_STANDARD_OBSERVER[i+1][0]
				    / (CIE_STANDARD_OBSERVER[i+1][0] + CIE_STANDARD_OBSERVER[i+1][1] + CIE_STANDARD_OBSERVER[i+1][2]);
			double y2 = CIE_STANDARD_OBSERVER[i+1][1]
				    / (CIE_STANDARD_OBSERVER[i+1][0] + CIE_STANDARD_OBSERVER[i+1][1] + CIE_STANDARD_OBSERVER[i+1][2]);
			
			return (new Point(x1,y1)).lerp(new Point(x2,y2), remainder/5);
		}
	}
	
	// input is in nanometers
	// output is in Kelvin
	public static double findCCTForMonochrome(double wavelength, double initialGuess) {
		Point mono_xy = monochromeToXY(wavelength);
		Point planckian_xy = tempToXY(initialGuess);
		Line perpToPlanck = new Line(planckian_xy, -1/slopePlanckianLocus(initialGuess));
		
		double currentGuess = initialGuess;
		double previousGuess = 0;
		
		while(perpToPlanck.isRightOf(mono_xy)) {
			previousGuess = currentGuess;
			currentGuess = currentGuess * 2;
			
			planckian_xy = tempToXY(currentGuess);
			perpToPlanck = new Line(planckian_xy, -1/slopePlanckianLocus(currentGuess));
		}
		
		while(Math.abs(currentGuess - previousGuess) > 0.1) {
			double midpoint = (currentGuess+previousGuess) / 2;
			planckian_xy = tempToXY(midpoint);
			perpToPlanck = new Line(planckian_xy, -1/slopePlanckianLocus(midpoint));
			if(perpToPlanck.isRightOf(mono_xy)) {
				previousGuess = Math.max(previousGuess, currentGuess);
			} else {
				previousGuess = Math.min(previousGuess, currentGuess);
			}
			
			currentGuess = midpoint;
		}
		
		return currentGuess;
	}
	
	
	// the table will have entries from 420nm to 695nm, in 5nm increments
	// temperature will be in mireds
	public static XYInterpolationPoint[] createInterpolationTable() {
		final int minWl = 420;
		final int maxWl = 695;
		XYInterpolationPoint[] table = new XYInterpolationPoint[(maxWl-minWl)/5 + 1]; 
		for(int wl=minWl; wl<=maxWl; wl+=5) {
			int i = (wl-minWl)/5;
			if(wl<490) {
				Point monoXY = monochromeToXY(wl);
				Point negTempIntersect = NEGATIVE_TEMP_LINE.perpendicularIntersect(monoXY);
				double temper = 20 * (wl-490);
				Point magentaIntersect = MAGENTA_LINE.perpendicularIntersect(negTempIntersect);
				table[i] = new XYInterpolationPoint(wl, temper, negTempIntersect, magentaIntersect);
			}
			if(wl==490) {
				Point magentaIntersect = MAGENTA_LINE.perpendicularIntersect(INFINTE_TEMP_XY);
				table[i] = new XYInterpolationPoint(490, 0, INFINTE_TEMP_XY, magentaIntersect);
			}
			
			if(wl>490) {
				double temper = findCCTForMonochrome(wl, 3000); // measured in Kelvins
				Point planckXY = tempToXY(temper);
				Point magentaIntersect = MAGENTA_LINE.perpendicularIntersect(planckXY);
				table[i] = new XYInterpolationPoint(wl, 1e6/temper, planckXY, magentaIntersect);
			}
		}
		return table;
	}

	
	// input is in Kelvin
	public static double slopePlanckianLocus(double temp) {
		Point xy1 = tempToXY(0.98 * temp);
		Point xy2 = tempToXY(1.02 * temp);
		return xy1.slope2(xy2);
	}

	public static TemperatureCoordinate getTemperatureCoordinate(Point xy) {
		for(int i=0; i<INTERP_TABLE.length-1; i++) {
			Line planckSegment = new Line(INTERP_TABLE[i].PLANCKIAN_XY, INTERP_TABLE[i+1].PLANCKIAN_XY);
			Ray leftVertical;
			Ray rightVertical;
			int tintSign;
			if(planckSegment.isBelow(xy)) {
				leftVertical  = new Ray(INTERP_TABLE[i].PLANCKIAN_XY,   monochromeToXY(INTERP_TABLE[i].WAVELENGTH));
				rightVertical = new Ray(INTERP_TABLE[i+1].PLANCKIAN_XY, monochromeToXY(INTERP_TABLE[i+1].WAVELENGTH));
				tintSign = 1;
			} else {
				leftVertical  = new Ray(INTERP_TABLE[i].PLANCKIAN_XY,   INTERP_TABLE[i].MAGENTA_XY);
				rightVertical = new Ray(INTERP_TABLE[i+1].PLANCKIAN_XY, INTERP_TABLE[i+1].MAGENTA_XY);
				tintSign = -1;
			}
			if(leftVertical.isLeftOf(xy) && rightVertical.isRightOf(xy)) {
				double displ = planckSegment.shortestDistance(xy);
				Point pLeft  = leftVertical.moveFromInitial(Math.abs(displ/Math.sin(leftVertical.angle-Math.atan(planckSegment.slope))));
				Point pRight = rightVertical.moveFromInitial(Math.abs(displ/Math.sin(rightVertical.angle-Math.atan(planckSegment.slope))));
				double temperature = lerp(INTERP_TABLE[i].TEMPERATURE, INTERP_TABLE[i+1].TEMPERATURE, xy.distance(pLeft) / pRight.distance(pLeft));
				return new TemperatureCoordinate(temperature, displ*tintSign);
			}
		}
		
		Line bluestPlanckSegment = new Line(BLUEST_POINT, INTERP_TABLE[0].PLANCKIAN_XY);
		Line bluestVerticalAbove = new Line(INTERP_TABLE[0].PLANCKIAN_XY, monochromeToXY(INTERP_TABLE[0].WAVELENGTH));
		Line bluestVerticalBelow = new Line(INTERP_TABLE[0].PLANCKIAN_XY, INTERP_TABLE[0].MAGENTA_XY);
		
		if(bluestPlanckSegment.isBelow(xy) && bluestVerticalAbove.isRightOf(xy)) { // top bluest sector
			double tint = bluestPlanckSegment.shortestDistance(xy);
			double planckLength = BLUEST_POINT.distance(INTERP_TABLE[0].PLANCKIAN_XY);
			double distXYToVertical = bluestVerticalAbove.shortestDistance(xy);
			double temperature = INTERP_TABLE[0].TEMPERATURE*planckLength/(planckLength-distXYToVertical);
			return new TemperatureCoordinate(temperature, tint);
		} else if(!bluestPlanckSegment.isBelow(xy) && bluestVerticalBelow.isRightOf(xy)) { // bottom bluest sector
			double tint = -bluestPlanckSegment.shortestDistance(xy);
			double planckLength = BLUEST_POINT.distance(INTERP_TABLE[0].PLANCKIAN_XY);
			double distXYToVertical = bluestVerticalAbove.shortestDistance(xy);
			double temperature = INTERP_TABLE[0].TEMPERATURE*planckLength/(planckLength-distXYToVertical);
			return new TemperatureCoordinate(temperature, tint);
		} else {
			int lastI = INTERP_TABLE.length-1;
			Line planckSegment = new Line(INTERP_TABLE[lastI].PLANCKIAN_XY, REDDEST_POINT);
			Ray vertical;
			int tintSign;
			if(planckSegment.isBelow(xy)) {
				vertical = new Ray(INTERP_TABLE[lastI].PLANCKIAN_XY,   monochromeToXY(INTERP_TABLE[lastI].WAVELENGTH));
				tintSign = 1;
			} else {
				vertical  = new Ray(INTERP_TABLE[lastI].PLANCKIAN_XY,   INTERP_TABLE[lastI].MAGENTA_XY);
				tintSign = -1;
			}
			
			if(vertical.isRightOf(xy)) throw new InternalError("xy is in illogical place");
			
			double displ = planckSegment.shortestDistance(xy);
			Point p = vertical.moveFromInitial(Math.abs(displ/Math.sin(vertical.angle-Math.atan(planckSegment.slope))));
			double planckDistance = INTERP_TABLE[lastI].PLANCKIAN_XY.distance(REDDEST_POINT);
			double temperature = INTERP_TABLE[lastI].TEMPERATURE * planckDistance / (planckDistance - p.distance(xy));
			return new TemperatureCoordinate(temperature, displ*tintSign);
		}
	}

	public static double lerp(double origin, double destination, double percentage) {
		return origin + percentage * (destination-origin);
	}
}
