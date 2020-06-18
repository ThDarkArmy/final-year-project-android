package com.tda.finalyear.activities.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.shockwave.pdfium.PdfDocument;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.assignment.AssignmentListActivity;
import com.tda.finalyear.activities.assignment.UploadAssignmentActivity;
import com.tda.finalyear.activities.student.StudentActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSolutionActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private int pageNumber = 0;

    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    private Spinner spinner;
    private EditText name;
    private Button choseFile, uploadFile;
    private String std;
    boolean classSelected = false;
    private Student student;
    String assignmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_solution);
        bind();
        student = (Student)Objects.requireNonNull(getIntent().getSerializableExtra("STUDENT"));
        assignmentId = Objects.requireNonNull(getIntent().getStringExtra("ASSIGNMENT_ID"));


        choseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPicker();
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    uploadSolution();
            }
        });
    }

    // chose file
    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }
        }
    }

    // display file
    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);

        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    // get file name
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }

    public void uploadSolution(){
        if (pdfPath == null) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        }else{
            File file = new File(pdfPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            RequestBody titleR = RequestBody.create(MediaType.parse("text/plane"), assignmentId);
            RequestBody stdR = RequestBody.create(MediaType.parse("text/plane"), student.getId());
            RequestBody nameR = RequestBody.create(MediaType.parse("text/plane"), name.getText().toString());
            MultipartBody.Part part = MultipartBody.Part.createFormData("solutionFile",file.getName(), requestBody);
            RetrofitClient.getInstance().getSolutionService().uploadSolution(part,titleR,stdR, nameR).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(UploadSolutionActivity.this, StudentActivity.class);
                        intent.putExtra("CLASS_TYPE", student);
                        startActivity(intent);
                        try {
                            Log.i("solution", response.body().string());
                        } catch (IOException e) {
                            Log.i("solutionE", e.getMessage());
                        }
                    }else{
                        try {
                            Log.i("solutionEl", response.errorBody().string());
                        }catch (Exception e){
                            Log.i("solutionX", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UploadSolutionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    protected void showDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hideDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    // validation
    public boolean validation(){
        if(!classSelected){
            Toast.makeText(this, "Select a class.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    // bind
    public void bind(){
        name = findViewById(R.id.title);
        choseFile = findViewById(R.id.chose_file);
        uploadFile = findViewById(R.id.upload_file);
        pdfView = findViewById(R.id.pdfView);
    }
}