package com.rokomari_poc.noteme.AllNotes;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokomari_poc.noteme.DetailsNote.DetailsNoteActivity;
import com.rokomari_poc.noteme.R;

import java.util.List;

public class RecyclerAdapterNotes extends RecyclerView.Adapter<RecyclerAdapterNotes.MyViewHolder>{
    private List<ModelNotes> modelNotes;
    private Context context;
    private String id = "";
    private String status="";
    private String category="";

    public RecyclerAdapterNotes(Context context,List<ModelNotes> modelNotes) {

        this.context = context;
        this.modelNotes = modelNotes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_all_notes,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        status=modelNotes.get(position).getStatus();
        category=modelNotes.get(position).getCategory();

        holder.tvTitle.setText(modelNotes.get(position).getTitle());
        holder.tvDate.setText(modelNotes.get(position).getTimestamp());
        holder.tvStatus.setText(modelNotes.get(position).getStatus());
        holder.tvSerial.setText(""+(position+1));

        if(category.equals("1"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_note_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("2"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_todo_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("3"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_remember_me_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("4"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_tag_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("5"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_urgencies_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("6"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_work_update_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("7"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_office);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }
        if(category.equals("8"))
        {
            Drawable new_image= context.getResources().getDrawable(R.drawable.ic_personal_black);
            holder.ivImageCategory.setBackgroundDrawable(new_image);
        }

        if(status.equals("1"))
            holder.tvStatus.setText("Undone");
        else if(status.equals("2"))
            holder.tvStatus.setText("Done");
        else if(status.equals("3"))
            holder.tvStatus.setText("Completed");
        else if (status.equals("4"))
            holder.tvStatus.setText("Abandoned");

        //item click
        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onItemClick(int pos) {
                id = modelNotes.get(pos).getId().toString();


                //Toast.makeText(context,""+id,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, DetailsNoteActivity.class);
                intent.putExtra("note_id", id);
                intent.putExtra("serial", "" + (pos + 1));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelNotes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ItemClickListener itemClickListener;

        TextView tvTitle,tvDate,tvStatus,tvSerial;
        ImageView ivImageCategory;
        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle=(TextView)itemView.findViewById(R.id.textview_title);
            tvDate=(TextView)itemView.findViewById(R.id.textview_date);
            tvStatus=(TextView)itemView.findViewById(R.id.textview_status);

            tvSerial=(TextView)itemView.findViewById(R.id.textview_serial);

            ivImageCategory=(ImageView)itemView.findViewById(R.id.image_category);
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
