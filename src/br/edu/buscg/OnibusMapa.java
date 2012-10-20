package br.edu.buscg;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import br.edu.buscg.dao.RotaGenericDao;
import br.edu.buscg.mapa.BolaOverlay;
import br.edu.buscg.mapa.Ponto;
import br.edu.buscg.mapa.TracaRota;
import br.edu.buscg.pojos.Onibus;
import br.edu.buscg.webservice.ClienteRest;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

/**
 * Classe que envia para webservice o id do ônibus e recebe um objeto ônibus que contém todas as informações
 * Essa classe renderiza no mapa o ponto geográfico do ônibus.
 * 
 * @author tgmarinho
 */


public class OnibusMapa extends MapActivity implements Runnable {

	private static final int TEMPO = 2000;
	private boolean fimHandler = false; 
	Bundle params;
	Intent it;
	// BolaOverlay é uma classe que cria um ponto verde no mapa, indicando a coordenada atual
	private BolaOverlay bolaOverlay;
	// Componente MapView e MapController declarados
	private MapView mapview;
	private MapController mapcontroler;
	// Handler é uma classe especial do Android que trabalha uma alternativa no lugar Threads
	Handler handler = new Handler();
	

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

		// recebo o id do ônibus passado como parametro
		it = getIntent();
		params = it.getExtras();

		System.err.println("id do onibus: " + params.getInt("id_onibus"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		RotaGenericDao rgd = new RotaGenericDao();
		List<GeoPoint> coordenadas;
		try {
			coordenadas = rgd.buscarCoordenadaPorId(params.getString("arquivo"), this);
			TracaRota rotaDesenhada = new TracaRota(coordenadas, Color.BLUE);
			mapview.getOverlays().add(rotaDesenhada);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Onibus onibus = getOnibus();
		GeoPoint coordenadaOnibus = getPosicaoOnibus(onibus);
		
		if (onibus != null) {
			
			mapview.getController().setZoom(13);
			bolaOverlay = new BolaOverlay(coordenadaOnibus, Color.GREEN);
			
			// Add a posição atual no mapa
			if(coordenadaOnibus != null) {
				mapcontroler.setCenter(coordenadaOnibus);
				mapcontroler.animateTo(coordenadaOnibus);
			}
			mapview.getOverlays().add(bolaOverlay);
			
			// Envia a mensagem depois de 2 segundos para atualizar a coordenada da bolinha
			handler.postDelayed(this,TEMPO);
		}
	}
	
	// Método que busca no servidor de banco de dados via webservice um objeto ônibus
	public Onibus getOnibus() {
		// Objeto que trabalha webservice via http
		ClienteRest clienteRest = new ClienteRest();
		Onibus onibus = null;
		try {
			onibus = clienteRest.get(params.getInt("id_onibus"));
			return onibus;
		} catch (Exception e) {
			e.printStackTrace();
			mensagemErro();
			return null;
		}
	}
	
	// Mensagem de erro genérica
	private void mensagemErro() {
		Toast.makeText(this, "Servidor indisponível, tente mais tarde", Toast.LENGTH_SHORT).show();
	}

	// Método que transforma a latitude e longitude do objeto Onibus em ponto de coordenada geográfica
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
		bolaOverlay.setGeoPoint(p);
		// Invalida o para para desenhar tudo novamente (refresh)
		mapview.invalidate(); //deixando comentado para poder ver os pontinhos sendo uimpressos na tela
		// Envia a mensagem depois de 2 segundos para atualizar a coordenada da bolinha
		if(!fimHandler){
			handler.postDelayed(this,TEMPO);
		} 
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Remove o listener para nao ficar rodando depois de sair
	}
	
	
	// Importante: Como estamos exibindo uma rota no mapa, o Google obriga a returnar TRUE
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

}
