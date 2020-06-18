package com.tda.finalyear.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.assignment.AssignmentActivity;
import com.tda.finalyear.activities.assignment.EditAssignmentActivity;
import com.tda.finalyear.activities.notes.EditNotesActivity;
import com.tda.finalyear.activities.notes.NotesActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.NotesList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    Context context;
    private NotesList notes;
    ConstraintLayout mainLayout;
    File file;

    public NotesAdapter(Context context, NotesList notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_card_view, parent, false);
        return new NotesAdapter.NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        holder.title.setText(notes.getNotes().get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getNotesService().downloadFileWithDynamicUrlAsync(RetrofitClient.BASE_URL+"/"+notes.getNotes().get(position).getNotesFile()).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), notes.getNotes().get(position).getTitle());
                                    Log.i("fileDownload", "file download was a success? " + writtenToDisk);
                                    Log.i("fileIn", file.getPath());
                                    Intent intent = new Intent(context, EditNotesActivity.class);
                                    intent.putExtra("NOTES_ID", notes.getNotes().get(position).getId());
                                    intent.putExtra("NOTES_STD", notes.getNotes().get(position).getStd());
                                    intent.putExtra("NOTES_TITLE", notes.getNotes().get(position).getTitle());
                                    intent.putExtra("NOTES_FILE_PATH", file.getPath());
                                    intent.setData(Uri.parse(RetrofitClient.BASE_URL+"/"+notes.getNotes().get(position).getNotesFile()));
                                    context.startActivity(intent);
                                    return null;
                                }
                            }.execute();
                        }else{
                            try {
                                Log.i("else",response.errorBody().string());
                            } catch (IOException e) {
                                Log.i("else",e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent = new Intent(context, EditNotesActivity.class);
//                intent.putExtra("NOTES_ID", notes.getNotes().get(position).getId());
//                intent.putExtra("NOTES_STD", notes.getNotes().get(position).getStd());
//                intent.putExtra("NOTES_TITLE", notes.getNotes().get(position).getTitle());
//                intent.setData(Uri.parse(RetrofitClient.BASE_URL+"/"+notes.getNotes().get(position).getNotesFile()));
//                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getAssignmentService().deleteAssignment(notes.getNotes().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            notes.getNotes().remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getNotesService().downloadFileWithDynamicUrlAsync(RetrofitClient.BASE_URL+"/"+notes.getNotes().get(position).getNotesFile()).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), notes.getNotes().get(position).getTitle());
                                    Log.i("fileDownload", "file download was a success? " + writtenToDisk);
                                    Log.i("fileIn", file.getPath());
                                    Intent intent = new Intent(context, NotesActivity.class);
                                    intent.putExtra("NOTES_ID", notes.getNotes().get(position).getId());
                                    intent.putExtra("NOTES_STD", notes.getNotes().get(position).getStd());
                                    intent.putExtra("NOTES_TITLE", notes.getNotes().get(position).getTitle());
                                    intent.putExtra("NOTES_FILE_PATH", file.getPath());
                                    intent.setData(Uri.parse(RetrofitClient.BASE_URL+"/"+notes.getNotes().get(position).getNotesFile()));
                                    context.startActivity(intent);
                                    return null;
                                }
                            }.execute();
                        }else{
                            try {
                                Log.i("else",response.errorBody().string());
                            } catch (IOException e) {
                                Log.i("else",e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.getNotes().size();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String title) {
        try {
            // todo change the file location/name according to your needs
            file = new File(context.getExternalFilesDir(null) + File.separator + title+"title.pdf");
            Log.i("file_path", file.getPath());

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.i( "file download: " ,+ fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        Button edit, delete;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            mainLayout = itemView.findViewById(R.id.notes_card);
        }
    }
}
