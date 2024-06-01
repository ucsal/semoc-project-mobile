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

import com.example.semocavi2.ui.notifications.NotificationHelper;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MiniCursoDetailsFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private MiniCursoViewModel mViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomeInstrutorTextView, dataTextView, horaTextView, bioTextView;
    private Button buttonInfoPalestrante;

    private ImageView bellIcon;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_curso_details, container, false);
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
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_minicursos);
            }
        });


        // esse cara esta inicializando as views models relacionados aos fragments, os dados ja foram pegos na main activity
        mViewModel = new ViewModelProvider(requireActivity()).get(MiniCursoViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);
// colocando os dados no main fragment
        if (getArguments() != null && getArguments().containsKey("miniCursoId")) {
            int miniCursoId = getArguments().getInt("miniCursoId");
            mViewModel.getMinicusosById(miniCursoId).observe(getViewLifecycleOwner(), miniCurso -> {
                if (miniCurso != null) {
                    titleTextView.setText(miniCurso.getNome());
                    descricaoTextView.setText(miniCurso.getDescricao());
                    temaTextView.setText(miniCurso.getTema());
                    localTextView.setText(miniCurso.getLocal());
                    nivelTextView.setText(String.format("Nivel: %s", miniCurso.getNivel()));
                    dataTextView.setText(String.format("Data: %s", miniCurso.getData()));
                    horaTextView.setText(String.format("Hora: %s", miniCurso.getHora()));
                    Log.d("palestrante id ", "" + miniCurso.getInstrutorId());

                    bellIcon = view.findViewById(R.id.imageView);
                    bellIcon.setOnClickListener(new
                                                        View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Context context = getContext();
                                                                NotificationHelper.createNotificationChannel(context);

                                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.getChannelId())
                                                                        .setSmallIcon(R.drawable.bell_notification)
                                                                        .setContentTitle(miniCurso.getNome())
                                                                        .setContentText("programado para: " + miniCurso.getData())
                                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                                                                        .setAutoCancel(true);

                                                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                                notificationManager.notify(1, builder.build());


                                                            }
                                                        });


                    pViewModel.getPalestraById(miniCurso.getInstrutorId()).observe(getViewLifecycleOwner(), palestrante -> {
//Se nao tiver id do palestrante, eu coloco o 1 pra representar que e parte do comite de organizacao da semoc, e vai continuar a funcionar mesmo se o professor atualizar o json
//deixo o botao invisivel caso ele esteja nulo
                        try {
                            nomeInstrutorTextView.setText(palestrante.getNome());
                            bioTextView.setText(palestrante.getBio());


                        } catch (NullPointerException e) {
                            nomeInstrutorTextView.setText("Instrutor Ainda nao Definido");
                            bioTextView.setText("");
                            buttonInfoPalestrante.setVisibility(View.GONE);


                        }
                        buttonInfoPalestrante.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("pessoaId", palestrante.getId());
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.navigation_palestrante, bundle);
                            }
                        });
                    });
                } else {
                    Log.d("MiniCursoDetail", "Minicurso not found");
                }
            });
        } else {
            Log.d("MiniCursoDetail", "miniCursoId not found in arguments");
        }


        return view;
    }

    private void sendNotification() {

    }
}
