package com.example.konkanivaani;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnAddFirstWord).setOnClickListener(view ->
            Toast.makeText(this, "Add First Word clicked", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navHome).setOnClickListener(view ->
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navCompare).setOnClickListener(view ->
            Toast.makeText(this, "Compare selected", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navContribute).setOnClickListener(view ->
            Toast.makeText(this, "Contribute selected", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navProfile).setOnClickListener(view ->
            Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
        );
    }
}
