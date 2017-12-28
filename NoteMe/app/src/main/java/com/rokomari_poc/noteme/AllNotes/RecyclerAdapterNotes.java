package com.rokomari_poc.noteme.AllNotes;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rokomari_poc.noteme.R;

import java.util.List;

public class RecyclerAdapterNotes extends RecyclerView.Adapter<RecyclerAdapterNotes.MyViewHolder>{
    private List<ModelNotes> modelNotes;

    public RecyclerAdapterNotes(List<ModelNotes> modelNotes) {
        this.modelNotes = modelNotes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_all_notes,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvTitle.setText(modelNotes.get(position).getTitle());
        holder.tvDate.setText(modelNotes.get(position).getTimestamp());
        holder.tvStatus.setText(modelNotes.get(position).getStatus());
        holder.tvSerial.setText(""+(position+1));

    }

    @Override
    public int getItemCount() {
        return modelNotes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvTitle,tvDate,tvStatus,tvSerial;
        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle=(TextView)itemView.findViewById(R.id.textview_title);
            tvDate=(TextView)itemView.findViewById(R.id.textview_date);
            tvStatus=(TextView)itemView.findViewById(R.id.textview_status);

            tvSerial=(TextView)itemView.findViewById(R.id.textview_serial);
        }
    }

}
