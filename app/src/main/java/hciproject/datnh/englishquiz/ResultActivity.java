package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView txtScore;
    private String finalScore;
    private int getIntentFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //set score
        Bundle bd = new Bundle();
        bd = getIntent().getExtras();
        getIntentFrom = bd.getInt("scoreFromIntent");

        txtScore = (TextView)findViewById(R.id.txtScore);
        finalScore = getIntent().getExtras().getString("finalScore");
        txtScore.setText("" + finalScore);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
