package dcheungaa.procal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fx50.Fx50Parser;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean call_load = false;
    private KeyPad_init keyPad;
    private KeyPad_init varPad;
    private KeyPad_init cmdPad;
    private KeyPad_init constPad;
    public static TextView matrixDisplay;
    public static TextView resultDisplay;

    //public static List <List<String>> vars = new ArrayList<>();
    public static List <String> vars = new ArrayList<>();
    public static Tokens tokens = new Tokens();

    public static TextView cursor;

    public static int fontWidth;
    public static int fontHeight;

    public static HorizontalScrollView scrollView;


    public static List<CalcBtn> calcBtns = new ArrayList<>();

    public static ScrollView svVar;
    public static ScrollView svCmd;
    public static ScrollView svConst;

    public static int screenWidth;
    public static int screenHeight;

    public static LinearLayout llkeyPad;
    public static Context context;

    public static Fx50IO fx50IO = new Fx50IO();
    public static Fx50Parser fx50Parser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            fx50Parser = new Fx50Parser(fx50IO);
        } catch (Exception e) {
            System.out.println("Fx50 Parser failed to initiate: " + e.getMessage());
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        InputHandler.setContext(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //
        //DrawerLayout.LayoutParams lp =(android.support.v4.widget.DrawerLayout.LayoutParams) drawer.getLayoutParams();
        //lp.setMargins(0,200,0,200);
        //drawer.setLayoutParams(lp);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        resultDisplay = (TextView) findViewById(R.id.resultDisplay);

        //Set font
        matrixDisplay = (TextView) findViewById(R.id.matrixDisplay);
        final Typeface FONT_FX50 = Typeface.createFromAsset(getAssets(), "fonts/Fx50.otf");
        matrixDisplay.setTypeface(FONT_FX50);
        cursor = (TextView) findViewById(R.id.tv_cursor);
        cursor.setTypeface(FONT_FX50);

        llkeyPad = (LinearLayout) findViewById(R.id.llKeyPad);

        //keypad gen
        final InputStream inSKey = getResources().openRawResource(R.raw.keypad);
        Display display = getWindowManager().getDefaultDisplay();
        RelativeLayout cm = (RelativeLayout) findViewById(R.id.content_main);
        LinearLayout lls = (LinearLayout) findViewById(R.id.llScreen);
        LinearLayout rows = (LinearLayout) findViewById(R.id.llKeyPad);
        Resources resources = getResources();
        keyPad = new KeyPad_init(this, resources, inSKey, display, cm, lls, rows);
        call_load = true;

        scrollView = (HorizontalScrollView) findViewById(R.id.llScrollView);

        matrixDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.print("Pressed Boss\n");
                if(event.getAction()==MotionEvent.ACTION_UP){
                    CursorHandler.locateCursorPos((int)event.getX());
                }
                return true;

            }
        });

        CursorHandler.blinkCursor();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                System.out.println("YASSS");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1234);
            }
        }
        FuncActivity.funcActivity_init();


        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //hide VARkeypad
        svVar = (ScrollView)findViewById(R.id.svVar);
        svVar.setVisibility(View.INVISIBLE);
        svVar.setPadding(0,0,0,0);
        svVar.setBackgroundResource(R.drawable.popup_container);
        svVar.setElevation(16f);
        LinearLayout llVar = (LinearLayout) findViewById(R.id.llVarPad);
        llVar.setElevation(32f);
        llVar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDarker));
        varPad = new KeyPad_init(this, resources, display, llVar);

        final InputStream inSCmd = getResources().openRawResource(R.raw.programming_keys);
        svCmd = (ScrollView)findViewById(R.id.svCmd);
        svCmd.setVisibility(View.INVISIBLE);
        svCmd.setPadding(0,0,0,0);
        svCmd.setBackgroundResource(R.drawable.popup_container);
        svCmd.setElevation(16f);
        LinearLayout llCmd = (LinearLayout) findViewById(R.id.llCmdPad);
        llCmd.setElevation(32f);
        llCmd.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDarker));
        cmdPad = new KeyPad_init(this, resources, inSCmd, display, llCmd);

        svConst = (ScrollView)findViewById(R.id.svConst);
        svConst.setVisibility(View.INVISIBLE);
        svConst.setPadding(0,0,0,0);
        svConst.setBackgroundResource(R.drawable.popup_container);
        svConst.setElevation(16f);
        LinearLayout llConst = (LinearLayout) findViewById(R.id.llConstPad);
        llConst.setElevation(32f);
        llConst.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDarker));
        constPad = new KeyPad_init(this, resources, llConst, display);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (call_load){
            call_load=false;
            fontWidth=cursor.getWidth();
            fontHeight=cursor.getHeight();
            cursor.setText(Character.toString((char) 0x258E));
            //cursor.setTop(matrixDisplay.getTop());
            cursor.setPadding(matrixDisplay.getPaddingLeft(),cursor.getPaddingTop(),cursor.getPaddingRight(),cursor.getPaddingBottom());
            cursor.setLeft(matrixDisplay.getLeft());
            //CursorHandler.hideCursor();
            RelativeLayout cm = (RelativeLayout) findViewById(R.id.content_main);
            LinearLayout rows = (LinearLayout) findViewById(R.id.llKeyPad);
            keyPad.KeyPad_resize(cm, rows);
            int fnbtnHeight = keyPad.btn_rows.get(0).get(0).get_mheight();
            System.out.print(Integer.toString(fnbtnHeight));
            varPad.resize(fnbtnHeight, fnbtnHeight*3, svVar);
            cmdPad.resize(fnbtnHeight, fnbtnHeight*3, svCmd);
            constPad.resize(fnbtnHeight, fnbtnHeight*3, svConst);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(svVar.getVisibility()==View.VISIBLE){
            InputHandler.hideDrawer(MainActivity.svVar);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.context, UserSettingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_save) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public static void set_Cursor_Visibility(TextView c,int visible){
        c.setVisibility(visible);
    }

}