package com.example.semocavi2;

import android.os.Bundle;
import android.util.Log;

import com.example.semocavi2.client.RetrofitClient;
import com.example.semocavi2.dao.MiniCursosDao;
import com.example.semocavi2.dao.PalestraDao;
import com.example.semocavi2.dao.PalestranteDao;
import com.example.semocavi2.database.SemocAppDB;
import com.example.semocavi2.repo.MiniCursoRepository;
import com.example.semocavi2.repo.PalestranteRepository;
import com.example.semocavi2.service.SemocApiService;
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.semocavi2.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
// esse cara foi de um template do android studio

    private ActivityMainBinding binding;

    private MiniCursosDao miniCursosDao;
    private PalestranteDao palestranteDao;
    private MiniCursoRepository miniCursoRepository;
    private PalestranteRepository palestranteRepository;
    private SemocAppDB database;

    private MiniCursoViewModel mViewModel;
    private PalestrantesViewModel pViewModel;

    private SemocApiService semocApiService;

    private NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
// esses caras sao estao sendo configurados apenas para a botton navbar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_information, R.id.navigation_notifications)
                .build();



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        database = SemocAppDB.getInstance(this);
        miniCursosDao = database.minicursoDao();
        palestranteDao = database.palestranteDao();
        miniCursoRepository = new MiniCursoRepository(semocApiService, miniCursosDao);
        palestranteRepository = new PalestranteRepository(semocApiService, palestranteDao);


        mViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MiniCursoViewModel.class)) {
                    return (T) new MiniCursoViewModel(miniCursoRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(MiniCursoViewModel.class);

        mViewModel.getMinicursos();


        pViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(PalestrantesViewModel.class)) {
                    return (T) new PalestrantesViewModel(palestranteRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(PalestrantesViewModel.class);


        pViewModel.getPalestrantes();





    }


//    @Override
//    public void onResume() {
//        super.onResume();
//
//        navController.popBackStack(R.id.navigation_home, false);
//    }

}