package particleSwarmOptimisationForMarketPricing;

import pricingProblem.Main;
import pricingProblem.PricingProblem;
import pricingProblem.PricingProblemHelper;

/**
 * This object represents a single particle for the Particle Swarm Optimisation Algorithm.
 * @author Tarek Foyz Ahmed
 *
 */
public class Particle {
	private PricingProblemHelper pricingProblemHelper;
	private PricingProblem pricingProblem;
	
	private double[] currentPosition = new double[3];
	private double[] velocity = new double[3];
	private double[] bestPosition = new double[3];
	private double totalRevenueOfBestPosition;
	
	/**
	 * Instantiates a particle with a random strategy.
	 * @param pricingProblem - The instance of PricingProblem.
	 * @param pricingProblemHelper
	 */
	public Particle(PricingProblem pricingProblem, PricingProblemHelper pricingProblemHelper) {
		this.pricingProblem = pricingProblem;
		this.pricingProblemHelper = pricingProblemHelper;
		
		currentPosition = pricingProblemHelper.generateRandomValidStrategy();
		double[] secondFeasiblePosition = pricingProblemHelper.generateRandomValidStrategy();
		velocity = getInitialVelocity(secondFeasiblePosition, currentPosition);
		bestPosition = currentPosition;
		totalRevenueOfBestPosition = this.pricingProblem.evaluate(bestPosition);
	}
	
	/**
	 * Updates the particle's position if the next position is a valid one.
	 * @param globalBestPosition - The best position found across all particles.
	 */
	public void update(double[] globalBestPosition) {
		/* Generate uniform random vectors */
		double[] r1 = pricingProblemHelper.generateRandomValidStrategy();
		double[] r2 = pricingProblemHelper.generateRandomValidStrategy();
		
		/* Calculate the co-efficients */
		double[] inertia = vectorScalarMultiplication(Main.INERTIAL, velocity);
		double[] cognitiveAttraction = vectorMultiplication(vectorScalarMultiplication(Main.COGNITIVE, r1), vectorSubtraction(bestPosition, currentPosition));
		double[] socialAttraction = vectorMultiplication(vectorScalarMultiplication(Main.SOCIAL, r2), vectorSubtraction(Swarm.globalBestPosition, currentPosition));
		
		/* Calculate the new velocity */
		velocity = vectorAddition(inertia, vectorAddition(cognitiveAttraction, socialAttraction));
		
		/* Invisible wall constraint. Update the position only if the new position is valid. Updates the Swarm's globalBestPosition if it's a better solution. */
		if(pricingProblem.is_valid(vectorAddition(currentPosition, velocity))) {
			currentPosition = vectorAddition(currentPosition, velocity);
			pricingProblemHelper.incrementFitnessEvaluationCount();
			if(totalRevenueOfBestPosition < pricingProblem.evaluate(currentPosition)) {
				totalRevenueOfBestPosition = pricingProblem.evaluate(currentPosition);
				bestPosition = currentPosition;
				if(pricingProblem.evaluate(currentPosition) > pricingProblem.evaluate(globalBestPosition)) {
					Swarm.globalBestPosition = currentPosition;
				}
			}
		}
	}
	
	/**
	 * Returns the particle's personal best position. 
	 * @return double[]
	 */
	public double[] getBestPosition() {
		return bestPosition;
	}
	
	/**
	 * Returns the particle's current position. 
	 * @return double[]
	 */
	public double[] getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * Calculates and returns the initial velocity of the particle.
	 * @param strategyA - Strategy. 
	 * @param strategyB - Strategy.
	 * @return double[]
	 */
	private double[] getInitialVelocity(double[] strategyA, double[] strategyB) {
		double [] initialVelocity = new double[strategyA.length];
		
		for(int i = 0; i < strategyA.length; i++) {
			initialVelocity[i] = (strategyA[i] - strategyB[i]) / 2;
		}
		
		return initialVelocity;
	}
	
	/**
	 * Returns the result of addition between two vectors.
	 * @param vectorA double[]
	 * @param vectorB double[] 
	 * @return double[]
	 */
	private double[] vectorAddition(double[] vectorA, double[] vectorB) {
		double [] resultingVector = new double[vectorA.length];
		
		for(int i = 0; i < vectorA.length; i++) {
			resultingVector[i] = vectorA[i] + vectorB[i];
		}
		
		return resultingVector;
	}
	
	/**
	 * Returns the result of subtraction between two vectors.
	 * @param vectorA double[]
	 * @param vectorB double[] 
	 * @return double[]
	 */
	private double[] vectorSubtraction(double[] vectorA, double[] vectorB) {
		double [] resultingVector = new double[vectorA.length];
		
		for(int i = 0; i < vectorA.length; i++) {
			resultingVector[i] = vectorA[i] - vectorB[i];
		}
		
		return resultingVector;
	}
	
	/**
	 * Returns the result of multiplication between two vectors.
	 * @param vectorA double[]
	 * @param vectorB double[] 
	 * @return double[]
	 */
	private double[] vectorMultiplication(double[] vectorA, double[] vectorB) {
		double [] resultingVector = new double[vectorA.length];
		
		for(int i = 0; i < vectorA.length; i++) {
			resultingVector[i] = vectorA[i] * vectorB[i];
		}
		
		return resultingVector;
	}
	
	/**
	 * Returns the result of a scalar multiplication on a vector.
	 * @param coefficient double - The scalar coefficient.
	 * @param vector double[] - The vector to apply the multiplication to.
	 * @return double[]
	 */
	private double[] vectorScalarMultiplication(double coefficient, double[] vector) {
		double [] resultingVector = new double[vector.length];
		
		for(int i = 0; i < vector.length; i++) {
			resultingVector[i] = coefficient * vector[i];
		}
		
		return resultingVector;
	}
}
