package materna.przemek.musicplayerv2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaylistFragment extends Fragment {


    @BindView(R.id.playlist_libary_rw)
    RecyclerView musicRecyclerView;


    ArrayList<Song> songsList;
    OnMusicItemClickListener listener;

    public static PlaylistFragment getInstance(ArrayList<Song> songsList, OnMusicItemClickListener listener) {
        PlaylistFragment fragment = new PlaylistFragment();
        fragment.songsList = songsList;
        fragment.listener = listener;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {

        SongAdapter songAdapter = new SongAdapter(songsList, listener);
        musicRecyclerView.setAdapter(songAdapter);
        musicRecyclerView.setItemAnimator(new DefaultItemAnimator());
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }


}
