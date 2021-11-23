package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import com.amplifyframework.datastore.generated.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    Context context;
    List<Task> allTask = new ArrayList<Task>();

    public TasksAdapter(Context ct,List<Task> allTask) {
        context=ct;
        this.allTask = allTask;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder =new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.task = allTask.get(position);
//        ImageView imageView = holder.imageView.findViewById(R.id.bdelete);
        TextView title = holder.itemView.findViewById(R.id.taskTitle);
        TextView body = holder.itemView.findViewById(R.id.taskBody);
        TextView state = holder.itemView.findViewById(R.id.taskState);

//        imageView.setImageAlpha(holder.task.getUid());
        title.setText(holder.task.getTitle());
        body.setText(holder.task.getBody());
        state.setText(holder.task.getState());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),TaskDetail.class);

                intent.putExtra("id", holder.task.getId());
                intent.putExtra("data", String.valueOf(holder.task));
                intent.putExtra("title",holder.task.getTitle());
                intent.putExtra("body",holder.task.getBody());
                intent.putExtra("state",holder.task.getState());
                view.getContext().startActivity(intent);

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Amplify.API.mutate(
                        ModelMutation.delete(holder.task),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                view.onFinishTemporaryDetach();
                Intent intent= new Intent(view.getContext(),MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allTask.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        public Task task;
        public View itemView;
        public ConstraintLayout constraintLayout;
        public ImageView imageView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            constraintLayout = itemView.findViewById(R.id.fragment);
            imageView = itemView.findViewById(R.id.bdelete);
        }
    }
}
