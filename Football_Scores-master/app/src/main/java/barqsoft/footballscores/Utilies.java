package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies {
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static String getLeague(int league_num,Context context) {
        switch (league_num) {
            case SERIE_A:
                return context.getString(R.string.seria_a);
            case PREMIER_LEGAUE:
                return context.getString(R.string.premier_league);
            case CHAMPIONS_LEAGUE:
                return context.getString(R.string.uefa_champions_league);
            case PRIMERA_DIVISION:
                return context.getString(R.string.primera_division);
            case BUNDESLIGA:
                return context.getString(R.string.bundesliga_leagues);
            default:
                return context.getString(R.string.not_known_league);
        }
    }

    public static String getMatchDay(int match_day, int league_num, Context context) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return context.getString(R.string.match_day_6);
            } else if (match_day == 7 || match_day == 8) {
                return context.getString(R.string.match_day_round);
            } else if (match_day == 9 || match_day == 10) {
                return context.getString(R.string.match_day_quarter);
            } else if (match_day == 11 || match_day == 12) {
                return context.getString(R.string.match_day_simi);
            } else {
                return context.getString(R.string.match_day_final);
            }
        } else {
            return context.getString(R.string.match_day) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamname, Context context) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
            //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            if (teamname.equals(context.getString(R.string.arsenal))) {
                return R.drawable.arsenal;
            }else if (teamname.equals(context.getString(R.string.manchester_united))) {
                return R.drawable.manchester_united;
            } else if (teamname.equals(context.getString(R.string.swansea_city_afc))) {
                return R.drawable.swansea_city_afc;
            } else if (teamname.equals(context.getString(R.string.leicester_city_fc_hd_logo))) {
                return R.drawable.leicester_city_fc_hd_logo;
            } else if (teamname.equals(context.getString(R.string.everton_fc_logo1))) {
                return R.drawable.everton_fc_logo1;
            } else if (teamname.equals(context.getString(R.string.west_ham))) {
                return R.drawable.west_ham;
            } else if (teamname.equals(context.getString(R.string.tottenham_hotspur))) {
                return R.drawable.tottenham_hotspur;
            } else if (teamname.equals(context.getString(R.string.west_bromwich_albion_hd_logo))) {
                return R.drawable.west_bromwich_albion_hd_logo;
            } else if (teamname.equals(context.getString(R.string.sunderland))) {
                return R.drawable.sunderland;
            } else if (teamname.equals(context.getString(R.string.stoke_city))) {
                return R.drawable.stoke_city;
            } else {
                return R.drawable.no_icon;
            }

    }
}
