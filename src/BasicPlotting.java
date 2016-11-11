import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static double[][] sampleData;
	public static String datafile = "data/p1_milling.csv";
	public static String datafile1 = "data/p2_milling.csv";
	public static String datafile2 = "data/p1_running.csv";
	public static String datafile3 = "data/p2_running.csv";
	public static String datafile4 = "data/p1_walking.csv";
	public static String datafile5 = "data/p2_walking.csv";
	
	public static void main(String[] args) {
		// Create data set
		CSVData dataset = CSVData.createDataSet(datafile, 0);

		// Get 2d array of all data
		sampleData = dataset.getAllData();

		double[] time = ArrayHelper.extractColumn(sampleData, 0);		
		double[][] sensorData = ArrayHelper.extractColumns(sampleData, new int[] { 10, 11, 12 });
		
		int steps = CountStepsBlank.countSteps(time, sensorData);
		int originalSteps = CountStepsBlank.originalCountSteps(time, sensorData);
		System.out.println(steps + " " + originalSteps);
		
		double[] accmags = CountStepsBlank.calculateMagnitudesFor(sensorData);
	
		Plot2DPanel plot = new Plot2DPanel();
		
		// add a line plot to the PlotPanel
		plot.addLinePlot("y = x + noise", accmags);

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
