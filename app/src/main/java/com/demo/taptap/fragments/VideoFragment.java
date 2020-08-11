package com.demo.taptap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.taptap.R;
import com.demo.taptap.fragments.dummy.DummyContent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        RecyclerView rvVideo = view.findViewById(R.id.rvVideo);
        rvVideo.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvVideo.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));

        return view;
    }
}
