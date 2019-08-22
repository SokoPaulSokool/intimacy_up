package com.sokool.intimacyup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sokool.intimacyup.model.LevelResults;
import com.sokool.intimacyup.model.Question;

public class MainActivity extends AppCompatActivity implements UserFragment.Records, MarksFragment.NewPlayer {

    protected LinearLayout dropView;
    ArrayList<Question> questionListOne;
    ArrayList<Question> questionListTwo;
    ArrayList<Question> questionListThree;
    int num;
    public static String LEVEL_ONE = "Level one";
    public static String LEVEL_TWO = "Level two";
    public static String LEVEL_THREE = "Level three";

    String currentLevel = LEVEL_ONE;
    ArrayList<LevelResults> levelResultsList;
    int playerOneTotal = 0;
    int playerTwoTotal = 0;
    int playerOneTotalL1 = 0;
    int playerTwoTotalL1 = 0;
    int playerOneTotalL2 = 0;
    int playerTwoTotalL2 = 0;
    int playerOneTotalL3 = 0;
    int playerTwoTotalL3 = 0;

    public String PLAYER_ONE;
    public String PLAYER_TWO;
    SharedPreferences.OnSharedPreferenceChangeListener spListener;

    String currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        hide();


        initView();

        questionListOne = new ArrayList<>();
        questionListOne = questions(LEVEL_ONE);
        questionListTwo = new ArrayList<>();
        questionListTwo = questions(LEVEL_TWO);
        questionListThree = new ArrayList<>();
        questionListThree = questions(LEVEL_THREE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        MarksFragment marksFragment = new MarksFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dropView, marksFragment, "marksFragment");
        fragmentTransaction.commit();

        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        NameFragment nameFragment = new NameFragment();
        final SharedPreferences spp = getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        String player1Svd = spp.getString("Player1", null);
        String player2Svd = spp.getString("Player2", null);
        if (TextUtils.isEmpty(player1Svd) || TextUtils.isEmpty(player2Svd)) {
            nameFragment.setCancelable(false);
        }

        nameFragment.show(fragmentTransaction1, "dialog");
        spListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                PLAYER_ONE = sharedPreferences.getString("Player1", "Player1");
                PLAYER_TWO = sharedPreferences.getString("Player2", "Player2");
                if (key.equals("Player1") || key.equals("Player2")) {
                    restart();
                }
            }
        };
        final SharedPreferences sp = this.getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(spListener);
        PLAYER_ONE = sp.getString("Player1", null);
        PLAYER_TWO = sp.getString("Player2", null);
        if (PLAYER_ONE == null || PLAYER_TWO == null) {
            PLAYER_ONE = "Player1";
            PLAYER_TWO = "Player2";
            sp.edit().putString("Player1", "").apply();
            sp.edit().putString("Player2", "").apply();
        }


        Gson gson = new Gson();
        Type questionType = new TypeToken<ArrayList<Question>>() {
        }.getType();

        String stored_questionListOne = sp.getString("questionListOne", null);
        if (stored_questionListOne != null) {
            questionListOne = gson.fromJson(stored_questionListOne, questionType);
        } else {
            questionListOne = questions(LEVEL_ONE);

        }

        String stored_questionListTwo = sp.getString("questionListTwo", null);
        if (stored_questionListTwo != null) {
            questionListTwo = gson.fromJson(stored_questionListTwo, questionType);
        } else {
            questionListTwo = questions(LEVEL_TWO);
        }

        String stored_questionListThree = sp.getString("questionListThree", null);
        if (stored_questionListThree != null) {
            questionListThree = gson.fromJson(stored_questionListThree, questionType);
        } else {
            questionListThree = questions(LEVEL_THREE);
        }

        currentLevel = sp.getString("currentLevel", LEVEL_ONE);


        playerOneTotalL1 = sp.getInt("playerOneTotalL1", 0);
        playerTwoTotalL1 = sp.getInt("playerTwoTotalL1", 0);

        playerOneTotalL2 = sp.getInt("playerOneTotalL2", 0);
        playerTwoTotalL2 = sp.getInt("playerTwoTotalL2", 0);

        playerOneTotalL3 = sp.getInt("playerOneTotalL3", 0);
        playerTwoTotalL3 = sp.getInt("playerTwoTotalL3", 0);


        String lastPlayer = sp.getString("lastPlayer", null);
        if (lastPlayer != null) {
            currentPlayer = lastPlayer;
            togglePlayers();
        } else {
            currentPlayer = PLAYER_TWO;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Question> questions(String level) {

        String[] current;


        String[] set1 = new String[]{
                "Given the choice of anyone in the world, whom would you want as a dinner guest?",
                "Would you like to be famous? In what way?",
                "Before making a telephone call, do you ever rehearse what you are going to say? Why?",
                "What would constitute a “perfect” day for you?",
                "When did you last sing to yourself? To someone else?",
                "If you were able to live to the age of 90 and retain either the mind or body of a 30-year-old for the last 60 years of your life, which would you want?",
                "Do you have a secret hunch about how you will die?",
                "Name three things you and I appear to have in common.",
                "For what in your life do you feel most grateful?",
                "If you could change anything about the way you were raised, what would it be?",
                "Take four minutes and tell me your life story in as much detail as possible.",
                "If you could wake up tomorrow having gained any one quality or ability, what would it be?"
        };
        String[] set2 = new String[]{
                "If someone could tell you the truth about yourself, your life, the future or anything else, what would you want to know?",
                "Is there something that you’ve dreamed of doing for a long time? Why haven’t you done it?",
                "What is the greatest accomplishment of your life?",
                "What do you value most in a friendship?",
                "What is your most treasured memory?",
                "What is your most terrible memory?",
                "If you knew that in one year you would die suddenly, would you change anything about the way you are now living? Why?",
                "What does friendship mean to you?",
                "What roles do love and affection play in your life?",
                "Alternate sharing something you consider a positive characteristic of me. Share a total of five items.",
                "How close and warm is your family? Do you feel your childhood was happier than most other people’s?",
                "How do you feel about your relationship with your mother?"
        };
        String[] set3 = new String[]{
                "Make three true “we” statements each. For instance, “We are both in this room feeling ... “",
                "Complete this sentence: “I wish I had someone with whom I could share ... “",
                "If you were going to become a close friend with me, please share what would be important for me to know.",
                "Tell me what you like about me; be very honest this time, saying things that you might not say to someone you’ve just met.",
                "Share with me an embarrassing moment in your life.",
                "When did you last cry in front of another person? By yourself?",
                "Tell me something that you like about me already.",
                "What, if something, is too serious to be joked about? Would you joke about it?",
                "If you were to die this evening with no opportunity to communicate with anyone, what would you most regret not having told someone? Why haven’t you told them yet?",
                "Your house, containing everything you own, catches fire. After saving your loved ones and pets, you have time to safely make a final dash to save any one item. What would it be? Why?",
                "Of all the people in your family, whose death would you find most disturbing? Why?",
                "Share a personal problem and ask my advice on how I might handle it. Also, ask me to reflect back to you how I seem to be feeling about the problem you have chosen.",

        };
        ArrayList<Question> questionArrayList = new ArrayList<>();
        if (level.equals(LEVEL_ONE)) {
            current = set1;

            for (int i = 0; i < current.length; i++
            ) {
                Question question = new Question(current[i]);
                questionArrayList.add(question);
            }
        }

        if (level.equals(LEVEL_TWO)) {
            current = set2;
            for (int i = 0; i < current.length; i++
            ) {
                Question question = new Question(current[i]);
                questionArrayList.add(question);
            }
        }

        if (level.equals(LEVEL_THREE)) {
            current = set3;
            for (int i = 0; i < current.length; i++
            ) {
                Question question = new Question(current[i]);
                questionArrayList.add(question);
            }
        }


        return questionArrayList;
    }

    private void initView() {
        dropView = (LinearLayout) findViewById(R.id.dropView);
    }

    @Override
    public void results(Question question) {
        String player = question.getAnsweredBy();
        if (currentLevel.equals(LEVEL_ONE)) {
            if (player.equals(PLAYER_ONE)) {

                playerOneTotalL1 += question.getPoints();
            }
            if (player.equals(PLAYER_TWO)) {
                playerTwoTotalL1 += question.getPoints();
            }

        }
        if (currentLevel.equals(LEVEL_TWO)) {
            if (player.equals(PLAYER_ONE)) {
                playerOneTotalL2 += question.getPoints();
            }
            if (player.equals(PLAYER_TWO)) {
                playerTwoTotalL2 += question.getPoints();
            }
        }
        if (currentLevel.equals(LEVEL_THREE)) {
            if (player.equals(PLAYER_ONE)) {
                playerOneTotalL3 += question.getPoints();
            }
            if (player.equals(PLAYER_TWO)) {
                playerTwoTotalL3 += question.getPoints();
            }
        }


        if (currentLevel.equals(LEVEL_ONE)) {
            questionListOne.remove(num);
        }
        if (currentLevel.equals(LEVEL_TWO)) {
            questionListTwo.remove(num);
        }
        if (currentLevel.equals(LEVEL_THREE)) {
            questionListThree.remove(num);
        }

        saveState(player);
        togglePlayers();
    }

    private void saveState(String lastPlayer) {
        Gson gson = new Gson();
        final SharedPreferences sp = getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        spEditor.putString("questionListOne", gson.toJson(questionListOne));
        spEditor.putString("questionListTwo", gson.toJson(questionListTwo));
        spEditor.putString("questionListThree", gson.toJson(questionListThree));


        spEditor.putInt("playerTwoTotalL1", playerTwoTotalL1);
        spEditor.putInt("playerOneTotalL1", playerOneTotalL1);

        spEditor.putInt("playerOneTotalL2", playerOneTotalL2);
        spEditor.putInt("playerTwoTotalL2", playerTwoTotalL2);

        spEditor.putInt("playerOneTotalL3", playerOneTotalL3);
        spEditor.putInt("playerTwoTotalL3", playerTwoTotalL3);

        spEditor.putString("currentLevel", currentLevel);

        spEditor.putString("lastPlayer", lastPlayer);

        spEditor.apply();


    }

    private void togglePlayers() {
        if (currentPlayer.equals(PLAYER_ONE)) {
            currentPlayer = PLAYER_TWO;
        } else if (currentPlayer.equals(PLAYER_TWO)) {
            currentPlayer = PLAYER_ONE;
        } else {
            currentPlayer = PLAYER_ONE;
        }
    }

    @Override
    public void restart() {
        final SharedPreferences sp = getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.remove("questionListOne");
        spEditor.remove("questionListTwo");
        spEditor.remove("questionListThree");

        spEditor.remove("playerTwoTotalL1");
        spEditor.remove("playerOneTotalL1");

        spEditor.remove("playerOneTotalL2");
        spEditor.remove("playerTwoTotalL2");

        spEditor.remove("playerOneTotalL3");
        spEditor.remove("playerTwoTotalL3");
        spEditor.remove("currentLevel");

        spEditor.putString("lastPlayer", PLAYER_TWO);
        spEditor.putString("currentLevel", LEVEL_ONE);
        currentLevel = LEVEL_ONE;
        currentPlayer = PLAYER_ONE;

        questionListOne = questions(LEVEL_ONE);
        questionListTwo = questions(LEVEL_TWO);
        questionListThree = questions(LEVEL_THREE);

        playerOneTotalL1 = 0;
        playerTwoTotalL1 = 0;

        playerOneTotalL2 = 0;
        playerTwoTotalL2 = 0;

        playerOneTotalL3 = 0;
        playerTwoTotalL3 = 0;
        spEditor.commit();

    }

    @Override
    public void play() {

        if (currentLevel.equals(LEVEL_ONE)) {
            if (questionListOne.size() <= 0) {
                currentLevel = LEVEL_TWO;

            } else {
                Random random = new Random();
                num = random.nextInt(questionListOne.size());
                FragmentManager fragmentManager = getSupportFragmentManager();
                Question qn = questionListOne.get(num);
                qn.setAnsweredBy(currentPlayer);
//                togglePlayers();
                UserFragment userFragment = new UserFragment(qn);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dropView, userFragment, "userFragment");
                fragmentTransaction.commit();
            }
        }

        if (currentLevel.equals(LEVEL_TWO)) {
            if (questionListTwo.size() <= 0) {
                currentLevel = LEVEL_THREE;
            } else {
                Random random = new Random();
                num = random.nextInt(questionListTwo.size());
                Question qn = questionListTwo.get(num);
                qn.setAnsweredBy(currentPlayer);
                FragmentManager fragmentManager = getSupportFragmentManager();
                UserFragment userFragment = new UserFragment(qn);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dropView, userFragment, "userFragment");
//                togglePlayers();
                fragmentTransaction.commit();
            }
        }
        if (currentLevel.equals(LEVEL_THREE)) {
            if (questionListThree.size() <= 0) {
                currentLevel = "done";
                Toast.makeText(this, "Levels Complete", Toast.LENGTH_SHORT).show();
            } else {
                Random random = new Random();
                num = random.nextInt(questionListThree.size());
                Question qn = questionListThree.get(num);
                qn.setAnsweredBy(currentPlayer);
                FragmentManager fragmentManager = getSupportFragmentManager();
                UserFragment userFragment = new UserFragment(qn);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dropView, userFragment, "userFragment");
//                togglePlayers();
                fragmentTransaction.commit();
            }
        }
        if (currentLevel.equals("done")) {

            currentLevel = "done";
            Toast.makeText(this, "Levels Complete Thank you for playing", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            MarksFragment marksFragment = new MarksFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dropView, marksFragment, "marksFragment");
            fragmentTransaction.commit();

        }


    }

    @Override
    public String getLevel() {
        return currentLevel;
    }

    @Override
    public ArrayList<LevelResults> getLevelResults() {
        LevelResults levelone = new LevelResults(LEVEL_ONE, playerOneTotalL1, playerTwoTotalL1);
        LevelResults leveltwo = new LevelResults(LEVEL_TWO, playerOneTotalL2, playerTwoTotalL2);
        LevelResults levelthree = new LevelResults(LEVEL_THREE, playerOneTotalL3, playerTwoTotalL3);
        levelResultsList = new ArrayList<>();
        levelResultsList.add(levelone);
        levelResultsList.add(leveltwo);
        levelResultsList.add(levelthree);
        return levelResultsList;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
}
