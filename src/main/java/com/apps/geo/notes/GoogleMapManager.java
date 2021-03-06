package com.apps.geo.notes;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;

import com.apps.geo.notes.db.PointInfoDBManager;
import com.apps.geo.notes.pojo.PointInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapManager {

    private class PointInfoView  {
        private Marker marker;
        private Circle circle;

        PointInfoView(PointInfo point){
            LatLng pos = new LatLng(point.getLatitude(), point.getLongitude());
            marker = map.addMarker
                    (new MarkerOptions()
                            .position(pos)
                            .title(point.getName())
                            .snippet(point.getId()+"")
                    );
            circle = map.addCircle
                    (new CircleOptions()
                            .center(pos)
                            .radius(point.getRadius())
                            .strokeColor(getCircleColor(point.isActive()))
                            .strokeWidth(3)
                    );
        }

    }

    private Context context;
    private GoogleMap map;

    private SparseArray<PointInfoView> views;

    public GoogleMapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
        views = new SparseArray<>();
        update();
    }

    public void update() {
        clearMap();

        PointInfoDBManager dbManager = new PointInfoDBManager(context);
        ArrayList<PointInfo> points = dbManager.getAllPoints();
        for (PointInfo point : points) {
            addPoint(point);
        }
    }

    public void addPoint(PointInfo point) {
        PointInfoView view = new PointInfoView(point);
        views.put(point.getId(), view);
    }

    public void removePoint(PointInfo point) {
        PointInfoView view = views.get(point.getId());
        if (view != null) {
            view.marker.remove();
            view.circle.remove();
            views.remove(point.getId());
        }
    }

    public void changePointState(PointInfo point) {
        PointInfoView view = views.get(point.getId());
        if (view != null) {
            //TODO change color, radius
        }
    }

    private int getCircleColor(boolean isActive) {
        return isActive ? Color.GREEN : Color.YELLOW;
    }

    public void centerOnPoint(PointInfo info){
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(info.getLatitude(),
                info.getLongitude())));
        map.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    public void clearMap(){
        map.clear();
        views.clear();
    }

    public void setTargetingMaker(LatLng latLng){
        clearMap();
        map.addMarker(new MarkerOptions().position(latLng));
    }

}
