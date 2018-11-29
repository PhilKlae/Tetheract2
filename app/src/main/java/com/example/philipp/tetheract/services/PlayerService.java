package com.example.philipp.tetheract.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;

import com.example.philipp.tetheract.MainActivity;





public class PlayerService extends Service {

    //TODO https://stackoverflow.com/questions/32644365/releasing-mediaplayer-and-stopping-it-onpause-and-onresume-gives-error-in-androi       https://priyankacool10.wordpress.com/2014/04/27/how-to-create-simple-unbound-service-in-android/ on pause und so


    private MediaSessionCompat mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "volume", Toast.LENGTH_SHORT).show();
        mediaSession = new MediaSessionCompat(this, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0) //you simulate a player which plays something.
                .build());

        //this will only work on Lollipop and up, see https://code.google.com/p/android/issues/detail?id=224134
        VolumeProviderCompat myVolumeProvider =
                new VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/100, /*initial volume level*/50) {
                    @Override
                    public void onAdjustVolume(int direction) {

                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(LaunchIntent);


                /*
                -1 -- volume down
                1 -- volume up
                0 -- volume button released
                 */
                    }

                };

        mediaSession.setPlaybackToRemote(myVolumeProvider);
        mediaSession.setActive(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaSession.release();
        Toast.makeText(getApplicationContext(), "volume", Toast.LENGTH_SHORT).show();
        mediaSession = new MediaSessionCompat(this, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0) //you simulate a player which plays something.
                .build());

        //this will only work on Lollipop and up, see https://code.google.com/p/android/issues/detail?id=224134
        VolumeProviderCompat myVolumeProvider =
                new VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/100, /*initial volume level*/50) {
                    @Override
                    public void onAdjustVolume(int direction) {

                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(LaunchIntent);


                /*
                -1 -- volume down
                1 -- volume up
                0 -- volume button released
                 */
                    }

                };




        mediaSession.setPlaybackToRemote(myVolumeProvider);
        mediaSession.setActive(true);

        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "volume", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "cant go back", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        mediaSession.release();
    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private class TestMediaSessionCallback extends MediaSessionCompat.Callback {
        private  final String TAG = TestMediaSessionCallback.class.getSimpleName();
        @Override
        public void onPlay() {
            super.onPlay();

        }

        @Override
        public void onPause() {
            super.onPause();

        }


    }*/
}