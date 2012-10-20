package br.edu.buscg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Activity que é invocada pela classe BusCgActivity, 
 * Monta o spinner da tela com todos os ônibus
 * E invoca a activity OnibusMapa passando o id do ônibus como parametro
 * @author tgmarinho
 */

public class LocalizarOnibus extends Activity {

	private int posicaoSpinner;
	private String nomeRota;
	
	/**
	 *  Atributo que contém as linhas de ônibus para serem exibidas no Spinner
	 */

	private String[] todosOnibus = new String[] { "maria",
			"Arnaldo Estevão Figueredo", "070", "Tiradentes" };
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Exibo o Spinner na Tela
		setContentView(R.layout.exemplo_spinner);
		// Faço um binding com o arquixo layout/XML para tratar eventos de click no spinner 
		Spinner spinner = (Spinner) findViewById(R.id.comboPlanetas);
		final Button botaoEnviar = (Button) findViewById(R.id.btn_enviar);

		// ArrayAdapter exibe todos os ônibus ao clicar no spinner
		@SuppressWarnings("rawtypes")
		ArrayAdapter adaptador = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, getTodosOnibus());
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adaptador);

		// Se selecionar algum item do Spinner
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int posicao,
					long id) {

				if (posicao != 0) {
					botaoEnviar.setVisibility(View.VISIBLE);
				}
				// int pos = posicao;
				setPosicaoSpinner(posicao);
				setNomeRota(todosOnibus[getPosicaoSpinner()]);

				// Quando o botão é pressionado executa esse método
				botaoEnviar.setOnClickListener(new OnClickListener() {

					// Quando clico no botão invoco outra atividade, passando por parametros o id do ônibus
					@Override
					public void onClick(View v) {
						Intent it = new Intent(v.getContext(), OnibusMapa.class);
						Bundle params = new Bundle();
						params.putInt("id_onibus", getPosicaoSpinner());
						params.putString("arquivo", getNomeRota());
						it.putExtras(params);
						// Método que envia a intenção ao SO para executar a atividade, 
						startActivity(it);
					}
				});

			}
			// Se não selecionar nada
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	

	public int getPosicaoSpinner() {
		return posicaoSpinner;
	}

	public void setPosicaoSpinner(int posicaoSpinner) {
		this.posicaoSpinner = posicaoSpinner;
	}

	public String getNomeRota() {
		return nomeRota;
	}

	public void setNomeRota(String nomeRota) {
		this.nomeRota = nomeRota;
	}

	public String[] getTodosOnibus() {
		return todosOnibus;
	}

	public void setTodosOnibus(String[] todosOnibus) {
		this.todosOnibus = todosOnibus;
	}
}