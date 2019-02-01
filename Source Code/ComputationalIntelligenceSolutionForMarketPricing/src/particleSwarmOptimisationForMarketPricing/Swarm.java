package particleSwarmOptimisationForMarketPricing;

import java.util.ArrayList;
import java.util.List;

import pricingProblem.Main;
import pricingProblem.PricingProblem;
import pricingProblem.PricingProblemHelper;

/**
 * This object represents a swarm of particles for the Particle Swarm Optimisation.
 * @author Tarek Foyz Ahmed
 *
 */
public class Swarm {
	public static double[] globalBestPosition;
	
	private List<Particle> population = new ArrayList<Particle>();
	private PricingProblem pricingProblem;
	private PricingProblemHelper pricingProblemHelper;
	
	/**
	 * Instantiates a swarm with a population with the size of the specified number of particles in the Main object.
	 * @param pricingProblem PricingProblem - The instance of PricingProblem.
	 * @param pricingProblemHelper PricingProblemHelper - The instance of PricingProblem.
	 */
	public Swarm(PricingProblem pricingProblem, PricingProblemHelper pricingProblemHelper) {
		Swarm.globalBestPosition = new double[Main.NUM_ITEMS_IN_STRATEGY];
		this.pricingProblemHelper = pricingProblemHelper;
		this.pricingProblem = pricingProblem;
		
		/* Initialise the Swarm of particles based on the specified population size from the input file */
		while(population.size() < Main.POPULATION_SIZE) {
			population.add(new Particle(pricingProblem, pricingProblemHelper));
		}
		
		/* Find the globalBestPosition of the initialised population */
		double highestTotalRevenue = 0.0;
		for(Particle particle : population) {
			if(highestTotalRevenue == 0.0 || pricingProblem.evaluate(particle.getBestPosition()) > highestTotalRevenue) {
				highestTotalRevenue = pricingProblem.evaluate(particle.getBestPosition());
				globalBestPosition = particle.getBestPosition();
			};
		}
	}
	
	/**
	 * Updates the Swarm.
	 */
	public void update() {
		ArrayList<Object> bestStrategyThisIteration = new ArrayList<Object>();
		
		/* Update all particles in the population and keep find of the best strategy in the iteration */
		for(Particle particle : population) {
			particle.update(globalBestPosition);
			pricingProblemHelper.incrementFitnessEvaluationCount();
			if((bestStrategyThisIteration.isEmpty() || (double)bestStrategyThisIteration.get(1) < pricingProblem.evaluate(particle.getCurrentPosition()))) {
				bestStrategyThisIteration.clear();
				bestStrategyThisIteration.add(particle.getCurrentPosition());
				bestStrategyThisIteration.add(pricingProblem.evaluate(particle.getCurrentPosition()));
			}
		}
		
		if(Main.PRINT_ALL_ITERATIONS == true) {			
			System.out.print("Highest total revenue in this iteration = £" + bestStrategyThisIteration.get(1));
			System.out.print(" for strategy:");
			pricingProblemHelper.printStrategy((double[])bestStrategyThisIteration.get(0));
			System.out.println();
		}
		
		/* Add chart data */
		Main.PSOSeries.add(pricingProblemHelper.getFitnessEvaluationCount(), (double)bestStrategyThisIteration.get(1));
		Double[] value = {(double) pricingProblemHelper.getFitnessEvaluationCount(), (double)bestStrategyThisIteration.get(1)};
		Main.PSOValues.add(value);
	}
}
