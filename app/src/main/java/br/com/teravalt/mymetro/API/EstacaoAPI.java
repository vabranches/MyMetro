package br.com.teravalt.mymetro.API;


import java.util.List;
import java.util.Observable;

import br.com.teravalt.mymetro.Model.Estacao;
import br.com.teravalt.mymetro.Model.Station;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstacaoAPI {
    @GET("/linhas")
    Call<List<Estacao>> getLinhas();

    @GET("/linhas/{linha}/estacoes")
    Call<List<Station>> getEstacoes(@Path("linha") String linha);
}
