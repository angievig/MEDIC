package guiElements;

import java.awt.Color;
import java.awt.Paint;

import com.google.common.base.Function;

import edu.uci.ics.jung.visualization.picking.PickedInfo;
import graph.NodeConstraint;
import graph.NodeVariable;
import graph.Vertex;

public class VertexLabel<V> implements Function<V,String> {
	protected PickedInfo<V> pi;
	
    public VertexLabel(PickedInfo<V> pi)
    {
        this.pi = pi;
    }

	@Override
	public String apply(V v) {
		// TODO Auto-generated method stub
		
	        
        if (v instanceof NodeVariable)
        {

            return ((Vertex) v).getId();
        }
        else{
        	 if (pi.isPicked(v))
 	        {
 	            return v.toString(); 
 	        }
        	 return ((Vertex) v).getId();
        	
        }
        
          
       
	}



}
