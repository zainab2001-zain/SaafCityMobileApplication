package com.buiseness.saafcitybuiseness.ui.RejectedComplaints;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.buiseness.saafcitybuiseness.AccountHistory;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.databinding.FragmentSlideshowBinding;


import java.util.ArrayList;
import java.util.List;

public class RejectedComplaintsFragment extends Fragment  implements OnButtonClickListener2 {

    Activity context;
    SearchView searchView;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<modelclassrejectedcomplaints> itemlist;
    Adapter2REJECTEDCOMPLAINTS adapter;
    private FragmentSlideshowBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        View root = inflater.inflate(R.layout.fragment_rejected_complaints,container,false);
        recyclerView = (RecyclerView) root.findViewById(R.id.Recyclerview3);
        initData();
        initRecyclerview();
        return root;
    }
    private void initRecyclerview()
    {
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter2REJECTEDCOMPLAINTS(itemlist, (Context) getActivity(), (OnButtonClickListener2) this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initData()
    {
        itemlist=new ArrayList<>();

        itemlist.add(new modelclassrejectedcomplaints(102445,"Completed"));
        itemlist.add(new modelclassrejectedcomplaints(102446,"Completed"));

        itemlist.add(new modelclassrejectedcomplaints(102447,"Completed"));
        itemlist.add(new modelclassrejectedcomplaints(102448,"Completed"));

    }
    @Override
    public void onButtonClick1(int position) {
        Intent intent = new Intent(getActivity(), AccountHistory.class);
        startActivity(intent);
    }
}