package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


// ChatGPT was used to help create this file

public class EditCityFragment extends DialogFragment {


    public interface EditCityDialogListener {
        void updateCity(City city, int position);
    }

    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditCityDialogListener");
        }
    }

    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putString("name", city.getName());
        args.putString("province", city.getProvince());
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText editCityName;
    private EditText editProvince;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_city, null);

        editCityName = view.findViewById(R.id.edit_text_city_text);
        editProvince = view.findViewById(R.id.edit_text_province_text);


        // Sets name and province of arguments passed into fragment on creation

        editCityName.setText(getArguments().getString("name"));
        editProvince.setText(getArguments().getString("province"));


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvince.getText().toString();
                    int position = getArguments().getInt("position");

                    City updatedCity = new City(cityName, provinceName);
                    listener.updateCity(updatedCity, position);
                })
                .create();
    }

}
