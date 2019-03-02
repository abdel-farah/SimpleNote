package com.simplenote.abdulazizfarah.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   FloatingActionButton fab;
   DatabaseHelper mDatabaseHelper;
   private ListView mListView;
   private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Simple Note");
        mDatabaseHelper = new DatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.listView);
        populateListView();



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initialize_dialog();
                // startActivity(new Intent(MainActivity.this, NotePad.class));
               /*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box, null);
               final EditText title = (EditText) mView.findViewById(R.id.Title);
                Button mOk = (Button) mView.findViewById(R.id.Ok);

                mOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(title.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter a title", Toast.LENGTH_LONG).show();
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, NotePad.class));

                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                */
             }
        });


    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the listview");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //Gets title from first column
            listData.add(data.getString(0));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + title);
                Cursor data = mDatabaseHelper.getNote(title);
                String note = "";
                while(data.moveToNext()){

                     note = data.getString(0);
                }

                Log.d(TAG, "onItemClick: The note is: " + note);
                Intent editScreenIntent = new Intent(MainActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("title", title);
                editScreenIntent.putExtra("note", note );
                startActivity(editScreenIntent);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //loadNotes();
        populateListView();

    }


    private void initialize_dialog() {
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box, null);
        final EditText title = (EditText) mView.findViewById(R.id.Title);
        Button mOk = (Button) mView.findViewById(R.id.Ok);
        mBuilder.setView(mView);
        final android.app.AlertDialog dialog = mBuilder.create();


        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.9f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCanceledOnTouchOutside(false);


        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a title", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent notePadIntent = new Intent(MainActivity.this, NotePad.class);
                    notePadIntent.putExtra("title", title.getText().toString());
                    dialog.cancel();
                    startActivity(notePadIntent);

                }
            }
        });
    }


    private void toastMessage(String message){
        Toast myToast = Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG);
        myToast.show();
    }
    public void myButtonTap(View v){
        Toast myToast = Toast.makeText(getApplicationContext(), "Ouch!", Toast.LENGTH_LONG);
        myToast.show();
    }
}
