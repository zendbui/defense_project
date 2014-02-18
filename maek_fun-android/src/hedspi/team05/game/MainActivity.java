package hedspi.team05.game;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics displayMetrics = getApplicationContext().getResources()
				.getDisplayMetrics();

		int screenWidthInPix = displayMetrics.widthPixels;

		int screenheightInPix = displayMetrics.heightPixels;
		
		Log.i("screen size in pixel", "width: " + screenWidthInPix + " -- height: " + screenheightInPix);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;

		initialize(new Drop_extend(), cfg);
	}
}