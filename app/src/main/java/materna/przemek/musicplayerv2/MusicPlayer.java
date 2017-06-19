package materna.przemek.musicplayerv2;

import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicPlayer extends AppCompatActivity {

    private static final int FORWARD_TIME = 5000;
    private static final int BACKWARD_TIME = 5000;

    FragmentManager fragmentManager;

    MediaPlayer musicPlayer;
    MusicManager musicManager;

    ArrayList<Song> songsListDevice;

    ButtonsFragment buttons;
    PlaylistFragment playlist;

    Song currentSong;
    int currentSongIndex;
    //ArrayList<HashMap<String, String>> songsListFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();

        initFields();
        loadFragments();

    }

    private void initFields() {
        musicPlayer = new MediaPlayer();
        musicManager = new MusicManager(getApplicationContext());


        songsListDevice = new ArrayList<>();
        songsListDevice = musicManager.getPlaylist();

    }


    private void loadFragments() {
        buttons = ButtonsFragment.getInstance(buttonsListener, musicPlayer);
        playlist = PlaylistFragment.getInstance(songsListDevice, musicItemClickListener);

        fragmentManager.beginTransaction().replace(R.id.buttons_holder, buttons).commit();
        fragmentManager.beginTransaction().replace(R.id.playlist_holder, playlist).commit();
    }

    public void  playSong(Song s) {
        // Play song
        try {

            musicPlayer.reset();
            musicPlayer.setDataSource(s.getData());
            musicPlayer.prepare();
            musicPlayer.start();

            // Displaying Song title
            buttons.setSongData(s.getTitle(), 0, Long.parseLong(s.getDuration()));
            buttons.startTimer();
            buttons.updateProgressBar(0, Long.parseLong(s.getDuration()));

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }


    //listeners
    OnMusicItemClickListener musicItemClickListener = new OnMusicItemClickListener() {
        @Override
        public void onClick(int postion, Object... params) {
            currentSong = (Song) params[0];
            currentSongIndex = postion;
            playSong(currentSong);
        }

        @Override
        public boolean onLongClick(int postion, Object... params) {
            return false;
        }
    };

    CustomButtonsListener buttonsListener = new CustomButtonsListener() {
        @Override
        public void onPreviousClick() {
            if (currentSongIndex - 1 >= 0) {
                currentSong = songsListDevice.get(--currentSongIndex);
                playSong(currentSong);
            }
        }

        @Override
        public void onBackwardClick() {
            int current = musicPlayer.getCurrentPosition();

            if (current - BACKWARD_TIME > 0) {
                musicPlayer.seekTo(current - BACKWARD_TIME);
            }
        }

        @Override
        public void onPlayClick() {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                buttons.setPlayingIcon(true);
            } else {
                if (currentSong != null) {
                    musicPlayer.start();
                    buttons.setPlayingIcon(false);
                }
            }
        }

        @Override
        public void onForwardClick() {
            int total = musicPlayer.getDuration();
            int current = musicPlayer.getCurrentPosition();

            if (current + FORWARD_TIME < total) {
                musicPlayer.seekTo(current + FORWARD_TIME);
            }
        }

        @Override
        public void onNextClick() {
            if (currentSongIndex + 1 < songsListDevice.size()) {
                currentSong = songsListDevice.get(++currentSongIndex);
                playSong(currentSong);
            }
        }

    };
}
