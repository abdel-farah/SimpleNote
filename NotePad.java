package com.simplenote.abdulazizfarah.firstapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NotePad extends AppCompatActivity {
    EditText Content;
    String stringTitle;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);
        Intent receivedIntent = getIntent();
        stringTitle = receivedIntent.getStringExtra("title");
        setTitle(stringTitle);
        Content = (EditText) findViewById(R.id.Content);
        mDatabaseHelper = new DatabaseHelper(this);

        //initialize_dialog();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.notepad, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_check){
            AddData(stringTitle, Content.getText().toString());



            finish();
        }
        return super.onOptionsItemSelected(item);

    }




    private void initialize_dialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NotePad.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box, null);
        final EditText title = (EditText) mView.findViewById(R.id.Title);
        Button mOk = (Button) mView.findViewById(R.id.Ok);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();


        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.9f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCanceledOnTouchOutside(false);


        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()){
                    Toast.makeText(NotePad.this, "Please enter a title", Toast.LENGTH_LONG).show();
                }
                else {

                    //Title.setText(title.getText().toString());
                    setTitle(title.getText().toString());
                    dialog.cancel();
                }
            }
        });
    }

    public void AddData(String title, String note){
        boolean insertData = mDatabaseHelper.addData(title, note);

        if (insertData){
            toastMessage("Saved sucessfully");
        } else{
            toastMessage("Something went wrong");
        }

    }

    private void toastMessage(String message){
        Toast myToast = Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG);
        myToast.show();
    }





}
