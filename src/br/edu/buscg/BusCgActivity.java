package br.edu.buscg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 *  Activity invocada pela classe SplashBusCG, mostra na tela o menu da aplicação 
 *  e trata eventos de click dos botões, chamado outras atividades
 *
 *  @author tgmarinho
 */

public class BusCgActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Exibo o menu na tela do smartphone
		setContentView(R.layout.menu);
		// Chamo os métodos que tratam os eventos com os quatro botões do menu
		servicos();

	}
	
	private void servicos() {
		
		ImageButton btnOndeComprar, btnComprarOnline, btnConsultarSaldo, btnLocalizarBus; 

		btnComprarOnline = (ImageButton) findViewById(R.id.comprar_passe);
		btnComprarOnline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent;
				String endereco = "http://www.assetur.com.br/Cartao/faces/VendaComum.xhtml";
				Uri site = Uri.parse(endereco);
				intent = new Intent(android.content.Intent.ACTION_VIEW, site);
				//envia a mensagem ao sistema operacional
				startActivity(intent);
			}
		});

		btnOndeComprar = (ImageButton) findViewById(R.id.btn_onde_compro_passe);
		btnOndeComprar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent;
				String endereco = "http://www.assetur.com.br/vendaseRecargas.php?Tab=3";
				Uri site = Uri.parse(endereco);
				intent = new Intent(android.content.Intent.ACTION_VIEW, site);
				//envia a mensagem ao sistema operacional
				startActivity(intent);
			}
		});


		btnConsultarSaldo = (ImageButton) findViewById(R.id.btn_saldo);
		btnConsultarSaldo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent;
				String endereco = "http://www.assetur.com.br/srcnet/ServletSrcNet?op=recargacartaocomumtendencia";
				Uri site = Uri.parse(endereco);
				intent = new Intent(android.content.Intent.ACTION_VIEW, site);
				//envia a mensagem ao sistema operacional
				startActivity(intent);
			}
		});
		
		btnLocalizarBus = (ImageButton) findViewById(R.id.meu_buscg);
		btnLocalizarBus.setOnClickListener(this);
	}	
	
	/**
	 * Método principal da Activity, chamo outra atividade a qual apresenta um combobox que contém as linha de ônibus
	 */
	public void onClick(View v) {
		Intent it = new Intent(this, LocalizarOnibus.class);
		startActivity(it);
	}
		
}