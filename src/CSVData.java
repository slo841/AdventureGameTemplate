import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVData {
	private static boolean DEBUG = false;
	private double[][] rawData;

	/***
	 * Changes the raw data into the new, organized data
	 * 
	 * @param data
	 * 			the new, organized data
	 */
	private CSVData(double[][] data) {
		rawData = data;
	}
	
	/***
	 * Creates a new CSVData object from the given filepath
	 * 
	 * @param filepath
	 * 			the path to the file
	 * 
	 * @param linesToSkip
	 * 			the number of lines to skip when creating the object
	 * 
	 * @return a new CSVData object
	 * 		
	 */
	public static CSVData createDataSet(String filepath, int linesToSkip) {
		debug("Reading file: " + filepath);
		
		String data = readFileAsString(filepath);
		String[] lines = data.split("\n");
		
		debug("Reading " + lines.length + " total lines from file");
		debug("Using index " + (linesToSkip) + " as header row");
		
		String headerLine = lines[linesToSkip];
		debug("Headers: " + headerLine);
		
		String[] headers = headerLine.split(",");
		debug("Parsed header line into: " + headers.length + " total columns");
		
		int startColumn = 0;
		return createDataSet(filepath, linesToSkip + 1, headers, startColumn);
	}
	
	/***
	 * Creates a new CSVdata object from the filepath and 
	 * includes headers
	 * 
	 * @param filepath
	 * 			the path to the file
	 * 
	 * @param linesToSkip
	 * 			the number of lines to skip when creating data set
	 * 
	 * @param columnHeaders
	 * 			a String array of all the column names
	 * 
	 * @param startColumn
	 * 			the starting column
	 * 
	 * @return a new CSVData object
	 */
	public static CSVData createDataSet(String filepath, int linesToSkip, String[] columnHeaders, int startColumn) {
		debug("Reading file: " + filepath);
		
		String data = readFileAsString(filepath);
		String[] lines = data.split("\n");
		
		debug("Reading " + lines.length + " total lines from file");
		
		int numColumns = columnHeaders.length;
		debug("Reading " + numColumns + " total columns");
		
		int startRow = linesToSkip;
		
		// create storage for data
		double[][] numdata = new double[lines.length - linesToSkip][numColumns];

		for (int r = startRow; r < lines.length; r++) {
			String line = lines[r];
			String[] coords = line.split(",");

			for (int j = startColumn; j < numColumns; j++) {
				if (coords[j].endsWith("#")) coords[j] = coords[j].substring(0, coords[j].length()-1);
				double val = Double.parseDouble(coords[j]);
				numdata[r - 1][j - startColumn] = val;
			}
		}

		return new CSVData(numdata);
	}
	
	public double[][] getAllData() {
		return rawData;
	}
	
	/***
	 * Saves the state of the data into a CSV file
	 * 
	 * @param filePath
	 *            the path to the file that you save the data into
	 * @param data
	 *            the file data
	 */
	public static void writeDataToFile(String filePath, String data) {
		File outFile = new File(filePath);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
			writer.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Returns a file as a String
	 * 
	 * @param filepath 
	 * 			the path to the file
	 * @return output
	 * 			the resulting String
	 */
	public static String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();

		try (Scanner scanner = new Scanner(new File(filepath))) {

			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output.toString();
	}
	
	/***
	 * Checks if an error has occurred
	 * 
	 * @param string
	 * 			the String that might or might not
	 * 			have an error
	 */
	private static void debug(String string) {
		if (DEBUG ) {
			System.err.println(string);
		}
	}
}