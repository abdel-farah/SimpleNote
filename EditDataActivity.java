package com.simplenote.abdulazizfarah.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by abdulazizfarah on 2018-04-23.
 */

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave, btnDelete;
    private EditText edit_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedTitle;
    private String selectedNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        edit_item = (EditText) findViewById((R.id.editText));
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedTitle = receivedIntent.getStringExtra("title");
        selectedNote = receivedIntent.getStringExtra("note");
        setTitle(selectedTitle);
        edit_item.setText(selectedNote);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_check){
            String itm = edit_item.getText().toString();
            if(!itm.equals("")){
                mDatabaseHelper.updateNote(itm, selectedTitle, selectedNote);
                toastMessage("Note saved!");
                finish();

            }
            else {
                toastMessage("You must enter a name");
            }



            //finish();
        }

        if (item.getItemId() == R.id.delete){
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this note?")
                    //.setIcon(android.R.drawable.ic_dialog_alert)

                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }})

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    mDatabaseHelper.deleteNote(selectedTitle, selectedNote);
                    edit_item.setText("");
                    toastMessage("Note deleted");
                    finish();
                }}).show();
          /*
            mDatabaseHelper.deleteNote(selectedTitle, selectedNote);
            edit_item.setText("");
            toastMessage("Note deleted");
            finish();
            */
        }
        return super.onOptionsItemSelected(item);

    }

    private void toastMessage(String message){
        Toast myToast = Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG);
        myToast.show();
    }



}
