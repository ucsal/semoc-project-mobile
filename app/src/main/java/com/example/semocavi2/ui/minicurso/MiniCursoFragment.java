package com.example.semocavi2.ui.minicurso;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;

import java.util.List;

public class MiniCursoFragment extends Fragment {

    private MiniCursoViewModel mViewModel;
//    private RecyclerView recyclerView;
    private List<MiniCursoViewModel> minicursoList;

    public static MiniCursoFragment newInstance() {
        return new MiniCursoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_curso, container, false);
    }



}