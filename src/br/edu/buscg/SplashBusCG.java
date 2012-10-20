package br.edu.buscg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Classe principal, exibe um splash e inicia o fluxo da aplicação 
 * @author tgmarinho
 */



public class SplashBusCG extends Activity implements Runnable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Exibo a tela com a logo marca
		setContentView(R.layout.splashbuscg);
		// Após 3 segundos inicio o método run que chama a próxima Atividade
		Handler handler = new Handler();
		handler.postDelayed(this, 3000);
	}

	public void run(){
		startActivity(new Intent(this, BusCgActivity.class));
		finish();
	}

}
