package com.example.deepthi.jsonlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MyActivity extends Activity {
    int buttonNumber=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }

    public void clickedButton1(View view){
        buttonNumber=1;
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("button", buttonNumber);
        startActivity(intent);
    }
    public void clickedButton2(View view){
        buttonNumber=2;
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.putExtra("button", buttonNumber);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
