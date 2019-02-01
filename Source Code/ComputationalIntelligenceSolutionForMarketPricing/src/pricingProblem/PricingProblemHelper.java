package pricingProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This object provides the additional functions required to solve the market pricing problem.
 * @author Tarek Foyz Ahmed
 *
 */
public class PricingProblemHelper {
	private int fitnessEvaluationCount = 0;
	private PricingProblem pricingProblem;

	public PricingProblemHelper(PricingProblem pricingProblem) {
		this.pricingProblem = pricingProblem;
	}
	
	/**
	 * Increments the fitness evaluations count.
	 */
	public void incrementFitnessEvaluationCount() {
		fitnessEvaluationCount++;
	}
	
	/**
	 * Reset's the fitness evaluations count.
	 */
	public void resetFitnessEvaluationCount() {
		fitnessEvaluationCount = 0;
	}
	
	/**
	 * Returns the fitness evaluations count.
	 * @return int
	 */
	public int getFitnessEvaluationCount() {
		return fitnessEvaluationCount;
	}
	
	/**
	 * Generates and returns a random valid pricing strategy based on the number of products allowed.   
	 * @return double[] 
	 */
	public double[] generateRandomValidStrategy() {
		double[] validStrategy = new double[Main.NUM_ITEMS_IN_STRATEGY];
		Random r = new Random();
		
		/* Adds random double values to validStrategy object that are between minPrice and maxPrice until validStrategy is valid */
		while(!pricingProblem.is_valid(validStrategy)) {
			for(int i = 0; i < Main.NUM_ITEMS_IN_STRATEGY; i++) {
				validStrategy[i] = Main.MIN_PRICE + (Main.MAX_PRICE - Main.MIN_PRICE) * r.nextDouble();
			}
		}
		
		return validStrategy;
	}
	
	/**
	 * Returns a list of neighbouring strategies of the given strategy using the 2-opt swap method.
	 * @param strategy double[] - Specified strategy to find neighbouring strategies for.
	 * @return List<double[]>
	 */
	public ArrayList<double[]> getNeighbourhood(double[] strategy){
		ArrayList<double[]> neighbourhood = new ArrayList<double[]>();
		
		for(int i = 0 ; i < strategy.length ; i++) {
			for(int j = 1 ; j < strategy.length ; j++) {
				/* Creates a copy of the given strategy */
				double[] newNeighbouringStrategy = new double[strategy.length];
				for(int k = 0; k < strategy.length; k++) {
					newNeighbouringStrategy[k] = strategy[k];
				}
				
				/* Does the necessary swap for the new strategy */
				double x = newNeighbouringStrategy[i];
				double y = newNeighbouringStrategy[j];
				
				newNeighbouringStrategy[i] = y;
				newNeighbouringStrategy[j] = x;
				
				/* If it's not a duplicate, then it adds it to the neighbourhood */
				boolean isDuplicate = false;
				
				for (double[] member : neighbourhood) {
					if(Arrays.equals(member, newNeighbouringStrategy) || Arrays.equals(newNeighbouringStrategy, strategy)) {
						isDuplicate = true;
					}
				}
				
				if(isDuplicate) {
					continue;
				} else {
					neighbourhood.add(newNeighbouringStrategy);
				}
			}
		}

		return neighbourhood;
	}
	
	/**
	 * Prints the given strategy to the console.
	 * @param strategy double[] - The strategy to print.
	 */
	public void printStrategy(double[] strategy) {
		for(int j = 0; j < strategy.length; j++) {
			System.out.println(" ITEM " + (j+1) + " = £" + strategy[j]);
		}
	}
	
	/**
	 * Runs a random search to find the best strategy for the given pricing problem instance.
	 * @param pricingProblem PricingProblem - An instance of PricingProblem.
	 * @param timeLimitInMilliseconds int - The amount of time to run the search for in milliseconds.
	 */
	public void runRandomSearch(int timeLimitInMilliseconds) {
		long currentTime = System.currentTimeMillis();
		ArrayList<Object> bestStrategy = new ArrayList<Object>();
		
		while (System.currentTimeMillis() <= currentTime + timeLimitInMilliseconds){
			double[] newStrategy = generateRandomValidStrategy();
			
			double totalRevenue = pricingProblem.evaluate(newStrategy);
			
			if(bestStrategy.isEmpty() || (double)bestStrategy.get(1) < totalRevenue) {
				bestStrategy.clear();
				bestStrategy.add(newStrategy);
				bestStrategy.add(totalRevenue);
			}
			
			System.out.print("Highest total revenue = £" + pricingProblem.evaluate(newStrategy));
			System.out.print(" for strategy:");
			printStrategy(newStrategy);
			System.out.println();
		};
		
		double[] bestStrategyPositions = (double[])bestStrategy.get(0);
		double bestStrategyTotalRevenue = (double)bestStrategy.get(1);
		
		System.out.println("--------------------------------------------------------------------------------RANDOM SEARCH COMPLETE!--------------------------------------------------------------------------------");
		System.out.println("The best strategy found is when where the total revenue = £" + bestStrategyTotalRevenue + " for strategy:");
		for(int i = 0; i < bestStrategyPositions.length; i++) {
			System.out.print(" Item " + (i+1) + " = £" + bestStrategyPositions[i]);
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
}

