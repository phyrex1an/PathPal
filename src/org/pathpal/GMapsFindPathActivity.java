package org.pathpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GMapsFindPathActivity extends Activity {
	
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpath);
        
        Button next = (Button) findViewById(R.id.FindBtn);
        final EditText from = (EditText) findViewById(R.id.from);
        final EditText to = (EditText) findViewById(R.id.to);
        final EditText nlp = (EditText) findViewById(R.id.nl_input); 
        
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("from", from.getText());
                intent.putExtra("to", to.getText());
                intent.putExtra("nlp", nlp.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        
    }
    
}
