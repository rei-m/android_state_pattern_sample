package me.rei_m.statepatternsample.fragment.state;

import android.os.Bundle;
import android.util.Log;

import me.rei_m.statepatternsample.fragment.MainFragment;

public enum MainFragmentState {

    INIT {
        @Override
        public void onResume(MainFragment fragment) {
            super.onResume(fragment);
            fragment.next(GETTING_READY);
        }
    },
    IDLE {
        @Override
        public void onPause(MainFragment fragment) {
            super.onPause(fragment);
            fragment.next(PAUSE);
        }
    },
    PAUSE {
        @Override
        public void onResume(MainFragment fragment) {
            super.onResume(fragment);
            fragment.next(GETTING_READY);
        }
    },
    GETTING_READY {

    };

    MainFragmentState() {
    }

    public void entry(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void exit(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onCreate(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(name(), "Not implemented");
    }

    public void onCreateView(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(name(), "Not implemented");
    }

    public void onActivityCreated(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(name(), "Not implemented");
    }

    public void onStart(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onResume(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onPause(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onStop(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onDestroyView(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }

    public void onDestroy(MainFragment fragment) {
        Log.d(name(), "Not implemented");
    }
}
