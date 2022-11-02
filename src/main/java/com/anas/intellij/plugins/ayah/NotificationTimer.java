package com.anas.intellij.plugins.ayah;

import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public enum NotificationTimer {
    INSTANCE
    ;

    private final Timer timer;
    private final NotificationTimerTask notificationTimerTask;
    private boolean isRunning;

    NotificationTimer() {
        timer = new Timer();
        notificationTimerTask = new NotificationTimerTask();
        isRunning = false;
    }
    public void start(@NotNull Project project) {
        if (isRunning) {
            return;
        }
        notificationTimerTask.setProject(project);
        schedule(AyahSettingsState.getInstance().getIntervalTimeBetweenNotifications());
        isRunning = true;

    }

    public void updateIntervalTimeBetweenNotifications(final int intervalTimeBetweenNotifications) {
        timer.cancel();
        schedule(intervalTimeBetweenNotifications);
    }

    private void schedule(final long intervalTimeBetweenNotifications) {
        timer.schedule(notificationTimerTask, 1000, intervalTimeBetweenNotifications * 60 * 1000);
    }
}
