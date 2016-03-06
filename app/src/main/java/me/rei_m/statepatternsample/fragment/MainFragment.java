package me.rei_m.statepatternsample.fragment;

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

public class MainFragment extends Fragment {

    @Bind(R.id.list_atnd_event)
    ListView listAtndEvent;

    @Bind(R.id.text_list_atnd_empty)
    TextView emptyView;

    @Bind(R.id.text_list_atnd_error)
    TextView errorView;

    private CompositeSubscription compositeSubscription;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        AtndEventListAdapter listAdapter = new AtndEventListAdapter(getContext(), R.layout.list_item_atnd_event);
        listAtndEvent.setAdapter(listAdapter);

        emptyView.setVisibility(View.GONE);

        errorView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RxBusProvider.INSTANCE.get()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (o instanceof AtndModel.AtndEventLoadedEvent) {
                        AtndModel.AtndEventLoadedEvent event = (AtndModel.AtndEventLoadedEvent) o;
                        if (event.isSuccess()) {
                            displayContents();
                        } else {
                            errorView.setVisibility(View.VISIBLE);
                        }
                    }
                }));
        ((AtndModel) ModelLocator.get(ModelLocator.Tag.ATND)).fetch();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeSubscription.unsubscribe();
    }

    private void displayContents() {
        AtndModel atndModel = ((AtndModel) ModelLocator.get(ModelLocator.Tag.ATND));

        if (0 < atndModel.getEventCount()) {
            AtndEventListAdapter listAdapter = (AtndEventListAdapter) listAtndEvent.getAdapter();
            listAdapter.clear();
            listAdapter.addAll(atndModel.getAtndEventList());
            listAdapter.notifyDataSetChanged();
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
