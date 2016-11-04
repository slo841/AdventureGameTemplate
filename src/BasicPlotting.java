import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static double[][] sampleData;
//	public static String datafile = "data/64StepsInPocketJogging-out.csv";
	public static String datafile = "data/64StepsInHandJogging-out.csv";
	
	public static void main(String[] args) {
		// Create data set
		CSVData dataset = CSVData.createDataSet(datafile, 0);

		// Get 2d array of all data
		sampleData = dataset.getAllData();

		double[] time = ArrayHelper.extractColumn(sampleData, 0);		
		double[][] sensorData = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3, 4, 5, 6 });
		
		int steps = CountStepsBlank.countSteps(time, sensorData);
		System.out.println(steps);
		
		double[][] accel = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3 });
		double[] accmags = CountStepsBlank.calculateMagnitudesFor(accel);
		
//		double[][] gyro = ArrayHelper.extractColumns(sampleData, new int[] { 4, 5, 6 });
//		double[] gmags = CountStepsBlank.calculateMagnitudesFor(gyro);
		
		System.out.println(CountStepsBlank.calculateMean(accmags));
		System.out.println(CountStepsBlank.calculateStandardDeviation(accmags, CountStepsBlank.calculateMean(accmags)));
		
//		System.out.println(CountStepsBlank.calculateMean(gmags));
//		System.out.println(CountStepsBlank.calculateStandardDeviation(gmags, CountStepsBlank.calculateMean(gmags)));
		
		
		Plot2DPanel plot = new Plot2DPanel();
		
		// add a line plot to the PlotPanel
		plot.addLinePlot("y = x + noise", accmags);
//		plot.addLinePlot("y = x + noise", gmags);
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
