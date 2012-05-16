package itnnov.financial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ITnnovFinancialActivity extends Activity {
	/** Called when the activity is first created. */

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Carregamento das animações
		final Animation viewAppearAnimation = AnimationUtils.loadAnimation(
				this, R.anim.view_appear);
		final Animation viewDisappearAnimation = AnimationUtils.loadAnimation(
				this, R.anim.view_disappear);

		// Lógica dos balanços
		final ImageView balancoCurrente = (ImageView) findViewById(R.id.balancoCurrente);
		final ImageView balancoPrevisto = (ImageView) findViewById(R.id.balancoPrevisto);

		balancoCurrente.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				balancoCurrente.setClickable(false);

				TextView descricao = (TextView) findViewById(R.id.textBalancoCurrente);
				descricao.setVisibility(View.INVISIBLE);

				TextView descricao2 = (TextView) findViewById(R.id.textBalancoPrevisto);
				descricao2.setVisibility(View.VISIBLE);

				balancoCurrente.startAnimation(viewDisappearAnimation);
				balancoCurrente.postDelayed(new Runnable() {
					public void run() {
						balancoCurrente.setVisibility(View.INVISIBLE);
					}
				}, 500);

				balancoPrevisto.setVisibility(View.VISIBLE);
				balancoPrevisto.startAnimation(viewAppearAnimation);
				balancoPrevisto.setClickable(true);

			}

		});

		balancoPrevisto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				balancoPrevisto.setClickable(false);

				TextView descricao = (TextView) findViewById(R.id.textBalancoPrevisto);
				descricao.setVisibility(View.INVISIBLE);

				TextView descricao2 = (TextView) findViewById(R.id.textBalancoCurrente);
				descricao2.setVisibility(View.VISIBLE);

				balancoPrevisto.startAnimation(viewDisappearAnimation);
				balancoPrevisto.postDelayed(new Runnable() {
					public void run() {
						balancoPrevisto.setVisibility(View.INVISIBLE);
					}
				}, 500);

				balancoCurrente.setVisibility(View.VISIBLE);
				balancoCurrente.startAnimation(viewAppearAnimation);
				balancoCurrente.setClickable(true);

			}

		});

		// Lógica da PieChart e das Barras
		final ImageView pieChart = (ImageView) findViewById(R.id.pieChart2);
		final ImageView bars1 = (ImageView) findViewById(R.id.bars1);

		// bars1.setVisibility(View.GONE);

		pieChart.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (bars1.isShown()) {
					bars1.startAnimation(viewDisappearAnimation);
					bars1.postDelayed(new Runnable() {
						public void run() {
							bars1.setVisibility(View.GONE);
						}
					}, 500);

				} else {
					bars1.setVisibility(View.VISIBLE);
					bars1.startAnimation(viewAppearAnimation);

				}

			}

		});

		// Lógica das barras
		registerForContextMenu(bars1);
		// bars1.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		//
		// }
		// });

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item2:
			//Gerir Transacções
			Intent myIntent = new Intent(this, CriarTransacaoActivity.class).putStringArrayListExtra("Transacao", null);
            startActivityForResult(myIntent, 0); 
			return true;
		case R.id.item7:
			//Gerir Categorias
			Intent intent_consultar_categorias = new Intent(this, ConsultarTransacoesActivity.class).putExtra("Codigo Categorias","categoria, valor"); 
			startActivityForResult(intent_consultar_categorias, 0);
			
            return true;
		default:
			return false;
		}
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
					AlimentacaoActivity.class);
			startActivityForResult(myIntent, 0);

			return true;
		case R.id.contextElement2:
			//do nothing
			return true;
		default:
			return false;
		}
	}

}