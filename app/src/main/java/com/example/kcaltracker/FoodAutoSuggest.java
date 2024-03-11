package com.example.kcaltracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.example.kcaltracker.model.AutoSuggestAdapter;
import com.example.kcaltracker.model.DatabaseHelper;

public class FoodAutoSuggest extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    private DatabaseHelper db;
    public FoodAutoSuggest(@NonNull Context context) {
        super(context);
        iniciateAutoComplete(context);
    }

    public FoodAutoSuggest(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        iniciateAutoComplete(context);
    }

    public FoodAutoSuggest(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniciateAutoComplete(context);
    }

    private void iniciateAutoComplete(@NonNull Context context) {
        Log.d("Test2", "Test2");
        AutoCompleteTextView itemInput = this;
        db = new DatabaseHelper(context);
        AutoSuggestAdapter adapter = new AutoSuggestAdapter(context,
                android.R.layout.simple_spinner_dropdown_item);
        itemInput.setThreshold(2);
        itemInput.setAdapter(adapter);
        itemInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {
                adapter.setData(db.getDescricao(itemInput.getText().toString()));
            }
        });
    }
}