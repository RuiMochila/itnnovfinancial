package itnnov.financial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RestauracaoActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restauracao_layout);
		
		ImageView bars = (ImageView) findViewById(R.id.restauracaoBuild);
		registerForContextMenu(bars);
	}
}
