package temple.edu.gridassignment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class PaletteFragment extends Fragment {


    FragmentInteractionListener listener;
    ArrayList<String> colors;
    ColorAdapter adapter;


    public PaletteFragment() {
    }

    /**
     * @param context need activity to call key in strings.xml
     * @param colors color arrayList to be passed to adapter
     * @return PaletteFragment instance
     */
    public static PaletteFragment newInstance(Context context, ArrayList<String> colors) {

        PaletteFragment fragment = new PaletteFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(context.getResources().getString(R.string.KEY), colors);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            colors = getArguments().getStringArrayList(getResources().getString(R.string.KEY));
            adapter = new ColorAdapter(getActivity(), colors, getResources().getConfiguration(), getResources());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // attachToRoot means instantly setting view to root of container specified...
        // in MainActivity the fragment manager is to add the root view of this fragment to the container in activity_main
        // define on Attach if you want to do so this way
        View view = inflater.inflate(R.layout.fragment_palette, container, false);

        GridView grid = (GridView) view.findViewById(R.id._colorGrid);
        ((GridView) view.findViewById(R.id._colorGrid)).setNumColumns(3);
        ((GridView) view.findViewById(R.id._colorGrid)).setAdapter(adapter);
        ((GridView)view.findViewById(R.id._colorGrid)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Define", "color: " + ((TextView) view).getText().toString());
                listener.displayColor(position, ((TextView) view).getText().toString());
            }
        });
        return view;
    }

    /**
     * calling activity is an implementing FragmentInteractionListener activity
     * @param context implementing activity
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (FragmentInteractionListener) context;
        } catch (ClassCastException e){
            Log.i("class cast error", "Must implement FragmentInteractionListener: " + e.getLocalizedMessage());
            throw new ClassCastException(e.getLocalizedMessage());
        }
    }

    /**
     * Activity must implement a medium to channel connection between fragments
     */
    public interface FragmentInteractionListener{
        void displayColor(int position, String color);

    }


}