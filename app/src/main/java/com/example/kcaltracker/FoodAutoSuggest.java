package com.example.kcaltracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.kcaltracker.model.AutoSuggestAdapter;
import com.example.kcaltracker.model.DatabaseHelper;
import com.example.kcaltracker.model.FoodItem;

public class FoodAutoSuggest extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    private DatabaseHelper db;
    private FoodItem foodItem;
    private FoodAutoSuggestCallback callback;

    // Callback interface
    public interface FoodAutoSuggestCallback {
        void onFoodItemSaved(FoodItem foodItem);
    }
    public void setFoodAutoSuggestCallback(FoodAutoSuggestCallback callback) {
        this.callback = callback;
    }

    // Method to notify the host
    private void notifyFoodItemSaved(FoodItem foodItem) {
        if (callback != null) {
            callback.onFoodItemSaved(foodItem);
        }
    }

    // Constructors
    public FoodAutoSuggest(@NonNull Context context) {
        super(context);
        initiateAutoComplete(context);
    }
    public FoodAutoSuggest(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initiateAutoComplete(context);
    }
    public FoodAutoSuggest(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiateAutoComplete(context);
    }

    // Retreive FoodItem
    public FoodItem getSelectedItem (){
        return foodItem;
    }

    // Initiate Auto Complete with db list
    private void initiateAutoComplete(@NonNull Context context) {
        AutoCompleteTextView itemInput = this;
        db = new DatabaseHelper(context);
        AutoSuggestAdapter adapter = new AutoSuggestAdapter(context,
                android.R.layout.simple_spinner_dropdown_item);
        itemInput.setThreshold(2);
        itemInput.setAdapter(adapter);

        itemInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                foodItem = db.getFoodItem(itemInput.getText().toString());
                notifyFoodItemSaved(foodItem);
            }
        });

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