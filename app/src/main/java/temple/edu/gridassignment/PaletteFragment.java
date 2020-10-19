package temple.edu.gridassignment;

import android.app.Activity;
import android.content.Context;
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


    public PaletteFragment() {

    }

    public static PaletteFragment newInstance(ArrayList<String> colors) {
        PaletteFragment fragment = new PaletteFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("key", colors);
        fragment.colors = colors;
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // attachToRoot means instantly setting view to root of container specified...
        // in MainActivity the fragment manager is to add the root view of this fragment to the container in activity_main
        View view = inflater.inflate(R.layout.fragment_palette, container, false);

        GridView grid = (GridView) view.findViewById(R.id._colorGrid);
        ((GridView) view.findViewById(R.id._colorGrid)).setNumColumns(3);

        ((GridView) view.findViewById(R.id._colorGrid)).setAdapter(new ColorAdapter(getActivity(), colors));
        ((GridView)view.findViewById(R.id._colorGrid)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.displayColor(((TextView) view).getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (FragmentInteractionListener) context;
        } catch (ClassCastException e){
            Log.i("class cast error", "Must implement FragmentInteractionListener: " + e.getLocalizedMessage());
        }
    }

    public interface FragmentInteractionListener{
        void displayColor(String color);
    }


}