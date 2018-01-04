package com.rokomari_poc.noteme.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rokomari_poc.noteme.AlertBox.ShowAlert;
import com.rokomari_poc.noteme.AllNotes.AllNotesActivity;
import com.rokomari_poc.noteme.LogIn.LogInActivity;
import com.rokomari_poc.noteme.MakeNoteActivity;
import com.rokomari_poc.noteme.R;
import com.rokomari_poc.noteme.RememberMe.RememberMeActivity;
import com.rokomari_poc.noteme.Utils.NetworkUtils.MyNetworkCheck;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout dl ;
    private ActionBarDrawerToggle toggle ;
    private Toolbar toolbar;

    private TextView tvNameHeader,tvEmailHeader;

    static SweetAlertDialog pDialog;
    private ShowAlert showAlert;
    private MyNetworkCheck myNetworkCheck = new MyNetworkCheck();

    private String account_id="",email="",first_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting account id starts
        SharedPreferences prefs=getSharedPreferences("Profile_PREF",MODE_PRIVATE);
        String restoredAccount=prefs.getString("account_id",null);
        String restoredEmail=prefs.getString("email",null);
        String restoredName=prefs.getString("first_name",null);

        if(restoredAccount!=null)
        {
            account_id=prefs.getString("account_id","No account defined");
        }

        if(restoredEmail!=null)
        {
            email=prefs.getString("email","No email defined");
        }

        if(restoredName!=null)
        {
            first_name=prefs.getString("first_name","No name defined");
        }

        //getting account id ends

        tvNameHeader=findViewById(R.id.textview_name_header);
        tvEmailHeader=findViewById(R.id.textview_email_header);



        LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);

        View vi = inflater.inflate(R.layout.header, null); //log.xml is your file.
        TextView tv = (TextView)vi.findViewById(R.id.textview_email_header);

        tv.setText("hello");




        Toast.makeText(HomeActivity.this,email,Toast.LENGTH_SHORT).show();

        dl = (DrawerLayout) findViewById(R.id.dl);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        showAlert=new ShowAlert(this);

        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        dl.addDrawerListener(toggle);

        toggle = new ActionBarDrawerToggle(this,dl,toolbar,R.string.open,R.string.close);

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerContent(nvDrawer);

        //checking net connection starts
        if (!myNetworkCheck.isConnected(HomeActivity.this))
            showAlert.showWarningNetHomeActivity();
        //checking net connection ends

    }
    public void selectItemDrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.home:
//                Toast.makeText(RememberMeActivity.this,"hello",Toast.LENGTH_LONG).show();
//                fragmentClass = Network.class;
                break;
            case R.id.all_notes:
                Intent intent1=new Intent(HomeActivity.this,AllNotesActivity.class);
                startActivity(intent1);
                break;
            case R.id.make_notes:
                Intent intent=new Intent(HomeActivity.this,MakeNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.remember_me:
                Intent intent2=new Intent(HomeActivity.this,RememberMeActivity.class);
                startActivity(intent2);
                break;
            case R.id.chat:

                break;
            case R.id.settings:

                break;

            default:
                // fragmentClass = Network.class;
        }
        try {
//            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        //setTitle(menuItem.getTitle());
        dl.closeDrawers();


    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    public void logOutClick(MenuItem item) {
        Intent intent=new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(intent);
    }
}
