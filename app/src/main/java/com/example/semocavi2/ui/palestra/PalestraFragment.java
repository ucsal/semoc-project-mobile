package com.example.semocavi2.ui.palestra;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.semocavi2.R;
import com.example.semocavi2.adapters.MiniCursoAdapter;

import com.example.semocavi2.adapters.PalestraAdapter;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;

public class PalestraFragment extends Fragment {

    private EditText editTextDate;
    private PalestraViewModel pViewModel;

    private PalestraAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestra, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_palestras);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PalestraAdapter();
        recyclerView.setAdapter(adapter);
        editTextDate = view.findViewById(R.id.editTextDate);

        pViewModel = new ViewModelProvider(requireActivity()).get(PalestraViewModel.class);
        pViewModel.getPalestras().observe(getViewLifecycleOwner(), palestras -> {
            adapter.setPalestraList(palestras);
        });


        editTextDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        @SuppressLint("DefaultLocale") String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                        @SuppressLint("DefaultLocale") String selectedDateToQuery = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        Log.d("data value", selectedDateToQuery);

                        editTextDate.setText(selectedDate);
                        pViewModel.getPalestrasByData(selectedDateToQuery).observe(getViewLifecycleOwner(), palestras -> {
                            adapter.setPalestraList(palestras);
                            Log.d("Database", "Minicursos filtrados por data: " + palestras.size());
                        });
                    }, mYear, mMonth, mDay);
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL, "Clear Date", (dialog, which) -> {
                editTextDate.setText("");
                dialog.dismiss();
                pViewModel.getPalestras().observe(getViewLifecycleOwner(), palestras -> {
                    adapter.setPalestraList(palestras);
                    Log.d("Database", "Todos os minicursos carregados: " + palestras.size());
                });
            });
            datePickerDialog.show();
        });



        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_home);
                navController.popBackStack(R.id.navigation_palestras_details, true);

        });

        adapter.setOnItemClickListener(palestra -> {
            Bundle bundle = new Bundle();
            bundle.putInt("palestraId", palestra.getId());

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_palestras_details,  bundle);
        });


        return view;


    }


}