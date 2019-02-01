package pricingProblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import evolutionaryAlgorithmForMarketPricing.EvolutionaryAlgorithmForMarketPricing;
import particleSwarmOptimisationForMarketPricing.ParticleSwarmOptimisationForMarketPricingProblem;

/**
 * This object is used to compare and test the proposed algorithms. It is the main entry point to the application. It takes in the parameter values
 * provided by the user in the input.txt file and undertakes 5 runs of particle swarm optimisation for market pricing, 5 runs of 
 * evolutionary algorithm for market pricing, and 1 run of both algorithms before generating graphs in the 'Results Graphs' directory.
 * @author Tarek Foyz Ahmed
 *
 */
public class Main {
	public static int NUM_ITEMS_IN_STRATEGY;
	public static double MIN_PRICE;
	public static double MAX_PRICE;

	public static int FITNESS_EVALUATIONS_LIMIT;
	public static int POPULATION_SIZE;

	/* Parameters for Particle Swarm Optimisation Algorithm */
	public static double COGNITIVE;
	public static double SOCIAL;
	public static double INERTIAL;
	
	/* Parameters for Evolutionary Algorithm */
	public static double MUTATION_PROBABILITY;
	public static int CROSSOVER_SET_SIZE;
	public static int PARENT_SELECTION_TOURNAMENT_SIZE;
	
	/* Fields for charting */
	public static XYSeries PSOSeries = new XYSeries("Particle Swarm Optimisation");
	public static XYSeries EASeries = new XYSeries("Evolutionary Algorithm");
	public static ArrayList<Double[]> PSOValues = new ArrayList<Double[]>();
	public static ArrayList<Double[]> EAValues = new ArrayList<Double[]>();
	
	/* File paths for settings */
	private String generalSettingsFilePath = "settings.txt";
	private String parameterSettingsFilePath = "parameterSettings.txt";
	
	/* Settings */
	public static boolean PRINT_ALL_ITERATIONS;
	public static boolean SHOW_GOODS_SETUP_DETAILS;

	/* Entry point of application */
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.runParticleSwarmOptimisationAlgorithm(5);
		main.runEvolutionaryAlgorithm(5);
		main.runBoth();
		System.out.println();
		System.out.println("Finished. Results graphs can be found in the 'Results' directory.");
	}
	
	/**
	 * Reads in the input file to instantiate the constant parameter values.
	 * @throws IOException
	 */
	public Main() throws IOException {
		String[] line = new String[2];
		
		BufferedReader generalSettingsReader = new BufferedReader(new FileReader(generalSettingsFilePath));
		line = generalSettingsReader.readLine().split("=");
		PRINT_ALL_ITERATIONS = Boolean.parseBoolean(line[1]);
		line = generalSettingsReader.readLine().split("=");
		SHOW_GOODS_SETUP_DETAILS = Boolean.parseBoolean(line[1]);
		generalSettingsReader.close();
		
		BufferedReader parameterSettingsReader = new BufferedReader(new FileReader(parameterSettingsFilePath));
		line = parameterSettingsReader.readLine().split("=");
		NUM_ITEMS_IN_STRATEGY = Integer.parseInt(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		MIN_PRICE = Double.parseDouble(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		MAX_PRICE = Double.parseDouble(line[1]);
		parameterSettingsReader.readLine();
		line = parameterSettingsReader.readLine().split("=");
		FITNESS_EVALUATIONS_LIMIT = Integer.parseInt(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		POPULATION_SIZE = Integer.parseInt(line[1]);
		parameterSettingsReader.readLine();
		parameterSettingsReader.readLine();
		line = parameterSettingsReader.readLine().split("=");
		COGNITIVE = Double.parseDouble(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		SOCIAL = Double.parseDouble(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		INERTIAL = Double.parseDouble(line[1]);
		parameterSettingsReader.readLine();
		parameterSettingsReader.readLine();
		line = parameterSettingsReader.readLine().split("=");
		MUTATION_PROBABILITY = Double.parseDouble(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		CROSSOVER_SET_SIZE = Integer.parseInt(line[1]);
		line = parameterSettingsReader.readLine().split("=");
		PARENT_SELECTION_TOURNAMENT_SIZE = Integer.parseInt(line[1]);
		parameterSettingsReader.close();
	}
	
	/**
	 * Instantiates a ParticleSwarmOptimizationForMarketPricingProblem object and runs it @param numberOfRuns times before generating a line graph
	 * of the results to compare the runs.
	 * @param numberOfRuns int - The number of runs to perform.
	 */
	private void runParticleSwarmOptimisationAlgorithm(int numberOfRuns) {
		PricingProblem pricingProblem = PricingProblem.courseworkInstance();
		PricingProblemHelper pricingProblemHelper = new PricingProblemHelper(pricingProblem);
		ParticleSwarmOptimisationForMarketPricingProblem particleSwarmOptimization = new ParticleSwarmOptimisationForMarketPricingProblem(pricingProblem, pricingProblemHelper);
		
		XYSeriesCollection datasetForPSO = new XYSeriesCollection();

		/* Run the algorithm numberOfRuns times and add results to chart */
		System.out.println();
		System.out.println();
		System.out.println("RUNNING PARTICLE SWARM OPTIMISATION " + numberOfRuns + " TIMES...");
		for(int i = 0; i < numberOfRuns; i++) {
			System.out.println("RUN " + (i+1));
			String key = "Run " + (i+1);
			XYSeries PSOSeries = new XYSeries(key);
			pricingProblemHelper.resetFitnessEvaluationCount();
			particleSwarmOptimization.run();
			
			for(Double[] value : PSOValues) {
				PSOSeries.add(value[0], value[1]);
			}
			
			datasetForPSO.addSeries(PSOSeries);
			PSOValues.clear();
		}
		
		/* Create and save the chart */
		JFreeChart chart = ChartFactory.createXYLineChart("Graph comparing the runs of the Particle Swarm Optimisation over a limited amount of fitness evaluations", "Number of fitness evaluations", "Highest revenue found (£)", datasetForPSO, PlotOrientation.VERTICAL, true, true, false);
		try {
			ChartUtilities.saveChartAsJPEG(new File("./Results/Particle swarm optimisation results.jpg"), chart, 1000, 700);
			System.out.println("TEST COMPLETED. GRAPH CREATED AND SAVED");
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
	
	/**
	 * Instantiates a EvolutionaryAlgorithmForMarketPricing object and runs it @param numberOfRuns times before generating a line graph
	 * of the results to compare the runs.
	 * @param numberOfRuns int - The number of runs to perform.
	 */
	private void runEvolutionaryAlgorithm(int numberOfRuns) {
		PricingProblem pricingProblem = PricingProblem.courseworkInstance();
		PricingProblemHelper pricingProblemHelper = new PricingProblemHelper(pricingProblem);
		EvolutionaryAlgorithmForMarketPricing evolutionaryAlgorithmForMarketPricing = new EvolutionaryAlgorithmForMarketPricing(pricingProblem, pricingProblemHelper);
		
		XYSeriesCollection datasetForEA = new XYSeriesCollection();
		
		/* Run the algorithm numberOfRuns times and add results to chart */
		System.out.println();
		System.out.println();
		System.out.println("RUNNING EVOLUTIONARY ALGORITHM " + numberOfRuns + " TIMES...");
		for(int i = 0; i < numberOfRuns; i++) {
			System.out.println("RUN " + (i+1));
			String key = "Run " + (i+1);
			XYSeries EASeries = new XYSeries(key);
			pricingProblemHelper.resetFitnessEvaluationCount();
			evolutionaryAlgorithmForMarketPricing.run();
			
			for(Double[] value : EAValues) {
				EASeries.add(value[0], value[1]);
			}
			
			datasetForEA.addSeries(EASeries);
			EAValues.clear();
		}
		
		/* Create and save the chart */
		JFreeChart chart = ChartFactory.createXYLineChart("Graph comparing the runs of the Evolution Algorithm over a limited amount of fitness evaluations", "Number of fitness evaluations", "Highest revenue found (£)", datasetForEA, PlotOrientation.VERTICAL, true, true, false);
		try {
			ChartUtilities.saveChartAsJPEG(new File("./Results/Evolutionary algorithm results.jpg"), chart, 1000, 700);
			System.out.println("TEST COMPLETED. GRAPH CREATED AND SAVED");
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
	
	/**
	 * Instantiates a ParticleSwarmOptimizationForMarketPricingProblem object and an EvolutionaryAlgorithmForMarketPricing object before running thme both 
	 * once and generating a line graph to compare both.
	 */
	private void runBoth() {
		PricingProblem pricingProblem = PricingProblem.courseworkInstance();
		PricingProblemHelper pricingProblemHelper = new PricingProblemHelper(pricingProblem);
		
		PSOSeries.clear();
		EASeries.clear();
		
		System.out.println();
		System.out.println();
		System.out.println("RUNNING PARTICLE SWARM OPTIMISATION...");
		ParticleSwarmOptimisationForMarketPricingProblem particleSwarmOptimization = new ParticleSwarmOptimisationForMarketPricingProblem(pricingProblem, pricingProblemHelper);
		particleSwarmOptimization.run();
		
		pricingProblemHelper.resetFitnessEvaluationCount();
		
		System.out.println();
		System.out.println();
		System.out.println("RUNNING EVOLUTIONARY ALGORITHM...");
		EvolutionaryAlgorithmForMarketPricing evolutionaryAlgorithmForMarketPricing = new EvolutionaryAlgorithmForMarketPricing(pricingProblem, pricingProblemHelper);
		evolutionaryAlgorithmForMarketPricing.run();
	
		XYSeriesCollection datasetForBoth = new XYSeriesCollection();
		datasetForBoth.addSeries(PSOSeries);
		datasetForBoth.addSeries(EASeries);

		/* Create and save the chart */
		JFreeChart chart = ChartFactory.createXYLineChart("Graph comparing the progress of the Particle Swarm Optimisation and Evolutionary Algorithm over a limited amount of fitness evaluations", "Number of fitness evaluations", "Highest revenue found (£)", datasetForBoth, PlotOrientation.VERTICAL, true, true, false);
		try {
			ChartUtilities.saveChartAsJPEG(new File("./Results/Comparison of both.jpg"), chart, 1000, 700);
			System.out.println("TEST COMPLETED. GRAPH CREATED AND SAVED");
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
}
