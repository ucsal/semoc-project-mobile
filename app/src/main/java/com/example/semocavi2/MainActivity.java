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
    private static final String CHANNEL_ID = "channel_id";
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

        checkAndRequestPermissions();
        createNotificationChannel();




        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

// esses caras sao estao sendo configurados apenas para a botton navbar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_information, R.id.navigation_notifications, R.id.navigation_minicursos, R.id.navigation_minicursos_details, R.id.navigation_palestrante)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        SemocApiService semocApiService = RetrofitClient.getClient().create(SemocApiService.class);
        database = SemocAppDB.getInstance(this);
        miniCursosDao = database.minicursoDao();
        palestranteDao = database.palestranteDao();
        palestraDao = database.palestraDao();
        miniCursoRepository = new MiniCursoRepository(semocApiService, miniCursosDao);
        palestranteRepository = new PalestranteRepository(semocApiService, palestranteDao);
        palestraRepository = new PalestraRepository(semocApiService,palestraDao);

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
        plViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(PalestraViewModel.class)) {
                    return (T) new PalestraViewModel(palestraRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(PalestraViewModel.class);

        plViewModel.getPalestras();



    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal de Notificação";
            String description = "Descrição do Canal";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


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