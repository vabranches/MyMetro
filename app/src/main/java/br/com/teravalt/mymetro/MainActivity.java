package br.com.teravalt.mymetro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.teravalt.mymetro.API.APIUtils;
import br.com.teravalt.mymetro.API.EstacaoAPI;
import br.com.teravalt.mymetro.Adapter.EstacaoAdapter;
import br.com.teravalt.mymetro.Model.Estacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvEstacoes;
    private EstacaoAdapter estacaoAdapter;
    private EstacaoAPI estacaoAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvEstacoes = (RecyclerView) findViewById(R.id.rvEstacoes);

        estacaoAdapter = new EstacaoAdapter(new ArrayList<Estacao>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvEstacoes.setLayoutManager(layoutManager);
        rvEstacoes.setAdapter(estacaoAdapter);
        rvEstacoes.setHasFixedSize(true);

        carregarDados();
    }

    private void carregarDados(){

        estacaoAPI = APIUtils.getEstacaoAPI();

        estacaoAPI.getLinhas().enqueue(new Callback<List<Estacao>>() {
            @Override
            public void onResponse(Call<List<Estacao>> call, Response<List<Estacao>> response) {
                if(response.isSuccessful()){
                    estacaoAdapter.update(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Estacao>> call, Throwable t) {

            }
        });


    }

}
