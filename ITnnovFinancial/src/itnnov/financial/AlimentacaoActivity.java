package itnnov.financial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;

public class AlimentacaoActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alimentacao_layout);
		
		ImageView bars = (ImageView) findViewById(R.id.alimentacaoBuild);
		registerForContextMenu(bars);
	}
	
	
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextmenu, menu);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contextElement1:
			//Ir para outra activity com os gastos por natureza 
			Intent myIntent = new Intent(this,
					RestauracaoActivity.class);
			startActivityForResult(myIntent, 0);

			return true;
		case R.id.contextElement2:
			//Do nothing
			return true;
		default:
			return false;
		}
	}
}
