package com.buiseness.saafcitybuiseness.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.buiseness.saafcitybuiseness.AccountHistory;
import com.buiseness.saafcitybuiseness.CameraScreen2;
import com.buiseness.saafcitybuiseness.LocationScreen;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.Verification_Number_Screen;
import com.buiseness.saafcitybuiseness.ui.slideshow.SlideshowFragment;
import com.buiseness.saafcitybuiseness.ui.slideshow.modelclass1;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>implements OnButtonClickListener{

    private List<modelclass> itemlist;
    private Context mContext;
    private OnButtonClickListener mListener;

    public Adapter(List<modelclass>itemlist,Context context,OnButtonClickListener listener){
        this.itemlist=itemlist;
        mContext = context;
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int complaintno=itemlist.get(position).getComplintno();
        String status=itemlist.get(position).getStatus();

       // try{
            holder.setData(complaintno,status);
        //}
        //catch (Exception e){

        //}

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    @Override
    public void onButtonClick(int position) {
        Intent intent = new Intent(mContext, CameraScreen2.class);
        Toast.makeText(mContext, "Kindly Verify your Complaint!", Toast.LENGTH_SHORT).show();
        mContext.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView complaint_no, status_c;
        View rootview;
        Button Viewbtn;

        public ViewHolder(@NonNull View itemview) {

            super(itemview);
           // rootview = itemview;
            complaint_no = itemview.findViewById(R.id.complaintno);
            status_c = itemview.findViewById(R.id.status);
            Viewbtn=itemview.findViewById(R.id.view_btn);

            Viewbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Viewbtn.isEnabled()) {
                        mListener.onButtonClick(getAdapterPosition());
                        Intent intent = new Intent(mContext, CameraScreen2.class);
                        mContext.startActivity(intent);
                    }
                }
            });
           // itemview.findViewById(R.id.view_btn).setOnClickListener(this);
        }

        public void setData(int resource, String status) {
            complaint_no.setText(String.valueOf(resource));
            status_c.setText(status);
            Viewbtn.setText("Verify");
            if (status.equals("In-Progress") || status.equals("Transport Send")) {
                Viewbtn.setEnabled(false);

            } else {
                Viewbtn.setEnabled(true);
            }
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
