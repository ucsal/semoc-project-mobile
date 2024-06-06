package com.example.semocavi2.ui.palestra;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.semocavi2.R;
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.notificationWorker.NotificationWorker;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PalestraDetaisFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private PalestraViewModel plViewModel;

    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomePalestranteTextView, dataTextView, horaTextView, bioTextView;
    private Button buttonInfoPalestrante;

    private void setupToolbar(View view) {
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_palestras);
            }
        });
    }

    private void initializeViews(View view) {
        titleTextView = view.findViewById(R.id.title);
        descricaoTextView = view.findViewById(R.id.descricaoTextView);
        temaTextView = view.findViewById(R.id.tema);
        localTextView = view.findViewById(R.id.local);
        nivelTextView = view.findViewById(R.id.nivel);
        nomePalestranteTextView = view.findViewById(R.id.palestranteNome);
        dataTextView = view.findViewById(R.id.data);
        horaTextView = view.findViewById(R.id.hora);
        bioTextView = view.findViewById(R.id.palestranteBio);
        buttonInfoPalestrante = view.findViewById(R.id.maisInfo);

    }

    private void populatePalestraData(PalestraModel palestra, View view) {
        titleTextView.setText(palestra.getNome());
        descricaoTextView.setText(palestra.getDescricao());
        temaTextView.setText(palestra.getTema());
        localTextView.setText(palestra.getLocal());
        nivelTextView.setText(String.format("Nível: %s", palestra.getNivel()));
        dataTextView.setText(String.format("Data: %s", palestra.getData()));
        horaTextView.setText(String.format("Hora: %s", palestra.getHora()));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestra_detais, container, false);
        initializeViews(view);
        setupToolbar(view);

        plViewModel = new ViewModelProvider(requireActivity()).get(PalestraViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);


        if (getArguments() != null && getArguments().containsKey("palestraId")) {
            plViewModel.getPalestraById(         getArguments().getInt("palestraId")).observe(getViewLifecycleOwner(), palestra -> {

//acho que esse vai funcionar sla
                populatePalestraData(palestra, view);
                ImageView agendarPalestras = view.findViewById(R.id.agendarPalestra);
                if (palestra.isSchedule()) {
                    agendarPalestras.setImageResource(R.drawable.img_10);
                }else {
                    agendarPalestras.setOnClickListener(v -> {

                        plViewModel.setScheduleEvent(         getArguments().getInt("palestraId"));
                        long delay = calculateDelay(palestra.getData());
                        Data data = new Data.Builder()
                                .putString("title", palestra.getNome())
                                .putString("message", "Programado para: " + palestra.getData())
                                .build();

                        // Crie o WorkRequest
                        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                                .setInputData(data)
                                .build();

                        // Agende o WorkRequest
                        WorkManager.getInstance(getContext()).enqueue(notificationWork);

                    });

                }


                observePalestranteData(palestra.getPalestrante_id(), view);

            });
        } else {
            Log.d("PalestraDetails", "palestraId não encontrado nos argumentos");
        }
        return view;
    }

    private void observePalestranteData(int palestranteId, View view) {
        pViewModel.getPalestraById(palestranteId).observe(getViewLifecycleOwner(), palestrante -> {

            try {

                nomePalestranteTextView.setText(palestrante.getNome());
                bioTextView.setText(palestrante.getBio());
                setupPalestranteInfoButton(view, palestrante.getId());

            } catch (NullPointerException e) {
                nomePalestranteTextView.setText("Palestrante Ainda não Definido");
                bioTextView.setText("");
                buttonInfoPalestrante.setVisibility(View.GONE);
            }
        });
    }

    private void setupPalestranteInfoButton(View view, int palestranteId) {
        buttonInfoPalestrante.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pessoaId", palestranteId);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_palestrante, bundle);
        });
    }



    private long calculateDelay(String eventDate) {
        // eu poderia evitar fazer isso se eu convertesse a string para sdf quando eu recebesse os dados da api, mas nhe
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date eventDateTime = sdf.parse(eventDate);
            long currentTime = System.currentTimeMillis();

            // Ajustar para meia-noite do dia do evento
            Calendar eventCalendar = Calendar.getInstance();
            eventCalendar.setTime(eventDateTime);
            eventCalendar.set(Calendar.HOUR_OF_DAY, 0);
            eventCalendar.set(Calendar.MINUTE, 0);
            eventCalendar.set(Calendar.SECOND, 0);
            eventCalendar.set(Calendar.MILLISECOND, 0);

            return eventCalendar.getTimeInMillis() - currentTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
