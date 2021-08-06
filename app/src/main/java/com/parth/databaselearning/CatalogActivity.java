package com.parth.databaselearning;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parth.databaselearning.data.petDBhelper;
import com.parth.databaselearning.data.petcontract.petentry;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private petDBhelper petdbobject;

    private static  final int PET_LOADER =0;
    PetCursorAdapter mcursoradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_activity);

        FloatingActionButton fab= findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editoract=new Intent(CatalogActivity.this,EditorActivity.class);
                startActivity((editoract));
            }
        });

        petdbobject= new petDBhelper(this);

        //for empty startup page..
        ListView petsList=(ListView)findViewById(R.id.main_pets_list);
        View emptyView = findViewById(R.id.empty_view);
        petsList.setEmptyView(emptyView);



        mcursoradapter = new PetCursorAdapter(this,null);
        petsList.setAdapter(mcursoradapter);

        // kick off the loader
        getSupportLoaderManager().initLoader(PET_LOADER,null,this);




    }





//
//    private void displayDatabaseInfo() {
//
//        String[] project={
//            petentry._ID,
//            petentry.COLUMN_PET_NAME,
//            petentry.COLUMN_PET_BREED,
//            petentry.COLUMN_PET_GENDER,
//            petentry.COLUMN_PET_WEIGHT,
//        };
//
//        //uses content providers to do the query method
//        Cursor cursor=getContentResolver().query(petentry.CONTENT_URI,project,null,null,null);
//
//        ListView petsList=(ListView)findViewById(R.id.main_pets_list);
//
//        View emptyView = findViewById(R.id.empty_view);
//        petsList.setEmptyView(emptyView);
//
//        PetCursorAdapter petsadapter =new PetCursorAdapter(this,cursor);
//
//        petsList.setAdapter(petsadapter);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        displayDatabaseInfo();
//    }







    private void insert_dummy_data(){
        // TODO: below is the old code for direct database insert , later petprovider was used
//        SQLiteDatabase db =petdbobject.getWritableDatabase();
//
//        ContentValues values =new ContentValues();
//        values.put(petentry.COLUMN_PET_NAME,"TOTO");
//        values.put(petentry.COLUMN_PET_BREED,"terrier");
//        values.put(petentry.COLUMN_PET_GENDER,petentry.GENDER_MALE);
//        values.put(petentry.COLUMN_PET_WEIGHT,7);
//
//        db.insert(petentry.TABLE_NAME,null,values);

        //TODO: Below petprovider is used.
        ContentValues values = new ContentValues();
        values.put(petentry.COLUMN_PET_NAME,"Toto");
        values.put(petentry.COLUMN_PET_BREED,"terrier");
        values.put(petentry.COLUMN_PET_GENDER,petentry.GENDER_MALE);
        values.put(petentry.COLUMN_PET_WEIGHT,7);

        Uri totosUri =getContentResolver().insert(petentry.CONTENT_URI,values);
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }






    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insert_dummy_data();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }






    /// All loadermanager methods implemented

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // define the projection that specefies the columns
        String[] projection ={
                petentry._ID,
                petentry.COLUMN_PET_NAME,
                petentry.COLUMN_PET_BREED};

        //this will execute the contentProvider in background thread
        return new CursorLoader(this,
                petentry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // update with this new cursor containing updated pet data
        mcursoradapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mcursoradapter.swapCursor(null);
    }
}