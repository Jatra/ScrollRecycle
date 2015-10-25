package uk.co.jatra.scrollrecyclerchild;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tim on 24/10/2015.
 */
public class SimpleViewHolder<T> extends RecyclerView.ViewHolder {
    public Button button;
    public TextView textview;

    public SimpleViewHolder(RelativeLayout itemView) {
        super(itemView);
        textview = (TextView) itemView.findViewById(R.id.textView);
        button = (Button) itemView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ScrollableListHeaderActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setData(T data) {
        button.setText(String.valueOf(data));
        textview.setText(data.toString());
    }
}
