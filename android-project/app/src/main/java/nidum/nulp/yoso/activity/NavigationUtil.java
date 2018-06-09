package nidum.nulp.yoso.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.EnumMap;

import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso_project.R;

import static java.lang.String.format;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.FINE;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.LOW;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.MASTERED;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.MIDDLE;

public class NavigationUtil {
    public static void initNavigation(final Context context, final Activity caller) {
        NavigationView navigationView = caller.findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        menuItem.setChecked(true);

                        if (id == R.id.nav_kana && !(caller instanceof MainActivity)) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        } else if (id == R.id.nav_radicals) {
                            Intent intent = new Intent(context, RadicalsListActivity.class);
                            context.startActivity(intent);
                        } else if (id == R.id.nav_kanji && !(caller instanceof KanjiLevelListActivity)) {
                            Intent intent = new Intent(context, KanjiLevelListActivity.class);
                            context.startActivity(intent);
                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(context, SearchActivity.class);
                            context.startActivity(intent);
                        } else if (id == R.id.nav_info) {
                            Intent intent = new Intent(context, InstructionActivity.class);
                            context.startActivity(intent);
                        }

                        DrawerLayout drawer = caller.findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}
