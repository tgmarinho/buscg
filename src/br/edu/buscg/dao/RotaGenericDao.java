package br.edu.buscg.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import br.edu.buscg.mapa.Ponto;

import com.google.android.maps.GeoPoint;

public class RotaGenericDao {

	public RotaGenericDao() {
	}

	public List<GeoPoint> buscarCoordenadaPorId(String arquivo, Context ctx)
			throws IOException {

		InputStream arquivo2;

		try {

			AssetManager assetManager = ctx.getAssets();
			arquivo2 = assetManager.open(arquivo);

	//		StringBuilder sb = new StringBuilder();
			String linha;
			List<GeoPoint> coordenadas = new ArrayList<GeoPoint>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo2, "UTF-8"));
			while ((linha = reader.readLine()) != null) {
				int posicao = 0;
				for (posicao = 0; posicao < linha.length(); posicao++) {
					char caracter = linha.charAt(posicao);
					if (caracter == ',')
						break;
				}

				String latitude = linha.substring(0, posicao);
				String longitude = linha.substring(++posicao,
						linha.length() - 1);

				System.out.println(latitude);
				System.out.println(longitude);

				GeoPoint pontoCoordenada = new Ponto(
						Double.parseDouble(longitude),
						Double.parseDouble(latitude));

				coordenadas.add(pontoCoordenada);

				// Aqui imprimimos a linha
				System.out.println(linha);
			}
			
			arquivo2.close();
			return coordenadas;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
