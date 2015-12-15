package im.ene.lab.rvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import im.ene.lab.rvp.R;
import im.ene.lab.rvp.core.BaseAdapter;
import im.ene.lab.rvp.core.BaseListAdapter;
import im.ene.lab.rvp.data.Cheeses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eneim on 12/15/15.
 */
public class CheeseListAdapter extends BaseListAdapter<Cheeses> {

  // Just because I like this number
  private static final int LIST_SIZE = 23;

  private final List<Cheeses> sItems = getRandomSublist(Cheeses.sCheeseStrings, LIST_SIZE);

  @Override public Cheeses getItem(int position) {
    return sItems.get(position);
  }

  @Override
  public BaseListAdapter.ViewHolder<Cheeses> onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(ViewHolder.LAYOUT_RES, parent, false);
    final ViewHolder viewHolder = new ViewHolder(view);
    // setup Click event listener here
    viewHolder.setOnViewHolderClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (CheeseListAdapter.this.mOnItemClickListener != null) {
          int adapterPosition = viewHolder.getAdapterPosition();
          // get this from Android Summit Video
          if (adapterPosition != RecyclerView.NO_POSITION) {
            CheeseListAdapter.this.mOnItemClickListener.onItemClick(
                CheeseListAdapter.this, viewHolder, v, adapterPosition, getItemId(adapterPosition)
            );
          }
        }
      }
    });
    return viewHolder;
  }

  /**
   * Customized Click event listener
   */
  public abstract static class OnCheeseClickListener implements OnItemClickListener {

    public abstract void onIconClick(View iconView, Cheeses cheese);

    public abstract void onCheeseClick(View nameView, Cheeses cheese);

    @Override
    public void onItemClick(BaseAdapter adapter, BaseAdapter.ViewHolder viewHolder,
                            View view, int adapterPosition, long itemId) {
      if (adapter instanceof BaseListAdapter) {
        // we expect a ListAdapter here, since we are using a List Adapter
        BaseListAdapter listAdapter = (BaseListAdapter) adapter;
        // so we can get clicked item
        Cheeses item = (Cheeses) listAdapter.getItem(adapterPosition);

        // Note 1: Casted object maybe null if we're using wrong adapter
        // Note 2:
        if (item != null && viewHolder instanceof ViewHolder) {
          if (view == ((ViewHolder) viewHolder).mImageView) {
            onIconClick(view, item);
          } else if (view == ((ViewHolder) viewHolder).itemView) {
            onCheeseClick(view, item);
          }
        }
      }
    }
  }

  @Override public int getItemCount() {
    return sItems.size();
  }

  private List<Cheeses> getRandomSublist(String[] array, int amount) {
    ArrayList<Cheeses> list = new ArrayList<>(amount);
    Random random = new Random();
    while (list.size() < amount) {
      list.add(new Cheeses(Cheeses.getRandomCheeseDrawable(),
          array[random.nextInt(array.length)]));
    }

    return list;
  }

  public static class ViewHolder extends BaseListAdapter.ViewHolder<Cheeses> {

    // A ViewHolder expects a UI Layout, so make it static here
    static final int LAYOUT_RES = R.layout.list_item;

    public final ImageView mImageView;
    public final TextView mTextView;

    public ViewHolder(View view) {
      super(view);
      mImageView = (ImageView) view.findViewById(R.id.item_icon);
      mTextView = (TextView) view.findViewById(R.id.item_name);
    }

    @Override public void bind(Cheeses item) {
      mTextView.setText(item.getName());
      Picasso.with(itemView.getContext())
          .load(item.getIconRes())
          .fit().centerCrop()
          .into(mImageView);
    }

    // !IMPORTANT Since we accept click event on 2 different views, we must delegate them here.
    @Override public void setOnViewHolderClickListener(View.OnClickListener listener) {
      mImageView.setOnClickListener(listener);
      itemView.setOnClickListener(listener);
    }
  }
}
