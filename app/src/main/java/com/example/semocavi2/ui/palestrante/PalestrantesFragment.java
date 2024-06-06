package com.example.semocavi2.ui.palestrante;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semocavi2.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

public class PalestrantesFragment extends Fragment {

    private TextView bioPalestranteTextView, nomePalestranteTextView;
    private ImageView palestranteImagemView;
    private PalestrantesViewModel pViewModel;

// setando a toolbar
    private void setupToolbar(View view) {
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);


        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_minicursos_details);
            }

            });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestrantes, container, false);

        // Inicialização das views e ViewModel
        initializeViews(view);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);

        // Configuração da Toolbar
        setupToolbar(view);

        // Verificação se há argumentos passados
        if (getArguments() != null && getArguments().containsKey("pessoaId")) {
            int instrutorId = getArguments().getInt("pessoaId");
            pViewModel.getPalestraById(instrutorId).observe(getViewLifecycleOwner(), palestrante -> {
                    // Configuração dos dados do palestrante nas views
                    nomePalestranteTextView.setText(palestrante.getNome());
                    bioPalestranteTextView.setText(palestrante.getBio());
//                    Picasso.get().load(palestrante.getFotoUrl()).into(palestranteImagemView);
                // test de imagem com o picasso com link valido
                String picassoImageUrl = "https://cdn2.thecatapi.com/images/3t0.jpg";
                Picasso.get().load(picassoImageUrl).into(palestranteImagemView);
            });
        } else {
            Log.d("palestrante ", "No instructor ID found in arguments");
        }
        return view;
    }

    // Método para inicializar as views
    private void initializeViews(View view) {
        bioPalestranteTextView = view.findViewById(R.id.bioInstrutorDetails);
        nomePalestranteTextView = view.findViewById(R.id.nomeInstrutorDetails);
        palestranteImagemView = view.findViewById(R.id.imagemInstrutor);
    }
}
