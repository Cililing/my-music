package materna.przemek.musicplayerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    ArrayList<Song> songs;
    OnMusicItemClickListener listener;


    public SongAdapter(ArrayList<Song> songs, OnMusicItemClickListener listener) {
        this.songs = songs;
        this.listener = listener;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_playlist_song_item, parent, false);

        return new SongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SongHolder holder, final int position) {
        //turn off visible of image

        holder.image.setVisibility(View.GONE);
        holder.title.setText(songs.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.getAdapterPosition(), songs.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(holder.getAdapterPosition(), songs.get(holder.getAdapterPosition()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;


        public SongHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.playlist_item_title);
            image = (ImageView) itemView.findViewById(R.id.playlist_item_image);

        }


    }

}
