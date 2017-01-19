import java.math.*;

public class ColorTemp {

	// CIE standard observer, 360nm to 830nm, 5nm increments
	private static final double[][] CIE_STANDARD_OBSERVER = {{0.0001299, 0.000003917, 0.0006061},
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

	private static final Point INFINTE_TEMP_XY = infinitelyHotXY();
	private static final Point BLUEST_POINT = monochromeToXY(415);
	private static final Point REDDEST_POINT = monochromeToXY(700);
	private static final Line NEGATIVE_TEMP_LINE = new Line(BLUEST_POINT, INFINTE_TEMP_XY);
	private static final Line MAGENTA_LINE = new Line(BLUEST_POINT, REDDEST_POINT);
	
	private static final XYInterpolationPoint[] INTERP_TABLE = new XYInterpolationPoint[]{
			new XYInterpolationPoint(420.000000, -1400.000000, new Point(0.172105,0.004896), new Point(0.172126,0.004851)),
			new XYInterpolationPoint(425.000000, -1300.000000, new Point(0.172203,0.005226), new Point(0.172332,0.004946)),
			new XYInterpolationPoint(430.000000, -1200.000000, new Point(0.172391,0.005861), new Point(0.172729,0.005130)),
			new XYInterpolationPoint(435.000000, -1100.000000, new Point(0.172682,0.006844), new Point(0.173344,0.005414)),
			new XYInterpolationPoint(440.000000, -1000.000000, new Point(0.173108,0.008286), new Point(0.174244,0.005831)),
			new XYInterpolationPoint(445.000000, -900.000000, new Point(0.173640,0.010086), new Point(0.175369,0.006352)),
			new XYInterpolationPoint(450.000000, -800.000000, new Point(0.174345,0.012469), new Point(0.176858,0.007041)),
			new XYInterpolationPoint(455.000000, -700.000000, new Point(0.175260,0.015561), new Point(0.178790,0.007936)),
			new XYInterpolationPoint(460.000000, -600.000000, new Point(0.176588,0.020053), new Point(0.181597,0.009235)),
			new XYInterpolationPoint(465.000000, -500.000000, new Point(0.178676,0.027110), new Point(0.186006,0.011277)),
			new XYInterpolationPoint(470.000000, -400.000000, new Point(0.182634,0.040496), new Point(0.194370,0.015149)),
			new XYInterpolationPoint(475.000000, -300.000000, new Point(0.189364,0.063250), new Point(0.208587,0.021731)),
			new XYInterpolationPoint(480.000000, -200.000000, new Point(0.200365,0.100443), new Point(0.231825,0.032491)),
			new XYInterpolationPoint(485.000000, -100.000000, new Point(0.217048,0.156849), new Point(0.267069,0.048808)),
			new XYInterpolationPoint(490.000000, 0.000000, new Point(0.239876,0.234034), new Point(0.315295,0.071136)),
			new XYInterpolationPoint(495.000000, 36.150892, new Point(0.251088,0.250220), new Point(0.330699,0.078268)),
			new XYInterpolationPoint(500.000000, 110.070911, new Point(0.286323,0.294896), new Point(0.376748,0.099588)),
			new XYInterpolationPoint(505.000000, 158.909823, new Point(0.316880,0.326862), new Point(0.414099,0.116881)),
			new XYInterpolationPoint(510.000000, 195.448988, new Point(0.341911,0.349025), new Point(0.443161,0.130336)),
			new XYInterpolationPoint(515.000000, 222.697956, new Point(0.361124,0.363787), new Point(0.464611,0.140267)),
			new XYInterpolationPoint(520.000000, 241.710741, new Point(0.374587,0.373016), new Point(0.479216,0.147029)),
			new XYInterpolationPoint(525.000000, 255.387469, new Point(0.384231,0.379073), new Point(0.489467,0.151775)),
			new XYInterpolationPoint(530.000000, 267.155844, new Point(0.392470,0.383885), new Point(0.498086,0.155766)),
			new XYInterpolationPoint(535.000000, 278.050726, new Point(0.400031,0.388008), new Point(0.505884,0.159376)),
			new XYInterpolationPoint(540.000000, 288.722652, new Point(0.407361,0.391736), new Point(0.513342,0.162829)),
			new XYInterpolationPoint(545.000000, 299.801462, new Point(0.414877,0.395283), new Point(0.520883,0.166321)),
			new XYInterpolationPoint(550.000000, 311.835632, new Point(0.422919,0.398770), new Point(0.528836,0.170002)),
			new XYInterpolationPoint(555.000000, 325.515323, new Point(0.431891,0.402280), new Point(0.537562,0.174042)),
			new XYInterpolationPoint(560.000000, 341.642947, new Point(0.442214,0.405817), new Point(0.547411,0.178602)),
			new XYInterpolationPoint(565.000000, 361.330731, new Point(0.454413,0.409296), new Point(0.558783,0.183868)),
			new XYInterpolationPoint(570.000000, 386.082735, new Point(0.469083,0.412451), new Point(0.572067,0.190018)),
			new XYInterpolationPoint(575.000000, 418.252601, new Point(0.486996,0.414735), new Point(0.587688,0.197250)),
			new XYInterpolationPoint(580.000000, 461.553631, new Point(0.509033,0.415078), new Point(0.605966,0.205713)),
			new XYInterpolationPoint(585.000000, 521.393225, new Point(0.535759,0.411640), new Point(0.626664,0.215295)),
			new XYInterpolationPoint(590.000000, 603.428908, new Point(0.566232,0.402264), new Point(0.648183,0.225259)),
			new XYInterpolationPoint(595.000000, 708.390082, new Point(0.597013,0.386705), new Point(0.667599,0.234248)),
			new XYInterpolationPoint(600.000000, 827.412065, new Point(0.623881,0.368341), new Point(0.682723,0.241250)),
			new XYInterpolationPoint(605.000000, 959.222505, new Point(0.646769,0.349701), new Point(0.694464,0.246686)),
			new XYInterpolationPoint(610.000000, 1095.004177, new Point(0.665087,0.333274), new Point(0.703286,0.250770)),
			new XYInterpolationPoint(615.000000, 1233.224192, new Point(0.679773,0.319430), new Point(0.710100,0.253925)),
			new XYInterpolationPoint(620.000000, 1370.300673, new Point(0.691365,0.308216), new Point(0.715371,0.256366)),
			new XYInterpolationPoint(625.000000, 1505.121492, new Point(0.700526,0.299235), new Point(0.719491,0.258273)),
			new XYInterpolationPoint(630.000000, 1638.809702, new Point(0.707866,0.291988), new Point(0.722773,0.259792)),
			new XYInterpolationPoint(635.000000, 1777.488473, new Point(0.714003,0.285905), new Point(0.725507,0.261058)),
			new XYInterpolationPoint(640.000000, 1919.287764, new Point(0.719016,0.280925), new Point(0.727736,0.262090)),
			new XYInterpolationPoint(645.000000, 2062.826566, new Point(0.723028,0.276933), new Point(0.729518,0.262916)),
			new XYInterpolationPoint(650.000000, 2196.393860, new Point(0.725972,0.274002), new Point(0.730825,0.263521)),
			new XYInterpolationPoint(655.000000, 2328.430328, new Point(0.728262,0.271721), new Point(0.731841,0.263991)),
			new XYInterpolationPoint(660.000000, 2453.980379, new Point(0.729960,0.270029), new Point(0.732594,0.264339)),
			new XYInterpolationPoint(665.000000, 2559.800016, new Point(0.731087,0.268905), new Point(0.733094,0.264571)),
			new XYInterpolationPoint(670.000000, 2667.317867, new Point(0.731993,0.268001), new Point(0.733495,0.264757)),
			new XYInterpolationPoint(675.000000, 2778.597473, new Point(0.732720,0.267276), new Point(0.733817,0.264906)),
			new XYInterpolationPoint(680.000000, 2925.975534, new Point(0.733417,0.266581), new Point(0.734126,0.265049)),
			new XYInterpolationPoint(685.000000, 3143.213429, new Point(0.734048,0.265951), new Point(0.734406,0.265178)),
			new XYInterpolationPoint(690.000000, 3370.153245, new Point(0.734389,0.265611), new Point(0.734557,0.265248)),
			new XYInterpolationPoint(695.000000, 3688.843859, new Point(0.734592,0.265408), new Point(0.734647,0.265290))};
	
	// yields a blackbody radiator sprectrum; lambda is measured in nanometers; temp is measured in Kelvin
	private static double plancksLaw(double temp, double lambda) {
		double h = 6.62607004e-34;
		double c = 299792458;
		double k = 1.38064852e-23;
		double wl = lambda / 1e9;
		
		return 2*h*c*c/(wl*wl*wl*wl*wl)/(Math.exp(h*c/(k*wl*temp))-1);
	}
	
	private static Point infinitelyHotXY() {
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
	private static Point tempToXY(double temp) {
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
	private static Point monochromeToXY(double wavelength) {
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
	private static double findCCTForMonochrome(double wavelength, double initialGuess) {
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
	private static XYInterpolationPoint[] createInterpolationTable() {
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
	private static double slopePlanckianLocus(double temp) {
		Point xy1 = tempToXY(0.98 * temp);
		Point xy2 = tempToXY(1.02 * temp);
		return xy1.slope2(xy2);
	}

	// shifts the temperature of the color
	// temperature measured in mireds
	// can return results outside the range of physically possible colors
	public static Point shiftColorTemperature(Point color, double temperature) {
		TemperatureCoordinate tc = getTemperatureCoordinate(color);
		return getXYFromTemperatureCoordinate(new TemperatureCoordinate(tc.temperature+temperature, tc.tint));
	}
	
	public static TemperatureCoordinate getTemperatureCoordinate(double x, double y) {
		return getTemperatureCoordinate(new Point(x,y));
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

		// if the point is at one of the two extreme corners
		Line bluestPlanckSegment = new Line(BLUEST_POINT, INTERP_TABLE[0].PLANCKIAN_XY);
		Line bluestVerticalAbove = new Line(INTERP_TABLE[0].PLANCKIAN_XY, monochromeToXY(INTERP_TABLE[0].WAVELENGTH));
		Ray bluestVerticalBelow = new Ray(INTERP_TABLE[0].PLANCKIAN_XY, INTERP_TABLE[0].MAGENTA_XY);
		
		if(bluestPlanckSegment.isBelow(xy) && bluestVerticalAbove.isRightOf(xy)) { // top bluest sector
			double tint = bluestPlanckSegment.shortestDistance(xy);
			double planckLength = BLUEST_POINT.distance(INTERP_TABLE[0].PLANCKIAN_XY);
			double distXYToVertical = bluestVerticalAbove.shortestDistance(xy); // bluestVerticalAbove is always perpendicular to bluestPlanckSegment
			double temperature = INTERP_TABLE[0].TEMPERATURE*planckLength/(planckLength-distXYToVertical);
			return new TemperatureCoordinate(temperature, tint);
		} else if(!bluestPlanckSegment.isBelow(xy) && bluestVerticalBelow.isRightOf(xy)) { // bottom bluest sector
			double tint = -bluestPlanckSegment.shortestDistance(xy);
			double planckLength = BLUEST_POINT.distance(INTERP_TABLE[0].PLANCKIAN_XY);
			Point p = bluestVerticalBelow.moveFromInitial(Math.abs(-tint/Math.sin(bluestVerticalBelow.angle-bluestPlanckSegment.slope)));
			double temperature = INTERP_TABLE[0].TEMPERATURE*planckLength/(planckLength-p.distance(xy));
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

	public static Point getXYFromTemperatureCoordinate(TemperatureCoordinate tc) {
		for(int i=0; i<INTERP_TABLE.length-1; i++) {
			if(tc.temperature > INTERP_TABLE[i].TEMPERATURE && tc.temperature < INTERP_TABLE[i+1].TEMPERATURE) {
				Ray leftVertical;
				Ray rightVertical;
				Line planckSegment = new Line(INTERP_TABLE[i].PLANCKIAN_XY, INTERP_TABLE[i + 1].PLANCKIAN_XY);
				if (tc.tint > 0) {
					leftVertical = new Ray(INTERP_TABLE[i].PLANCKIAN_XY, monochromeToXY(INTERP_TABLE[i].WAVELENGTH));
					rightVertical = new Ray(INTERP_TABLE[i + 1].PLANCKIAN_XY, monochromeToXY(INTERP_TABLE[i + 1].WAVELENGTH));
				} else {
					leftVertical = new Ray(INTERP_TABLE[i].PLANCKIAN_XY, INTERP_TABLE[i].MAGENTA_XY);
					rightVertical = new Ray(INTERP_TABLE[i + 1].PLANCKIAN_XY, INTERP_TABLE[i + 1].MAGENTA_XY);
				}
				double displacement = Math.abs(tc.tint);
				Point pLeft = leftVertical.moveFromInitial(Math.abs(displacement / Math.sin(leftVertical.angle - Math.atan(planckSegment.slope))));
				Point pRight = rightVertical.moveFromInitial(Math.abs(displacement / Math.sin(rightVertical.angle - Math.atan(planckSegment.slope))));
				return pLeft.lerp(pRight, (tc.temperature - INTERP_TABLE[i].TEMPERATURE) / (INTERP_TABLE[i + 1].TEMPERATURE - INTERP_TABLE[i].TEMPERATURE));
			}
		}

		// if point is at one of th two extreme corners
		int lastI = INTERP_TABLE.length-1;
		Ray planckSegment;
		Ray vertical;
		XYInterpolationPoint interpP;
		double planckLength;

		if(tc.temperature <= INTERP_TABLE[0].TEMPERATURE) { // bluest corner
			interpP = INTERP_TABLE[0];
			planckSegment = new Ray(interpP.PLANCKIAN_XY, BLUEST_POINT);
			planckLength = interpP.PLANCKIAN_XY.distance(BLUEST_POINT);
		} else if(tc.temperature >= INTERP_TABLE[lastI].TEMPERATURE){ // reddest corner
			interpP = INTERP_TABLE[lastI];
			planckSegment = new Ray(interpP.PLANCKIAN_XY, REDDEST_POINT);
			planckLength = interpP.PLANCKIAN_XY.distance(REDDEST_POINT);
		} else {
			throw new IllegalArgumentException("TemperatureCoordinate has impossible temperature.");
		}

		if(tc.tint > 0) {
			vertical = new Ray(interpP.PLANCKIAN_XY, monochromeToXY(interpP.WAVELENGTH));
		} else {
			vertical = new Ray(interpP.PLANCKIAN_XY, interpP.MAGENTA_XY);
		}

		double displacement = Math.abs(tc.tint);
		Point p = vertical.moveFromInitial(Math.abs(displacement/Math.sin(vertical.angle-planckSegment.angle)));
		return planckSegment.moveParallel(p, planckLength*(1-interpP.TEMPERATURE/tc.temperature));
	}

	private static double lerp(double origin, double destination, double percentage) {
		return origin + percentage * (destination-origin);
	}
}
