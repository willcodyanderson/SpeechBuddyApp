package william.anderson;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewLists extends AppCompatActivity {
    private ListsObjectDataSource datasource;
    public List<String> values2;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String userID;
    private ListView listview;
    public ArrayList<String> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lists);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(-1);
        setSupportActionBar(toolbar);



        //Check to see if the screen is a certain size and force landscape
        int isTablet = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (isTablet == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseUser = mFirebaseAuth.getCurrentUser();

        datasource = new ListsObjectDataSource(this);
       // datasource.open();

        try {
            values = datasource.getAllListsObject();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        listview = (ListView) findViewById(R.id.list2);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedFromList = (listview.getItemAtPosition(position).toString());
                Intent intent = new Intent(ViewLists.this, ItemView.class);
                intent.putExtra("listkey", selectedFromList);
                startActivity(intent);
            }});
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewLists.this);
                final TextView et = new TextView(ViewLists.this);
                et.setText("\tDelete this List?");
                et.setTextSize(18);
                alertDialogBuilder.setView(et);
                alertDialogBuilder.setCancelable(true).setPositiveButton(R.string.delete2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListsObjectDataSource datasource;
                        datasource = new ListsObjectDataSource(getApplicationContext());
//                        datasource.open();
                        datasource.deleteListsObject(listview.getItemAtPosition(pos).toString());
                        finish();
                        startActivity(getIntent());
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public void getLists() throws InterruptedException {
//        datasource.open();
        values = datasource.getAllListsObject();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        final ListView listview = (ListView) findViewById(R.id.list2);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        switch (item.getItemId()) {
            case R.id.addList:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                final EditText et = new EditText(this);
                alertDialogBuilder.setView(et);
                alertDialogBuilder.setCancelable(true).setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        ListsObjectDataSource datasource;
                        datasource = new ListsObjectDataSource(getApplicationContext());
//                        datasource.open();
                        datasource.createListsObject(et.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                break;

            case R.id.deleteList:
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
                final EditText et2 = new EditText(this);
                alertDialogBuilder2.setView(et2);

                alertDialogBuilder2.setCancelable(true).setPositiveButton(R.string.delete3, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListsObjectDataSource datasource;
                        datasource = new ListsObjectDataSource(getApplicationContext());
//                        datasource.open();
                        String text = et2.getText().toString();
                        datasource.deleteListsObject(text);
                        finish();
                        startActivity(getIntent());
                    }
                });

                // create alert dialog
                AlertDialog alertDialog2 = alertDialogBuilder2.create();
                // show it
                alertDialog2.show();
                break;
            case R.id.refreshList:
                finish();
                startActivity(getIntent());
                break;
            case R.id.logout: //logout and back to login screen

//                mFirebaseAuth.signOut();
//                loadLogInView();

                break;

            case R.id.about:
                Intent intent = new Intent(ViewLists.this, About.class);
                startActivity(intent);
                break;

            case R.id.clear: {
                AlertDialog.Builder alertDialogBuilder3 = new AlertDialog.Builder(this);
                final TextView eta = new TextView(this);
                eta.setText(R.string.confirmdelete);
                eta.setTextSize(18);
                alertDialogBuilder3.setView(eta);
                alertDialogBuilder3.setCancelable(true).setPositiveButton(R.string.delall, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListsObjectDataSource datasource;
                        ItemsObjectDataSource datasource2;
                        datasource2 = new ItemsObjectDataSource(getApplicationContext());
                        datasource2.open();
                        datasource2.deleteallItemsObject();
                        datasource = new ListsObjectDataSource(getApplicationContext());
//                        datasource.open();
                        datasource.deleteallListsObject();
                        finish();
                        startActivity(getIntent());
                    }
                });
                AlertDialog alertDialog3 = alertDialogBuilder3.create();
                // show it
                alertDialog3.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
//        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
//        datasource.close();
        super.onPause();
    }
//    private void loadLogInView() {
//        Intent intent = new Intent(this, LogIn.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
}
