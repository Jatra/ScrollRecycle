package uk.co.jatra.scrollrecyclerchild;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tim on 10/10/2015.
 */
public class HeadedAdapterImpl extends HeadedAdapter<String> {

    public HeadedAdapterImpl(List<String> data, View headerView) {
        super(data, headerView);
    }

    @Override
    public RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return HeaderHolder.create(parent.getContext());
    }

    @Override
    public RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_TYPE:
                ((HeaderHolder) holder).setHeader(headerView);
                break;
            case ITEM_TYPE:
                if (headerView != null) {
                    position--;
                }
                ((SimpleViewHolder) holder).setData(getItem(position));
        }
    }


    public static class SimpleViewHolder<T> extends RecyclerView.ViewHolder {
        public Button button;
        public TextView textview;

        public SimpleViewHolder(RelativeLayout itemView) {
            super(itemView);
            textview = (TextView)itemView.findViewById(R.id.textView);
            button = (Button)itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), CollapsingHeaderActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
        public void setData(T data) {
            button.setText(String.valueOf(data));
            textview.setText(data.toString());
        }
    }
}
