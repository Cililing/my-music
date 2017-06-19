package materna.przemek.musicplayerv2;


public interface OnMusicItemClickListener {

    void onClick(int postion, Object... params);
    boolean onLongClick(int postion, Object... params);

}
