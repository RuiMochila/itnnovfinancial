package itnnov.financial;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsultarTransacoesActivity extends ExpandableListActivity {
	String subCategorias2[][] = {
			// Alimentação
			{ "Restauração", "-500", "Super Mercado", "-200", "Outros",
					"-123.4" },
			// Automóvel e Transportes
			{ "Combustível", "-300", "Passe Social", "-50", "Revisão", "-140",
					"Seguro", "-140", "Selo e Impostos", "-140" },
			// Casa
			{ "Luz", "-300", "Agua", "-50", "Gás", "-140", "Internet", "-140",
					"Telefone", "-140", "Televisão", "-140" },
			// Educação e Profissionais
			{},
			// Lazer e Turismo
			{},
			// Saúde e Cuidados Pessoais
			{},
			// Vesturário e Calçado
			{} };

	private String Codigo;
	private Dialog dialog_cria_categoria;
	private ConsultarTransacoesActivity me = this;
	private TextView popup_categoria_box;
	private String categoriaNova;
	private List<String> Categorias;
	private ArrayList<ArrayList<String>> subCategorias;
	private SimpleExpandableListAdapterWithEmptyGroups expListAdapter;

	public void inicializaCategorias() {
		Categorias = new ArrayList<String>();
		Categorias.add("Alimentação");
		Categorias.add("Automóvel e Transportes");
		Categorias.add("Casa");
		Categorias.add("Educação e Profissionais");
		Categorias.add("Lazer e Turismo");
		Categorias.add("Saúde e Cuidados Pessoais");
		Categorias.add("Vesturário e Calçado");
	}

	public void inicializaSubCategorias() {
		subCategorias = new ArrayList<ArrayList<String>>();
		ArrayList<String> templist = new ArrayList<String>();

		for (int i = 0; i < Categorias.size(); ++i) {
			templist = new ArrayList<String>();
			for (int j = 0; j < subCategorias2[i].length; ++j) {
				templist.add(subCategorias2[i][j]);
			}
			subCategorias.add(templist);
		}
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.consultar_transacoes);

		inicializaCategorias();
		inicializaSubCategorias();

		Codigo = getIntent().getStringExtra("Codigo Categorias");

		expListAdapter = new SimpleExpandableListAdapterWithEmptyGroups(this,
				createGroupList(), R.layout.group_row,
				new String[] { "Categorias" }, new int[] { R.id.groupname },
				createChildList(), R.layout.child_row, new String[] {
						"subCategorias", "valor" }, new int[] { R.id.childname,
						R.id.valor });
		setListAdapter(expListAdapter);
		onContentChanged();

		getExpandableListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, final long id) {
				dialog_cria_categoria = new Dialog(me);
				dialog_cria_categoria.setContentView(R.layout.popup_cria_categoria);
				dialog_cria_categoria.setCancelable(true);

				popup_categoria_box = (TextView) dialog_cria_categoria.findViewById(R.id.popup_cria_categoria_box);

				Button button_cancel = (Button) dialog_cria_categoria
								.findViewById(R.id.popup_cria_categoria_button_cancelar);
				button_cancel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog_cria_categoria.dismiss();
					}
				});

				Button button_ok = (Button) dialog_cria_categoria.findViewById(R.id.popup_cria_categoria_button_criar);
				button_ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
							int groupPosition = ExpandableListView.getPackedPositionGroup(id);
							int childPosition = ExpandableListView.getPackedPositionChild(id);

							categoriaNova = popup_categoria_box.getText().toString();

							String pai = Categorias.get(groupPosition);
							subCategorias.get(ExpandableListView.getPackedPositionGroup(id)).add(categoriaNova);
							subCategorias.get(ExpandableListView.getPackedPositionGroup(id)).add("0");

							// nao da para fazer subcategorias dos filhos era necessario uma
							// implementacao especializada
//							Log.i("You clicked here:", "clicou no filho");
							dialog_cria_categoria.dismiss();

						} else {
							// Log.i("You clicked here:", "clicou no pai");

							categoriaNova = popup_categoria_box.getText().toString();

							if (((CompoundButton) dialog_cria_categoria.findViewById(R.id.popup_cria_categoria_radiobutton_categoria))
									.isChecked()) {
								Categorias.add(categoriaNova);
								subCategorias.add(new ArrayList<String>());
							} else {
								String pai = Categorias.get(ExpandableListView.getPackedPositionGroup(id));
								subCategorias.get(ExpandableListView.getPackedPositionGroup(id)).add(categoriaNova);
								subCategorias.get(ExpandableListView.getPackedPositionGroup(id)).add("0");
							}
						}
						expListAdapter = new SimpleExpandableListAdapterWithEmptyGroups(
								me, createGroupList(), R.layout.group_row,
								new String[] { "Categorias" },
								new int[] { R.id.groupname },
								createChildList(), R.layout.child_row,
								new String[] { "subCategorias", "valor" },
								new int[] { R.id.childname, R.id.valor });
						setListAdapter(expListAdapter);
						expListAdapter.notifyDataSetChanged();
						dialog_cria_categoria.dismiss();
					}
				});
				dialog_cria_categoria.show();
				return false;
			}
		});
		
		
		 getExpandableListView().setOnGroupClickListener(new OnGroupClickListener(){
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				if (subCategorias.get(groupPosition).size() == 0){
//					Log.i("You clicked here:", "clicaste no pai sem filhos");
					if (Codigo.equals("categoria")) {
						Object o = Categorias.get(groupPosition);
						Intent returnIntent = new Intent();
						returnIntent.putExtra("Categoria Escolhida", o.toString());
						setResult(RESULT_OK, returnIntent);
						finish();
					}
				}
//				Log.i("You clicked here:", "clicaste no pai");
				return false;
			}
		 });
		 
		 
		 getExpandableListView().setOnChildClickListener(new OnChildClickListener(){
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				if (Codigo.equals("categoria, valor")) {
					if (subCategorias.get(groupPosition).get(childPosition * 2).equals("Luz")) {
						Intent myIntent = new Intent(v.getContext(),ConsultarTransacoesCategoriaActivity.class);
						startActivityForResult(myIntent, 0);
					}
				} else if (Codigo.equals("categoria")) {
					Object o = subCategorias.get(groupPosition).get(childPosition * 2);
					Intent returnIntent = new Intent();
					returnIntent.putExtra("Categoria Escolhida", o.toString());
					setResult(RESULT_OK, returnIntent);
					finish();
				}
//				 Log.i("You clicked here:", "clicaste no filho");
				return false;
			}
		 });

	}

	private List createGroupList() {
		ArrayList result = new ArrayList();
		for (int i = 0; i < Categorias.size(); ++i) {
			HashMap m = new HashMap();
			m.put("Categorias", Categorias.get(i));
			result.add(m);
		}
		return (List) result;
	}

	private List createChildList() {
		ArrayList result = new ArrayList();
		for (int i = 0; i < subCategorias.size(); ++i) {
			ArrayList secList = new ArrayList();
			for (int n = 0; n < subCategorias.get(i).size(); n += 2) {
				HashMap child = new HashMap();
				child.put("subCategorias", subCategorias.get(i).get(n));
				if (Codigo.equals("categoria, valor"))
					child.put("valor", subCategorias.get(i).get(n + 1));
				secList.add(child);
			}
			result.add(secList);
		}
		return result;
	}
}