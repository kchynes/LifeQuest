package ca.chynes_b51_final;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Help extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        
        // Check for Viewing the About Page Achievement
        LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 14);
        
        return view;
    }
}
