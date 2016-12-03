package co.edu.eafit.parkingmobile;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.parkingmobile.models.ModelManager;
import co.edu.eafit.parkingmobile.models.Parking;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    public ContentAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView picture;
        private TextView title;
        private TextView countAvailable;
        private TextView price;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            title = (TextView) itemView.findViewById(R.id.card_title);
            countAvailable = (TextView) itemView.findViewById(R.id.card_available);
            price = (TextView) itemView.findViewById(R.id.card_price);
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{
        Context context;

        public ContentAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Parking parking = ModelManager.getInstance().parkings.get(position);

            int resId = context.getResources().getIdentifier(parking.getPicture(), "drawable", context.getPackageName());
            Drawable drawable = ContextCompat.getDrawable(context, resId);

            holder.picture.setImageDrawable(drawable);
            holder.title.setText(parking.getName());
            holder.price.setText(parking.getPrice());
            holder.countAvailable.setText(parking.getFieldsAvailable());
        }

        @Override
        public int getItemCount() {
            return ModelManager.getInstance().parkings.size();
        }
    }
}
