package com.tda.finalyear.activities.exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditExamActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText title, std;
    ImageView routineImage;
    Button chooseImage, editExam;
    TextView showExams;
    ProgressBar progressBar;
    Bitmap bitmap = null;
    InputStream inputStream = null;
    File imageFile = null;
    private Uri imageUri;
    private boolean isImage = false;
    private static final int MY_PERMISSIONS_REQUESTS = 0;

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUESTS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // go ahead
            }else{
                // fuck off
            }
        }
    }

    private void requestPermissions() {
        List<String> requiredPermissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.CAMERA);
        }

        if (!requiredPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    requiredPermissions.toArray(new String[]{}),
                    MY_PERMISSIONS_REQUESTS);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exam);
        requestPermissions();
        bind();
        String examId = Objects.requireNonNull(getIntent().getStringExtra("EXAM_ID"));
        String ETitle = getIntent().getStringExtra("EXAM_TITLE");
        String EStd = getIntent().getStringExtra("EXAM_STD");
        Uri myUri=getIntent().getData();
        title.setText(ETitle);
        std.setText(EStd);
        Picasso.get().load(myUri).into(routineImage);


        showExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditExamActivity.this, ExamListActivity.class));
            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromDevice();
            }
        });

        editExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    updateExam(examId);
                }

            }
        });
    }

    public void chooseImageFromDevice(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK && data!= null && data.getData()!= null){
            imageUri = data.getData();
            try{
                inputStream = getContentResolver().openInputStream(imageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inSampleSize = 2;
                options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                imageFile = new File(Environment.getExternalStorageDirectory()+"/"+title.getText().toString()+std.getText().toString()+"image.jpg");
                imageFile.createNewFile();
                if(!imageFile.exists()){
                    imageFile.mkdir();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e){

            }
            Log.i("image", imageUri.toString());
            Picasso.get().load(imageUri).into(routineImage);        }
    }

    public void updateExam(String id) {
        //Log.i("file", file.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        RequestBody titleR = RequestBody.create(MediaType.parse("text/plane"), title.getText().toString());
        RequestBody stdR = RequestBody.create(MediaType.parse("text/plane"), std.getText().toString());
        MultipartBody.Part part = MultipartBody.Part.createFormData("routine",imageFile.getName(), requestBody);
        RetrofitClient.getInstance().getExamService().editExam(id, part, titleR, stdR).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(EditExamActivity.this, ExamListActivity.class));
                    }else{
                        try{
                            //ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                            Log.i("examElse", response.errorBody().string());
                            //Toast.makeText(EditExamActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Log.i("exam", e.getMessage());
                        }

                    }
                }catch (Exception e){
                    Log.i("examEx", e.getMessage());
                    Toast.makeText(EditExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditExamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validation(){
        if(title.getText().toString().isEmpty()){
            title.setError("Title cannot be empty.");
            return false;
        }else if(std.getText().toString().isEmpty()){
            std.setError("Class cannot be empty.");
            return false;
        }else if(imageUri == null){
            Toast.makeText(EditExamActivity.this, "Image field cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        title = findViewById(R.id.title);
        std = findViewById(R.id.std);
        routineImage = findViewById(R.id.image_view);
        chooseImage = findViewById(R.id.button_choose_image);
        editExam = findViewById(R.id.edit_exam);
        showExams = findViewById(R.id.show_exams);
        progressBar = findViewById(R.id.progress_bar);
    }
}