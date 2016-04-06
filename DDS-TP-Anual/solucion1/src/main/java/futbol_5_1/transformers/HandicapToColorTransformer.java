package futbol_5_1.transformers;

import java.awt.Color;
import com.uqbar.commons.collections.Transformer;

public final class HandicapToColorTransformer implements Transformer<Double, Color> {
	
	@Override
	public Color transform(Double handicap) {
		if (handicap >= 8) 
			return Color.BLUE;
		
		return Color.WHITE;
	}
}