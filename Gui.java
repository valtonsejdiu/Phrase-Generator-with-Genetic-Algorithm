import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame {


	public Gui() {
		
		JPanel p = new JPanel();

		Container cp = getContentPane();

		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		JScrollPane js = new JScrollPane(table);

		JTextField phrase = new JTextField("Lorem ispum");
		JLabel ph = new JLabel("Enter the phrase:");

		JTextField mutation = new JTextField("0.01");
		JLabel m = new JLabel("Mutation Rate:");

		JTextField numPop = new JTextField("200");
		JLabel n = new JLabel("Popullation N.:");

		JLabel best = new JLabel("Best Phrase:");
		JLabel totG = new JLabel("Total Generatios:");
		JLabel avgF = new JLabel("Average fitness:");

		Buttons b = new Buttons(phrase,mutation,numPop,best,avgF,totG,model);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 500);
		setVisible(true);
		setResizable(false);
		setTitle("Phrase generator with Genetic Algorithm");
		setLocation(200, 100);

		p.setBackground(Color.white);

		js.setBounds(400,50,500,350);

		ph.setBounds(50, 70, 100, 30);
		phrase.setBounds(50, 100, 150, 25);
		m.setBounds(50, 130, 100, 30);
		mutation.setBounds(140, 135, 30, 25);
		n.setBounds(50, 166, 100, 30);
		numPop.setBounds(140, 170, 30, 25);

		best.setBounds(50, 220, 200, 25);
		totG.setBounds(50, 250, 150, 25);
		avgF.setBounds(50, 280, 200, 25);	

		model.addColumn("All phrases of last generation");
		model.addColumn("Fitness");

		p.setLayout(null);
		p.add(js);
		p.add(phrase);
		p.add(ph);
		p.add(mutation);
		p.add(m);
		p.add(numPop);
		p.add(n);
		p.add(b.start);
		p.add(b.reset);
		p.add(best);
		p.add(avgF);
		p.add(totG);
		cp.add(p);

	}
	public void paintComponent(Graphics g) {
		repaint();
	}


	public static void main(String[] args) {
		new Gui();
	}

}
