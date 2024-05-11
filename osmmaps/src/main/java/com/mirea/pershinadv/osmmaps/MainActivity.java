package com.mirea.pershinadv.osmmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.mirea.pershinadv.osmmaps.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends AppCompatActivity {
    private MapView mapview = null;
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;
    private boolean isWork;
    private MyLocationNewOverlay locationNewOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapview = binding.mapView;

        mapview.setZoomRounding(true);
        mapview.setMultiTouchControls(true);

        IMapController mapController = mapview.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.62846, 37.64884);
        mapController.setCenter(startPoint);

        int locationPermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationPermissionStatus == PackageManager.PERMISSION_GRANTED){
            isWork = true;
        }else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
        }

        if(isWork){
            locationNewOverlay = new MyLocationNewOverlay(new
                    GpsMyLocationProvider(getApplicationContext()),mapview);
            locationNewOverlay.enableMyLocation();
            mapview.getOverlays().add(this.locationNewOverlay);

        }

        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), new
                InternalCompassOrientationProvider(getApplicationContext()), mapview);
        compassOverlay.enableCompass();
        mapview.getOverlays().add(compassOverlay);

        final Context context = this.getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapview);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapview.getOverlays().add(scaleBarOverlay);

        Marker marker_1 = new Marker(mapview);
        marker_1.setPosition(new GeoPoint(55.72945, 37.60117));
        marker_1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Парк Горького",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapview.getOverlays().add(marker_1);
        marker_1.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker_1.setTitle("Большая Якиманка");

        Marker marker_2 = new Marker(mapview);
        marker_2.setPosition(new GeoPoint(55.70899, 37.59519));
        marker_2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Любимая работа и крутой дисконт-цент",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapview.getOverlays().add(marker_2);
        marker_2.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker_2.setTitle("Орджоникидзе");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!isWork) finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapview != null) {
            mapview.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapview != null) {
            mapview.onPause();
        }
    }
}