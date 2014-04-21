package eu.petlack.android.bundleinjector.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eu.petlack.android.bundleinjector.BundleInjector;
import eu.petlack.android.bundleinjector.InjectBundle;


public class ActivityA extends Activity {

    @InjectBundle
    String returnedParam;

    private TextView returned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        final Button launchB = (Button) findViewById(R.id.launch_b);
        final EditText data = (EditText) findViewById(R.id.data);
        returned = (TextView) findViewById(R.id.returned_value);

        launchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityA.this, ActivityB.class);
                Bundle extras = new Bundle();
                extras.putString("passedParam", data.getText().toString());
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        BundleInjector.inject(this, data.getExtras());
        returned.setText(returnedParam);
    }

}
