package com.example.demet.demetandroid;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,LoaderManager.LoaderCallbacks<Cursor>{
    private static final int EDITOR_REQUEST_CODE = 100;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    private NotesCursorAdapter cursorAdapter;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        cursorAdapter=new NotesCursorAdapter(this,null,0);

        ListView listView=(ListView) findViewById(android.R.id.list);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0,null,this);

    }



    private void insertNote(String noteText) {
        ContentValues values=new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT,noteText);
        Uri noteUri=getContentResolver().insert(NotesProvider.CONTENT_URI,values);
        Log.d("Main Activity","Inserted note" + noteUri.getLastPathSegment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

        int res_id=item.getItemId();
        if(res_id==R.id.action_adding){
            Intent ad=new Intent(MainActivity.this,NewPlan.class);
            startActivity(ad);
        }
        else if(res_id==R.id.action_calendar){
            Intent c=new Intent(MainActivity.this,Calendar1.class);
            startActivity(c);
        }
        else if(res_id==R.id.action_create_sample){
            insertSampleData();
        }
        else if(res_id==R.id.action_delete_sample){
            deleteAllNotes();
        }
        return true;
    }
    private void deleteAllNotes() {
        //confirm
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(NotesProvider.CONTENT_URI, null, null);

                            restartLoader();
                            getContentResolver().delete(NotesProvider.CONTENT_URI, null, null);

                            //Tells user what happened
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.all_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }
    private void insertSampleData() {
        insertNote("Simple note");
        insertNote("multi-line\nnote");
        insertNote("Very long note that extends past the end of the line");
        restartLoader();
    }
    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id=item.getItemId();
        if(id==R.id.nav_camera) {
            Intent h = new Intent(MainActivity.this, Task.class);
            startActivity(h);
        }

        else if(id==R.id.nav_send) {
            Intent k = new Intent(MainActivity.this, Help.class);
            startActivity(k);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotesProvider.CONTENT_URI, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    public void openEditorforNewNote(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivityForResult(intent, EDITOR_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDITOR_REQUEST_CODE && resultCode == RESULT_OK) {
            restartLoader();
        }
    }
}
