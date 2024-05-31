package com.example.semocavi2.ui.palestra;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.semocavi2.R;
import com.example.semocavi2.adapters.MiniCursoAdapter;
import com.example.semocavi2.adapters.PalestraAdapter;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.databinding.FragmentMiniCursoBinding;
import com.example.semocavi2.databinding.FragmentPalestraBinding;
import com.example.semocavi2.repo.PalestraRepository;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.google.android.material.appbar.MaterialToolbar;

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
        View view = inflater.inflate(R.layout.fragment_palestra, container, false);


        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//  acho q vai seguir essa ordem, quando eu tiver um botao que leve para a
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_home);

            }
        });

        SemocAppDB database = SemocAppDB.getInstance(requireContext());
        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        PalestraDao palestraDao = database.palestraDao();

        PalestraRepository repository = new PalestraRepository(semocApiService, palestraDao);

        return view;


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}