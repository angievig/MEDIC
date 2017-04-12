package graphicConstraintNetwork;

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

import constraintNetwork.Network;
import constraintNetwork.NodeConstraint;
import constraintNetwork.Vertex;
import cspElements.Constraint;
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


public class GraphicNetwork extends JPanel {
	private Network net;
	private Graph<Vertex, String> g;
//	private JFrame frame;
//	private JPanel drawPanel;
	private VisualizationViewer<Vertex, String> vv;
	private VertexLayout<Vertex> vertexLayout;
	private VertexLabel<Vertex> vertexLabel;
	private DefaultModalGraphMouse<Vertex,String> gm;
	
	public GraphicNetwork(Network n){
		net= n;
		g= createGraph();
//		frame= new JFrame();
		//drawPanel= new JPanel();
		vv = new VisualizationViewer<Vertex, String>(new FRLayout<Vertex, String>(g));
		vv.setBackground(Color.white);
		GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        PickedState<Vertex> picked_state = vv.getPickedVertexState();
        System.out.println(picked_state);

       
        
        // create decorators
        vertexLayout = new VertexLayout<Vertex>(picked_state);
        vertexLabel = new VertexLabel<Vertex>(picked_state);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexLayout);
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        
        //funciones basicas de zoom y mouse
        gm = new DefaultModalGraphMouse<Vertex,String>();
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
		 Graph <Vertex, String> ng= new UndirectedSparseGraph<Vertex, String>();
		 
		 //painting variables
		for(Vertex v: net.getVariables().values()){
			ng.addVertex(v);
			//int i=0;
			for(Constraint c: v.getConstraints()){
//				String id= "OC"+v.getId()+i;
				String id= c.getId();

				System.out.println(id);
				NodeConstraint  option= new NodeConstraint(id, c);
				ng.addVertex(option);
				ng.addEdge(id, v, option);
				//i++;
			}
			
		}
		//painting constraints
		for(Vertex v: net.getConstraints().values()){
			ng.addVertex(v);
			for(Vertex end: v.getNeighbors()){
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
