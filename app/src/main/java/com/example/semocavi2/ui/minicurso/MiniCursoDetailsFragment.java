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


import com.example.semocavi2.R;

import com.example.semocavi2.models.MiniCursoModel;
import com.example.semocavi2.ui.notifications.NotificationHelper;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;


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
        setupNotification(view, miniCurso);
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
            observeMiniCursoData(miniCursoId, view);
            mViewModel.getMinicusosById(miniCursoId).observe(getViewLifecycleOwner(), miniCurso -> {
                populateMiniCursoData(miniCurso, view);
                observePalestranteData(miniCurso.getInstrutorId(), view);

            });
        } else {
            Log.d("MiniCursoDetail", "miniCursoId nulo");
        }


        return view;
    }


    private void observeMiniCursoData(int miniCursoId, View view) {

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

    private void setupPalestranteInfoButton(View view, int palestranteId) {
        buttonInfoPalestrante.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pessoaId", palestranteId);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_palestrante, bundle);
        });
    }

    private void setupNotification(View view, MiniCursoModel miniCurso) {
        ImageView bellIcon = view.findViewById(R.id.imageView);
        bellIcon.setOnClickListener(v -> {
            Context context = getContext();
            NotificationHelper.createNotificationChannel(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.getChannelId())
                    .setSmallIcon(R.drawable.bell_notification)
                    .setContentTitle(miniCurso.getNome())
                    .setContentText("Programado para: " + miniCurso.getData())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());

            bellIcon.setOnClickListener(null);
        });
    }
}
