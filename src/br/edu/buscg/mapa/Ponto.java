package br.edu.buscg.mapa;

import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * Converte latitude e longitude em coordenada
 * @author Ricardo Lecheta - Livro Android
 */

public class Ponto extends GeoPoint {

	// Valores em graus * 1E6 (microdegrees)
	public Ponto(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	}
	
	// Converte para graus * 1E6
	public Ponto(double latitude, double longitude){
		this((int)(latitude*1E6), (int)(longitude*1E6));
	}
	
	// Cria baseado no objeto Location diretamento recebido do GPS
	public Ponto(Location location) {
		this(location.getLatitude(), location.getLongitude());
		
	}
	
}
