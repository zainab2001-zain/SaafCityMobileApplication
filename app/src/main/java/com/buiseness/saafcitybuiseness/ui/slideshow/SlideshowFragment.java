package com.buiseness.saafcitybuiseness.ui.slideshow;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buiseness.saafcitybuiseness.AccountHistory;
import com.buiseness.saafcitybuiseness.Interface.API.ApiService;
import com.buiseness.saafcitybuiseness.Model.Complaint;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.databinding.FragmentSlideshowBinding;
import com.buiseness.saafcitybuiseness.ui.gallery.modelclass;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment implements OnButtonClickListener1 {
    Activity context;
    SearchView searchView;
    int lengthofcomplaints;
    String complaintidszie;
    RecyclerView recyclerView;
    String userEmail;
    LinearLayoutManager layoutManager;
    List<modelclass1> itemlist;
    List<String> complaintIds = new ArrayList<>();
    List<String> ComplaintStatus = new ArrayList<>();
    Adapter1 adapter;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View root = inflater.inflate(R.layout.fragment_slideshow,container,false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("logged_in_email", "");

        Log.d("C_Email: ", userEmail);
        itemlist = new ArrayList<>();
        recyclerView = root.findViewById(R.id.Recyclerview2);
        initRecyclerview();
        getComplaintdetailsapi();
        return root;
    }
    private void initRecyclerview()
    {
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter1(itemlist, (Context) getActivity(), (OnButtonClickListener1) this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initData()
    {
        itemlist=new ArrayList<>();

        itemlist.add(new modelclass1(102445,"Completed"));
        itemlist.add(new modelclass1(102446,"Completed"));

        itemlist.add(new modelclass1(102447,"Completed"));
        itemlist.add(new modelclass1(102448,"Completed"));

    }
    @Override
    public void onButtonClick1(int position) {
        Intent intent = new Intent(getActivity(), AccountHistory.class);
        startActivity(intent);
    }
    public void getComplaintdetailsapi() {
        String ipAddress = EnvVariables.MY_IP_ADDRESS;
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ipAddress) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a Complaint instance with the user's email
        Complaint complaint = new Complaint();

        complaint.setComplainant_Email(userEmail); // Replace with the user's email

        // Make the API call
        Call<List<Complaint>> call = apiService.getCompletedComplaints(complaint);
        call.enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                if (response.isSuccessful()) {
                    // Check the content type of the response
                    String contentType = response.headers().get("Content-Type");
                    if (contentType != null && contentType.contains("application/json")) {
                    // Handle successful response
                    Log.d("Response", response.toString());

                    List<Complaint> complaints = response.body();
                        if (complaints != null && !complaints.isEmpty()) {
                        // Iterate over the complaints and store the data in separate variables
                        for (Complaint complaint : complaints) {
                            String complaintId = complaint.getComplaint_ID();
                            String complainttime = complaint.getComplaint_Time();
                            String complaintStatus = complaint.getComplaint_Status();
                            complaintIds.add(complaintId);
                            ComplaintStatus.add(complaintStatus);
                            Log.d("Complaint_ID: ", complaintIds.toString());
                            Log.d("ComplaintStautus: ", ComplaintStatus.toString());
                            // Retrieve other fields and store them in respective variables

                            // Do something with the data

                            itemlist.add(new modelclass1(Integer.parseInt(complaintId), complaintStatus));
                            initRecyclerview(); // Initialize the RecyclerView after updating the data

                        }
                        lengthofcomplaints = complaintIds.size();
                        complaintidszie = String.valueOf(complaintIds.size());
                        Log.d("Complaint id size: ", complaintidszie);
                        Toast.makeText(getContext(), "COMPLAINT SIZE: " + lengthofcomplaints, Toast.LENGTH_SHORT).show();
                    }
                        else {
                            // Handle the case when the response body is empty or null
                            Toast.makeText(getContext(), "No completed complaints found", Toast.LENGTH_SHORT).show();
                        }
                } else if (contentType != null && contentType.contains("text/plain")) {
                    // Handle the case when the response contains a string message
                    String message = response.body().toString();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }

// Convert the ArrayList to an array if needed
                    // String[] complaintIdsArray = complaintIds.toArray(new String[1]);
                    //String[] C_Status = complaintIds.toArray(new String[1]);
                } else {
                    // Handle error response
                    Toast.makeText(getContext(), "Failed to fetch complaints", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                // Handle network failure
                Toast.makeText(getContext(), "Network error: "+t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("completed complaints: ",t.toString());
            }
        });
    }

}