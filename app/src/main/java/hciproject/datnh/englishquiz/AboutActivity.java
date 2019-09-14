package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private  TextView txtAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txtAbout = (TextView) findViewById(R.id.txtTeam);
        String scoreTitle = "Ngô Hoàng Đạt\n" +
                "Trương Tổ Kiệt\n" +
                "Nguyễn Võ Phước Hưng"
                ;

        txtAbout.setText(scoreTitle);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
