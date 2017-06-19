package materna.przemek.musicplayerv2;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class MusicManager {

    final Uri MEDIA_PATH = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final Context context;

    private ArrayList<Song> songs = new ArrayList<>();

    public MusicManager(Context context) {
        this.context = context;
    }

    public ArrayList<Song> getPlaylist() {

        songs.clear();

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = context.getContentResolver().query(
                MEDIA_PATH,
                null,
                selection,
                null,
                sortOrder
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)).endsWith(".mp3")) {
                    Song s = new Song(
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    );
                    songs.add(s);
                }

            }
        }

        return songs;
    }




}
