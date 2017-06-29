package br.com.teravalt.mymetro;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.messages.SubscribeCallback;

import java.util.List;

import br.com.teravalt.mymetro.API.APIUtils;
import br.com.teravalt.mymetro.API.EstacaoAPI;
import br.com.teravalt.mymetro.Model.Estacao;
import br.com.teravalt.mymetro.Model.Station;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private Estacao linha;
    private EstacaoAPI mService;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent() != null) {
            this.linha = getIntent().getParcelableExtra("LINHA");
        }
    }

    public void loadLinhas() {
        mService = APIUtils.getEstacaoAPI();

        mService.getEstacoes(linha.getCor()).enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                colocarNoMapa(response.body());
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Deu ruim",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    private void colocarNoMapa(List<Station> estacoes) {
        for (Station estacao : estacoes) {

            LatLng posicao = new LatLng(
                    estacao.getLatitude(),
                    estacao.getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(posicao)
                    .title(estacao.getNome()));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 12));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadLinhas();

    }

    private void handleNewLocation(Location location) {
        LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(minhaLocalizacao).title("Estou aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 16));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                handleNewLocation(location);
            };
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        handleNewLocation(location);
    }
}
