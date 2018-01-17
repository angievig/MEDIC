package graficalHlcl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.common.base.Supplier;
import com.variamos.reasoning.medic.model.graph.ConstraintGraphHLCL;
import com.variamos.reasoning.medic.model.graph.NodeConstraintHLCL;
import com.variamos.reasoning.medic.model.graph.NodeVariableHLCL;
import com.variamos.reasoning.medic.model.graph.VertexHLCL;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.util.SelfLoopEdgePredicate;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
//import edu.uci.ics.jung.samples.PluggableRendererDemo.VoltageTips;
//import edu.uci.ics.jung.samples.PluggableRendererDemo.SeedFillColor;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import guiElements.VertexLabel;
import guiElements.VertexLayout;


public class GuiHlcl extends JPanel {
	private ConstraintGraphHLCL net;
	private Graph<VertexHLCL, String> g;
//	private JFrame frame;
//	private JPanel drawPanel;
	private VisualizationViewer<VertexHLCL, String> vv;
	private VertexLayout<VertexHLCL> vertexLayout;
	private VertexLabel<VertexHLCL> vertexLabel;
	private DefaultModalGraphMouse<VertexHLCL,String> gm;
	
	public GuiHlcl(ConstraintGraphHLCL n){
		net= n;
		g= createGraph();
//		frame= new JFrame();
		//drawPanel= new JPanel();
		vv = new VisualizationViewer<VertexHLCL, String>(new FRLayout<VertexHLCL, String>(g));
		vv.setBackground(Color.white);
		GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        PickedState<VertexHLCL> picked_state = vv.getPickedVertexState();
        System.out.println(picked_state);

       
        
        // create decorators
        vertexLayout = new VertexLayout<VertexHLCL>(picked_state);
        vertexLabel = new VertexLabel<VertexHLCL>(picked_state);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexLayout);
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        
        //funciones basicas de zoom y mouse
        gm = new DefaultModalGraphMouse<VertexHLCL,String>();
        vv.setGraphMouse(gm);
       // vv.setVertexToolTipTransformer(new VoltageTips<Number>());

		//this.add(vv);
		this.add(scrollPane);
		
		this.setBorder(BorderFactory.createTitledBorder("Constraint Network"));
//		frame.getContentPane().add(vv);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.pack();
//	    frame.setVisible(true);
	}
	public Graph createGraph(){
		 Graph <VertexHLCL, String> ng= new UndirectedSparseGraph<VertexHLCL, String>();
		 
		 //painting variables
		for(NodeVariableHLCL v: net.getVariables().values()){
			ng.addVertex(v);
			//int i=0;
			for(NodeConstraintHLCL c: v.getUnary()){
//				String id= "OC"+v.getId()+i;
				String id= c.getId();

				System.out.println(id);
				//NodeConstraintHLCL  option= new NodeConstraintHLCL(id, c);
				ng.addVertex(c);
				ng.addEdge(id, v, c);
				//i++;
			}
			
		}
		//painting constraints
		for(VertexHLCL v: net.getConstraints().values()){
			ng.addVertex(v);
			for(VertexHLCL end: v.getNeighbors()){
				String id= "Edge_"+v.getId()+"_"+end.getId();
				ng.addEdge(id, v, end);
			
			}
			
		}
		
		return ng;
	}
	
	public JPanel modePanel(){
		 JComboBox<?> modeBox = gm.getModeComboBox();
	        modeBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	        JPanel modePanel = new JPanel(new BorderLayout()) {
	            public Dimension getMaximumSize() {
	                return getPreferredSize();
	            }
	        };
	        modePanel.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
	        modePanel.add(modeBox);
	        JPanel comboGrid = new JPanel(new GridLayout(0,1));
	        comboGrid.add(modePanel);
	       return modePanel;
	        
		
	}
	

	    
	    /**
	     * Generates a graph: in this case, reads it from the file
	     * "samples/datasetsgraph/simple.net"
	     * @return A sample undirected graph
	     * @throws IOException if there is an error in reading the file
	     */
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public static Graph getGraph() throws IOException 
	    {
	        PajekNetReader pnr = new PajekNetReader(new Supplier(){
				public Object get() {
					return new Object();
				}});
	        Graph g = new UndirectedSparseGraph();
	        
	        pnr.load("src/dataset/simple.net", g);
	        return g;
	    }
}
