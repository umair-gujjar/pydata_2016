package lathia.accelerometercollector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Accelerometer accelerometer;
    private Button sensingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometer = Accelerometer.getInstance();
        sensingButton = (Button) findViewById(R.id.sensingButton);
        setRecyclerView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setButton();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (accelerometer.isSensing())
        {
            accelerometer.stop();
        }
        LabelPreferences.clear(this);
    }

    private void setRecyclerView()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (recyclerView != null)
        {
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            LabelAdapter adapter = new LabelAdapter();
            recyclerView.setAdapter(adapter);
        }
    }

    private void setButton()
    {
        if (!accelerometer.isSensing())
        {
            sensingButton.setText(R.string.sensing_start);
        }
        else
        {
            sensingButton.setText(R.string.sensing_stop);
        }
    }

    private boolean labelSet()
    {
        return LabelPreferences.getLabel(this) != null;
    }

    public void onSensingButtonClicked(final View view)
    {
        if (!accelerometer.isSensing())
        {
            if (!labelSet())
            {
                Toast.makeText(this, R.string.no_label, Toast.LENGTH_LONG).show();
            }
            else
            {
                accelerometer.start();
            }
        }
        else
        {
            accelerometer.stop();
        }
        setButton();
    }
}