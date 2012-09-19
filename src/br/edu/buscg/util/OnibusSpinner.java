package br.edu.buscg.util;

import java.util.ArrayList;
import java.util.List;

public enum OnibusSpinner {

			MARIA_AP_PEDROSSIAN("Maria Ap Pedrossian - Oiti"),
			ARNALDO_ESTEVAO_FIGUEREDO("Arnaldo Estevão Figueredo"), 
			ZEROSENTENTA("070"), 
			TIRADENTES("Tiradentes"); 
			
	private String onibus;

	OnibusSpinner(String onibus) {
		this.onibus = onibus;
	}

	public String getNomeOnibus() {
		return onibus;
	}
	
	public List<String> getTodosOnibus() {
		List<String> bus = new ArrayList<String>();
		for (OnibusSpinner onibusSpinner : OnibusSpinner.values()) {
			bus.add(onibusSpinner.getNomeOnibus());
		}
		return bus;
	}
			
			
}
