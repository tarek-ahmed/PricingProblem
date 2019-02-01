package evolutionaryAlgorithmForMarketPricing;

import java.util.ArrayList;

import pricingProblem.Main;
import pricingProblem.PricingProblem;
import pricingProblem.PricingProblemHelper;

/**
 * This object represents a population of candidate strategies. The main data structure for the population is an 
 * ArrayList<Object[]>. Each Object[] in this list contains 2 Objects. Index 0 contains the strategy as a double[], 
 * and index 1 is the total revenue of that strategy as a double.
 * @author Tarek Foyz Ahmed
 *
 */
public class Population {
	private ArrayList<Object[]> population;
	private int populationSize;
	private PricingProblemHelper pricingProblemHelper;
	private PricingProblem pricingProblem;
	
	public Population(PricingProblem pricingProblem, PricingProblemHelper pricingProblemHelper) {
		this.pricingProblem = pricingProblem;
		this.populationSize = Main.POPULATION_SIZE;
		this.population = new ArrayList<Object[]>(populationSize);
		this.pricingProblemHelper = pricingProblemHelper;
	}
	
	/**
	 * Initialises the population with random valid strategies as candidates.
	 */
	public void initialise() {
		/* Until population size is met, add a random valid strategy */
		for(int i = 0; i < populationSize; i++) {
			Object[] candidate = new Object[2];
			
			double[] strategy = pricingProblemHelper.generateRandomValidStrategy();
			double cost = pricingProblem.evaluate(strategy);
			candidate[0] = strategy;
			candidate[1] = cost;
			
			population.add(candidate);
		}
	}
	
	/**
	 * Returns the population data structure. 
	 * @return ArrayList<Object[]>
	 */
	public ArrayList<Object[]> getPopulation() {
		return population;
	}
	
	/**
	 * Adds the @param strategy to the population.
	 * @param strategy Strategy - The strategy to add to the population. 
	 */
	public void addMember(double[] strategy) {
		Object[] candidate = new Object[2];
		candidate[0] = strategy;
		candidate[1] = pricingProblem.evaluate(strategy);
		population.add(candidate);
	}
	
	/**
	 * Returns the size of the population instance.
	 * @return int
	 */
	public int getPopulationSize() {
		return population.size();
	}
}
