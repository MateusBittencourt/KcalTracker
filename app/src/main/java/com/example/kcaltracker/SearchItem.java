package com.example.kcaltracker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.kcaltracker.model.DatabaseHelper;

public class SearchItem extends Fragment {
    private  DatabaseHelper db;

    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AutoCompleteTextView itemInput = view.findViewById(R.id.SearchItem_Input);

        db = new DatabaseHelper(getActivity());
        itemInput.setThreshold(2);
        itemInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {
                adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        db.getDescricao(itemInput.getText().toString()));
                itemInput.setAdapter(adapter);
            }
        });
    }
}