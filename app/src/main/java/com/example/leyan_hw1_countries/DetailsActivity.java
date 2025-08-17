package com.example.leyan_hw1_countries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DetailsActivity extends AppCompatActivity {

    private CountryViewModel viewModel;
    private Country current; // العنصر المعروض حالياً

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // 1) ViewModel
        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        // 2) استلام الداتا من الـ Intent
        int id        = getIntent().getIntExtra("id", -1);
        String name   = getIntent().getStringExtra("name");
        String capital= getIntent().getStringExtra("capital");
        String desc   = getIntent().getStringExtra("description");
        int flag      = getIntent().getIntExtra("flag", R.drawable.ic_flag_placeholder);

        // نبني Country بنفس القيم ونعيّن نفس الـ id
        current = new Country(name, capital, desc, flag);
        current.id = id;

        // 3) ربط الـ Views
        ImageView imgFlagLarge   = findViewById(R.id.imgFlagLarge);
        TextView tvNameDetail    = findViewById(R.id.tvNameDetail);
        TextView tvCapitalDetail = findViewById(R.id.tvCapitalDetail);
        TextView tvDescription   = findViewById(R.id.tvDescription);
        MaterialButton btnEdit   = findViewById(R.id.btnEdit);
        MaterialButton btnDelete = findViewById(R.id.btnDelete);

        // 4) عرض البيانات
        tvNameDetail.setText(name != null ? name : "—");
        tvCapitalDetail.setText("Capital: " + (capital != null ? capital : "—"));
        tvDescription.setText(desc != null ? desc : "—");
        imgFlagLarge.setImageResource(flag);

        // 5) Edit: ديالوج تعديـل ثم update
        btnEdit.setOnClickListener(v -> showEditDialog());

        // 6) Delete: تأكيد ثم delete
        btnDelete.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Delete this country?")
                        .setPositiveButton("Yes", (d, w) -> {
                            viewModel.delete(current);   // <-- المهم
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show()
        );
    }

    private void showEditDialog() {
        // من ملفاتك عندك edit_country.xml — نستخدمه
        View dialogView = LayoutInflater.from(this).inflate(R.layout.edit_country, null, false);
        TextInputEditText etName        = dialogView.findViewById(R.id.etName);
        TextInputEditText etCapital     = dialogView.findViewById(R.id.etCapital);
        TextInputEditText etDescription = dialogView.findViewById(R.id.etDescription);

        // تعبئة القيم الحالية
        etName.setText(current.name);
        etCapital.setText(current.capital);
        etDescription.setText(current.description);

        new AlertDialog.Builder(this)
                .setTitle("Edit country")
                .setView(dialogView)
                .setPositiveButton("Save", (d, w) -> {
                    String newName   = String.valueOf(etName.getText()).trim();
                    String newCapital= String.valueOf(etCapital.getText()).trim();
                    String newDesc   = String.valueOf(etDescription.getText()).trim();

                    if (newName.isEmpty() || newCapital.isEmpty()) {
                        Toast.makeText(this, "Name & Capital are required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // نبني كائن جديد بنفس id عشان @Update يشتغل
                    Country updated = new Country(newName, newCapital, newDesc, current.flagResId);
                    updated.id = current.id;

                    viewModel.update(updated);     // <-- المهم
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    finish(); // ارجعي للقائمة، LiveData رح تحدث تلقائياً
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
