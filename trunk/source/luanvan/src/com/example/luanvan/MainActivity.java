package com.example.luanvan;

import com.example.luanvan.R;
import com.example.luanvan.R.id;
import com.example.luanvan.R.menu;
import com.example.luanvan.game.Game;
import com.example.luanvan.mainmenu.MainMenu;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GamePanel(this));
		
//		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layount);
//		
//		
//		
//		Game game = new Game(this);
//		game.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		
//		layout.addView(game);
//		
//		Button btn = new Button(this);
//		btn.setLayoutParams(new LayoutParams(100, 30 ));
//		btn.setBackgroundResource(R.drawable.main_menu_button_bat_dau);
//		btn.setX(150);
//		btn.setY(200);
//		layout.addView(btn);
		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
