package ca.uoit.project;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EatAtHome extends AppCompatActivity
{
    ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_at_home);

        actionbar = getActionBar();
    }
}
