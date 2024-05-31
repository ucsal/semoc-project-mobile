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

    private String imageUrl = "https://www.google.com.br/imgres?q=macaco&imgurl=https%3A%2F%2Fs2-g1.glbimg.com%2Fl7fUkvdovxaODjM-7_LKacF-pU4%3D%2F0x0%3A1700x1065%2F1008x0%2Fsmart%2Ffilters%3Astrip_icc()%2Fs3.glbimg.com%2Fv1%2FAUTH_59edd422c0c84a879bd37670ae4f538a%2Fphotos%2Fapis%2Fb03aa813eaaa4e059f069c843d77415a%2Fselfie.jpg&imgrefurl=https%3A%2F%2Fg1.globo.com%2Fnatureza%2Fnoticia%2Fmacaco-da-selfie-pode-desaparecer-na-indonesia-por-causa-de-sua-carne.ghtml&docid=2jfR-GTnebwXlM&tbnid=XFh0Zv9Wzn0YFM&vet=12ahUKEwj41bjZh7eGAxXdLrkGHbKHDlsQM3oECBQQAA..i&w=1008&h=631&hcb=2&ved=2ahUKEwj41bjZh7eGAxXdLrkGHbKHDlsQM3oECBQQAA";


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


        if (getArguments() != null && getArguments().containsKey("instrutorId")) {
            int instrutorId = getArguments().getInt("instrutorId");
            pViewModel.getPalestraById(instrutorId).observe(getViewLifecycleOwner(), palestrante -> {
                if (palestrante != null) {

                    nomePalestramteTextView.setText(palestrante.getNome());
                    bioPalestranteTextView.setText(palestrante.getBio());

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