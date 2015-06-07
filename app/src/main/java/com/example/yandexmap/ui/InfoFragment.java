package com.example.yandexmap.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yandexmap.R;
import com.example.yandexmap.entity.Price;
import com.example.yandexmap.entity.Task;
import org.apache.http.impl.cookie.DateUtils;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    public static final String TAG = InfoFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TASK = "arg_task";

    private Task task;
    private TextView title, text, longText, date, location;
    private ListView pricesList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param task Parameter 1.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(Task task) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = getArguments().getParcelable(ARG_TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, null);
        title = (TextView)view.findViewById(R.id.title);
        text = (TextView)view.findViewById(R.id.text);
        longText = (TextView)view.findViewById(R.id.long_text);
        date = (TextView)view.findViewById(R.id.date);
        location = (TextView)view.findViewById(R.id.location_text);
        pricesList = (ListView)view.findViewById(R.id.list_prices);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(task.getTitle());
        text.setText(task.getText());
        longText.setText(task.getLongText());
        date.setText(DateUtils.formatDate(new Date(task.getDate())));
        location.setText(task.getLocationText());
        pricesList.setAdapter(new PriceAdapter(getActivity(), task.getPrices()));
    }

    private static class PriceAdapter extends BaseAdapter {

        private List<Price> prices;
        LayoutInflater layoutInflater;

        public PriceAdapter(Context context, List<Price> prices) {
            this.prices = prices;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return prices.size();
        }

        @Override
        public Object getItem(int position) {
            return prices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            private TextView price;
            private TextView description;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.price_item, parent, false);
                holder.price = (TextView)convertView.findViewById(R.id.price);
                holder.description = (TextView)convertView.findViewById(R.id.description);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            Price price = prices.get(position);
            holder.price.setText(String.valueOf(price.getPrice()));
            holder.description.setText(price.getDescription());

            return convertView;
        }
    }
}
