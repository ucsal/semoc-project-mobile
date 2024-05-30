package com.example.semocavi2.ui.minicurso;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semocavi2.R;
import com.example.semocavi2.adapters.MiniCursoAdapter;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.service.SemocApiService;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.Calendar;

public class MiniCursoFragment extends Fragment {
    private EditText editTextDate;
    private MiniCursoViewModel mViewModel;
    private MiniCursoAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_minicursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MiniCursoAdapter();
        recyclerView.setAdapter(adapter);

        editTextDate = view.findViewById(R.id.editTextDate);

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
                        filterMinicursosByDate(selectedDateToQuery);
                    }, mYear, mMonth, mDay);

            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL, "Clear Date", (dialog, which) -> {
                editTextDate.setText("");
                dialog.dismiss();
                loadAllMinicursos();
            });
            datePickerDialog.show();
        });


        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_home);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mViewModel = new ViewModelProvider(requireActivity()).get(MiniCursoViewModel.class);
        mViewModel.getMinicursos().observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
        });

        adapter.setOnItemClickListener(miniCurso -> {
            Bundle bundle = new Bundle();
            bundle.putInt("miniCursoId", miniCurso.getId());
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_minicursos_details, bundle);
        });

        return view;
    }

    private void filterMinicursosByDate(String selectedDate) {
        mViewModel.getMinicursosByDate(selectedDate).observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
            Log.d("Database", "Minicursos filtrados por data: " + minicursos.size());
        });
    }

    private void loadAllMinicursos() {
        mViewModel.getMinicursos().observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
            Log.d("Database", "Todos os minicursos carregados: " + minicursos.size());
        });
    }
}
