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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.assignment.AssignmentActivity;
import com.tda.finalyear.activities.solution.SolutionActivity;
import com.tda.finalyear.activities.solution.SolutionListActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.SolutionList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.SolutionViewHolder> {
    Context context;
    private SolutionList solutionList;
    ConstraintLayout mainLayout;
    File file;

    public SolutionAdapter(Context context, SolutionList solutionList) {
        this.context = context;
        this.solutionList = solutionList;
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.solution_card_view, parent, false);
        return new SolutionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewHolder holder, int position) {
        holder.title.setText(solutionList.getSolutions().get(position).getStudentName());
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getSolutionService().downloadFileWithDynamicUrlAsync(RetrofitClient.BASE_URL + "/" + solutionList.getSolutions().get(position).getSolutionFile()).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), solutionList.getSolutions().get(position).getStudentName());
                                    Log.i("fileDownload", "file download was a success? " + writtenToDisk);
                                    Log.i("fileIn", file.getPath());
                                    Intent intent = new Intent(context, SolutionActivity.class);
                                    intent.putExtra("SOLUTION_ID", solutionList.getSolutions().get(position).getId());
                                    intent.putExtra("SOLUTION_STUDENT", solutionList.getSolutions().get(position).getStudent());
                                    intent.putExtra("SOLUTION_STUDENT_NAME", solutionList.getSolutions().get(position).getStudentName());
                                    intent.putExtra("SOLUTION_FILE_PATH", file.getPath());
                                    intent.setData(Uri.parse(RetrofitClient.BASE_URL + "/" + solutionList.getSolutions().get(position).getSolutionFile()));
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
        });
    }

    @Override
    public int getItemCount() {
        return solutionList.getSolutions().size();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String title) {
        try {
            // todo change the file location/name according to your needs
            file = new File(context.getExternalFilesDir(null) + File.separator + title + "title.pdf");
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

                    Log.i("file download: ", +fileSizeDownloaded + " of " + fileSize);
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

    public class SolutionViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public SolutionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            mainLayout = itemView.findViewById(R.id.solution_card);
        }
    }
}
