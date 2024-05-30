package com.example.semocavi2.ui.minicurso;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.semocavi2.R;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.repo.PalestraRepository;
import com.example.semocavi2.repo.PalestranteRepository;
import com.example.semocavi2.service.SemocApiService;
import com.google.android.material.appbar.MaterialToolbar;


public class MiniCursoDetailsFragment extends Fragment {

    private MiniCursoViewModel mViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView;
    private MaterialToolbar materialToolbar;
    private SemocApiService semocApiService;
    private MiniCursosDao miniCursosDao;
    private SemocAppDB database;

    private PalestranteDao palestranteDao;
    private PalestranteRepository palestraRepository;
    private MiniCursoRepository repository;
    private TextView descriptionTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso_details, container, false);
        materialToolbar = view.findViewById(R.id.materialToolbar);
        titleTextView = view.findViewById(R.id.title);
        descricaoTextView = view.findViewById(R.id.descricaoTextView);
        temaTextView = view.findViewById(R.id.tema);
        localTextView = view.findViewById(R.id.local);
        nivelTextView = view.findViewById(R.id.nivel);

        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
            navController.navigate(R.id.navigation_minicursos);
        });
// mds do ceu q coisa dificil eu devo ser mt burro
        database = SemocAppDB.getInstance(requireContext());
        semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        miniCursosDao = database.minicursoDao();
        palestranteDao = database.palestranteDao();
        repository = new MiniCursoRepository(semocApiService, miniCursosDao);



        palestraRepository = new PalestranteRepository(semocApiService, palestranteDao);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MiniCursoViewModel.class)) {
                    return (T) new MiniCursoViewModel(repository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(MiniCursoViewModel.class);
// essa maluquice deu certo
        if ((getArguments() != null ? getArguments().getInt("miniCursoId") : null) != null) {
            mViewModel.getMinicusosById((getArguments() != null ? getArguments().getInt("miniCursoId") : null)).observe(getViewLifecycleOwner(), miniCurso -> {
                if (miniCurso != null) {
                    titleTextView.setText(miniCurso.getNome());
                    descricaoTextView.setText(miniCurso.getDescricao());
                    temaTextView.setText(miniCurso.getTema());
                    localTextView.setText(miniCurso.getLocal());
                    nivelTextView.setText(miniCurso.getNivel());


                    ;
                } else {
                    Log.d("MiniCursoDetail", "Minicurso not found");
                }
            });
        }

        return view;
    }
}
