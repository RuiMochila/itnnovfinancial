package itnnov.financial;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConsultarTransacoesCategoriaActivity extends Activity {

	private ArrayList<String> lista = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultar_transacoes_categoria);

		Button activity1 = (Button) findViewById(R.id.transacao_editar_botao1);
		activity1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				TextView descricao = (TextView) findViewById(R.id.TextView10);
				TextView dia = (TextView) findViewById(R.id.TextView12);
				TextView mes = (TextView) findViewById(R.id.TextView12);
				TextView ano = (TextView) findViewById(R.id.TextView12);
				lista = new ArrayList<String>();
				lista.add(descricao.getText().toString());
				lista.add((String) dia.getText().toString().subSequence(0, 2));
				lista.add((String) mes.getText().toString().subSequence(3, 5));
				lista.add((String) ano.getText().toString().subSequence(6, 10));
				lista.add("0");
				Intent myIntent = new Intent(v.getContext(),
						CriarTransacaoActivity.class).putStringArrayListExtra("Transacao", lista);
				startActivityForResult(myIntent, 0);

			}

		});
	}
	private TextView descricao;
	private TextView valor;
	private TextView date;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			descricao = (TextView) findViewById(R.id.TextView10);
			valor = (TextView) findViewById(R.id.TextView11);
			date = (TextView) findViewById(R.id.TextView12);
			
			lista = data.getStringArrayListExtra("Transacao");
			descricao.setText(lista.get(0));
			valor.setText("-" + lista.get(4));
			date.setText(lista.get(1) + lista.get(2) + lista.get(3));
		}
	}
}
