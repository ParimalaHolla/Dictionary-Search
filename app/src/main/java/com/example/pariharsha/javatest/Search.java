package com.example.pariharsha.javatest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Search extends AppCompatActivity {

    private TextView dictData,length;

    private Button findLength;

    private EditText startEdtBx, endEdtBx;

    Set<String> dictionary = new HashSet<String>();

    {
        dictionary.add("lack");
        dictionary.add("hack");
        dictionary.add("lick");
        dictionary.add("sick");
        dictionary.add("sock");
        dictionary.add("mock");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*Initiallizing views*/
        dictData = (TextView) findViewById(R.id.dictData);
        findLength = (Button) findViewById(R.id.findBtn);
        startEdtBx = (EditText) findViewById(R.id.startWordTxt);
        endEdtBx = (EditText) findViewById(R.id.endWordTxt);
        length = (TextView) findViewById(R.id.lengthTxt);

        List<String> list = new ArrayList<String>(dictionary);
        for (int i = 0; i < list.size(); i++) {
            dictData.setText(dictData.getText() + "[" + list.get(i) + "]");
        }


        findLength.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String startWord = startEdtBx.getText().toString();
                String endWord = endEdtBx.getText().toString();

                /*checking editboxes are not empty*/
                if (!startWord.isEmpty() && !endWord.isEmpty()) {

                    /* Passing copy of dictionary */
                    Set<String> subDictionary = new HashSet<String>();
                    subDictionary.addAll(dictionary);

                    /* Finding shortest length between two words */
                    Word shortestLength = getShortestTransformation(startEdtBx.getText().toString(), endEdtBx.getText().toString(), subDictionary);

                    if (shortestLength != null) {
                        length.setText(" Length is : " + shortestLength.getPathLength() + "\n Path is : " + shortestLength.getTransformationPath());
                    } else {

                        length.setText("Word is not present in dictionary");
                    }
                } else {
                    Toast.makeText(Search.this, "Please enter start and end words", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    // get shortest path using BFS
    private static Word getShortestTransformation(String startWord, String endWord, Set<String> dictionary) {

        if (dictionary.contains(startWord) && dictionary.contains(endWord)) {

            List<String> shortestPath = new LinkedList<String>();
            shortestPath.add(startWord);

            Queue<Word> queue = new LinkedList<Word>();

            // adding start wor d to queue
            queue.add(new Word(shortestPath, 0, startWord));

            // begin with start word and remove from dictionary to avoid visiting again the same word
            dictionary.remove(startWord);

            // queue iteration until queue is empty or found end word.
            while (!queue.isEmpty() && !queue.peek().equals(endWord)) {
                Word ladder = queue.remove();

                if (endWord.equals(ladder.setStartWord())) {
                    return ladder;
                }

                Iterator<String> i = dictionary.iterator();
                while (i.hasNext()) {
                    String string = i.next();

                    if (letterDifferByOne(string, ladder.setStartWord())) {

                        List<String> list = new LinkedList<String>(ladder.getTransformationPath());
                        list.add(string);

                        // letter difference between the two words is 1, add that to queue for iteration
                        queue.add(new Word(list, ladder.getPathLength() + 1, string));
                        System.out.print("values in queue" + queue.element().toString());


                        i.remove();
                    }
                }
            }

            // returns the head of the node but do not remove from the queue
            if (!queue.isEmpty()) {
                return queue.peek();

            }
        }

        return null;
    }


    // characters difference between two words

    private static boolean letterDifferByOne(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int differenceCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                differenceCount++;
            }
        }
        return (differenceCount == 1);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Search.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
