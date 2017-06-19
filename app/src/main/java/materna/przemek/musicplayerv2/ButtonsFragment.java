package materna.przemek.musicplayerv2;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ButtonsFragment extends Fragment {

    @BindView(R.id.mp_title)
    TextView title;
    @BindView(R.id.mp_current_time)
    TextView currentTime;
    @BindView(R.id.mp_total_time)
    TextView totalTime;

    @BindView(R.id.mp_play)
    ImageButton playButton;

    @BindView(R.id.mp_seek_bar)
    SeekBar progressBar;
    Handler handler = new Handler();
    CustomButtonsListener listener;
    MediaPlayer mp;


    public static ButtonsFragment getInstance(CustomButtonsListener listener, MediaPlayer mp) {
        ButtonsFragment fragment = new ButtonsFragment();
        fragment.listener = listener;
        fragment.mp = mp;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buttons, container, false);

        ButterKnife.bind(this, view);
        initFields();


        return view;
    }

    private void initFields() {
        progressBar.setOnSeekBarChangeListener(seekBarChangeListener);
        title.setText(getString(R.string.default_title));
        currentTime.setText(getString(R.string.default_time));
        totalTime.setText(getString(R.string.default_time));
    }

    public void setSongData(String title, long currentTime, long totalTime) {
        this.title.setText(title);
        this.currentTime.setText(Tools.millisecondsToTimer(currentTime));
        this.totalTime.setText(Tools.millisecondsToTimer(totalTime));
    }

    public void updateProgressBar(long currentTime, long totalTime) {
        updateCurrentTime(currentTime);
        progressBar.setProgress((int) currentTime);
        progressBar.setMax((int) totalTime);
    }

    public void updateCurrentTime(long currentTime) {
        this.currentTime.setText(Tools.millisecondsToTimer(currentTime));
    }

    public void startTimer() {
        progressBar.setProgress(0);
        seekBarChangeListener.onStopTrackingTouch(progressBar);
    }



    @OnClick(R.id.mp_previous)
    public void onPrevious() {
        listener.onPreviousClick();
    }

    @OnClick(R.id.mp_backward)
    public void onBackward() {
        listener.onBackwardClick();
    }

    @OnClick(R.id.mp_play)
    public void onPlay() {
        listener.onPlayClick();
    }

    @OnClick(R.id.mp_forward)
    public void onForward() {
        listener.onForwardClick();
    }

    @OnClick(R.id.mp_next)
    public void onNext() {
        listener.onNextClick();
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeCallbacks(updateTimeTask);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.removeCallbacks(updateTimeTask);
            int current = seekBar.getProgress();
            mp.seekTo(current);
            updateProgressBar();
        }

        public void updateProgressBar() {
            handler.postDelayed(updateTimeTask, 100);
        }

        private Runnable updateTimeTask = new Runnable() {
            @Override
            public void run() {
                int current = mp.getCurrentPosition();

                currentTime.setText("" + Tools.millisecondsToTimer(current));

                progressBar.setProgress(current);

                handler.postDelayed(this, 100);
            }

        };

    };

    public void setPlayingIcon(boolean b) {
        if (b) {
            playButton.setImageDrawable(getActivity().getDrawable(R.drawable.play_button));
        } else {
            playButton.setImageDrawable(getActivity().getDrawable(R.drawable.pause_button));
        }
    }
}

