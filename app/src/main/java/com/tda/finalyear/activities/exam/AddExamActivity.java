package com.tda.finalyear.activities.exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExamActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText title, std;
    ImageView routineImage;
    Button chooseImage, addExam;
    TextView showExams;
    ProgressBar progressBar;
    Bitmap bitmap = null;
    InputStream inputStream = null;
    File imageFile = null;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        bind();
        showExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddExamActivity.this, ExamListActivity.class));
            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromDevice();
            }
        });

        addExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addExamToDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
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
                imageFile = new File(Environment.getExternalStorageDirectory()+"/image.jpg");
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

    public void addExamToDatabase() throws IOException {


        //Log.i("file", file.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        RequestBody titleR = RequestBody.create(MediaType.parse("text/plane"), title.getText().toString());
        RequestBody stdR = RequestBody.create(MediaType.parse("text/plane"), std.getText().toString());
        MultipartBody.Part part = MultipartBody.Part.createFormData("image",imageFile.getName(), requestBody);
        RetrofitClient.getInstance().getExamService().addExam(part, titleR, stdR).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(AddExamActivity.this, ExamListActivity.class));
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("examElse", errorPojo.getMsg());
                        Toast.makeText(AddExamActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.i("examEx", e.getMessage());
                    Toast.makeText(AddExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddExamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        title = findViewById(R.id.title);
        std = findViewById(R.id.std);
        routineImage = findViewById(R.id.image_view);
        chooseImage = findViewById(R.id.button_choose_image);
        addExam = findViewById(R.id.add_exam);
        showExams = findViewById(R.id.show_exams);
        progressBar = findViewById(R.id.progress_bar);
    }
}