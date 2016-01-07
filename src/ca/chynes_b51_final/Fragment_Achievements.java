package ca.chynes_b51_final;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment_Achievements extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        
        // Get Current Database
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        
        // Fill ArrayList of Achievements from the DB
        ArrayList<Achievement> achieve = db.getAchievements();
        
        // Get ListView, Create AchievementAdapter add Adapter to Listview and display
        ListView lvAchievements = (ListView) view.findViewById(R.id.lvAchievements);
        AchievementViewAdapter adt = new AchievementViewAdapter(getActivity(), achieve);
        lvAchievements.setAdapter(adt);
        
        return view;
    }
}
