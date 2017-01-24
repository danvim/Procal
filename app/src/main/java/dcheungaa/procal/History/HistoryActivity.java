package dcheungaa.procal.History;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dcheungaa.procal.InputHandler;
import dcheungaa.procal.MainActivity;
import dcheungaa.procal.R;
import fx50.API.InputToken;

import static dcheungaa.procal.Tokens.inputTokensMap;

/**
 * Created by Bryan on 1/23/2017.
 */

public class HistoryActivity extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout llmain = (LinearLayout) findViewById(R.id.history_list);

        for (List<String> keyIds: HistoryHandler.history){
            Button historyItem = new Button(this, null, getResources().getIdentifier(
                    "Button_Fn","attr",this.getPackageName()
            ));

            historyItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            setButtonText(historyItem, keyIds);
            historyItem.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            historyItem.setTextSize(20);
            final Typeface FONT_FX50 = Typeface.createFromAsset(getAssets(), "fonts/Fx50.otf");
            historyItem.setTypeface(FONT_FX50);

            historyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printToDisplay(keyIds);
                }
            });

            historyItem.setFocusable(true);
            //tv.setFocusable(false);

            /*
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printToDisplay(keyIds);
                }
            });
            */

            llmain.addView(historyItem);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                /*this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);*/
                //this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                return (true);

        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonText(TextView btn, List<String> keyids){
        final SpannableStringBuilder sb = new SpannableStringBuilder();
        for (String id: keyids){
            InputToken token = inputTokensMap.get(id);
            if (token == null)
                throw new NullPointerException();
            int i = sb.length();
            try {
                sb.append(token.display);
            } catch (Exception e) {
                System.out.println("Cannot use token!");
            }
            sb.setSpan(new ForegroundColorSpan(token.color.getColor()), i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            btn.setText(sb);
        }
    }

    private void printToDisplay(List<String> keyids){
        final SpannableStringBuilder sb = new SpannableStringBuilder();
        InputHandler.allClearToken();
        for (String id: keyids){
            InputToken token = inputTokensMap.get(id);
            HistoryHandler.subHistory.add(id);
            InputHandler.inputExpression.add(token);
            if (token == null)
                throw new NullPointerException();
            int i = sb.length();
            try {
                sb.append(token.display);
            } catch (Exception e) {
                System.out.println("Cannot use token!");
            }
            sb.setSpan(new ForegroundColorSpan(token.color.getColor()), i, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) MainActivity.views.get("matrixDisplay")).setText(sb);
        }
        InputHandler.execute();
        HistoryHandler.history.remove(HistoryHandler.history.size()-1);
        ((DrawerLayout) MainActivity.views.get("drawer")).closeDrawer(Gravity.LEFT);
        finish();
    }
}
