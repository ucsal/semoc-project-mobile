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

    private TextView bioPalestranteTextView, nomePalestramteTextView;
    private ImageView palestranteImagemView;
    private PalestrantesViewModel pViewModel;



    public static PalestrantesFragment newInstance() {
        return new PalestrantesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestrantes, container, false);

        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);

bioPalestranteTextView = view.findViewById(R.id.bioInstrutorDetails);
nomePalestramteTextView = view.findViewById(R.id.nomeInstrutorDetails);
palestranteImagemView = view.findViewById(R.id.imagemInstrutor);
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_minicursos_details);
            }
        });


        if (getArguments() != null && getArguments().containsKey("pessoaId")) {
            int instrutorId = getArguments().getInt("pessoaId");
            pViewModel.getPalestraById(instrutorId).observe(getViewLifecycleOwner(), palestrante -> {
                if (palestrante != null) {

                    nomePalestramteTextView.setText(palestrante.getNome());
                    bioPalestranteTextView.setText(palestrante.getBio());
                    // isso aqui deve funcionar
                    Picasso.get().load(palestrante.getFotoUrl()).into(palestranteImagemView);

                    Log.d("palestrante id ", "" + palestrante.getId());

                } else {
                }
            });
        } else {
            Log.d("palestrante ", "instrutor not found in arguments");
        }
        return view;


    }


}