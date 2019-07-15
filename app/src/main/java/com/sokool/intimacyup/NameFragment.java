package com.sokool.intimacyup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameFragment newInstance(String param1, String param2) {
        NameFragment fragment = new NameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText player1Edt = view.findViewById(R.id.player1);
        final EditText player2Edt= view.findViewById(R.id.player2);
        Button submit= view.findViewById(R.id.submit);
        final SharedPreferences sp = getActivity().getSharedPreferences("PLAYERS", Context.MODE_PRIVATE);
        String player1Svd = sp.getString("Player1",null);
        String player2Svd = sp.getString("Player2",null);
        if(!TextUtils.isEmpty(player1Svd)){
            player1Edt.setText(player1Svd);
        }
 if(!TextUtils.isEmpty(player2Svd)){
     player2Edt.setText(player2Svd);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player1 = player1Edt.getText().toString();
                String player2 = player2Edt.getText().toString();
                if(TextUtils.isEmpty(player1) || TextUtils.isEmpty(player1)){
                    Toast.makeText(getActivity().getBaseContext(), "All players must be set", Toast.LENGTH_SHORT).show();
                } else{

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Player1", player1);
                    editor.putString("Player2", player2);
                    editor.apply();
                    dismiss();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_name, container, false);
        return  view;
    }





}
