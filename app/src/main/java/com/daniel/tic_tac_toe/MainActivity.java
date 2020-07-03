package com.daniel.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_share && viewModel.getRoom().getValue() != null) {
            Room room = viewModel.getRoom().getValue();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String text = room.getNicks()[room.getPlayerNumber()]+" "+getString(R.string.invitation_text)+"\n"+
                        getString(R.string.share_url)+viewModel.getRoomID();
            Log.d("PAPIERKAMIEN", text);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, null));
        }
        return super.onOptionsItemSelected(item);
    }
}
