package com.aptech.mymusic.presentation.view.fragment.mainpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aptech.mymusic.R;
import com.aptech.mymusic.domain.entity.CardModel;
import com.aptech.mymusic.domain.entity.PlaylistModel;
import com.aptech.mymusic.presentation.presenter.Callback;
import com.aptech.mymusic.presentation.presenter.PlaylistPresenter;
import com.aptech.mymusic.presentation.view.activity.MainActivity;
import com.aptech.mymusic.presentation.view.adapter.CardAdapter;
import com.aptech.mymusic.presentation.view.adapter.ICardListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends BaseTabFragment implements Callback.GetDataPlayListCallBack, ICardListener {

    private RecyclerView rcvCard;
    private PlaylistPresenter playlistPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistPresenter = new PlaylistPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    private void setAdapter(List<CardModel> cardModelList) {
        CardAdapter adapter = new CardAdapter(cardModelList, false, this);
        rcvCard.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getContext(), MainActivity.TWO_ITEM_CARD);
        rcvCard.setLayoutManager(manager);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvCard = view.findViewById(R.id.rcv_card);
        playlistPresenter.getDataAllPlaylist(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playlistPresenter.release();
        playlistPresenter = null;
    }

    @Override
    public void getDataPlaylistSuccess(List<PlaylistModel> data) {
        setAdapter(new ArrayList<>(data));
    }

    @Override
    public void getDataPlaylistFailure(String error) {

    }

    @NonNull
    @Override
    protected RecyclerView getScrollView() {
        return rcvCard;
    }
}
