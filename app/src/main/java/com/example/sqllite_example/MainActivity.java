package com.example.sqllite_example;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.sqllite_example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new FeedReaderDbHelper(getBaseContext());

        binding.titleInputButton.setOnClickListener(v -> {

            if (!binding.switchBtn.isChecked()) {
                long result = dbHelper.insertData(binding.titleInput.getText().toString());
                Log.d("insert_result", "onCreate: " + result);
            } else {
                dbHelper.delete(binding.titleInput.getText().toString());
            }
        });

        binding.readButton.setOnClickListener(v -> {
            Cursor cursor = dbHelper.getAll();
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                result.append("\n").append(cursor.getString(1));
            }
            binding.readTextView.setText(result.toString());
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        dbHelper = null;
    }
}