package itnnov.financial;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CriarTransacaoActivity extends Activity {
	CriarTransacaoActivity me = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criar_transacao);

		ArrayList<String> lista = getIntent().getStringArrayListExtra("Transacao");

		if (lista != null) {
			descricao = (EditText) findViewById(R.id.criar_transacao_box_descricao);
			descricao.setText(lista.get(0));

			date = (DatePicker) findViewById(R.id.datePicker1);
			int dia = Integer.parseInt(lista.get(1));
			int mes = Integer.parseInt(lista.get(2));
			int ano = Integer.parseInt(lista.get(3));
			date.updateDate(ano, mes - 1, dia);
			
			salvar = (Button) findViewById(R.id.criar_transicao_botao_criar);
			salvar.setText("Salvar");
			
			gastos = (RadioButton) findViewById(R.id.radio1);
			gastos.setChecked(true);
		}

		categorias = (Button) findViewById(R.id.criar_tansacao_botao_categorias);
		categorias.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myIntent = new Intent(v.getContext(),
						ConsultarTransacoesActivity.class).putExtra(
						"Codigo Categorias", "categoria");
				startActivityForResult(myIntent, 0);
			}

		});

		final Spinner periodicidades = (Spinner) findViewById(R.id.Periocidade_box);
		ArrayAdapter<CharSequence> periodicidades_adapter = ArrayAdapter
				.createFromResource(this, R.array.Periodicidades,
						android.R.layout.simple_spinner_item);
		periodicidades_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		periodicidades.setAdapter(periodicidades_adapter);

		periodicidade_text_result = (TextView) findViewById(R.id.periodicidade_text_result);
		periodicidade_text_result.setText("");

		periodicidades.setSelection(0);

		periodicidades.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 != 0) {
					dialog_periodicidade = new Dialog(me);
					dialog_periodicidade
							.setContentView(R.layout.popup_periodicidade);
					dialog_periodicidade.setCancelable(true);

					popup_periodicidade_box = (TextView) dialog_periodicidade
							.findViewById(R.id.popup_periodicidade_periodicidade_box);

					Button button_ok = (Button) dialog_periodicidade
							.findViewById(R.id.popup_periodicidade_button_ok);
					button_ok.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							periodicidade_text_result.setText("Registar de "
									+ popup_periodicidade_box.getText()
											.toString()
									+ " em "
									+ popup_periodicidade_box.getText()
											.toString()
									+ " "
									+ periodicidades.getSelectedItem()
											.toString());
							dialog_periodicidade.dismiss();
						}
					});
					Button button_cancel = (Button) dialog_periodicidade
							.findViewById(R.id.popup_periodicidade_button_cancel);
					button_cancel.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog_periodicidade.dismiss();
							periodicidades.setSelection(0);
						}
					});
					dialog_periodicidade.show();
				} else {
					periodicidade_text_result.setText("Registo unico");
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		Button salvar = (Button) findViewById(R.id.criar_transicao_botao_criar);
		salvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}

		});
		Button cancelar = (Button) findViewById(R.id.criar_transicao_botao_cancelar);
		cancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String name = data.getStringExtra("Categoria Escolhida");
			categorias.setText("Selecionou: " + name.toString());
		}
	}

	private RadioButton gastos;
	private EditText descricao;
	private Button categorias;
	private Button salvar;
	private Intent myIntent;
	private Dialog dialog_periodicidade;
	private TextView periodicidade_text_result;
	private TextView popup_periodicidade_box;
	private DatePicker date;

}
