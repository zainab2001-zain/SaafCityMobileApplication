package com.buiseness.saafcitybuiseness;

import android.content.SharedPreferences;
import java.io.ByteArrayOutputStream;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.database.Cursor;
import android.hardware.camera2.CameraMetadata;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.text.SimpleDateFormat;
import android.graphics.drawable.BitmapDrawable;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Date;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buiseness.saafcitybuiseness.Interface.API.ApiService;
import com.buiseness.saafcitybuiseness.Model.Complainant;
import com.buiseness.saafcitybuiseness.Model.ComplaintResponse;
import com.buiseness.saafcitybuiseness.Model.DataPart;
import com.buiseness.saafcitybuiseness.Model.EnvVariables;
//import com.buiseness.saafcitybuiseness.Multipartrequest;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CameraScreen extends AppCompatActivity {

    public static final int REQUEST_CODE = 102;
    ImageView image1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    Button camera,gallery;
    Button next;
    String filepath;
String complainant_email;
    private static final int GALLERY_REQUEST_CODE = 100;
    int SELECT_PICTURE = 200;
    String Imagestring;
    private RequestQueue mRequestQueue;
    public static final int CAMERA_PERM=2;

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);
        } else {
            // Permission already granted
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_screen);

        image1=findViewById(R.id.cameraimage);
        camera=findViewById(R.id.camerabutton);
        gallery=findViewById(R.id.gllerybtn);
        next=findViewById(R.id.Nextbutton);
        complainant_email= getIntent().getStringExtra("Email");
        Log.d("email",complainant_email);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the permission is granted
                if (ContextCompat.checkSelfPermission(CameraScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Request runtime permission
                    ActivityCompat.requestPermissions(CameraScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    // Permission is already granted, you can proceed with opening the gallery
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 100);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (image1.equals(null)){

                    Toast.makeText(CameraScreen.this, "Please Upload the Photo from Camera or Gallery!", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CameraScreen.this);
                    builder.setMessage("Are you sure you want to upload this photo ?");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) -> {
                        // When the user click yes button then app will close
                        //uploadImageToServer();
                        //uploadImageToServer();
                        sendingimage();

                    });

                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // If user click no then dialog box is canceled.
                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
            }
        });


    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void saveImageToGallery(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, imageFileName, "Captured image");
    }

private void sendingimage(){
    // Create a File object for the image file
    File imageFile = new File(filepath);

// Create a RequestBody object from the image file
    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

// Create a MultipartBody.Part object from the RequestBody
    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
    String ipAddress = EnvVariables.MY_IP_ADDRESS;
// Create a Retrofit instance
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ipAddress) // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build();

// Create an instance of your ApiService interface
    ApiService apiService = retrofit.create(ApiService.class);

// Get the logged-in user's email
    String complaintid =  getComplaintIdFromSharedPreferences(); // Replace "5112" with the default value if complaint ID is not found
    ; // Replace with the logged-in user's email

// Call the uploadImage() method on your ApiService interface
    Call<ComplaintResponse> call = apiService.uploadImage(complaintid, filePart);

// Execute the API call asynchronously
    call.enqueue(new Callback<ComplaintResponse>() {
        @Override
        public void onResponse(Call<ComplaintResponse> call, retrofit2.Response<ComplaintResponse> response) {
            if (response.isSuccessful()) {
                // Handle success response
                ComplaintResponse uploadResponse = response.body();
                if (uploadResponse.isSuccess()) {
                    // Image uploaded successfully
                    String message = uploadResponse.getMessage();
                    Log.d("Upload", "Image uploaded successfully: " + message);
                    Intent intent = new Intent(CameraScreen.this, LoadingScreen.class);

                    startActivity(intent);

                } else {
                    // Error uploading image
                    String message = uploadResponse.getMessage();
                    Log.e("Upload", "Error uploading image: " + message);
                }
            } else {
                // Handle error response
                Log.e("Upload", "response successful API call failed: " + response.message()+" "+complaintid);
            }
        }

        @Override
        public void onFailure(Call<ComplaintResponse> call, Throwable t) {
            Log.e("Upload", "API call failed: " + t.getMessage());
        }



    });

}
    private String getComplaintIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ComplaintData", MODE_PRIVATE);
        int complaintId = sharedPreferences.getInt("complaintId", 0); // Replace 0 with the default value if complaint ID is not found
        return String.valueOf(complaintId);
    }

    // Helper method to read the file as a byte array
    public static byte[] readFileToByteArray(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }


    // Handle the result of selecting an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image1.setImageBitmap(imageBitmap);
// Create a new file for the captured image
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                Log.e("ExceptionTag", "Exception message: " + e.getMessage());
                throw new RuntimeException(e);


            }
            filepath = imageFile.getAbsolutePath();
            saveImageToGallery(imageBitmap);
        }
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filepath = cursor.getString(columnIndex);
            cursor.close();

            try {
                // Load the selected image into the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                image1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

