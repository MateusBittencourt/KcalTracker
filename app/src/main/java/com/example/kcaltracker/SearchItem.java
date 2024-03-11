package com.example.kcaltracker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.kcaltracker.model.DatabaseHelper;
import com.example.kcaltracker.model.AutoSuggestAdapter;

public class SearchItem extends Fragment {
    private  DatabaseHelper db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*AutoCompleteTextView itemInput = view.findViewById(R.id.Search_Input_FoodItem);

        db = new DatabaseHelper(getActivity());
        itemInput.setThreshold(2);

        AutoSuggestAdapter adapter = new AutoSuggestAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        itemInput.setAdapter(adapter);
        itemInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {
                adapter.setData(db.getDescricao(itemInput.getText().toString()));
            }
        });*/
    }
}