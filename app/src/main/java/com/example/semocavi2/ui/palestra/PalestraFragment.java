package com.example.semocavi2.ui.palestra;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.google.android.material.appbar.MaterialToolbar;

public class PalestraFragment extends Fragment {

    private EditText editTextDate;
    private PalestraViewModel mViewModel;
    public static PalestraFragment newInstance() {
        return new PalestraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestra, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_palestras);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MiniCursoAdapter adapter = new MiniCursoAdapter();
        recyclerView.setAdapter(adapter);


        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_home);
            }
        });


        return view;


    }


}