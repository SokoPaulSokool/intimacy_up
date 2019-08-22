package com.sokool.intimacyup;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.sokool.intimacyup.helpers.HelperFuctions;
import com.sokool.intimacyup.model.Question;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected TextView userNameTextView;
    protected TextView questionTextView;
    protected ScrollView questionViewScrollView;
    protected Button awardButton;
    protected Spinner ratingSpinner;

    Question question;

    Records records;

    public UserFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public UserFragment(Question question) {
        // Required empty public constructor
        this.question = question;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.user_fragment, container, false);
        initView(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        randomBackground();
        records = (Records) getActivity();
        questionTextView.setText(question.getQuestion());
        userNameTextView.setText(HelperFuctions.capitalize(question.getAnsweredBy()));
        String[] strings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.rating_item, strings);
        ratingSpinner.setAdapter(adaptor);
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
        if (view.getId() == R.id.award_button) {
            String mrks = ratingSpinner.getSelectedItem().toString();

            if (!TextUtils.isEmpty(mrks)) {
                int mark = Integer.parseInt(mrks);
                if (mark > 10 || mark < 0) {

                    Toast.makeText(getContext(), "Number must be between 0 and 10", Toast.LENGTH_SHORT).show();
                } else {
                    question.setPoints(mark);
                    records.results(question);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    MarksFragment marksFragment = new MarksFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.dropView, marksFragment, "marksFragment");
                    fragmentTransaction.commit();
                }

            } else {
                Toast.makeText(getContext(), "First give marks", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void randomBackground() {
        //            String[] gradients = new String[]{
//                    "gradient_aubergine",
//                    "gradient_cherry",
//                    "gradient_frost",
//                    "gradient_kashmir",
//                    "gradient_piglet",
//                    "gradient_purplelove",
//                    "gradient_roseanna",
//                    "gradient_royal",
//                    "gradient_socialive"
//            };

        String[] gradients = new String[]{
                "gradient_aubergine",
                "gradient_cherry",
                "gradient_frost",
                "gradient_kashmir",
                "gradient_purplelove",
                "gradient_royal",
                "gradient_socialive"
        };

        Random random = new Random();
        int num = random.nextInt(6);

        questionViewScrollView.setBackgroundResource(getDrawableIdFromString(getContext(), gradients[num]));


    }

    private void initView(View rootView) {
        userNameTextView = (TextView) rootView.findViewById(R.id.user_name_text_view);
        questionTextView = (TextView) rootView.findViewById(R.id.question_text_view);
        questionViewScrollView = (ScrollView) rootView.findViewById(R.id.question_view_scroll_view);
        awardButton = (Button) rootView.findViewById(R.id.award_button);
        awardButton.setOnClickListener(UserFragment.this);
        ratingSpinner = (Spinner) rootView.findViewById(R.id.rating_spinner);
    }

    public interface Records {
        public void results(Question question);
    }
}
