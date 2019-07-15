package com.sokool.intimacyup;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.Random;

import com.sokool.intimacyup.model.LevelResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarksFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected Button nextQuestionButton;
    protected RecyclerView scoresRecyclerView;
    protected TextView currentLevelTextView;
    NewPlayer newPlayer;
    String level;


    public MarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.marks_fragment, container, false);

        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newPlayer = (NewPlayer) getActivity();
        level = newPlayer.getLevel();
        currentLevelTextView.setText(level);
        if(level.equals("done")){
            currentLevelTextView.setText(level+" Thank you");
        }
        final SharedPreferences sp = getActivity().getBaseContext().getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                updateScores(sharedPreferences);
            }
        });
        updateScores(sp);

    }

    public void updateScores(final SharedPreferences sp){

        scoresRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.score_item, new SlimInjector<LevelResults>() {

                    @Override
                    public void onInject(LevelResults data, IViewInjector injector) {
                        String[] gradients = new String[]{
                                "gradient_aubergine",
                                "gradient_cherry",
                                "gradient_frost",
                                "gradient_kashmir",
                                "gradient_purplelove",
                                "gradient_socialive"
                        };

                        Random random = new Random();
                        int num =  random.nextInt(6);

                        View parent = injector.findViewById(R.id.scores_parent_linear_layout);
                        ScoresViewHolder scoresViewHolder = new ScoresViewHolder(parent);
                        scoresViewHolder.levelTextView.setText(data.getLevel());
                        scoresViewHolder.player1MarksTextView.setText(data.getPlayerOneTotal() + "");
                        scoresViewHolder.player2MarksTextView.setText(data.getPlayerTwoTotal() + "");

                        String player1 = sp.getString("Player1", "Player1");
                        String player2 = sp.getString("Player2", "Player2");
                        scoresViewHolder.player1LableTextView.setText(player1);
                        scoresViewHolder.player2LableTextView.setText(player2);
                        if(data.getLevel().equals(level)){
                            parent.setBackgroundResource(getDrawableIdFromString(getContext(),"gradient_cherry"));
                        }

                    }
                }).attachTo(scoresRecyclerView);

        ArrayList<LevelResults> levelResults = new ArrayList<>();
        levelResults = newPlayer.getLevelResults();


        slimAdapter.updateData(levelResults);

    }

    public static Drawable getDrawableFromString(Context context, String drawableName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(drawableName, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    public static int getDrawableIdFromString(Context context, String drawableName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(drawableName, "drawable",
                context.getPackageName());
        return resourceId;
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.next_question_button) {
            newPlayer.play();

        }
    }


    private void initView(View rootView) {
        nextQuestionButton = (Button) rootView.findViewById(R.id.next_question_button);
        nextQuestionButton.setOnClickListener(MarksFragment.this);
        scoresRecyclerView = (RecyclerView) rootView.findViewById(R.id.scores_recycler_view);
        currentLevelTextView = (TextView) rootView.findViewById(R.id.current_level_text_view);
    }

    public interface NewPlayer {
        public void play();

        public String getLevel();

        public ArrayList<LevelResults> getLevelResults();
    }


    protected class ScoresViewHolder {
        private TextView levelTextView;
        private TextView player1LableTextView;
        private TextView player2LableTextView;
        private TextView player1MarksTextView;
        private TextView player2MarksTextView;

        public ScoresViewHolder(View view) {
            levelTextView = (TextView) view.findViewById(R.id.level_text_view);
            player1LableTextView = (TextView) view.findViewById(R.id.player1_lable_text_view);
            player2LableTextView = (TextView) view.findViewById(R.id.player2_lable_text_view);
            player1MarksTextView = (TextView) view.findViewById(R.id.player1_marks_text_view);
            player2MarksTextView = (TextView) view.findViewById(R.id.player2_marks_text_view);
        }
    }
}
