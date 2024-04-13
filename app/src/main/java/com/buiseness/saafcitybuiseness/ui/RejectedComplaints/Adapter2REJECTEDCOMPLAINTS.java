package com.buiseness.saafcitybuiseness.ui.RejectedComplaints;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buiseness.saafcitybuiseness.AccountHistory;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.ui.RejectedComplaints.OnButtonClickListener2;
import com.buiseness.saafcitybuiseness.ui.RejectedComplaints.modelclassrejectedcomplaints;

import java.util.List;

public class Adapter2REJECTEDCOMPLAINTS extends RecyclerView.Adapter<Adapter2REJECTEDCOMPLAINTS.ViewHolder>implements OnButtonClickListener2 {

    private List<modelclassrejectedcomplaints> itemlist;
    private Context mContext;
    private OnButtonClickListener2 mListener;

    public Adapter2REJECTEDCOMPLAINTS(List<modelclassrejectedcomplaints>itemlist, Context context, OnButtonClickListener2 listener){
        this.itemlist=itemlist;
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitemresolvedcomplaints,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int complaintno=itemlist.get(position).getComplintno();
        String status=itemlist.get(position).getStatus();

        try{
            holder.setData(complaintno,status);
        }
        catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    @Override
    public void onButtonClick1(int position) {
        Intent intent = new Intent(mContext, AccountHistory.class);
        mContext.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView complaint_no, status_c;
        View rootview;
        Button Viewbtn;

        public ViewHolder(@NonNull View itemview) {

            super(itemview);
           // rootview = itemview;
            complaint_no = itemview.findViewById(R.id.resolvedcomplaintno);
            status_c = itemview.findViewById(R.id.resolved_cstatus);
            Viewbtn=itemview.findViewById(R.id.resolved_cview_btn);
            Viewbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onButtonClick1(getAdapterPosition());
                }
            });
           // itemview.findViewById(R.id.view_btn).setOnClickListener(this);
        }

        public void setData(int resource, String status) {
            complaint_no.setText(resource);
            status_c.setText(status);
        }


       /* public void onClick(View view) {
            try {
                int position = getAdapterPosition();
                Toast.makeText(context, "position " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AccountHistory.class);
                context.startActivity(intent);
            } catch (Exception e) {
                Log.d("Error: ", e.toString());
            }
        }*/
    }
}
