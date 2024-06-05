package com.example.semocavi2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.repo.PalestraRepository;
import com.example.semocavi2.repo.PalestranteRepository;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.example.semocavi2.ui.palestra.PalestraViewModel;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.semocavi2.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
// esse cara foi de um template do android studio
private static final int PERMISSION_REQUEST_CODE = 1;
    private ActivityMainBinding binding;

    private MiniCursosDao miniCursosDao;
    private PalestranteDao palestranteDao;
    private PalestraDao palestraDao;
    private MiniCursoRepository miniCursoRepository;
    private PalestranteRepository palestranteRepository;
    private PalestraRepository palestraRepository;
    private SemocAppDB database;
    private MiniCursoViewModel mViewModel;
    private PalestrantesViewModel pViewModel;
    private PalestraViewModel plViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // aesse bad boy aqui que pede se pode mandar notificacao
        checkAndRequestPermissions();
        // escondendo a navbar padrao do app, real n me lembro como que eu tinha tirado no outro projeto mas n foi com isso
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // setando o root layout
        setContentView(binding.getRoot());
        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        database = SemocAppDB.getInstance(this);

        // inicializando os daos com a instancia das tabelas dentro do banco
        miniCursosDao = database.minicursoDao();
        palestranteDao = database.palestranteDao();
        palestraDao = database.palestraDao();


        //inicializando os repositorios
        miniCursoRepository = new MiniCursoRepository(semocApiService, miniCursosDao);
        palestranteRepository = new PalestranteRepository(semocApiService, palestranteDao);
        palestraRepository = new PalestraRepository(semocApiService,palestraDao);

        initializeViewModels();


    }
    // esses dois methodos seguintes foram criados apenas para retirar a redundancia na iniciacao de um view model

    private void initializeViewModels() {
        mViewModel = new ViewModelProvider(this, createViewModelFactory(new MiniCursoViewModel(miniCursoRepository)))
                .get(MiniCursoViewModel.class);
        mViewModel.getMinicursos();

        pViewModel = new ViewModelProvider(this, createViewModelFactory(new PalestrantesViewModel(palestranteRepository)))
                .get(PalestrantesViewModel.class);
        pViewModel.getPalestrantes();

        plViewModel = new ViewModelProvider(this, createViewModelFactory(new PalestraViewModel(palestraRepository)))
                .get(PalestraViewModel.class);
        plViewModel.getPalestras();
    }

    private ViewModelProvider.Factory createViewModelFactory(ViewModel viewModel) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(viewModel.getClass())) {
                    return (T) viewModel;
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };
    }


    // aesse bad boy aqui que pede se pode mandar notificacao
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, continue com a operação
            } else {
                // Permissão negada, informe ao usuário que a permissão é necessária
                Toast.makeText(this, "A permissão de notificações é necessária para esta funcionalidade.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}

























































































//corre e diz pra ela que a casaaaa ta vazia e as cores que ela via desconhecem as tuas maaaoxxx ii o sol esta no abandono que nao te aceita como donooouuu e te banhaaaa no colch. iiiiii aaaaaaaaa tua viaaa se resume em sintir o seu perfuuumeeee