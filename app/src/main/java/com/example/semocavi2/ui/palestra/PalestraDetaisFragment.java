package com.example.semocavi2.ui.palestra;

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
import com.example.semocavi2.ui.minicurso.MiniCursoViewModel;
import com.example.semocavi2.ui.notifications.NotificationHelper;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class PalestraDetaisFragment extends Fragment {

    private PalestrantesViewModel pViewModel;
    private PalestraViewModel plViewModel;
    private TextView titleTextView, descricaoTextView, temaTextView, nivelTextView, localTextView, nomePalestranteTextView, dataTextView, horaTextView, bioTextView;
    private Button buttonInfoPalestrante;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestra_detais, container, false);
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
        MaterialToolbar materialToolbar = view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                navController.navigate(R.id.navigation_palestras);

            }
        });

        // esse cara esta inicializando as views models relacionados aos fragments, os dados ja foram pegos na main activity
        plViewModel = new ViewModelProvider(requireActivity()).get(PalestraViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);

        // colocando os dados no main fragment
        if (getArguments() != null && getArguments().containsKey("palestraId")) {
            plViewModel.getPalestraById(getArguments().getInt("palestraId")).observe(getViewLifecycleOwner(), palestra -> {
                if (palestra != null) {
                    titleTextView.setText(palestra.getNome());
                    descricaoTextView.setText(palestra.getDescricao());
                    temaTextView.setText(palestra.getTema());
                    localTextView.setText(palestra.getLocal());
                    nivelTextView.setText(String.format("Nivel: %s", palestra.getNivel()));
                    dataTextView.setText(String.format("Data: %s", palestra.getData()));
                    horaTextView.setText(String.format("Hora: %s", palestra.getHora()));

                    Log.d("palestrante id ", "" + palestra.getPalestrante_id());


                    // se tu queria algomais complexo mario, deveria ter deixado mais especifico na atividade😎
                    ImageView bellIcon = view.findViewById(R.id.imageView);
                    bellIcon.setOnClickListener(new
                                                        View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Context context = getContext();
                                                                NotificationHelper.createNotificationChannel(context);

                                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.getChannelId())
                                                                        .setSmallIcon(R.drawable.bell_notification)
                                                                        .setContentTitle(palestra.getNome())
                                                                        .setContentText("programado para: " + palestra.getData())
                                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                                                                        .setAutoCancel(true);

                                                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                                notificationManager.notify(1, builder.build());
// aqui eu abre espaco para salvar os minicrusos selecionados dentro de um banco de dados, fazer a verificacao de minicursos disponiveis para salvar e etc. mas como nao foi pedido na atividade vou deixar so isso aqui mesmo

                                                                // so pra nao deixarf clicar dnv

                                                                bellIcon.setOnClickListener(null);

                                                            }

                                                        });

                    pViewModel.getPalestraById(palestra.getPalestrante_id()).observe(getViewLifecycleOwner(), palestrante -> {
                        // Se nao tiver id do palestrante, eu coloco o 1 pra representar que e parte do comite de organizacao da semoc, e vai continuar a funcionar mesmo se o professor atualizar o json
                        // deixo o botao invisivel caso ele esteja nulo
                        try {
                            nomePalestranteTextView.setText(palestrante.getNome());
                            bioTextView.setText(palestrante.getBio());
                        } catch (NullPointerException e) {
                            nomePalestranteTextView.setText("Palestrante Ainda nao Definido");
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
                    Log.d("PalestraDetail", "Palestra not found");
                }
            });
        } else {
            Log.d("PalestraDetail", "palestraId not found in arguments");
        }

        return view;
    }
}
