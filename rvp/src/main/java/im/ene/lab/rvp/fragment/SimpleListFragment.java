package im.ene.lab.rvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.lab.rvp.R;
import im.ene.lab.rvp.activity.CheeseDetailActivity;
import im.ene.lab.rvp.adapter.SimpleListAdapter;
import im.ene.lab.rvp.core.BaseAdapter;

/**
 * Created by eneim on 12/15/15.
 */
public class SimpleListFragment extends Fragment {

  public SimpleListFragment() {
  }

  public static SimpleListFragment newInstance() {
    return new SimpleListFragment();
  }

  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  SimpleListAdapter mAdapter;
  BaseAdapter.OnItemClickListener mClickListener;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    return inflater.inflate(R.layout.fragment_cheese_list, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(mLayoutManager);

    mAdapter = new SimpleListAdapter();
    mRecyclerView.setAdapter(mAdapter);

    mClickListener = new BaseAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseAdapter adapter, BaseAdapter.ViewHolder viewHolder, View view,
                              int adapterPosition, long itemId) {
        String item = null;
        if (adapter instanceof SimpleListAdapter) {
          item = ((SimpleListAdapter) adapter).getItem(adapterPosition);
        }

        if (item != null) {
          Intent intent = new Intent(getContext(), CheeseDetailActivity.class);
          intent.putExtra(CheeseDetailActivity.EXTRA_NAME, item);
          startActivity(intent);
        }
      }
    };

    mAdapter.setOnItemClickListener(mClickListener);
  }

  @Override public void onDestroyView() {
    // This click event is attached to UI behavior, so we should properly release it before all
    // views are dead.
    // IMO, Doing this in onDestroyView or onDetach is really depends on how you use your listener.
    mClickListener = null;
    super.onDestroyView();
  }
}
