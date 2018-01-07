package com.rokomari_poc.noteme.WorkUpdate;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rokomari_poc.noteme.AllNotes.ItemClickListener;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.WorkUpdate.DetailsWorkUpdate.DetailsWorkUpdateActivity;

import java.util.List;

public class RecyclerAdapterWorkUpdate extends RecyclerView.Adapter<RecyclerAdapterWorkUpdate.MyViewHolderWorkUpdate>{

    private List<ModelWorkUpdate> modelWorkUpdates;
    private Context context;
    private String id="";
    private String status="";

    public RecyclerAdapterWorkUpdate( Context context,List<ModelWorkUpdate> modelWorkUpdates) {
        this.modelWorkUpdates = modelWorkUpdates;
        this.context = context;
    }

    @Override
    public MyViewHolderWorkUpdate onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_work_update,parent,false);

        return new MyViewHolderWorkUpdate(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderWorkUpdate holder, int position) {

        holder.tvName.setText(modelWorkUpdates.get(position).getFirstName());
        holder.tvDate.setText(modelWorkUpdates.get(position).getTimestamp());
        holder.tvDetails.setText(modelWorkUpdates.get(position).getDetail());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                id=modelWorkUpdates.get(pos).getId().toString();

                Intent intent=new Intent(context,DetailsWorkUpdateActivity.class);
                intent.putExtra("first_name",modelWorkUpdates.get(pos).getFirstName());
                intent.putExtra("work_update_id",id);
                intent.putExtra("serial_work_update", "" + (pos + 1));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return modelWorkUpdates.size();
    }

    public class MyViewHolderWorkUpdate extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ItemClickListener itemClickListener;
        TextView tvName,tvDate,tvDetails,tvserial;

        public MyViewHolderWorkUpdate(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.textview_name_work_update);
            tvDate=itemView.findViewById(R.id.textview_date_work_update);
            tvDetails=itemView.findViewById(R.id.textview_work_update);
            tvserial=itemView.findViewById(R.id.textview_serial_work_update);

            itemView.setOnClickListener(this);
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

}
