package co.edu.eafit.parkingmobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.eafit.parkingmobile.models.ModelManager;
import co.edu.eafit.parkingmobile.models.Parking;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{


    private MapView mapView;
    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map,container,false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return mapView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        LatLng eafit = new LatLng(6.200696D,-75.578433D);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eafit));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));

        for (Parking parking: ModelManager.getInstance().parkings)
        {
            LatLng geoposition = new LatLng(parking.getLatitude(),parking.getLongitude());
            mMap.addMarker(new MarkerOptions().title(parking.getName()).snippet(parking.getDetails()).position(geoposition));

        }


    }

    public void onResume()
    {

        mapView.onResume();
        super.onResume();

    }
}
