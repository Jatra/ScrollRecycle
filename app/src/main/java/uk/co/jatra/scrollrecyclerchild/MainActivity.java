package uk.co.jatra.scrollrecyclerchild;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ITEM_TYPE = 1;
    public static final int HEADER_TYPE = 2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MapView> mapViews = new ArrayList<>();
    private MapView mapView;
    private FrameLayout mapContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapContainer = new FrameLayout(MainActivity.this);

        mapView = new MapView(MainActivity.this);
        mapView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "map ready");
            }
        });
        mapViews.add(mapView);

        for (MapView mapView : mapViews) {
            mapView.onCreate(savedInstanceState);
        }

        setContentView(R.layout.activity_main);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case ITEM_TYPE:
                        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.item, parent, false);
                        return new SimpleViewHolder(view);
                    case HEADER_TYPE:
                        return new HeaderViewHolder(mapContainer);
                    default:
                        return null;
                }
            }

            @Override
            public int getItemViewType(int position) {
                return position == 0 ? HEADER_TYPE : ITEM_TYPE;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
                switch (getItemViewType(position)) {
                    case HEADER_TYPE:
                        HeaderViewHolder hvh = (HeaderViewHolder)holder;
                        hvh.container.removeAllViews();
                        hvh.container.addView(mapView);
                        break;
                    case ITEM_TYPE:
                        SimpleViewHolder svh = (SimpleViewHolder) holder;
                        svh.button.setText(String.valueOf(position));
                        svh.textview.setText("<<<< " + position + " >>>>");
                }
            }

            @Override
            public int getItemCount() {
                return 3000;
            }


        };
        ScrollableHeaderRecycler recyclerView = (ScrollableHeaderRecycler)findViewById(R.id.recycler_view);
        recyclerView.setHeaderView(mapContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        for (MapView mapView : mapViews) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (MapView mapView : mapViews) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (MapView mapView : mapViews) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MapView mapView : mapViews) {
            mapView.onDestroy();
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        public TextView textview;
        public FrameLayout mapHolder;

        public SimpleViewHolder(RelativeLayout itemView) {
            super(itemView);
            button = (Button)itemView.findViewById(R.id.button);
            textview = (TextView)itemView.findViewById(R.id.textView);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout container;

        public HeaderViewHolder(FrameLayout view) {
            super(view);
            container = view;
        }
    }
}
