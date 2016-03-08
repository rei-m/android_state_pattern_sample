package me.rei_m.statepatternsample.fragment.state;

public class MainFragmentStateIdle extends AbsMainFragmentState {

    @Override
    protected String getTag() {
        return MainFragmentStateIdle.class.getSimpleName();
    }
}
