package com.example.semocavi2.ui.minicurso;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
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
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.example.semocavi2.R;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.notificationWorker.NotificationWorker;
import com.example.semocavi2.ui.notifications.NotificationHelper;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MiniCursoDetailsFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private MiniCursoViewModel mViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomeInstrutorTextView, dataTextView, horaTextView, bioTextView;
    private Button buttonInfoPalestrante;


    //setando a toolbar
    private void setupToolbar(View view) {
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_minicursos);
            }
        });
    }

    //inicializando as views
    private void initializeViews(View view) {
        titleTextView = view.findViewById(R.id.title);
        descricaoTextView = view.findViewById(R.id.descricaoTextView);
        temaTextView = view.findViewById(R.id.tema);
        localTextView = view.findViewById(R.id.local);
        nivelTextView = view.findViewById(R.id.nivel);
        nomeInstrutorTextView = view.findViewById(R.id.instrutorNome);
        dataTextView = view.findViewById(R.id.data);
        horaTextView = view.findViewById(R.id.hora);
        bioTextView = view.findViewById(R.id.instutorBio);
        buttonInfoPalestrante = view.findViewById(R.id.maisInfo);

    }

    //populando as views
    private void populateMiniCursoData(MiniCursoModel miniCurso, View view) {
        titleTextView.setText(miniCurso.getNome());
        descricaoTextView.setText(miniCurso.getDescricao());
        temaTextView.setText(miniCurso.getTema());
        localTextView.setText(miniCurso.getLocal());
        nivelTextView.setText(String.format("Nível: %s", miniCurso.getNivel()));
        dataTextView.setText(String.format("Data: %s", miniCurso.getData()));
        horaTextView.setText(String.format("Hora: %s", miniCurso.getHora()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso_details, container, false);
        initializeViews(view);
        setupToolbar(view);

        // esse cara esta inicializando as views models relacionados aos fragments, os dados ja foram pegos na main activity
        mViewModel = new ViewModelProvider(requireActivity()).get(MiniCursoViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);
// colocando os dados no main fragment
        if (getArguments() != null && getArguments().containsKey("miniCursoId")) {
            int miniCursoId = getArguments().getInt("miniCursoId");
            mViewModel.getMinicusosById(miniCursoId).observe(getViewLifecycleOwner(), miniCurso -> {
                populateMiniCursoData(miniCurso, view);

                ImageView agendarMinicurso = view.findViewById(R.id.agendarMiniCurso);
                if (miniCurso.isSchedule()) {
                    agendarMinicurso.setImageResource(R.drawable.img_10);
                }else {
                    agendarMinicurso.setOnClickListener(v -> {

                        mViewModel.setScheduleEvent(         getArguments().getInt("miniCursoId"));
                        long delay = calculateDelay(miniCurso.getData());
                        Data data = new Data.Builder()
                                .putString("title", miniCurso.getNome())
                                .putString("message", "Programado para: " + miniCurso.getData())
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

                observePalestranteData(miniCurso.getInstrutorId(), view);
            });
        } else {
            Log.d("MiniCursoDetail", "miniCursoId nulo");
        }
        return view;
    }


    //aqui eu to verificando se os atributos do palestrante vem nulo, ele no caso nao vem nulo inteiro  por conta de alguma coisa maluco que eu fiz na hora de configurar o banco ent eu preciso forcar a exception a acontecer e tratar ela
    // se tiver nulo eu coloco que ainda nao foi definido um instrutor
    private void observePalestranteData(int instrutorId, View view) {
        pViewModel.getPalestraById(instrutorId).observe(getViewLifecycleOwner(), palestrante -> {
            try {
                nomeInstrutorTextView.setText(palestrante.getNome());
                bioTextView.setText(palestrante.getBio());
                setupPalestranteInfoButton(view, palestrante.getId());
            } catch (NullPointerException e) {
                nomeInstrutorTextView.setText("Instrutor Ainda nao Definido");
                bioTextView.setText("");
                buttonInfoPalestrante.setVisibility(View.GONE);
            }
        });
    }

    // eu to usando bundle mas acho que podeira ser intent, ou intent e so pra activitys sla
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
