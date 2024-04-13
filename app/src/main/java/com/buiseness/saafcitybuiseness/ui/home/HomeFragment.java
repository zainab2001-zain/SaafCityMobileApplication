package com.buiseness.saafcitybuiseness.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.buiseness.saafcitybuiseness.CameraScreen;
import com.buiseness.saafcitybuiseness.LocationScreen;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.R;
import com.buiseness.saafcitybuiseness.ui.gallery.GalleryFragment;

public class HomeFragment extends Fragment {

    Activity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        context=getActivity();
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        return root;
    }

    public void onStart(){
        super.onStart();
        Button login_btn,verify_btn,departmentbtn;
        login_btn= (Button) context.findViewById(R.id.log_complaint_btn);
        //verify_btn=(Button) context.findViewById(R.id.verify_complaint_btn);
        Complainant complainant=new Complainant();
      //  Log.d("email homefragment",complainant.getComplainant_Email());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(context, LocationScreen.class);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

     /*   verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    GalleryFragment fragment = new GalleryFragment();

                    fragmentTransaction.replace(R.id.homefragment, fragment,"history");
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                }
                catch (Exception e){
                    Log.d("error",e.toString());
                }
            }
        });*/
    }
}