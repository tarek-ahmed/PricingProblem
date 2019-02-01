package pricingProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import evolutionaryAlgorithmForMarketPricing.EvolutionaryAlgorithmForMarketPricing;
import evolutionaryAlgorithmForMarketPricing.Population;

public class BasicUnitTests {

	public static void main(String[] args) {
		PricingProblem pricingProblem = PricingProblem.courseworkInstance();
		PricingProblemHelper oricingProblemHelper = new PricingProblemHelper(pricingProblem);
		Random rng = new Random();
		EvolutionaryAlgorithmForMarketPricing evolutionaryAlgorithmForMarketPricing = new EvolutionaryAlgorithmForMarketPricing(pricingProblem, oricingProblemHelper);
		
		/* Tests the generateRandomValidStrategy function in PricingProblemHelper */
//		System.out.println(Arrays.toString(oricingProblemHelper.generateRandomValidStrategy()));
		
		/* Tests the getNeighbourhood function in PricingProblemHelper */
//		double[] strategy = oricingProblemHelper.generateRandomValidStrategy();
//		System.out.println("INPUT: " + Arrays.toString(strategy));
//		List<double[]> neighbourhood = oricingProblemHelper.getNeighbourhood(strategy );
//		for(double[] member : neighbourhood) {
//			System.out.println("OUTPUT: " + Arrays.toString(member));
//		}
		
		/* Tests the mutateStrategy function in EvolutionaryAlgorithmForMarketPricing */
//		double[] strategy = oricingProblemHelper.generateRandomValidStrategy();
//		System.out.println("INPUT: " + Arrays.toString(strategy));
//		System.out.println("OUTPUT: " + Arrays.toString(evolutionaryAlgorithmForMarketPricing.mutateStrategy(strategy)));
		
		/* Tests the selectParents function in EvolutionaryAlgorithmForMarketPricing */
//		Population population = new Population(pricingProblem, oricingProblemHelper);
//		double[] strategy1 = oricingProblemHelper.generateRandomValidStrategy();
//		population.addMember(strategy1);
//		double[] strategy2 = oricingProblemHelper.generateRandomValidStrategy();
//		population.addMember(strategy2);
//		double[] strategy3 = oricingProblemHelper.generateRandomValidStrategy();
//		population.addMember(strategy3);
//		double[] strategy4 = oricingProblemHelper.generateRandomValidStrategy();
//		population.addMember(strategy4);
//		double[] strategy5 = oricingProblemHelper.generateRandomValidStrategy();
//		population.addMember(strategy5);
//		
//		ArrayList<Object[]> wholePopulation = population.getPopulation();
//		
//		System.out.println("POPULATION: ");
//		for (Object[] member : wholePopulation) {
//			System.out.println(Arrays.toString((double[])member[0]));
//			System.out.println(member[1]);
//		}
//		
//		System.out.println();
//		System.out.println("PARENTS SELECTED: ");
//		ArrayList<double[]> parents = evolutionaryAlgorithmForMarketPricing.selectParents(population);
//		
//		for(double[] parent : parents) {
//			System.out.println(Arrays.toString(parent));
//			System.out.println(pricingProblem.evaluate(parent));
//		}
//		
		/* Tests the createChild function in EvolutionaryAlgorithmForMarketPricing */
//		double[] strategy1 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
//		double[] strategy2 = {10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0};
//		System.out.println(Arrays.toString(evolutionaryAlgorithmForMarketPricing.createChild(strategy1, strategy2)));
	}
}
