//@emanuel3queijos

package com.example.semocavi2.ui.minicurso;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semocavi2.R;
import com.example.semocavi2.adapters.MiniCursoAdapter;
import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.databinding.FragmentMiniCursoBinding;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.service.SemocApiService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;

public class MiniCursoFragment extends Fragment {
    private TextView textView;

    private EditText editTextDate;

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
//        EditText editTextDate = view.findViewById(R.id.editTextDate);

        // ajeitando o RecyclerView e o Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_minicursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MiniCursoAdapter();


        recyclerView.setAdapter(adapter);
        editTextDate = view.findViewById(R.id.editTextDate);
        textView = view.findViewById(R.id.textView);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
// configura o data picker e coloca ja uma maskara marota, crio outra para poder fazer as querys, ja q as datas vem com o ofrmato dd-mm-aaaa, pq eles n usam slash logo. sim  aquele cara do guns in roses
           // adicionei tb o obtao de clear date,
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Format date as dd/mm/yyyy
                                @SuppressLint("DefaultLocale") String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                                // real n sei se vai ter problema se eu dixar do outro formato.
                                @SuppressLint("DefaultLocale") String selectedDateToQuery = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                                Log.d("data value",selectedDateToQuery);

                                editTextDate.setText(selectedDate);
                                filterMinicursosByDate(selectedDateToQuery);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL, "Clear Date", (dialog, which) -> {
                    editTextDate.setText("");
                    dialog.dismiss();

                    loadAllMinicursos();

                });
                datePickerDialog.show();





            }
        });

        // essa era uma solucao mas vou deixar aqui pq eu acho q vou usar ainda
//        editTextDate.addTextChangedListener(new TextWatcher() {
//            boolean isUpdating;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int after) {
//                if (isUpdating) {
//                    isUpdating = false;
//                    return;
//                }
//
//                boolean hasMask = s.toString().indexOf('/') > -1;
//                String str = s.toString().replaceAll("[/]", "");
//                if (after > before) {
//                    if (str.length() > 1) {
//                        editTextDate.requestFocus();
//                        str = str.substring(0, 2) + '/' + str.substring(2);
//                    }
//                    if (str.length() > 5) {
//                        str = str.substring(0, 5) + '/' + str.substring(5);
//                    }
//                    isUpdating = true;
//                    editTextDate.setText(str);
//                    editTextDate.setSelection(editTextDate.getText().length());
//                } else {
//                    isUpdating = true;
//                    editTextDate.setText(str);
//                    editTextDate.setSelection(Math.max(0, Math.min(hasMask ? start - before : start, str.length())));
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//// vou fazer essa validacao n
////                Validador validador = new Validador();
////                String data = editTextDate.getText().toString();
////                boolean opt = validador.validarData(data);
////
////                if (opt == false && data.length() >=9) {
////                    Toast.makeText(getContext(), "Data inválida!", Toast.LENGTH_LONG).show();
////                }
//
//            }
//        });

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




        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


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

    private void filterMinicursosByDate(String selectedDate) {
        mViewModel.getMinicursosByDate(selectedDate).observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
            Log.d("Database", "Minicursos filtrados por data: " + minicursos.size());
        });
    }

    private void loadAllMinicursos() {
        mViewModel.getMinicursos().observe(getViewLifecycleOwner(), minicursos -> {
            adapter.setMinicursoList(minicursos);
            Log.d("Database", "Todos os minicursos carregados: " + minicursos.size());
        });
    }
}
// emanuel gameplays, queria muito ta jogando lol agora mas n posso