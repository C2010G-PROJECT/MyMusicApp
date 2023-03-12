package com.aptech.mymusic.presentation.view.service;

import com.aptech.mymusic.di.DataInjection;
import com.aptech.mymusic.domain.entity.SongModel;
import com.aptech.mymusic.utils.MusicPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicDelegate {

    ///////////////////////////////////////////////////////////////////////////
    // ACTION
    ///////////////////////////////////////////////////////////////////////////
    public static final String ACTION_UPDATE_VIEW = "ACTION_UPDATE_VIEW";

    ///////////////////////////////////////////////////////////////////////////
    // KEY
    ///////////////////////////////////////////////////////////////////////////

    public static final String KEY_MUSIC_ACTION = "music_action";

    public static final String KEY_SONG_OBJECT = "song_object";
    public static final String KEY_LIST_SONG_OBJECT = "list_song_object";
    public static final String KEY_POSITION_NEW_SONG = "position_new_song";
    public static final String KEY_TIME_SEEK_SONG = "time_seek_song";

    ///////////////////////////////////////////////////////////////////////////
    // ENUM
    ///////////////////////////////////////////////////////////////////////////

    public enum Action {
        UPDATE_VIEW,
        PLAY_NEW_SONG,
        PLAY_NEW_LIST_SONG,
        STOP_SERVICE,

        PREV_SONG,
        PLAY_SONG,
        PAUSE_SONG,
        NEXT_SONG,
        SHUFFLE_SONG,
        REPEAT_SONG,
        SEEK_SONG,

    }

    public enum Mode {
        NORMAL,     // run to end of list song and stop
        SHUFFLE,    // shuffle list song and run first song (run like normal)
        REPEAT,     // run to end of list song and restart
        REPEAT_ONE, // run to end of song and restart

    }

    public enum MediaState {
        IDLE,
        PREPARED,
        PLAYING,
        PAUSED,
        RELEASE
    }

    public static class MediaBundle {

        private static MediaBundle instance;
        Mode mMode;
        SongModel mSong;
        List<SongModel> mListSongOrigin;
        List<SongModel> mListSongTemp;

        private MediaBundle() {
            this.mMode = getPreference().getLastMode();
            this.mSong = getPreference().getLastSong();
            this.mListSongOrigin = getPreference().getLastListSong();
            this.mListSongTemp = new ArrayList<>(this.mListSongOrigin);
        }

        static MediaBundle getInstance() {
            if (instance == null) {
                instance = new MediaBundle();
            }
            return instance;
        }

        int addSong(SongModel song, int i) {
            if (!mListSongTemp.contains(song)) {
                int index = validateSongIndex(i, mListSongOrigin.size());
                mListSongOrigin.add(index, song);
                mListSongTemp.add(index, song);
                getPreference().setLastListSong(mListSongOrigin);
                return index;
            }
            return mListSongTemp.indexOf(song);
        }

        void removeSong(SongModel song) {
            mListSongOrigin.remove(song);
            mListSongTemp.remove(song);
            getPreference().setLastListSong(mListSongOrigin);
        }

        void removeSongs(List<SongModel> songs) {
            mListSongOrigin.removeAll(songs);
            mListSongTemp.removeAll(songs);
            getPreference().setLastListSong(mListSongOrigin);
        }


        void swapSong(int drag, int target) {
            try {
                if (mMode == Mode.SHUFFLE) {
                    Collections.swap(mListSongTemp, drag, target);
                } else {
                    Collections.swap(mListSongOrigin, drag, target);
                    Collections.swap(mListSongTemp, drag, target);
                    getPreference().setLastListSong(mListSongOrigin);
                }
            } catch (Throwable ignore) {
            }
        }

        MusicPreference getPreference() {
            return DataInjection.provideMusicPreference();
        }

        private int validateSongIndex(int index, int size) {
            return index >= 0 && index <= size ? index : size;
        }

    }

}
