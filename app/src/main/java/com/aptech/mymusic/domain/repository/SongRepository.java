package com.aptech.mymusic.domain.repository;

import com.aptech.mymusic.domain.entity.SongModel;
import com.aptech.mymusic.domain.entity.TopicModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;

public interface SongRepository {

    Call<List<SongModel>> getNewlyReleasedMusic();

    Call<List<SongModel>> getAllSongFrom(String type, Long id);

    /**
     * @param id                song id
     * @param listCurrentSongId ma hoa base 64
     * @return List<SongModel>
     */
    Call<List<SongModel>> getSuggestSong(Long id, String listCurrentSongId);

}
