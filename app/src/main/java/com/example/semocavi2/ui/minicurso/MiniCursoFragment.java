//@emanuel3queijos

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
        // vou inflar o layour dps de ajeitar tudo, acho que pode quebrar alguma coisa se der errado
        View view = inflater.inflate(R.layout.fragment_mini_curso, container, false);



        // n sei pq agora ta funcionando kkkkkkkkk, mas estava crashando o app antes, deve ser macumba

        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada quando o ícone de navegação for clicado

                NavController navController = Navigation.findNavController(v);
               //esse cara seria usado para deixa selecionar o botao home dps pra voltar, n sei se eu deixo ele
                navController.popBackStack();
                navController.navigate(R.id.navigation_home);
            }
        });



        // ajeitando o RecyclerView e o Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_minicursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MiniCursoAdapter();
        recyclerView.setAdapter(adapter);
        // ajeitando o db, passando qual fragmento estou como context
        SemocAppDB database = SemocAppDB.getInstance(requireContext());
        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        MiniCursosDao miniCursosDao = database.minicursoDao();
        // esse bad boy aqui passa o servic e com  client do retrofit, essa parte eui ja peguei pronto ent n entendi mt bem como funfa, mas acho q ele passa os metdods de busca e save em banco pro repositorio, tenho quase certeza disso na real
        MiniCursoRepository repository = new MiniCursoRepository(semocApiService, miniCursosDao);


        // esse cara vai ajudar o fragment a acesse e observe os dados do repository. mais duvidas joga no chat gpt, ele q fez isso aqui, na real eu n sei se tirar ele qubra alguma coisa mas vou deixar como ta. n tira meu ponto mario pf
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
// emanuel gameplays, queria muito ta jogando lol agora mas n posso