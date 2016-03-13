package me.rei_m.statepatternsample.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.rei_m.statepatternsample.R;
import me.rei_m.statepatternsample.event.RxBusProvider;
import me.rei_m.statepatternsample.manager.ModelLocator;
import me.rei_m.statepatternsample.model.AtndModel;
import me.rei_m.statepatternsample.view.adaptor.AtndEventListAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

abstract public class MainFragmentLogic extends Fragment {

    @Bind(R.id.list_atnd_event)
    ListView listAtndEvent;

    @Bind(R.id.text_list_atnd_empty)
    TextView emptyView;

    @Bind(R.id.text_list_atnd_error)
    TextView errorView;

    private CompositeSubscription compositeSubscription;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

    public void initView() {
        AtndEventListAdapter listAdapter = new AtndEventListAdapter(getContext(), R.layout.list_item_atnd_event);
        listAtndEvent.setAdapter(listAdapter);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void fetchEvent() {
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RxBusProvider.INSTANCE.get()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::subscribe));
        ((AtndModel) ModelLocator.get(ModelLocator.Tag.ATND)).fetch();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    abstract public void subscribe(Object o);

    public void displayContents() {
        AtndModel atndModel = ((AtndModel) ModelLocator.get(ModelLocator.Tag.ATND));
        AtndEventListAdapter listAdapter = (AtndEventListAdapter) listAtndEvent.getAdapter();
        listAdapter.clear();
        listAdapter.addAll(atndModel.getAtndEventList());
        listAdapter.notifyDataSetChanged();
    }

    public void displayEmptyMessage() {
        emptyView.setVisibility(View.VISIBLE);
    }

    public void displayErrorMessage() {
        errorView.setVisibility(View.VISIBLE);
    }

    public boolean hasEventData() {
        AtndModel atndModel = ((AtndModel) ModelLocator.get(ModelLocator.Tag.ATND));
        return (0 < atndModel.getEventCount());
    }
}
