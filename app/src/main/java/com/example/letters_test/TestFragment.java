package com.example.letters_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TestFragment extends Fragment {
    private TextView letterTextView, answerTextView;
    private char[] skyLetters = {'b', 'd', 'f', 'h', 'k', 'l', 't'};
    private char[] grassLetters = {'g', 'j', 'p', 'q', 'y'};
    private char[] rootLetters = {'a', 'c', 'e', 'i', 'm', 'n', 'o', 'r', 's', 'u', 'v', 'w', 'x', 'z'};
    private ShiftDatabaseHelper databaseHelper;
    private List<String> currentShiftAnswers;
    private int currentQuestionCount = 0;
    private int currentShiftNumber = 1;
    private String answerString = "";
    String L;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
        databaseHelper = new ShiftDatabaseHelper(requireContext());
        currentShiftAnswers = new ArrayList<>();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);

L=getRandomLetter();
        letterTextView = rootView.findViewById(R.id.letter_text_view);
        letterTextView.setText(L);

        answerTextView = rootView.findViewById(R.id.answer_text_view);

        Button skyButton = rootView.findViewById(R.id.sky_button);
        skyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Sky Letter");
            }
        });

        Button grassButton = rootView.findViewById(R.id.grass_button);
        grassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Grass Letter");
            }
        });

        Button rootButton = rootView.findViewById(R.id.root_button);
        rootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Root Letter");
            }
        });

        return rootView;
        }
    private void checkAnswer(String expectedAnswer) {



        if (expectedAnswer.equals(answerString)) {
            answerTextView.setText("Correct! The letter '"+L+"' is a "+answerString+".");
            currentShiftAnswers.add("Correct!"+"Letter = '"+L+"' "+"is "+answerString);
        } else {
            answerTextView.setText("Incorrect! The letter ' "+L+"' is a "+answerString+".");
            currentShiftAnswers.add("InCorrect!"+"Letter = '"+L+"' "+"is not "+expectedAnswer);
        }
        currentQuestionCount++;

        if (currentQuestionCount == 5) {
            processShiftCompletion();
        }

        // Wait for 5 seconds and create a new question
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                L=getRandomLetter();
                letterTextView.setText(L);
                answerTextView.setText("");
            }
        }, 5000); // 5000 milliseconds = 5 seconds
    }

    private void processShiftCompletion() {
        if (!currentShiftAnswers.isEmpty()) {
            databaseHelper.addShift(currentShiftNumber, currentShiftAnswers);
            currentShiftNumber++;
            currentQuestionCount = 0;
            currentShiftAnswers.clear();
        }
    }
    private String getRandomLetter() {
        Random random = new Random();
        int category = random.nextInt(3);
        char letter;
        switch (category) {
            case 0:
                letter = skyLetters[random.nextInt(skyLetters.length)];
                answerString = "Sky Letter";
                break;
            case 1:
                letter = grassLetters[random.nextInt(grassLetters.length)];
                answerString = "Grass Letter";
                break;
            default:
                letter = rootLetters[random.nextInt(rootLetters.length)];
                answerString = "Root Letter";
                break;
        }
        return String.valueOf(letter);
    }
}


