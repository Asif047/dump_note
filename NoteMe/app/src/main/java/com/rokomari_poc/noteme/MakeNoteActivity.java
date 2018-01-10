package com.rokomari_poc.noteme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MakeNoteActivity extends AppCompatActivity {
    private ImageView ivNote,ivTodo,ivRememberMe,ivTag,ivUrgencies,ivWorkUpdate,ivTitleIcon,ivOffice,ivPersonal,ivMail,ivUrl,ivPhone;
    private EditText etTitle,etDetails;
    private Button btnNoteMe;
    private MyPostRequest myPostRequest;

    private Toolbar toolbar;

    private String date_time,status="1",category="2",phone="01733547364",url="rokomari.com",mail="rokomari.com",response_msg;
    private String account_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);


        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);

        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        myPostRequest=new MyPostRequest(this);

        ivNote=findViewById(R.id.imageview_note);
        ivTodo=findViewById(R.id.imageview_todo);
        ivRememberMe=findViewById(R.id.imageview_remeber_me);
        ivTag=findViewById(R.id.imageview_tag);
        ivUrgencies=findViewById(R.id.imageview_urgencies);
        ivWorkUpdate=findViewById(R.id.imageview_work_update);
        ivTitleIcon=findViewById(R.id.imageview_title_icon);
        ivOffice=findViewById(R.id.imageview_office);
        ivPersonal=findViewById(R.id.imageview_personal);
        ivMail=findViewById(R.id.imageview_action_bar_mail);
        ivUrl=findViewById(R.id.imageview_action_bar_url);
        ivPhone=findViewById(R.id.imageview_action_bar_phone);
        etTitle=findViewById(R.id.edittext_title);
        etDetails=findViewById(R.id.edittext_details);
        btnNoteMe=findViewById(R.id.button_note_me);

        //for getting action bar starts

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //for getting action bar ends

        //to get time starts
        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date_time = sd.format(c.getTime());
        //to get time ends

        Drawable new_image= getResources().getDrawable(R.drawable.ic_todo_black);
        ivTitleIcon.setBackgroundDrawable(new_image);


        ivNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="1";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_note_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="2";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_todo_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivRememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="3";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_remember_me_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="4";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_tag_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivUrgencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="5";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_urgencies_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivWorkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="6";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_work_update_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="7";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_office);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

        ivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="8";
                Drawable new_image= getResources().getDrawable(R.drawable.ic_personal_black);
                ivTitleIcon.setBackgroundDrawable(new_image);
            }
        });

//        ivPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MakeNoteActivity.this,"Phone",Toast.LENGTH_SHORT).show();
//            }
//        });

        btnNoteMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etTitle.getText().toString().isEmpty())
                    etTitle.setError("Please enter the title");
                if(etDetails.getText().toString().isEmpty())
                    etDetails.setError("Please enter the details");

                if(!etTitle.getText().toString().isEmpty()&&!etDetails.getText().toString().isEmpty())
                {
                    myPostRequest.postData(""+date_time,""+status,""+etTitle.getText(),""+etDetails.getText(),""+url,""+mail,""+phone,""+category,account_id);
                }

                //Toast.makeText(MakeNoteActivity.this,""+response_msg,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            // Handle item selection
            switch (item.getItemId()) {
                case R.id.menu_undone:
                    status="1";
                    Toast.makeText(MakeNoteActivity.this,"Undone",Toast.LENGTH_SHORT).show();
                    return false;
                case R.id.menu_done:
                    status="2";
                    Toast.makeText(MakeNoteActivity.this,"Done",Toast.LENGTH_SHORT).show();
                    return false;
                case R.id.menu_completed:
                    status="3";
                    Toast.makeText(MakeNoteActivity.this,"Completed",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_abandoned:
                    status="4";
                    Toast.makeText(MakeNoteActivity.this,"Abandoned",Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_me_menu, menu);
        return true;
    }


    public void actionCall(View view) {

        //showing alert box starts
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);

       // alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Phone Number");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
//                Editable YouEditTextValue = edittext.getText();
                //OR
                phone = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        overridePendingTransition(R.anim.move_right_to_left_enter, R.anim.move_right_to_left_exit);
        alert.show();
        //showing alert box ends

        Toast.makeText(MakeNoteActivity.this,"Phone",Toast.LENGTH_SHORT).show();
    }

    public void actionMail(View view) {
        //showing alert box starts
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);

        // alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Mail Address");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
//                Editable YouEditTextValue = edittext.getText();
                //OR
                mail = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
        //showing alert box ends

        Toast.makeText(MakeNoteActivity.this,"Mail",Toast.LENGTH_SHORT).show();
    }

    public void actionUrl(View view) {

        //showing alert box starts
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);

        // alert.setMessage("Enter Your Message");
        alert.setTitle("Enter URL Address");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
//                Editable YouEditTextValue = edittext.getText();
                //OR
                url = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
        //showing alert box ends

        Toast.makeText(MakeNoteActivity.this,"Url",Toast.LENGTH_SHORT).show();
    }
}
