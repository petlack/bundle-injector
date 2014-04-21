package eu.petlack.android.bundleinjector.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import eu.petlack.android.bundleinjector.BundleInjector;
import eu.petlack.android.bundleinjector.InjectBundle;


public class ActivityB extends Activity {

    @InjectBundle
    String passedParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        final Button finish = (Button) findViewById(R.id.finish);
        final TextView passed = (TextView) findViewById(R.id.passed_value);

        BundleInjector.inject(this, getIntent().getExtras());

        passed.setText(passedParam);

        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent result = new Intent();
                Bundle extras = new Bundle();
                extras.putString("returnedParam", "SAMPLE RETURNED VALUE");
                result.putExtras(extras);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

}
