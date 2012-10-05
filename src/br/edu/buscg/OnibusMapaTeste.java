package br.edu.buscg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import br.edu.buscg.mapa.BolaOverlay;
import br.edu.buscg.mapa.Ponto;
import br.edu.buscg.webservice.ClienteRest;
import br.edu.pojos.Onibus;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class OnibusMapaTeste extends MapActivity implements Runnable {

	private static final int TEMPO = 2000;
	private boolean fimHandler = false; 
	Bundle params;
	Intent it;
	private BolaOverlay localizacao_bus;
	
	// Componente MapView declarado
	private MapView mapview;
	private MapController mapcontroler;
	// Handler é uma classe especial do Android que trabalha uma alternativa no
	// lugar Threads
	Handler handler = new Handler();

	// BolaOverlay é uma classe que cria um ponto verde no mapa, indicando a
	// coordenada atual

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.mapa);
		mapview =  (MapView) findViewById(R.id.mapa);
		mapview.setBuiltInZoomControls(true);
		mapview.setSatellite(true);
		mapview.setTraffic(false);
		mapview.setStreetView(false);
		mapcontroler = mapview.getController();

		it = getIntent();
		params = it.getExtras();

		System.err.println("id do onibus: " + params.getInt("id_onibus"));

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		Onibus onibus = getOnibus();
		GeoPoint onibus_ponto = getPosicaoOnibus(onibus);
		
		if (onibus != null) {

			mapview.getController().setZoom(13);
			localizacao_bus = new BolaOverlay(onibus_ponto, Color.GREEN);
			// Add a posição atual no mapa
			if(onibus_ponto != null) {
				
				mapcontroler.setCenter(onibus_ponto);
				mapcontroler.animateTo(onibus_ponto);
			}
			mapview.getOverlays().add(localizacao_bus);
			
			// Envia a mensagem depois de 2 segundos para atualizar a coordenada da bolinha
			handler.postDelayed(this,TEMPO);
		}

	}
	
	
	public Onibus getOnibus() {
		
		ClienteRest cliREST = new ClienteRest();
		Onibus onibus = null;
		try {
			onibus = cliREST.get(params.getInt("id_onibus"));
			return onibus;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public GeoPoint getPosicaoOnibus(Onibus onibus){

		try {
			GeoPoint novoPonto = new Ponto(onibus.getLatitude(), onibus.getLongitude());
			return novoPonto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		fimHandler = true;
	}
	
	// Implementa o método run() da interface Runnable
	@Override
	public void run() {
		// Recupera o próximo ponto para mover a bolinha
		
		Onibus onibus = getOnibus();
		GeoPoint p = getPosicaoOnibus(onibus);
		// Atualiza a coordenada da bolinha
		localizacao_bus.setGeoPoint(p);
		//centraliza o map no ponto
		// Invalida o para para desenhar tudo novamente (refresh)
//		mapview.invalidate(); deixando comentado para poder ver os pontinhos sendo uimpressos na tela
		// Envia a mensagem depois de 2 segundos para atualizar a coordenada da bolinha
		if(!fimHandler){
			handler.postDelayed(this,TEMPO);
		} 
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Remove o listener para n�o ficar rodando depois de sair
		
	}
	
	
	// Importante: Como estamos exibindo uma rota no mapa, o Google obriga a returnar TRUE
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

}
