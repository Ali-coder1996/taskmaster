package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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

        TextView title = holder.itemView.findViewById(R.id.taskTitle);
        TextView body = holder.itemView.findViewById(R.id.taskBody);
        TextView state = holder.itemView.findViewById(R.id.taskState);

        title.setText(holder.task.title);
        body.setText(holder.task.body);
        state.setText(holder.task.state);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,TaskDetail.class);
                intent.putExtra("title1",holder.task.title);
                context.startActivity(intent);
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
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            constraintLayout = itemView.findViewById(R.id.fragment);
        }
    }
}
