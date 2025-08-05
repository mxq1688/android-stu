package com.example.javamvvm.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamvvm.data.model.User;
import com.example.javamvvm.databinding.ItemUserBinding;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {
    
    private OnUserClickListener listener;
    
    public interface OnUserClickListener {
        void onUserClick(User user);
        void onUserEdit(User user);
        void onUserDelete(User user);
    }
    
    public UserAdapter(OnUserClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }
    
    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getEmail().equals(newItem.getEmail());
        }
    };
    
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user);
    }
    
    class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;
        
        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        public void bind(User user) {
            binding.setUser(user);
            
            // 设置点击事件
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserClick(user);
                }
            });
            
            binding.btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserEdit(user);
                }
            });
            
            binding.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserDelete(user);
                }
            });
            
            binding.executePendingBindings();
        }
    }
} 