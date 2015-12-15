package im.ene.lab.rvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.ene.lab.rvp.core.BaseListAdapter;
import im.ene.lab.rvp.data.Cheeses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eneim on 12/15/15.
 */
public class SimpleListAdapter extends BaseListAdapter<String> {

  List<String> getRandomSublist(String[] array, int amount) {
    ArrayList<String> list = new ArrayList<>(amount);
    Random random = new Random();
    while (list.size() < amount) {
      list.add(array[random.nextInt(array.length)]);
    }

    return list;
  }

  // Just because I like this number
  private static final int LIST_SIZE = 23;

  private final List<String> sItems = getRandomSublist(Cheeses.sCheeseStrings, LIST_SIZE);

  @Override public String getItem(int position) {
    return sItems.get(position);
  }

  @Override
  public BaseListAdapter.ViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(ViewHolder.LAYOUT_RES, parent, false);
    final ViewHolder viewHolder = new ViewHolder(view);
    viewHolder.setOnViewHolderClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        int adapterPosition = viewHolder.getAdapterPosition();
        // get this from Android Summit Video
        if (adapterPosition != RecyclerView.NO_POSITION) {
          SimpleListAdapter.this.mOnItemClickListener.onItemClick(
              SimpleListAdapter.this, viewHolder, v, adapterPosition, getItemId(adapterPosition)
          );
        }
      }
    });

    return viewHolder;
  }

  @Override public int getItemCount() {
    return sItems.size();
  }

  public static class ViewHolder extends BaseListAdapter.ViewHolder<String> {

    // This is a TextView's layout
    static final int LAYOUT_RES = android.R.layout.simple_list_item_1;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    @Override public void bind(String item) {
      // It's safe here
      ((TextView) itemView).setText(item);
    }
  }
}
