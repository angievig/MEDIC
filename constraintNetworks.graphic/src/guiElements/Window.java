package guiElements;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.variamos.reasoning.medic.model.graph.ConstraintGraphHLCL;

import graficalHlcl.GuiHlcl;


public class Window extends JFrame{
	private GuiHlcl graph;
	private ControlPanel controls;
	private JPanel modePanel;
	
	
	public Window(ConstraintGraphHLCL net, String title){
		graph= new GuiHlcl(net);
		modePanel= graph.modePanel();
		controls = new ControlPanel(this, modePanel);
		//this.setTitle(title);
		this.setLayout(new BorderLayout());
		
		this.add(graph, BorderLayout.CENTER);
		this.add(controls, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.pack();
	    this.setVisible(true);
	}
	

}
