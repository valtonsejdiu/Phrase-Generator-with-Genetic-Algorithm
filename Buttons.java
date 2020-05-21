import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 * Klasa buttons eshte klase kontrolluese e cila implementon interface ActionListener dhe ne te behet krijimi i buttonav
 * Fushave Tekstuale , tabeles etj. 
 * @author Val'Fly
 *
 */
public class Buttons implements ActionListener{
	DefaultTableModel model;//Tabela ne te cilen vendosen gjeneratat
	DefaultListModel l;//Lista me fjalite me te mira ne gjenerata
	JTextField p;//Fusha tekstuale ku vendoset targeti
	JTextField m;//Fusha tekstuale ku vendoset vlera e mutacionit
	JTextField n;//Fusha tekstuale ku vendoset numri i popullates
	JLabel best;//Label ne te cilen shfaqet fraza me e mire
	JLabel avg;//Label ne te cilen shfaqet mesatarja e fitnesit
	JLabel gen;//Label ne te cilet shfaqet numri i gjeneratave te krijuara
	JButton start = new JButton("Start");
	JButton reset= new JButton("Reset");
	Population pop =new Population();
	boolean go = true;

	/**
	 * Konstruktori i cili ben inicializimin e tgjitha objekteve te nevojshme per kete klase si parametrat me poshte
	 * @param phrase: Fusha tekstuale ku vendoset targeti
	 * @param mutation: Fusha tekstuale ku vendoset targeti
	 * @param popNum: Fusha tekstuale ku vendoset targeti
	 * @param b: Labele ku vendoset fraza me e mire e krijuar
	 * @param a: Labele ku vendoset mesatarja e fitnessit
	 * @param g: Labele ku vendoset numri i gjeneratave
	 * @param mo: Tabela ku vendosen gjeneratat
	 */
	public Buttons(JTextField phrase, JTextField mutation, JTextField popNum,JLabel b,JLabel a,JLabel g,DefaultTableModel mo,DefaultListModel list) {
		p=phrase;
		model=mo;
		l=list;
		m=mutation;
		n=popNum;
		best=b;
		avg=a;
		gen=g;
		start.setBounds(220, 100, 70, 25);
		reset.setBounds(220, 130, 70, 25);
		start.setBackground(new Color(	144, 238, 144));
		reset.setBackground(new Color(	255, 114, 111));
		start.addActionListener(this);
		reset.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==start) {

l.clear();
            model.setRowCount(0);

			pop.setPhrase(prepPhrase());//Vendosja e targetit ne algoritem
			pop.setMutation(prepMutation());//Vendosja e vleres se mutacionit ne algoritem
			pop.setPopullationNumber(prepPopulation());//Vendosja e nurmit te popullates ne algoritem
			pop.firstGen();

			while (go) {
            
            
				
            pop.naturalSelection();
				pop.generate();
				pop.calcFitness();
				pop.evaluate();
				best.setText("Best Phrase: "+pop.getBestPhrase());
				best.revalidate();
				l.addElement(pop.getBestPhrase()+" - "+pop.getGenerations()+" - "+new DecimalFormat("##.##").format(pop.getAverageFitness()));
				avg.setText("Average fitness: "+new DecimalFormat("##.##").format(pop.getAverageFitness()));
				gen.setText("Total Generatios: "+pop.getGenerations());


				if (pop.isFinished()) {
					go = false;
				}
				//				try {
				//					Thread.sleep(50);
				//				} catch (InterruptedException e1) {
				//					// TODO Auto-generated catch block
				//					e1.printStackTrace();
				//				}

			}
			pop.reset();
			go=true;
			for(int i = 0;i<pop.population.length;i++) {
				model.addRow(pop.allPhrases().get(i));
			}

		}if(e.getSource()==reset) {
         model.setRowCount(0);
			l.clear();
			p.setText("");
			m.setText("");
			n.setText("");
         best.setText("Best Phrase: ");
         avg.setText("Average fitness: ");
   		gen.setText("Total Generatios: ");

		}
	}
	/**
	 * Metode e cila e pergadite tekstin e marre nga fusha tekstuale e targetit per largimin e problemeve te inputit
	 * @return kthen Stringun e perpunuar
	 */
	private String prepPhrase() {
		return p.getText().trim();

	}
	/**
	 * Metode e cila e pergadite tekstin e marre nga fusha tekstuale e vleres se mutacionit per largimin e problemeve te inputit
	 * @return kthen nje variabel te tipit double qe permbane vleren e mutacionit
	 */
	private double prepMutation() {
		String mRate = m.getText().trim();
		double mutation = 0.0;
		try {
			mutation = Double.parseDouble(mRate);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Gabim ne inputin e Mutation Rate!\nRestarto aplikacionin.",null,2);

		}
		return mutation;
	}
	/**
	 * Metode e cila e pergadite tekstin e marre nga fusha tekstuale numrit te popullit per largimin e problemeve te inputit
	 * @return kthen nje variabel te tipit int qe permbane numrin e Popullates
	 */
	private int prepPopulation() {
		String popNum = n.getText().trim();
		int population = 0;
		try {
			population = Integer.parseInt(popNum);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Gabim ne inputin e Population Number!\nRestarto aplikacionin.",null, 2);

		}
		return population;
	}
}
