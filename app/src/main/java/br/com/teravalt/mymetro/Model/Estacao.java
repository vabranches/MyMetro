package br.com.teravalt.mymetro.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Estacao implements Parcelable{

    private String cor;
    private String numero;
    private String urlImagem;

    protected Estacao(Parcel in) {
        cor = in.readString();
        numero = in.readString();
        urlImagem = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cor);
        dest.writeString(numero);
        dest.writeString(urlImagem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Estacao> CREATOR = new Creator<Estacao>() {
        @Override
        public Estacao createFromParcel(Parcel in) {
            return new Estacao(in);
        }

        @Override
        public Estacao[] newArray(int size) {
            return new Estacao[size];
        }
    };

    public String getCor() { return cor;  }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
