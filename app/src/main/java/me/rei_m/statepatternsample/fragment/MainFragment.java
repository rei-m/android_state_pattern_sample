package me.rei_m.statepatternsample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.statepatternsample.fragment.state.MainFragmentState;

public class MainFragment extends MainFragmentLogic {

    private MainFragmentState state = MainFragmentState.INIT;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void next(MainFragmentState nextState) {
        state.exit(this);
        state = nextState;
        state.entry(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state.onCreate(this, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        state.onCreateView(this, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        state.onActivityCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        state.onStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        state.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        state.onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        state.onPause(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        state.onDestroyView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        state.onDestroy(this);
    }
}

