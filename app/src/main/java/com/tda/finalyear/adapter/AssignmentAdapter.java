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
import com.tda.finalyear.activities.solution.SolutionListActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.AssignmentList;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    Context context;
    private AssignmentList assignments;
    ConstraintLayout mainLayout;
    File file;
    Teacher classType;
    Student student;


    public AssignmentAdapter(Context context, AssignmentList assignments, Teacher classType) {
        this.context = context;
        this.assignments = assignments;
        this.classType = classType;
    }

    public AssignmentAdapter(Context context, AssignmentList assignments,Student student) {
        this.context = context;
        this.assignments = assignments;
        this.student = student;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assignment_card_view, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        holder.title.setText(assignments.getAssignments().get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitClient.getInstance().getAssignmentService().downloadFileWithDynamicUrlAsync(RetrofitClient.BASE_URL + "/" + assignments.getAssignments().get(position).getAssignmentFile()).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), assignments.getAssignments().get(position).getTitle());
                                    Log.i("fileDownload", "file download was a success? " + writtenToDisk);
                                    Log.i("fileIn", file.getPath());
                                    Intent intent = new Intent(context, EditAssignmentActivity.class);
                                    intent.putExtra("ASSIGNMENT_ID", assignments.getAssignments().get(position).getId());
                                    intent.putExtra("ASSIGNMENT_STD", assignments.getAssignments().get(position).getStd());
                                    intent.putExtra("ASSIGNMENT_TITLE", assignments.getAssignments().get(position).getTitle());
                                    intent.putExtra("ASSIGNMENT_FILE_PATH", file.getPath());
                                    intent.putExtra("CLASS_TYPE", classType);
                                    intent.setData(Uri.parse(RetrofitClient.BASE_URL + "/" + assignments.getAssignments().get(position).getAssignmentFile()));
                                    intent.putExtra("STUDENT", student);
                                    context.startActivity(intent);
                                    return null;
                                }
                            }.execute();
                        } else {
                            try {
                                Log.i("else", response.errorBody().string());
                            } catch (IOException e) {
                                Log.i("else", e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent = new Intent(context, EditAssignmentActivity.class);
//                intent.putExtra("ASSIGNMENT_ID", assignments.getAssignments().get(position).getId());
//                intent.putExtra("ASSIGNMENT_STD", assignments.getAssignments().get(position).getStd());
//                intent.putExtra("ASSIGNMENT_TITLE", assignments.getAssignments().get(position).getTitle());
//                intent.setData(Uri.parse(RetrofitClient.BASE_URL+"/"+assignments.getAssignments().get(position).getAssignmentFile()));
//                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getAssignmentService().deleteAssignment(assignments.getAssignments().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            assignments.getAssignments().remove(position);
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
                if (student == null) {
                    Intent intent = new Intent(context, SolutionListActivity.class);
                    intent.putExtra("ASSIGNMENT_ID", assignments.getAssignments().get(position).getId());
                    intent.putExtra("ASSIGNMENT_STD", assignments.getAssignments().get(position).getStd());
                    intent.putExtra("ASSIGNMENT_TITLE", assignments.getAssignments().get(position).getTitle());
                    //intent.putExtra("ASSIGNMENT_FILE_PATH", file.getPath());
                    intent.setData(Uri.parse(RetrofitClient.BASE_URL + "/" + assignments.getAssignments().get(position).getAssignmentFile()));
                    context.startActivity(intent);
                } else {
                    RetrofitClient.getInstance().getAssignmentService().downloadFileWithDynamicUrlAsync(RetrofitClient.BASE_URL + "/" + assignments.getAssignments().get(position).getAssignmentFile()).enqueue(new Callback<ResponseBody>() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        boolean writtenToDisk = writeResponseBodyToDisk(response.body(), assignments.getAssignments().get(position).getTitle());
                                        Log.i("fileDownload", "file download was a success? " + writtenToDisk);
                                        Log.i("fileIn", file.getPath());
                                        Intent intent = new Intent(context, AssignmentActivity.class);
                                        intent.putExtra("ASSIGNMENT_ID", assignments.getAssignments().get(position).getId());
                                        intent.putExtra("ASSIGNMENT_STD", assignments.getAssignments().get(position).getStd());
                                        intent.putExtra("ASSIGNMENT_TITLE", assignments.getAssignments().get(position).getTitle());
                                        intent.putExtra("ASSIGNMENT_FILE_PATH", file.getPath());
                                        intent.setData(Uri.parse(RetrofitClient.BASE_URL + "/" + assignments.getAssignments().get(position).getAssignmentFile()));
                                        intent.putExtra("STUDENT", student);
                                        context.startActivity(intent);
                                        return null;
                                    }
                                }.execute();
                            } else {
                                try {
                                    Log.i("else", response.errorBody().string());
                                } catch (IOException e) {
                                    Log.i("else", e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignments.getAssignments().size();
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

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        Button edit, delete;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            mainLayout = itemView.findViewById(R.id.assignment_card);

        }

    }
}
