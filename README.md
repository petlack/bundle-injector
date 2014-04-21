Bundle Injector
===============

Android library for injecting parameters from Bundle.
It helps you reduce code needed to retrieve values from Bundle.

Example
-----
```java
public class YourActivity extends Activity {

    @InjectBundle
    String itemId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject itemId from extras Bundle
        BundleInjector.inject(this, getIntent().getExtras());
    }
}
```

Setup
-----
**Eclipse**: In Eclipse, import the BundleInjectorLibrary as Android library project. If needed, fix build path, so `src/main/java` is the source folder. Finally, add dependency to the project via project settings.

**Android Studio**: In Android Studio, import the BundleInejctorLibrary module via **File -> Import module**. If you don't have dependency `classpath 'com.squareup.gradle:gradle-android-test-plugin:0.9.1-SNAPSHOT'`in your main build.gradle, add it there, or remove `apply plugin: 'android-test'` and `testCompile`, `androidTestCompile` dependencies  from build.gradle in BundleInjectorLibrary

Usage
-----
Simple example showing data exchange between 2 activities:

**ActivityA.java**
```java
public class ActivityA extends Activity {

    @InjectBundle
    String returnedParam;

    public void onCreate(Bundle savedInstanceState) {
        ...
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ActvityA.this, ActivityB.class);
                Bundle extras = new Bundle();
                extras.putString("passedParam", "SAMPLE VALUE");
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });
        ...
    }
    
     public void onActivityResult(int reqCode, int resCode, Intent data) {
        ...
        BundleInjector.inject(this, data);
        // now, returnedParam contains value from Activity B
        ...
     }

}
```
**ActivityB.java**
```java
public class ActivityB extends Activity {

    @InjectBundle
    String passedParam;

    public void onCreate(Bundle savedInstanceState) {
        ...
        BundleInjector.inject(this, getIntent().getExtras());
        // now, passedParam contains value from Activity A
        ...
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("returnedParam", "SAMPLE RETURNED VALUE");
                setResult(RESULT_OK, extras);
                finish();
            }
        });
        ...
    }

}
```


Caveats
-------
If you have generic class and injecting field is parametrized, make sure you put correct type into Bundle, since type check is failing when using generics.

For more information, see [stackoverflow thread][1]

[1]:http://stackoverflow.com/questions/23188458/creating-a-generic-field-of-type-x-and-setting-its-value-as-unrelated-type-y-t

