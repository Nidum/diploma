package nidum.nulp.yoso.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import nidum.nulp.yoso_project.R;

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
                        } else if (id == R.id.nav_kanji && !(caller instanceof KanjiActivity)) {
                            Intent intent = new Intent(context, KanjiActivity.class);
                            context.startActivity(intent);
                        } else if (id == R.id.nav_flashcards) {

                        } else if (id == R.id.nav_info) {

                        }

                        DrawerLayout drawer = caller.findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}
