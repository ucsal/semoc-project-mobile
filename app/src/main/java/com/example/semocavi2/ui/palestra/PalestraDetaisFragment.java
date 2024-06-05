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
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.ui.notifications.NotificationHelper;
import com.example.semocavi2.ui.palestrante.PalestrantesViewModel;
import com.google.android.material.appbar.MaterialToolbar;

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
        setupNotification(view, palestra);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palestra_detais, container, false);
        initializeViews(view);
        setupToolbar(view);

        plViewModel = new ViewModelProvider(requireActivity()).get(PalestraViewModel.class);
        pViewModel = new ViewModelProvider(requireActivity()).get(PalestrantesViewModel.class);

        if (getArguments() != null && getArguments().containsKey("palestraId")) {
            int palestraId = getArguments().getInt("palestraId");
            plViewModel.getPalestraById(palestraId).observe(getViewLifecycleOwner(), palestra -> {
                populatePalestraData(palestra, view);
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

    private void setupNotification(View view, PalestraModel palestra) {
        ImageView bellIcon = view.findViewById(R.id.imageView);
        bellIcon.setOnClickListener(v -> {
            Context context = getContext();
            NotificationHelper.createNotificationChannel(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.getChannelId())
                    .setSmallIcon(R.drawable.bell_notification)
                    .setContentTitle(palestra.getNome())
                    .setContentText("Programado para: " + palestra.getData())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());

            bellIcon.setOnClickListener(null);
        });
    }
}
