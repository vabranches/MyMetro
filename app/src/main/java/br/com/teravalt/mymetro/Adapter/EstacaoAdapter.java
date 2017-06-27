package br.com.teravalt.mymetro.Adapter;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.teravalt.mymetro.API.APIUtils;
import br.com.teravalt.mymetro.Model.Estacao;
import br.com.teravalt.mymetro.R;

public class EstacaoAdapter extends RecyclerView.Adapter<EstacaoAdapter.AndroidViewHolder> {

    private List<Estacao> linhas;
    private OnItemClickListener listener;

    public EstacaoAdapter(List<Estacao> a, OnItemClickListener l){
        this.linhas = a;
        this.listener = l;
    }

    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.activity_estacoes, parent,false);
        return new AndroidViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(AndroidViewHolder holder, final int position) {
        holder.tvNumero.setText(linhas.get(position).getNumero());
        holder.tvCor.setText(linhas.get(position).getCor());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(linhas.get(position));
            }
        });

        Picasso.with(holder.itemView.getContext())
                .load(APIUtils.BASE_URL + linhas.get(position).getUrlImagem())
                .placeholder(R.drawable.carregando)
                .error(R.drawable.carregando)
                .into(holder.ivImagem);
    }

    @Override
    public int getItemCount() {
        return linhas.size();
    }

    public void update(List<Estacao> a){
        this.linhas = a;
        notifyDataSetChanged();
    }

    public class AndroidViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImagem;
        public TextView tvNumero;
        public TextView tvCor;

        public AndroidViewHolder(View itemView){
            super(itemView);

            ivImagem = (ImageView) itemView.findViewById(R.id.iv_imagem);
            tvNumero = (TextView) itemView.findViewById(R.id.tv_numero);
            tvCor = (TextView) itemView.findViewById(R.id.tv_cor);
        }

    }
}
