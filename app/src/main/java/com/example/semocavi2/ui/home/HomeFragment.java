package com.example.semocavi2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.semocavi2.R;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.databinding.FragmentHomeBinding;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.repo.PalestranteRepository;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.ui.minicurso.MiniCursoFragment;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);




        ImageView minicursos = view.findViewById(R.id.navigation_minicursos);
        ImageView palestras = view.findViewById(R.id.navigation_palestras);


        palestras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
//                navController.popBackStack();

                navController.navigate(R.id.navigation_palestras);
            }
        });
        minicursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                // esse cara limpa a stack de navegacao e dps muda para o minicuros fragment, dessa forma a gente pode voltar para o hoem sem pr
//                navController.popBackStack();
                navController.navigate(R.id.navigation_minicursos);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}