package br.com.teravalt.mymetro.API;


import java.util.List;

import br.com.teravalt.mymetro.Model.Estacao;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EstacaoAPI {
    @GET("/linhas")
    Call<List<Estacao>> getLinhas();
}
