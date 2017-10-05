package guiElements;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cspElements.CSP;
import graph.ConstraintGraph;
import graphicConstraintNetwork.GraphicNetwork;

public class Window extends JFrame{
	private GraphicNetwork graph;
	private ControlPanel controls;
	private JPanel modePanel;
	
	
	public Window(ConstraintGraph net){
		graph= new GraphicNetwork(net);
		modePanel= graph.modePanel();
		controls = new ControlPanel(this, modePanel);
		
		this.setLayout(new BorderLayout());
		
		this.add(graph, BorderLayout.CENTER);
		this.add(controls, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.pack();
	    this.setVisible(true);
	}
	

}