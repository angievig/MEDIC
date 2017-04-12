package graphicConstraintNetwork;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener{
	
	private JPanel layout;
	private JPanel algorithm;
	private Window mainFrame;
	
	public ControlPanel(Window main, JPanel mode){
		mainFrame = main; 
		makeLayoutControls();
		makeAlgorithmControls();
		this.setLayout(new GridLayout(1,3));
		this.add(layout);
		this.add(algorithm);
		this.add(mode);		
	}
	
	public void makeLayoutControls(){
		layout= new JPanel();
		JButton in= new JButton("-");
		JButton out= new JButton("+");
		layout.setLayout(new GridLayout(1,2));
		layout.setBorder(BorderFactory.createTitledBorder("Zoom controls"));
		layout.add(in);
		layout.add(out);
		
	}
	
	public void makeAlgorithmControls(){
		algorithm= new JPanel();
		JButton path= new JButton("Path");
		JButton start= new JButton("Execution");
		algorithm.setLayout(new GridLayout(1,2));
		algorithm.setBorder(BorderFactory.createTitledBorder("Algorithm controls"));
		algorithm.add(start);
		algorithm.add(path);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
