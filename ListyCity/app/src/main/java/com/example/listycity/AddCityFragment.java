package com.example.listycity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city);
        void onDialogCancelled();

    }

    private AddCityDialogListener listener;
    private City city; // city being edited (null if add)
    private boolean isEdit = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddCityDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (getArguments() != null) {
            city = (City) getArguments().getSerializable("city");
            if (city != null) {
                isEdit = true;
                editCityName.setText(city.getName());
                editProvinceName.setText(city.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit City" : "Add City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (dialog, which) -> {

                    String name = editCityName.getText().toString();
                    String province = editProvinceName.getText().toString();

                    if (isEdit) {
                        city.setName(name);
                        city.setProvince(province);
                        listener.editCity(city);
                    } else {
                        listener.addCity(new City(name, province));
                    }
                })
                .create();
    }

    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }
}
