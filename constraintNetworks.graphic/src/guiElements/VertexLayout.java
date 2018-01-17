package guiElements;

import java.awt.Color;
import java.awt.Paint;

import com.google.common.base.Function;
import com.variamos.reasoning.medic.model.graph.NodeConstraintHLCL;
import com.variamos.reasoning.medic.model.graph.VertexHLCL;

import edu.uci.ics.jung.visualization.picking.PickedInfo;

public class VertexLayout<V> implements Function<V,Paint> {

	protected final static float CONSTRAINT_COLOR = 0.8f;
	protected final static float VARIABLE_COLOR = 0.8f; 
	protected PickedInfo<V> pi;

	public VertexLayout(PickedInfo<V> pi)
	{
		this.pi = pi;
	}

	@Override
	public Paint apply(V v) {
		//float alpha = transparency.get(v).floatValue();
		if (pi.isPicked(v))
		{
			return Color.ORANGE; 
		}
		else
		{
			if (v instanceof NodeConstraintHLCL)
			{ 
				switch(((VertexHLCL) v).getSearchState()){
			
			case VertexHLCL.VISITED:
				return Color.RED;
			case VertexHLCL.INSTACK:
				return Color.BLUE;

			default:
				return Color.CYAN;

			}


			}
			else{
				 switch(((VertexHLCL) v).getSearchState()){
				case VertexHLCL.VISITED:
					return Color.RED;
				case VertexHLCL.INSTACK:
					return Color.BLUE;

				default:
					return Color.LIGHT_GRAY;

				}
				}

			}

		}

	}
