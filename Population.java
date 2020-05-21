import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * Klasa Population eshte klase modeluese e cila permbane popullaten apo grupin e kromosomeve me te cilat manovrohet
 * me qellim qe te arrim ne targetin final
 *
 */
public class Population {
	Random r = new Random();
	Dna[] population;//Vargu qe permban kromosomet
	ArrayList<Dna> matingPool = new ArrayList<>();//Vargu ne te cilin vendosen kromosomet kromosomet te shumefishuara ne baze te fitnessit te tyre
	private int generations = 0;//Numri i gjeneratave te krijuara PrindA + PrindB = Femij - generation++
	private boolean finished = false;//Variabla na mundeson ta dime a kemi arritur ne qellimin e duhur
	String target;//Targeti apo qellimi i kerkuar
	double mutationRate;//Vlera e mutacionit
	int popNum;//Numri maksimal i nje popullate apo grupi kromosomesh
	private int perfectScore = 1;//Variabel qe mundeson per caktimin e arritjes se qellimit
	String best = "";//Kromosomi me i mire

	/**
	 * Metode e cila ben krijimin e gjenerates se pare
	 */
	public void firstGen() {

		this.population = new Dna[popNum];
		for (int i = 0; i < popNum; i++) {
			population[i] = new Dna(target.length());
		}
		calcFitness();

	}
/**
 * Metode e cila llogarite fitnessin e tgjith kromosomeve ne popullate
 */
	public void calcFitness() {
		for (int i = 0; i < this.population.length; i++) {

			this.population[i].calcFitness(target);
		}
	}
/**
 * Metode e vecant e GA e cila ben selektimin natyral te prinderve per krijimin e femise apo gjenerates se re
 */
	public void naturalSelection() {
		/*Fillimisht behet krijimi i nje vargu i cili permban kromosome te perseritur ne proporcion me ftinessin e atij
		 *kromosomi ne krahasim me kromosomet e tjera, me shkurt aq sa e ka fitnessin krahas kromosomev tjera aq here eshte
		 *i perseritur ne varg, ashtu qe probabiliteti i seleksionimi i kromosomit te kromosomit me te mire te jete mi i madh 
		 */
		matingPool.clear();
		double maxFitness = 0;//Caktimi i fitnessit maksimal ne vargun e kromosomeve
		for (int i = 0; i < this.population.length; i++) {
			if (this.population[i].fitness > maxFitness) {
				maxFitness = this.population[i].fitness;
			}
		}

		//Permes unazes for  behet llogaritja e fitnessit te secilit dhe kromosom dhe caktimi se sa here do te perseritet ne varg
		for (int i = 0; i < this.population.length; i++) {

			double fitness = this.population[i].fitness / maxFitness;
			int n = (int) (fitness * 100);
			
			for (int j = 0; j < n; j++) {
				this.matingPool.add(this.population[i]);
			}
		}
	}

	/**
	 * Metode e cila ben krijimin e gjenerates se re me bashkimin e dy prinderve nga gjeneratat e kaluara
	 */
	public void generate() {

		//Permes unazes se re behet rikrijimi i gjenerates se re
		for (int i = 0; i < this.population.length; i++) {
			int a = r.nextInt(matingPool.size());//Numeri random i cili me vone perdoret per zgjedhjen e prinditA
			int b = r.nextInt(matingPool.size());//Numeri random i cili me vone perdoret per zgjedhjen e prinditB
			Dna partnerA = matingPool.get(a);//Zgjedhja e prinditA nga vargu me kromosomet e perseritura
			Dna partnerB = matingPool.get(b);//Zgjedhja e prinditB nga vargu me kromosomet e perseritura
			Dna child = partnerA.crossover(partnerB);//Krijimi i kromosomit femije nga dy prinderit me crossover
			child.mutate(this.mutationRate);//Mutacioni i kromosomit femij
			this.population[i] = child;//Futja ne gjeneraten e re
		}
		this.generations++;
	}

	/**
	 * Metode e cila na ndihmon ne kontrollimin e arritjes se targetit apo qeliimit dhe caktimit te frazes me te mire
	 */
	public void evaluate() {
		double record = 0.0;
		int index = 0;
		for (int i = 0; i < this.population.length; i++) {
			if (this.population[i].fitness > record) {
				index = i;
				record = this.population[i].fitness;
			}
		}
		this.best = this.population[index].getPhrase();
		if (record == this.perfectScore) {
			this.finished = true;
		}
	}

	public void setPhrase(String p) {
		this.target=p;
	}

	public void setMutation(double m) {
		this.mutationRate=m;
	}

	public void setPopullationNumber(int n) {
		this.popNum=n;
	}

	public String getBestPhrase() {
		return this.best;
	}

	public boolean isFinished() {
		return this.finished;
	}

	public void reset() {
		this.finished=false;
      this.generations=0;
	}
	public int getGenerations() {
		return this.generations;
	}

	/**
	 * Metode e cila llogarite fitnessin apo pershtatjen mesatare te nje gjenerate
	 * @return kthen varabel te tipit double e cila tregon fitnessin mesatare
	 */
	public double getAverageFitness() {
		double total = 0;
		for (int i = 0; i < this.population.length; i++) {
			total += this.population[i].fitness;
		}
		return total / this.population.length;
	}

	public ArrayList<String[]> allPhrases() {
		ArrayList<String[]> everything = new ArrayList<>();

		for (int i = 0; i < this.population.length; i++) {
			everything.add(i,new String[]{this.population[i].getPhrase(),""+new DecimalFormat("##.##").format(this.population[i].fitness)});
		}
		return everything;
	}


}
