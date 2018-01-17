package guiElements;

import java.awt.Color;
import java.awt.Paint;

import com.google.common.base.Function;
import com.variamos.reasoning.medic.model.graph.NodeVariableHLCL;
import com.variamos.reasoning.medic.model.graph.VertexHLCL;

import edu.uci.ics.jung.visualization.picking.PickedInfo;


public class VertexLabel<V> implements Function<V,String> {
	protected PickedInfo<V> pi;
	
    public VertexLabel(PickedInfo<V> pi)
    {
        this.pi = pi;
    }

	@Override
	public String apply(V v) {
		// TODO Auto-generated method stub
		
	        
        if (v instanceof NodeVariableHLCL)
        { 

            return ((VertexHLCL) v).getId()+ 
            		((((VertexHLCL) v).getSearchState() == VertexHLCL.VISITED)? "_"+((VertexHLCL) v).getOrder():"");
        }
        else{
        	 if (pi.isPicked(v))
 	        {
 	            return v.toString(); 
 	        }
        	 return ((VertexHLCL) v).getId()+ 
             		((((VertexHLCL) v).getSearchState() == VertexHLCL.VISITED)? "_"+((VertexHLCL) v).getOrder():"");
        	
        }
        
          
       
	}



}
