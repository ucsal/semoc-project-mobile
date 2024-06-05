package com.example.semocavi2.notificationWorker;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.semocavi2.R;
import com.example.semocavi2.ui.notifications.NotificationHelper;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Obtenha os dados necessários do Data
        String title = getInputData().getString("title");
        String message = getInputData().getString("message");

        // Crie a notificação
        NotificationHelper.createNotificationChannel(getApplicationContext());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NotificationHelper.getChannelId())
                .setSmallIcon(R.drawable.bell_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        return Result.success();
    }
}
