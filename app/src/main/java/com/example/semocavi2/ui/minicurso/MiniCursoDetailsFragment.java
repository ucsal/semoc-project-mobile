package com.example.semocavi2.ui.minicurso;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.semocavi2.R;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.models.PalestranteModel;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.repo.PalestranteRepository;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class MiniCursoDetailsFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private MiniCursoViewModel mViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomeInstrutorTextView, dataTextView, horaTextView, bioTextView;
   private Button buttonInfoPalestrante;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso_details, container, false);
        titleTextView = view.findViewById(R.id.title);
        descricaoTextView = view.findViewById(R.id.descricaoTextView);
        temaTextView = view.findViewById(R.id.tema);
        localTextView = view.findViewById(R.id.local);
        nivelTextView = view.findViewById(R.id.nivel);
        nomeInstrutorTextView = view.findViewById(R.id.instrutorNome);
        dataTextView = view.findViewById(R.id.data);
        horaTextView = view.findViewById(R.id.hora);
        bioTextView = view.findViewById(R.id.instutorBio);
        buttonInfoPalestrante = view.findViewById(R.id.maisInfo);



        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_minicursos);
            }
        });


        // esse cara esta inicializando as views models relacionados aos fragments, os dados ja foram pegos na main activity
        mViewModel = new ViewModelProvider(requireActivity()).get(MiniCursoViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);
// colocando os dados no main fragment
        if (getArguments() != null && getArguments().containsKey("miniCursoId")) {
            int miniCursoId = getArguments().getInt("miniCursoId");
            mViewModel.getMinicusosById(miniCursoId).observe(getViewLifecycleOwner(), miniCurso -> {
                if (miniCurso != null) {
                    titleTextView.setText(miniCurso.getNome());
                    descricaoTextView.setText(miniCurso.getDescricao());
                    temaTextView.setText(miniCurso.getTema());
                    localTextView.setText(miniCurso.getLocal());
                    nivelTextView.setText(miniCurso.getNivel());
                    dataTextView.setText(miniCurso.getData());
                    horaTextView.setText(miniCurso.getHora());

                    Log.d("palestrante id ", "" + miniCurso.getInstrutorId());

                    pViewModel.getPalestraById(miniCurso.getInstrutorId()).observe(getViewLifecycleOwner(), palestrante -> {
//Se nao tiver id do palestrante, eu coloco o 1 pra representar que e parte do comite de organizacao da semoc

                        try {
                            nomeInstrutorTextView.setText(palestrante.getNome());
                            bioTextView.setText(palestrante.getBio());
                        }catch (NullPointerException e){
                            pViewModel.getPalestraById(1).observe(getViewLifecycleOwner(), organizacao -> {
                                nomeInstrutorTextView.setText(organizacao.getNome());
                                bioTextView.setText(organizacao.getBio());
                            });

                        }
                        buttonInfoPalestrante.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Bundle bundle = new Bundle();
                                bundle.putInt("instrutorId", palestrante.getId());
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.navigation_palestrante, bundle);
                            }
                        });
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
