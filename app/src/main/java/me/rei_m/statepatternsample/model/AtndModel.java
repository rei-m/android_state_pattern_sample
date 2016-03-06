package me.rei_m.statepatternsample.model;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.statepatternsample.entity.AtndEventEntity;
import me.rei_m.statepatternsample.event.RxBusProvider;
import me.rei_m.statepatternsample.network.AtndHttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AtndModel {

    private final List<AtndEventEntity> atndEventList = new ArrayList<>();

    private boolean isBusy = false;

    public AtndModel() {
    }

    public List<AtndEventEntity> getAtndEventList() {
        return atndEventList;
    }

    public int getEventCount() {
        return atndEventList.size();
    }

    public void fetch() {

        if (isBusy) {
            return;
        }

        Observer<List<AtndEventEntity>> observer = new Observer<List<AtndEventEntity>>() {
            @Override
            public void onCompleted() {
                isBusy = false;
                RxBusProvider.INSTANCE.get().send(new AtndEventLoadedEvent(true));
            }

            @Override
            public void onError(Throwable e) {
                isBusy = false;
                RxBusProvider.INSTANCE.get().send(new AtndEventLoadedEvent(false));
            }

            @Override
            public void onNext(List<AtndEventEntity> atndEventEntityList) {
                atndEventList.addAll(atndEventEntityList);
            }
        };

        isBusy = true;

        new AtndHttpClient().fetchEvent()
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static class AtndEventLoadedEvent {

        private boolean isSuccess;

        public AtndEventLoadedEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
