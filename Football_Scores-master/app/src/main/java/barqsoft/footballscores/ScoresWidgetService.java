package barqsoft.footballscores;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by morxander on 10/6/15.
 */
public class ScoresWidgetService extends IntentService {

    // The columns array for the query.
    public static final String[] COLUMNS = {
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.MATCH_DAY,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.LEAGUE_COL,
    };
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = df.format(c.getTime());
    String where = DatabaseContract.scores_table.DATE_COL +"=?";
    String[] args = {formattedDate};
    // The columns indexes.
    public static final double MATCH_ID_INDEX = 0;
    public static final int COL_MATCHDAY_INDEX = 1;
    public static final int COL_DATE_INDEX = 2;
    public static final int COL_MATCHTIME_INDEX = 3;
    public static final int COL_HOME_INDEX = 4;
    public static final int COL_HOME_GOALS_INDEX = 5;
    public static final int COL_AWAY_INDEX = 6;
    public static final int COL_AWAY_GOALS_INDEX = 7;
    public static final int COL_LEAGUE_INDEX = 8;

    public ScoresWidgetService() {
        super("ScoresWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get Today's widget ids.
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = widgetManager.getAppWidgetIds(new ComponentName(this,
                ScoresWidget.class));
        // Get the data from the DB
        SQLiteDatabase db = new ScoresDBHelper(getBaseContext()).getReadableDatabase();
        Cursor scoresData = db.query(DatabaseContract.SCORES_TABLE, COLUMNS, where, args, null, null, DatabaseContract.scores_table.DATE_COL + " ASC");
        // get the widget
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.scores_widget);
        // Check the returned data
        if (scoresData == null){
            Log.e("Error #1","scoresData is Null");
            return;
        }else if(scoresData.getCount() == 0){
            views.setTextViewText(R.id.date_textview, "No Matchs Today");
            Log.e("Error #2","scoresData equal zero");
            scoresData.close();
        }else{
            // Move to the first.
            scoresData.moveToFirst();
            // Get the data
            int match_day = scoresData.getInt(COL_MATCHDAY_INDEX);
            String match_date = scoresData.getString(COL_DATE_INDEX);
            String match_time = scoresData.getString(COL_MATCHTIME_INDEX);
            String match_home = scoresData.getString(COL_HOME_INDEX);
            int match_home_goals = scoresData.getInt(COL_HOME_GOALS_INDEX);
            String match_away = scoresData.getString(COL_AWAY_INDEX);
            int match_away_goals = scoresData.getInt(COL_AWAY_GOALS_INDEX);
            int match_league = scoresData.getInt(COL_LEAGUE_INDEX);
            // Close the connection
            scoresData.close();
            // Update the widget views
            views.setImageViewResource(R.id.home_icon, Utilies.getTeamCrestByTeamName(match_home,getBaseContext()));
            views.setViewVisibility(R.id.home_icon, View.VISIBLE);
            views.setImageViewResource(R.id.away_icon, Utilies.getTeamCrestByTeamName(match_away,getBaseContext()));
            views.setViewVisibility(R.id.away_icon, View.VISIBLE);
            views.setTextViewText(R.id.home_name, match_home);
            views.setTextViewText(R.id.away_name, match_away);
            views.setTextViewText(R.id.date_textview, match_time);
            views.setTextViewText(R.id.score_textview, Utilies.getScores(match_home_goals, match_away_goals));
            // Launch the app if the user clicked on the widget
        }
        Intent launchIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        views.setOnClickPendingIntent(R.id.appWidget, pendingIntent);
        widgetManager.updateAppWidget(widgetIds[0], views);
    }
}
