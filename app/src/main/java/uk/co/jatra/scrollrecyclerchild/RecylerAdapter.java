package uk.co.jatra.scrollrecyclerchild;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by tim on 24/10/2015.
 */
class RecylerAdapter extends RecyclerView.Adapter {

    List<String> data = new Data().getData();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SimpleViewHolder) holder).setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
