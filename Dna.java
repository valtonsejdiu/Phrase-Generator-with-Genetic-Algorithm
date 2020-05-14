import java.util.Random;
/**
 * 
 * Klasa Dna eshte modeluese e cila permbane nje element apo nje anetare te popullsise si dhe metodat per manovrimin me to
 *
 */
public class Dna {

	Random r = new Random();
	char[] genes;//Array qe permban gjenet me te cilat do te behet manovrimi ne algoritem
	double fitness = 0;//Variabel e cila permban vleren e fitnessit

	/**
	 * Kontstruktori i klases Dna ben krijimi i nje kromosomi fillestar permes metodes newChar()
	 * @param num: Parameter i cili percakton numrin e elementeve apo geneve ne kromozom
	 */
	public Dna(int num) {

		this.genes = new char[num];//Inicializimi i gjeneve
		//Krijimi i secilit gjen ne arrayn e gjeneve me metoden newChar permes unazes for 
		for (int i = 0; i < num; i++) {
			genes[i] = newChar();
		}
	}

	/**
	 * Metoda newChar() ben krijimin e nje karakteri ne menyre randome ne te cilen perfshihen gjitha shkronjat
	 * e alfabetit anglez te medha dhe te vogla se dhe disa shenja te pikesimit si !?.,"':
	 * @return kthen nje variabel te tipit char.
	 */
	public char newChar() {

		int c = r.nextInt(61) + 65;//Inicializimi random permes metodes nextInt() te klases Random
		
		//Me ane te switch jane inkuadruar dhe disa shenja te pikesimit duke i zavendsuar karakteret e panevojshem
		switch (c) {
		case 91:
			c = 32;//Space
			break;
		case 92:
			c = 33;//!
			break;
		case 93:
			c = 34;//"
			break;
		case 94:
			c = 39;//'
			break;
		case 95:
			c = 44;//,
			break;
		case 123:
			c = 46;//.
			break;
		case 124:
			c = 58;//:
			break;
		case 125:
			c = 63;//?
			break;
		}

		return (char) c;

	}

	/**
	 * Metoda getPhrase() eshte metode e cila vargun e tipit char e kthen ne nje string duke i bashkuar tgjitha elementet
	 * @return kthen Stringun i cili eshte formuar me bashkimin e gjitha elementeve
	 */
	public String getPhrase() {
		String phrase = "";
		for (int i = 0; i < this.genes.length; i++) {
			phrase += this.genes[i];
		}
		return phrase;
	}

	/**
	 * Metoda calcFitness() eshte metode e vecant e GA e cila llogarite fitnessin apo pershtatshmerine e ketij grupi
	 * gjenesh apo kromosomi ne krahasim me stringun target apo tcilin deshirojme te arrijme
	 * @param target: Parameter i tipit String ne te cilin inicializohet targeti apo qellimi qe duhet te arrij kromosomi,
	 * 				  ku ne metode perdoret si objekt krahasues.
	 */
	public void calcFitness(String target) {
		double score = 0;//Variabla e cila permban numrin e elementeve te njejt te grupit te gjeneve me targetin
		
		//Permes unazes for behet krahasimi i tgjitha gjeneve te kromosomit me secilin gjen te targetit
		for (int i = 0; i < this.genes.length; i++) {

			//Nese gjenet e te njejtit pozicion jane te njejte, atehere shto numrin e ngjashmerive 
			if (this.genes[i] == target.charAt(i)) {
				score++;
			}
		}
		fitness = score / target.length();//Inicializimi i vleres se fitnesit te kromosomit ne krahasim me targetin
	}

	/**
	 * Metoda crossover() eshte metode e vecant e GA e cila ben ndarjen apo ndrrimin e gjeneve mes dy grupe gjenesh apo 
	 * kromosomi duke krijuar nje kromosom te ri femij.
	 * @param partner: Parameter ne te cilin vendoset kromosomi i dyte me te cilin behet ndarja apo ndrrimi gjeneve
	 * @return kthen nje kromosom te ri (femije) i cili permban gjenet e dy kromosomeve prind.
	 */
	public Dna crossover(Dna partner) {
		Dna child = new Dna(this.genes.length);//Deklarimi dhe inicializimi i kromozomit femij
		int midpoint = r.nextInt(this.genes.length);//Caktimi random i mesit te pikes ne kromosom

		//Permes unazes for behet mundesimi i krijimit te kromosomit te ri duke i marre gjenet
		for (int i = 0; i < this.genes.length; i++) {
			//Pjesa e djatht nga pika merret nga ky kromosom, ndersa pjesa e majt nga pika merret nga kromosomi partner
			if (i > midpoint) {
				child.genes[i] = this.genes[i];
			} else {
				child.genes[i] = partner.genes[i];
			}
		}
		return child;
	}

	/**
	 * Metoda mutate() eshte metode e vecant e GA, metode e cila ben mutacionin e gjeneve ne menyre qe shanca per te arritur
	 * targetin te rritet
	 * @param mutationRate: Parameter i tipi double qe eshte vlera e probabilitetit te mutacionit.
	 */
	public void mutate(double mutationRate) {
		for (int i = 0; i < this.genes.length; i++) {
			if (Math.random() < mutationRate) {
				this.genes[i] = newChar();
			}
		}
	}

}
