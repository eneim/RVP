package im.ene.lab.rvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.lab.rvp.R;
import im.ene.lab.rvp.activity.CheeseDetailActivity;
import im.ene.lab.rvp.adapter.CheeseListAdapter;
import im.ene.lab.rvp.data.Cheeses;

/**
 * Created by eneim on 12/15/15.
 */
public class CheeseListFragment extends Fragment {

  RecyclerView mRecyclerView;
  RecyclerView.LayoutManager mLayoutManager;
  CheeseListAdapter mAdapter;
  /**
   * My customized click listener. We can directly handle click event from Fragment or Setup
   * another callback to attach to Host Activity's lifecycle. IMO, Both are good practices.
   */
  private CheeseListAdapter.OnCheeseClickListener mClickListener;
  public CheeseListFragment() {
  }

  public static CheeseListFragment newInstance() {
    return new CheeseListFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    return inflater.inflate(R.layout.fragment_cheese_list, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    /*
      We can try both UI to check if our position works as expected;
     */
    // mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    mLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
    // a little bit more complicated Grid.
    GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return position % 3 == 2 ? 2 : 1;
      }
    };

    ((GridLayoutManager) mLayoutManager).setSpanSizeLookup(spanSizeLookup);

    mRecyclerView.setLayoutManager(mLayoutManager);

    mAdapter = new CheeseListAdapter();
    mRecyclerView.setAdapter(mAdapter);

    mClickListener = new CheeseListAdapter.OnCheeseClickListener() {
      @Override public void onIconClick(View iconView, Cheeses cheese) {
        if (getView() != null) {
          Snackbar.make(getView(), cheese.getName(), Snackbar.LENGTH_LONG).show();
        }
      }

      @Override public void onCheeseClick(View nameView, Cheeses cheese) {
        Intent intent = new Intent(getContext(), CheeseDetailActivity.class);
        intent.putExtra(CheeseDetailActivity.EXTRA_NAME, cheese.getName());
        startActivity(intent);
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
