package com.example.semocavi2.ui.palestra;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;
import com.example.semocavi2.adapters.MiniCursoAdapter;
import com.example.semocavi2.adapters.PalestraAdapter;
import com.example.semocavi2.databinding.FragmentMiniCursoBinding;
import com.example.semocavi2.databinding.FragmentPalestraBinding;

public class PalestraFragment extends Fragment {

    private FragmentPalestraBinding binding;

    private PalestraAdapter adapter;


    private PalestraViewModel mViewModel;

    public static PalestraFragment newInstance() {
        return new PalestraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_palestra, container, false);





    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}