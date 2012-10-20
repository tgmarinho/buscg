package br.edu.buscg.mapa;

import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * Classe que desenha a rota de ônibus
 */


public class TracaRota extends Overlay {

	@SuppressWarnings("unused")
	private int cor;
	private Paint paint = new Paint();
	private List<GeoPoint> geoPoint;
	
	public TracaRota(List<GeoPoint> geoPoint, int cor) {
		this.geoPoint = geoPoint;
		this.cor = cor;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
			Point current = new Point();
		    Path path = new Path();
		    Projection projection = mapView.getProjection();
		    Iterator<GeoPoint> iterator = geoPoint.iterator();

		    if (iterator.hasNext()) {
		        projection.toPixels(iterator.next(), current);
		        path.moveTo((float) current.x, (float) current.y);
		    } else {
		        return;
		    }

		    while(iterator.hasNext()) {
		        projection.toPixels(iterator.next(), current);
		        path.lineTo((float) current.x, (float) current.y);
		    }

		    Paint pathPaint = new Paint();
		    pathPaint.setAntiAlias(true);
		    pathPaint.setStrokeWidth(8.0f);
		    pathPaint.setColor(Color.argb(64, 0, 0, 255));
		    pathPaint.setStyle(Style.STROKE);
		    canvas.drawPath(path, pathPaint);
		
			for (GeoPoint geo : geoPoint) {
				Point ponto = mapView.getProjection().toPixels(geo, null);
				@SuppressWarnings("unused")
				Projection pro = mapView.getProjection();
				canvas.drawLine(ponto.x, ponto.y, ponto.x, ponto.y, paint);
			}
	}	
}
