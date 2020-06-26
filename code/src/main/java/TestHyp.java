
public class TestHyp {
	public static void main(String[] args) {
		
		//a dataset consisting of 3 features each with 2 possible values is constructed as follows
		//feature 1 can have values A and B, feature 2 can have values C and D, feature 3 can have values E and F
		//? represents any value
		char[] c1 = {'A', 'B', '?'};
		char[] c2 = {'C', 'D', '?'};
		char[] c3 = {'E', 'F', '?'};
		Hypothesis[] hypotheses = new Hypothesis[27];
		
		//all possible combinations of them are constructed
		for(int i=0; i<Math.pow(3, 3); i++) {
			int[] result = new int[3];
			int copyi = i;
			for(int j = 2; j>=0;j--) {
				int mod = (copyi%3);
				copyi/=3;
				result[j]=mod;
			}
			hypotheses[i]=new Hypothesis(new String[] {Character.toString(c1[result[0]]), Character.toString(c2[result[1]]), Character.toString(c3[result[2]])});
		}
		
		//testing of the methods is done whith the above construced hypotheses as arguments
		System.out.println("Checking the isMoreGeneralThan method:");
		for(int i=0; i<27; i++) {
			for(int j=0;j<27;j++) {
				if(i == j) continue;
				System.out.println("Hypothesis "+hypotheses[i]+" more general than "+hypotheses[j]+" = "+hypotheses[i].isMoreGeneralThan(hypotheses[j]));
			}
			System.out.println("------------------------------------------");
		}
		
		System.out.println("Checking the isMoreSpecificThan method:");
		for(int i=0; i<27; i++) {
			for(int j=0;j<27;j++) {
				if(i == j) continue;
				System.out.println("Hypothesis "+hypotheses[i]+" more specific than "+hypotheses[j]+" = "+hypotheses[i].isMoreSpecificThan(hypotheses[j]));
			}
			System.out.println("------------------------------------------");
		}
	}
	
}
