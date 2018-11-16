package ca.uoit.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button eatAtHome;
    Button eatOutside;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eatAtHome = (Button) findViewById(R.id.eat_at_home);
        eatOutside = (Button) findViewById(R.id.eat_outside);

        eatAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EatAtHome.class);
                startActivity(intent);
            }
        });

        eatOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EatOutside.class);
                startActivity(intent);
            }
        });
    }
}
