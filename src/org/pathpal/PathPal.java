package org.pathpal;
/**
 * 
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author phyrex1an
 *
 */
public class PathPal extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello, PathPal");
        setContentView(tv);
    }
}
