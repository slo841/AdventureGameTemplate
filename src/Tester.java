import processing.core.PApplet;

public class Tester {
	public static double[][] sampleData;
	public static String datafile = "data/walkingSampleData-out.csv";
	public static String videofile = "data/walkingSampleData.mp4";

	public static void main(String[] args) {
		// Create data set
		CSVData dataset = CSVData.createDataSet(datafile, 0);

		// Get 2d array of all data
		sampleData = dataset.getAllData();

		// Extract columns for time, and x acceleration, y acceleration, z
		// acceleration
		double[] time = ArrayHelper.extractColumn(sampleData, 0);
		double[][] accelerationData = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3 });
		double[][] gyroData = ArrayHelper.extractColumns(sampleData, new int[] { 4, 5, 6 });

		double[] accelerationMagnitudes = CountStepsBlank.calculateMagnitudesFor(accelerationData);
		double[] gyroMagnitudes = CountStepsBlank.calculateMagnitudesFor(gyroData);

		double[][] displayData = ArrayHelper.combineAsColumns(time, accelerationMagnitudes, gyroMagnitudes);
		
		DataExplorer.runDataExplorer(displayData, new String[] {"time" ,"accmag", "gyromag"}, videofile);
		System.out.println(CountStepsBlank.countSteps(time, accelerationData));
	}
}