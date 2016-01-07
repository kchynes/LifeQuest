package ca.chynes_b51_final;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_About extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        
        // Check for Viewing the About Page Achievement
        LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 12);
        
        // Get Current Date and Display
        TextView tvCurrentDate = (TextView) view.findViewById(R.id.tvCurrentDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM. dd, yyyy - HH:mm:ss");
        Date date = new Date();
        tvCurrentDate.setText(dateFormat.format(date));
        
        return view;
    }
}
