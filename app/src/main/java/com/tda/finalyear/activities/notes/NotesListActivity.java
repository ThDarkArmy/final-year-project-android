package com.tda.finalyear.activities.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.assignment.AssignmentListActivity;
import com.tda.finalyear.adapter.AssignmentAdapter;
import com.tda.finalyear.adapter.NotesAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.AssignmentList;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.NotesList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        recyclerView = findViewById(R.id.notes_recycler);
        getAssignmentList();
    }

    public void getAssignmentList(){
        RetrofitClient.getInstance().getNotesService().getNotesList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        NotesList notesList = new GsonBuilder().create().fromJson(response.body().string(), NotesList.class);
                        Log.i("notesList", notesList.toString());
                        NotesAdapter notesAdapter = new NotesAdapter(NotesListActivity.this, notesList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(NotesListActivity.this));
                        recyclerView.setAdapter(notesAdapter);
                    }catch(Exception e){
                        Log.i("notesListE", e.getMessage());
                    }
                }else{
                    try{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Toast.makeText(NotesListActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(NotesListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}