package particleSwarmOptimisationForMarketPricing;

import pricingProblem.Main;
import pricingProblem.PricingProblem;
import pricingProblem.PricingProblemHelper;

/**
 * This object represents a Particle Swarm Optimisation algorithm to find the best pricing strategy for the Market Pricing Problem.
 * @author Tarek Foyz Ahmed
 *
 */
public class ParticleSwarmOptimisationForMarketPricingProblem {
	private PricingProblem pricingProblem;
	private PricingProblemHelper pricingProblemHelper;
	
	public ParticleSwarmOptimisationForMarketPricingProblem(PricingProblem pricingProblem, PricingProblemHelper pricingProblemHelper) {
		this.pricingProblem = pricingProblem;
		this.pricingProblemHelper = pricingProblemHelper;
	}
	
	/**
	 * Runs a Particle Swarm Optimisation search to find the best strategy for the given market pricing problem instance.
	 * @param pricingProblem - An instance of PricingProblem.
	 * @param numParticles - Number of particles to have in swarm population.
	 */
	public void run() {
		Swarm swarm = new Swarm(pricingProblem, pricingProblemHelper);
		
		if(Main.PRINT_ALL_ITERATIONS == true) {	
			System.out.println("SWARM INITIALISED");

			System.out.println("STARTING PARTICLE SWARM OPTIMISATION");
		}
		
		/* Updates swarm until the fitness evaluations limit is reached */
		while (pricingProblemHelper.getFitnessEvaluationCount() < Main.FITNESS_EVALUATIONS_LIMIT){
			swarm.update();
		}
		if(Main.PRINT_ALL_ITERATIONS == true) {	
			System.out.println();
			System.out.println("FITNESS EVALUATIONS LIMIT REACHED!");
		}
		
		System.out.println("---------------------------------------------------------------------PARTICLE SWARM OPTIMISATION SEARCH COMPLETE!----------------------------------------------------------------------");
		System.out.println("The best strategy found is where the total revenue = £" + pricingProblem.evaluate(Swarm.globalBestPosition) + " for strategy:");
		pricingProblemHelper.printStrategy(Swarm.globalBestPosition);
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
}
