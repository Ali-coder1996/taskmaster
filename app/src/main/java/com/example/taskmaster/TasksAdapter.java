package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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
        title.setText(holder.task.title);
        body.setText(holder.task.body);
        state.setText(holder.task.state);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),TaskDetail.class);

                intent.putExtra("id", holder.task.getUid());
                intent.putExtra("title",holder.task.title);
                intent.putExtra("body",holder.task.body);
                intent.putExtra("state",holder.task.state);
                view.getContext().startActivity(intent);

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Task> allTask = new ArrayList<>();
                AppDatabase db = AppDatabase.getDataBaseObj(view.getContext());
                TaskDao taskDao = db.taskDao();
                taskDao.delete(holder.task);

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
