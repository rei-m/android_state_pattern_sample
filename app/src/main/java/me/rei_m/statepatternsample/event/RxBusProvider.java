package me.rei_m.statepatternsample.event;

public enum RxBusProvider {

    INSTANCE;

    private final RxBus BUS = new RxBus();

    public RxBus get() {
        return BUS;
    }
}
