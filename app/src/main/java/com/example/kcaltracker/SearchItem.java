package com.example.kcaltracker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.kcaltracker.model.DatabaseHelper;
import com.example.kcaltracker.model.AutoSuggestAdapter;
import com.example.kcaltracker.model.FoodItem;

public  class SearchItem extends Fragment implements FoodAutoSuggest.FoodAutoSuggestCallback {
    private  DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FoodAutoSuggest itemInput = view.findViewById(R.id.Search_Input_FoodItem);
        itemInput.setFoodAutoSuggestCallback(new FoodAutoSuggest.FoodAutoSuggestCallback() {
            @Override
            public void onFoodItemSaved(FoodItem foodItem) {
                Log.d("Test1", foodItem.descricao);
            }
        });
    }

    @Override
    public void onFoodItemSaved(FoodItem foodItem) {

    }
}