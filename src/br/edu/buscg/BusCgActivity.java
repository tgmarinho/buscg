package br.edu.buscg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BusCgActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

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
	
	public void onClick(View v) {
		Intent it = new Intent(this, LocalizarOnibus.class);
		startActivity(it);
	}
		
}