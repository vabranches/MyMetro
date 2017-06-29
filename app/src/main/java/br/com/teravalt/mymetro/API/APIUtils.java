package br.com.teravalt.mymetro.API;

public class APIUtils {
    public static final String BASE_URL = "http://10.3.1.4:3000";

    public static EstacaoAPI getEstacaoAPI() {
        return RetrofitClient.getClient(BASE_URL).create(EstacaoAPI.class);
    }
}
