package ma.emsi.my_quizzer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationMaps extends AppCompatActivity implements OnMapReadyCallback {

    public static final int REQUEST_CODE = 101;
    public static Double latitude, longitude;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.location_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient (this);
        fetchLastLocation ();

        findViewById (R.id.iv_back).setOnClickListener (v -> {
            startActivity (new Intent (v.getContext (), Score.class));
            finish ();
        });

    }

    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation ();
        task.addOnSuccessListener (new OnSuccessListener<Location> () {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    latitude = location.getLatitude ();
                    longitude = location.getLongitude ();

                    Toast.makeText (LocationMaps.this, "Latitude : " + latitude, Toast.LENGTH_SHORT).show ();
                    Toast.makeText (LocationMaps.this, "Longitude : " + longitude, Toast.LENGTH_SHORT).show ();

                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager ().findFragmentById (R.id.GoogleMaps_fragement);
                    supportMapFragment.getMapAsync (LocationMaps.this);

                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng (currentLocation.getLatitude (), currentLocation.getLongitude ());
        MarkerOptions markerOptions = new MarkerOptions ().position (latLng)
                .title ("Current Location");
        googleMap.animateCamera (CameraUpdateFactory.newLatLng (latLng));
        googleMap.animateCamera (CameraUpdateFactory.newLatLngZoom (latLng, 5));
        googleMap.addMarker (markerOptions);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    fetchLastLocation ();
                break;
        }
    }
}