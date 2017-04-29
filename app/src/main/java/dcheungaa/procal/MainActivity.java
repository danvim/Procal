package dcheungaa.procal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dcheungaa.procal.Func.FuncItem;
import dcheungaa.procal.History.HistoryActivity;
import fx50.Fx50ParserCallable;



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
    public static Map<String, View> views = new HashMap<>();

    //public static List <List<String>> vars = new ArrayList<>();
    public static List <String> vars = new ArrayList<>();
    public static Tokens tokens = new Tokens();

    public static int fontWidth;
    public static int fontHeight;

    public static HorizontalScrollView horizontalScrollView;
    public static ScrollView verticalScrollView;


    public static List<CalcBtn> calcBtns = new ArrayList<>();

    public static ScrollView svVar;
    public static ScrollView svCmd;
    public static ScrollView svConst;

    public static int screenWidth;
    public static int screenHeight;

    public static LinearLayout llKeyPad;
    public static Context context;

    public static Fx50ParserCallable fx50Parser;

    public List<FuncItem> funcItemList = new ArrayList<>();
    public static boolean func_initialised = false;


    public static Thread fx50ParserThread;

    public static List<Activity> mainActivities = new ArrayList<>();

    public static boolean FuncEditing = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivities.add(this);
        try {
            fx50Parser = new Fx50ParserCallable();
        } catch (Exception e) {
            System.out.println("Fx50 Parser failed to initiate: " + e.getMessage());
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        InputHandler.setContext(this);

        views.put("drawer", (DrawerLayout) findViewById(R.id.drawer_layout));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, ((DrawerLayout) views.get("drawer")), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ((DrawerLayout) views.get("drawer")).setDrawerListener(toggle);
        toggle.syncState();

        //
        //DrawerLayout.LayoutParams lp =(android.support.v4.widget.DrawerLayout.LayoutParams) drawer.getLayoutParams();
        //lp.setMargins(0,200,0,200);
        //drawer.setLayoutParams(lp);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        views.put("resultDisplay", findViewById(R.id.resultDisplay));

        //Set font
        TextView inquiryDisplay = (TextView) findViewById(R.id.inquiryDisplay);
        TextView matrixDisplay = (TextView) findViewById(R.id.matrixDisplay);
        views.put("matrixDisplay", matrixDisplay);
        views.put("inquiryDisplay", inquiryDisplay);
        final Typeface FONT_FX50 = Typeface.createFromAsset(getAssets(), "fonts/Fx50.otf");
        matrixDisplay.setTypeface(FONT_FX50);
        TextView cursor = (TextView) findViewById(R.id.tv_cursor);
        views.put("cursor", (TextView) findViewById(R.id.tv_cursor));
        cursor.setTypeface(FONT_FX50);

        //keypad gen
        final InputStream inSKey = getResources().openRawResource(R.raw.keypad);
        Display display = getWindowManager().getDefaultDisplay();
        RelativeLayout cm = (RelativeLayout) findViewById(R.id.content_main);
        LinearLayout lls = (LinearLayout) findViewById(R.id.llScreen);
        llKeyPad = (LinearLayout) findViewById(R.id.llKeyPad);
        Resources resources = getResources();
        keyPad = new KeyPad_init(this, resources, inSKey, display, cm, lls, llKeyPad);
        call_load = true;

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.llHoriScrollView);

        matrixDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                System.out.print("Pressed Boss\n");
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if (!CursorHandler.cursorVisible && DisplayModeHandler.displayMode) {
                        CursorHandler.cursorVisible =true;
                        CursorHandler.blinkCursor();
                        DisplayModeHandler.displayMode = false;
                        InputHandler.updateMatrixDisplay();
                    }
                    if (!InputHandler.error)
                        CursorHandler.locateCursorPos((int)event.getX());
                    InputHandler.error = false;
                }
                return true;

            }
        });

        CursorHandler.blinkCursor();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("Requesting writing permission");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1234);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                System.out.println("Requesting reading permission");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1235);
            }
        }





        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //hide VAR keypad
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
        llCmd.setBackgroundColor(context.getResources().getColor(R.color.colorAccentDarker));
        cmdPad = new KeyPad_init(this, resources, inSCmd, display, llCmd, "", "COMMAND");

        final InputStream inSConst = getResources().openRawResource(R.raw.constant_keypad);
        svConst = (ScrollView)findViewById(R.id.svConst);
        svConst.setVisibility(View.INVISIBLE);
        svConst.setPadding(0,0,0,0);
        svConst.setBackgroundResource(R.drawable.popup_container);
        svConst.setElevation(16f);
        LinearLayout llConst = (LinearLayout) findViewById(R.id.llConstPad);
        llConst.setElevation(32f);
        llConst.setBackgroundColor(context.getResources().getColor(R.color.colorSecondaryDarker));
        constPad = new KeyPad_init(this, resources, inSConst, display, llConst, "&", "CONSTANT");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        TextView cursor = (TextView) MainActivity.views.get("cursor");
        TextView matrixDisplay = (TextView) MainActivity.views.get("matrixDisplay");
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
            int fnBtnHeight = keyPad.btn_rows.get(0).get(0).get_mheight();
            System.out.print(Integer.toString(fnBtnHeight));
            varPad.resize(fnBtnHeight, fnBtnHeight*3, svVar);
            cmdPad.resize(fnBtnHeight, fnBtnHeight*3, svCmd);
            constPad.resize(fnBtnHeight, fnBtnHeight*3, svConst);
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
    @NonNull
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch(item.getItemId()){

            case R.id.nav_history:
                Intent intent = new Intent(MainActivity.context, HistoryActivity.class);
                startActivity(intent);
                return true;

            case R.id.nav_about:

                String credits = "<p>This piece of software used the work <a href=\"https://arxiv.org/abs/0908.3030v1\">\"A Java Math.BigDecimal Implementation of Core Mathematical Functions\" of Richard J. Mathar</a>, which was made available under the LGPL3.0 license as a library. As part of the obligations to the license, if the user wish to replace this library with their own, they may contact: <a href=\"mailto:dcheungaa@connect.ust.hk\">dcheungaa@connect.ust.hk</a></p>\n" +
                        "\n" +
                        "<p>Other code libraries used:</p>\n" +
                        "<p><a href=\"https://github.com/atorstling/bychan\">Bychan</a><br>\n" +
                        "<a href=\"https://github.com/evant/gradle-retrolambda\">Gradle-Retrolambda</a><br>\n" +
                        "<a href=\"https://github.com/streamsupport/streamsupport\">SteamSupport</a></p>\n";

                final AlertDialog.Builder builder_add = new AlertDialog.Builder(MainActivity.this);
                builder_add.setTitle("About");
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout aboutLayout = (LinearLayout) inflater.inflate(R.layout.about_alert, null);
                builder_add.setView(aboutLayout);
                TextView aboutTextView = (TextView) aboutLayout.findViewById(R.id.about_content);
                aboutTextView.setText(Html.fromHtml(credits));
                aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());

                builder_add.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert_add = builder_add.create();
                alert_add.show();
                return true;

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
