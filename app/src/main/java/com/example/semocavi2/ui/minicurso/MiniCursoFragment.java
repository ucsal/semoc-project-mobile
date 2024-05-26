package com.example.semocavi2.ui.minicurso;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.databinding.FragmentMiniCursoBinding;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.service.SemocApiService;
import com.google.android.material.appbar.MaterialToolbar;

public class MiniCursoFragment extends Fragment {

    private FragmentMiniCursoBinding binding;

    private MiniCursoViewModel mViewModel;
    private MiniCursoAdapter adapter;

    public static MiniCursoFragment newInstance() {
        return new MiniCursoFragment();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso, container, false);


        // n sei pq agora ta funcionando kkkkkkkkk
        
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada quando o ícone de navegação for clicado
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_home);
            }
        });



        // Inicialize o RecyclerView e o Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_minicursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MiniCursoAdapter();
        recyclerView.setAdapter(adapter);
        SemocAppDB database = SemocAppDB.getInstance(requireContext());
        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        MiniCursosDao miniCursosDao = database.minicursoDao();
        MiniCursoRepository repository = new MiniCursoRepository(semocApiService, miniCursosDao);

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

        // Observar o LiveData dos minicursos
        mViewModel.getMinicursos().observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
            Log.d("Database", "Minicursos carregados: " + minicursos.size());

        });

        return view;
    }
}
