package com.example.lab5_security;
import com.example.lab5_security.Category;
import com.example.lab5_security.Task;
import com.example.lab5_security.UserEntity;
import com.example.lab5_security.CategoryRepository;
import com.example.lab5_security.TaskRepository;
import com.example.lab5_security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Task> getTasksByUser(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task addTask(String username, Task task, Long categoryId) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }

        return taskRepository.save(task);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
