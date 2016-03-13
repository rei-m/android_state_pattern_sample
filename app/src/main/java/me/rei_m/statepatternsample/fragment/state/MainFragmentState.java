package me.rei_m.statepatternsample.fragment.state;

import android.os.Bundle;
import android.util.Log;

import me.rei_m.statepatternsample.fragment.MainFragment;
import me.rei_m.statepatternsample.model.AtndModel;

public enum MainFragmentState {

    INIT {
        @Override
        public void onActivityCreated(MainFragment fragment, Bundle savedInstanceState) {
            super.onActivityCreated(fragment, savedInstanceState);
            fragment.initView();
        }

        @Override
        public void onResume(MainFragment fragment) {
            super.onResume(fragment);
            if (fragment.hasEventData()) {
                fragment.displayContents();
                fragment.next(IDLE);
            } else {
                fragment.next(GETTING_READY);
            }
        }
    },
    GETTING_READY {
        @Override
        public void entry(MainFragment fragment) {
            super.entry(fragment);
            fragment.showProgressDialog();
            fragment.fetchEvent();
        }

        @Override
        public void onPause(MainFragment fragment) {
            super.onPause(fragment);
            fragment.next(PAUSE);
        }

        @Override
        public void subscribe(MainFragment fragment, AtndModel.AtndEventLoadedEvent event) {
            super.subscribe(fragment, event);
            fragment.hideProgressDialog();
            if (event.isSuccess()) {
                if (fragment.hasEventData()) {
                    fragment.displayContents();
                } else {
                    fragment.displayEmptyMessage();
                }
            } else {
                fragment.displayErrorMessage();
            }
            fragment.next(IDLE);
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
        public void onCreateView(MainFragment fragment, Bundle savedInstanceState) {
            super.onCreateView(fragment, savedInstanceState);
            fragment.next(INIT);
        }
    };

    MainFragmentState() {
    }

    private String tag = MainFragmentState.class.getSimpleName() + "/" + name();

    public void entry(MainFragment fragment) {
        Log.d(tag, "entry");
    }

    public void exit(MainFragment fragment) {
        Log.d(tag, "exit");
    }

    public void onCreate(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(tag, "onCreate");
    }

    public void onCreateView(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(tag, "onCreateView");
    }

    public void onActivityCreated(MainFragment fragment, Bundle savedInstanceState) {
        Log.d(tag, "onActivityCreated");
    }

    public void onStart(MainFragment fragment) {
        Log.d(tag, "onStart");
    }

    public void onResume(MainFragment fragment) {
        Log.d(tag, "onResume");
    }

    public void onPause(MainFragment fragment) {
        Log.d(tag, "onPause");
    }

    public void onStop(MainFragment fragment) {
        Log.d(tag, "onStop");
    }

    public void onDestroyView(MainFragment fragment) {
        Log.d(tag, "onDestroyView");
    }

    public void onDestroy(MainFragment fragment) {
        Log.d(tag, "onDestroy");
    }

    public void subscribe(MainFragment fragment, AtndModel.AtndEventLoadedEvent event) {
        Log.d(tag, "subscribe/AtndEventLoadedEvent");
    }
}
