//@emanuel3queijos
package com.example.semocavi2.ui.pessoa;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semocavi2.R;
import com.example.semocavi2.databinding.FragmentMiniCursoBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class PessoaFragment extends Fragment {

    private FragmentMiniCursoBinding binding;

    private PessoaViewModel mViewModel;

    public static PessoaFragment newInstance() {
        return new PessoaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pessoa, container, false);

        // nao sei se essa e a melhor forma de se fazer isso, mas continuarei
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada quando o ícone de navegação for clicado
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_home);
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