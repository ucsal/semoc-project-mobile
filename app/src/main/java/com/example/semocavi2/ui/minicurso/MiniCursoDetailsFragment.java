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
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class MiniCursoDetailsFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private MiniCursoViewModel mViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomeInstrutorTextView;
    private MaterialToolbar materialToolbar;
    private SemocApiService semocApiService;
    private MiniCursosDao miniCursosDao;
    private SemocAppDB database;

    private PalestranteDao palestranteDao;
    private PalestranteRepository palestraRepository;
    private MiniCursoRepository repository;

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
        nomeInstrutorTextView = view.findViewById(R.id.nomeIstrutor);

        database = SemocAppDB.getInstance(requireContext());
        semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        miniCursosDao = database.minicursoDao();
        palestranteDao = database.palestranteDao();
        repository = new MiniCursoRepository(semocApiService, miniCursosDao);
        palestraRepository = new PalestranteRepository(semocApiService, palestranteDao);

        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
            navController.navigate(R.id.navigation_minicursos);
        });

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

        pViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(PalestrantesViewModel.class)) {
                    return (T) new PalestrantesViewModel(palestraRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(PalestrantesViewModel.class);

        if (getArguments() != null && getArguments().containsKey("miniCursoId")) {
            int miniCursoId = getArguments().getInt("miniCursoId");
            mViewModel.getMinicusosById(miniCursoId).observe(getViewLifecycleOwner(), miniCurso -> {
                if (miniCurso != null) {
                    titleTextView.setText(miniCurso.getNome());
                    descricaoTextView.setText(miniCurso.getDescricao());
                    temaTextView.setText(miniCurso.getTema());
                    localTextView.setText(miniCurso.getLocal());
                    nivelTextView.setText(miniCurso.getNivel());
                    Log.d("palestrante id ", "" + miniCurso.getInstrutorId());
                    pViewModel.getPalestraById(miniCurso.getInstrutorId()).observe(getViewLifecycleOwner(), palestrante -> {
                        nomeInstrutorTextView.setText(palestrante.getNome());
                        Log.d("Database", "Minicursos filtrados por data: " + palestrante);
                    });
                } else {
                    Log.d("MiniCursoDetail", "Minicurso not found");
                }
            });
        } else {
            Log.d("MiniCursoDetail", "miniCursoId not found in arguments");
        }

        return view;
    }
}
