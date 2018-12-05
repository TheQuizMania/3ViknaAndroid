package a.b.c.quizmania.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a.b.c.quizmania.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrueOrFalseFragment extends Fragment {


    public TrueOrFalseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_true_or_false, container, false);
    }

}
