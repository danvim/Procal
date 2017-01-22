package dcheungaa.procal.Func;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import dcheungaa.procal.InputHandler;
import dcheungaa.procal.MainActivity;
import dcheungaa.procal.ProcalDocParser.Parser;
import dcheungaa.procal.ProcalDocParser.ProcalDoc;
import dcheungaa.procal.R;

import static dcheungaa.procal.MainActivity.context;

/**
 * Created by Bryan on 1/17/2017.
 */

public class FuncActivity extends ActionBarActivity {

    static Context context = MainActivity.context;
    public FuncAdapter funcAdapter;
    public static List<FuncItem> funcItemList = new ArrayList<>();
    public static RecyclerView recyclerView;

    static List<ProcalContent> presetContents = new ArrayList<>();
    static List<ProcalContent> userContents = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.func_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // MakeDirs and CopyAsset
        String mainDirectory = "Procal";
        String presetProgDirectory = "/Preset";
        String userProgDirectory = "/User";

        File presetFolder = new File(Environment.getExternalStorageDirectory() + "/" + mainDirectory, presetProgDirectory);
        File userFolder = new File(Environment.getExternalStorageDirectory() + "/" + mainDirectory, userProgDirectory);

        if (!presetFolder.exists()) {
            presetFolder.mkdirs();
            copyFolder("Preset");
        }
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }

        updateFuncItems(presetFolder, userFolder);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final FuncAdapter funcAdapter = new FuncAdapter(funcItemList){
            @Override
            public void onBindViewHolder(FuncViewHolder holder, int position){
                FuncItem funcItem = funcItemsList.get(position);
                holder.title.setText(funcItem.getTitle());
                holder.description.setText(funcItem.getDescription());
                holder.funcItemLayout.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        FuncItem funcItem = FuncActivity.funcItemList.get(position);
                        InputHandler.runProgram(funcItem.getProcalContentString());
                        finish();
                        //overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                        Toast.makeText(context, funcItem.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                        // /clickListener.onClick(holder.funcItemLayout);
                    }
                });
                holder.funcItemMenu.setOnClickListener(new View.OnClickListener(){
                    FuncItem funcItem = funcItemsList.get(position);
                    @Override
                    public void onClick(View v){
                        System.out.println("Menu Button pressed");
                        PopupMenu popup = new PopupMenu(context, v);
                        popup.inflate(R.menu.func_useritem_menu);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        Toast.makeText(context, funcItem.getTitle() + " : "+item+" is selected!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.delete:
                                        boolean deleted = funcItem.getProcalContentFile().delete();
                                        funcItemList.remove(funcItem);
                                        notifyDataSetChanged();
                                        //updateFuncItems(presetFolder, userFolder);
                                        System.out.println(".procal Deleted? : existence == "+funcItem.getProcalContentFile().exists());
                                        break;
                                    case R.id.share:
                                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                        sharingIntent.setType("text/plain");
                                        sharingIntent.putExtra(Intent.EXTRA_TEXT, funcItem.getProcalContentString());
                                        startActivity(Intent.createChooser(sharingIntent, "Share plain text program via"));
                                        break;
                                    case R.id.details:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(FuncActivity.this);
                                        builder.setTitle(funcItem.getTitle())
                                                .setMessage(funcItem.getDescription())
                                                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                /*.setNegativeButton(R.string.modify, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })*/
                                                //.setIcon(android.R.drawable.ic_menu_edit);
                                        AlertDialog alert = builder.create();
                                        alert.show();

                                        /*Dialog dialog = new Dialog(FuncActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.func_details_modify);
                                        EditText title = (EditText) dialog.findViewById(R.id.details_title);
                                        title.setText(funcItem.getTitle());
                                        EditText desc = (EditText) dialog.findViewById(R.id.details_desc);
                                        desc.setText(funcItem.getDescription());
                                        dialog.setCancelable(false);
                                        dialog.setCanceledOnTouchOutside(true);
                                        dialog.show();*/
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();


                    }
                });
            }
        };
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(funcAdapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void updateFuncItems(File presetFolder, File userFolder){
        File[] presetProcals = presetFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".procal");
            }
        });
        File[] userProcals = userFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".procal");
            }
        });

        presetContents = extractProcalContents(presetProcals);
        userContents = extractProcalContents(userProcals);

        System.out.println("presetContents has length: " + presetContents.size());
        System.out.println("userContents has length: " + userContents.size());

        funcItemList.clear();
        for (ProcalContent procalContent : presetContents) {
            ProcalDoc procalDoc = Parser.extractProcalDoc(procalContent.content);
            funcItemList.add(new FuncItem(procalDoc.title, procalDoc.desc, procalContent.content, procalContent.file));
        }

        for (ProcalContent procalContent : userContents) {
            ProcalDoc procalDoc = Parser.extractProcalDoc(procalContent.content);
            funcItemList.add(new FuncItem(procalDoc.title, procalDoc.desc, procalContent.content, procalContent.file));
        }

    }

    private static void copyFolder(String name) {
        // "Name" is the name of your folder!
        AssetManager assetManager = context.getAssets();
        String[] files = null;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            // Checking file on assets subfolder
            try {
                files = assetManager.list(name);
            } catch (IOException e) {
                Log.e("ERROR", "Failed to get asset file list.", e);
            }
            // Analyzing all file on assets subfolder
            for (String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                // First: checking if there is already a target folder
                File folder = new File(Environment.getExternalStorageDirectory() + "/Procal/" + name);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    // Moving all the files on external SD
                    try {
                        in = assetManager.open(name + "/" + filename);
                        out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Procal/" + name + "/" + filename);
                        Log.i("WEBVIEW", Environment.getExternalStorageDirectory() + "/Procal/" + name + "/" + filename);
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    } catch (IOException e) {
                        Log.e("ERROR", "Failed to copy asset file: " + filename, e);
                    /*} finally {
                        // Edit 3 (after MMs comment)
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;*/
                    }
                } else {
                    // Do something else on failure
                }
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            // is to know is we can neither read nor write
        }
    }

    // Method used by copyAssets() on purpose to copy a file.
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private static List<ProcalContent> extractProcalContents(File[] procalFiles){
        List<ProcalContent> procalContents = new ArrayList<>();
        if (procalFiles != null)
            for (File procalFile: procalFiles){
                ProcalContent procalContent = new ProcalContent();
                String fileContent = "";
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(procalFile));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        fileContent += line + "\n";
                    bufferedReader.close();
                    procalContent.content = fileContent;
                    procalContent.file = procalFile;
                    procalContents.add(procalContent);
                    //MainActivity.fx50Parser.parse(fileContent);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        return procalContents;
    }

    /* Checks if external storage is available for read and write *//*
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    *//* Checks if external storage is available to at least read *//*
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.func_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                /*this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);*/
                this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                return (true);
            case R.id.action_add:
                Toast.makeText(MainActivity.context, "Add", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Func Page") // TODO: Define a title for the content shown.
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
}
