package com.buiseness.saafcitybuiseness.ui.gallery;

import com.buiseness.saafcitybuiseness.Interface.API.ApiService;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.Complaint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buiseness.saafcitybuiseness.AccountHistory;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.databinding.FragmentGalleryBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment implements OnButtonClickListener {
    Activity context;
    SearchView searchView;
    String userEmail;
    List<String> complaintIds = new ArrayList<>();
    List<String> ComplaintStatus = new ArrayList<>();
    List<modelclass> itemlist;
    RecyclerView recyclerView;
    String complaintidszie;
    int lengthofcomplaints;

    LinearLayoutManager layoutManager;

    View root;
    Adapter adapter;
    String Complainant_Email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("logged_in_email", "");

        Log.d("C_Email: ", userEmail);
        itemlist = new ArrayList<>();
        recyclerView = root.findViewById(R.id.Recyclerview1);
        initRecyclerview();
        getComplaintDetailsAPI();

        return root;
    }

    private void initRecyclerview() {
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(itemlist, getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onButtonClick(int position) {
        Intent intent = new Intent(getActivity(), AccountHistory.class);
        startActivity(intent);
    }

    public void getComplaintDetailsAPI() {
        String ipAddress = EnvVariables.MY_IP_ADDRESS;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Complaint complaint = new Complaint();
        complaint.setComplainant_Email(userEmail);

        Call<List<Complaint>> call = apiService.getOpenComplaints(complaint);
        call.enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                if (response.isSuccessful()) {
                    List<Complaint> complaints = response.body();

                    for (Complaint complaint : complaints) {
                        String complaintId = complaint.getComplaint_ID();
                        String complainttime = complaint.getComplaint_Time();
                        String complaintStatus = complaint.getComplaint_Status();
                        complaintIds.add(complaintId);
                        ComplaintStatus.add(complaintStatus);
                        itemlist.add(new modelclass(Integer.parseInt(complaintId), complaintStatus));
                    }

                    lengthofcomplaints = complaintIds.size();
                    complaintidszie = String.valueOf(complaintIds.size());
                    Toast.makeText(getContext(), "COMPLAINT SIZE: " + lengthofcomplaints, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch complaints", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
