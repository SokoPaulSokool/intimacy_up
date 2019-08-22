package com.sokool.intimacyup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NameFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText player1Edt = view.findViewById(R.id.player1);
        final EditText player2Edt = view.findViewById(R.id.player2);

        final Button submit = view.findViewById(R.id.submit);
        submit.setVisibility(View.GONE);
        final Button exit = view.findViewById(R.id.exit);
        exit.setVisibility(View.VISIBLE);

        final TextView message = view.findViewById(R.id.message);
        message.setVisibility(View.GONE);

        final SharedPreferences sp = getActivity().getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        final String player1Svd = sp.getString("Player1", null);
        final String player2Svd = sp.getString("Player2", null);
        final String lastPlayer = sp.getString("lastPlayer", null);
        if (!TextUtils.isEmpty(player1Svd)) {
            player1Edt.setText(player1Svd);
        }
        if (!TextUtils.isEmpty(player2Svd)) {
            player2Edt.setText(player2Svd);
        }
        if (TextUtils.isEmpty(player1Svd) && TextUtils.isEmpty(player2Svd)) {
            exit.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
        player1Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String changed = s + "";
                    if (!TextUtils.isEmpty(player1Svd) && changed.equals(player1Svd)) {
                        submit.setVisibility(View.GONE);
                        message.setVisibility(View.GONE);
                        exit.setVisibility(View.VISIBLE);
                    } else if(TextUtils.isEmpty(player1Svd)){
                        submit.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        exit.setVisibility(View.GONE);
                    } else {
                        submit.setVisibility(View.VISIBLE);
                        message.setVisibility(View.VISIBLE);
                        exit.setVisibility(View.GONE);
                    }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        player2Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String changed = s + "";
                if (!TextUtils.isEmpty(player2Svd) && changed.equals(player2Svd)) {
                    submit.setVisibility(View.GONE);
                    message.setVisibility(View.GONE);
                    exit.setVisibility(View.VISIBLE);
                } else if(TextUtils.isEmpty(player2Svd)){
                    submit.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    exit.setVisibility(View.GONE);
                } else {
                    submit.setVisibility(View.VISIBLE);
                    message.setVisibility(View.VISIBLE);
                    exit.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player1 = player1Edt.getText().toString().trim();
                String player2 = player2Edt.getText().toString().trim();
                if (TextUtils.isEmpty(player1) || TextUtils.isEmpty(player1)) {
                    Toast.makeText(getActivity().getBaseContext(), "All players must be set", Toast.LENGTH_SHORT).show();
                } else if(player1.equals(player2)){
                    Toast.makeText(getActivity().getBaseContext(), "Names must be different", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences.Editor editor = sp.edit();
                    if (lastPlayer == null) {
                        editor.putString("lastPlayer", player1);
                    }
                    editor.putString("Player1", player1);
                    editor.putString("Player2", player2);
                    editor.apply();
                    dismiss();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_name, container, false);
        return view;
    }


}
