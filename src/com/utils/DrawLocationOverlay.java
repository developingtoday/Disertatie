package com.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 6/6/12
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrawLocationOverlay extends Overlay {
    private GeoPoint gp1;
    private GeoPoint gp2;

    public DrawLocationOverlay(GeoPoint start, GeoPoint end)
    {
        gp1=start;
        gp2=end;
    }
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
                        long when) {
        // TODO Auto-generated method stub
        Projection projection = mapView.getProjection();
        if (!shadow) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Point point = new Point();
            projection.toPixels(gp1, point);
            paint.setColor(Color.BLUE);
            Point point2 = new Point();
            projection.toPixels(gp2, point2);
            paint.setStrokeWidth(2);
            canvas.drawLine((float) point.x, (float) point.y, (float) point2.x,(float) point2.y, paint);
        }
        return super.draw(canvas, mapView, shadow, when);
    }
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        // TODO Auto-generated method stub

        super.draw(canvas, mapView, shadow);
    }


}