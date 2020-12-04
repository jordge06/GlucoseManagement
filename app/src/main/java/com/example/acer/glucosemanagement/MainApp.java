package com.example.acer.glucosemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.glucosemanagement.Databases.DBUserLogbook;
import com.example.acer.glucosemanagement.Databases.DBUserPills;

import com.example.acer.glucosemanagement.Databases.Log;
import com.example.acer.glucosemanagement.Databases.LogViewModel;
import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.Models.PillsCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;
import com.google.android.material.navigation.NavigationView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//implements NavigationView.OnNavigationItemSelectedListener


public class MainApp extends AppCompatActivity implements IMainInterface, RecyclerInterface {

    // widgets
    Toolbar toolbar;
    //private DrawerLayout drawer;

    // vars
    String email = "john016dionisio@gmail.com";

    //instance
    private LogViewModel logViewModel;
    //private DBUserLogbook dbUserLogbook;
    private DBUserPills dbUserPills;

    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        drawerLayout = findViewById(R.id.drawer_layout);

        //dbUserLogbook = new DBUserLogbook(this);
        dbUserPills = new DBUserPills(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Set<Integer> topLevelDestinations = new ArraySet<>();
        topLevelDestinations.add(R.id.nav_logbook);
        topLevelDestinations.add(R.id.nav_connect);
        topLevelDestinations.add(R.id.nav_reports);
        topLevelDestinations.add(R.id.nav_settings);
        topLevelDestinations.add(R.id.nav_manual);
        topLevelDestinations.add(R.id.nav_feedback);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations)
                .setOpenableLayout(drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(this.<NavigationView>findViewById(R.id.nav_view), navController);


        logViewModel = new ViewModelProvider(this).get(LogViewModel.class);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    // Get the function in interface then execute the functionality

    @Override
    public void toggleLayout(ExpandableLayout expandableLayout, ImageView imageView) {

        if (!expandableLayout.isExpanded()) {
            expandableLayout.expand();
            imageView.setBackgroundResource(R.drawable.ic_collapse);
        } else {
            expandableLayout.collapse();
            imageView.setBackgroundResource(R.drawable.ic_expand);
        }
    }

    @Override
    public void saveSettings(int unitSelection, int weightSelection, int a1cSelection) {

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.KEY_SHARED_PREFS), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.KEY_BLOOD_SUGAR_SETTINGS), unitSelection);
        editor.putInt(getString(R.string.KEY_A1C_SETTINGS), a1cSelection);
        editor.putInt(getString(R.string.KEY_WEIGHT_SETTINGS), weightSelection);
        editor.apply();

        navController.popBackStack(R.id.nav_settings, true);
        navController.navigate(R.id.nav_logbook);
    }

    @Override
    public void savePillEntries2(List<PillsCategory> pillsCategoryList) {
        ArrayList<String> pills = new ArrayList<>();

        for (PillsCategory pillsCategory : pillsCategoryList) {
            pills.add(pillsCategory.getPillsEntry());
        }
        dbUserPills.deleteAllPills(email);
        dbUserPills.insertAllPillsData(email, pills);
        Toast.makeText(this, "Pill Saved", Toast.LENGTH_SHORT).show();

        navController.navigate(R.id.action_pillsEntryFragment_to_nav_settings);
    }

    @Override
    public void inflateFragment(String fragmentTag) {

        if (fragmentTag.equals(getString(R.string.btn_addEntry))) {
            navController.navigate(R.id.action_nav_logbook_to_addEntryFragment);
        } else if (fragmentTag.equals(getString(R.string.txt_pills))) {
            navController.navigate(R.id.action_addEntryFragment_to_pillsEntryFragmentWithArgs);
        } else if (fragmentTag.equals("txt_pills")) {
            navController.navigate(R.id.action_nav_settings_to_pillsEntryFragment);
        } else {
            Toast.makeText(this, "Null Pointer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addLogs(HashMap<String, String> logsList, ArrayList<PillSelectCategory> pillSelectCategoryArrayList) {
        logViewModel.insert(createLog(logsList, pillSelectCategoryArrayList));

        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_logbook, true).build();
        navController.navigate(R.id.action_addEntryFragment_to_nav_logbook, null, navOptions);

    }

    @Override
    public void updateLogs(ArrayList<PillSelectCategory> pillSelectCategoryArrayList, HashMap<String, String> logsList, int id) {

        logViewModel.update(createLog(logsList, pillSelectCategoryArrayList));
        navController.navigate(R.id.action_addEntryFragment_to_nav_logbook);
    }

    @Override
    public void deleteEntry(int id) {
        //logViewModel.delete();
        navController.navigate(R.id.action_displayEntryFragment_to_nav_logbook);

    }

    private Log createLog(HashMap<String, String> logsList, ArrayList<PillSelectCategory> pillSelectCategoryArrayList) {

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < pillSelectCategoryArrayList.size(); i++) {
            String name;
            String amount;
            if (!(pillSelectCategoryArrayList.get(i).getPillsName().equals("-") ||
                    pillSelectCategoryArrayList.get(i).getPillsAmount().equals("-"))) {
                if (i == pillSelectCategoryArrayList.size() - 1) {
                    name = pillSelectCategoryArrayList.get(i).getPillsName();
                    amount = pillSelectCategoryArrayList.get(i).getPillsAmount();

                } else {
                    name = pillSelectCategoryArrayList.get(i).getPillsName() + ",";
                    amount = pillSelectCategoryArrayList.get(i).getPillsAmount() + ",";

                }
                sb1.append(name);
                sb2.append(amount);
            }

        }

        Log log = new Log(
                logsList.get(DBUserLogbook.KEY_EMAIL),
                logsList.get(DBUserLogbook.KEY_DATE),
                logsList.get(DBUserLogbook.KEY_TIME),
                logsList.get(DBUserLogbook.KEY_SUGAR_LEVEL),
                logsList.get(DBUserLogbook.KEY_SYSTOLIC_PRESSURE),
                logsList.get(DBUserLogbook.KEY_DIASTOLIC_PRESSURE),
                logsList.get(DBUserLogbook.KEY_A1C_LEVEL),
                logsList.get(DBUserLogbook.KEY_WEIGHT),
                String.valueOf(sb1),
                String.valueOf(sb2),
                logsList.get(DBUserLogbook.KEY_EVENT),
                logsList.get(DBUserLogbook.KEY_NOTES),
                logsList.get(DBUserLogbook.KEY_TAGS)
        );

        return log;

    }

    @Override
    public void passDataToAddEntry(ArrayList<RowCategory> rowCategoryArrayList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Sample_Key", rowCategoryArrayList);
        navController.navigate(R.id.action_displayEntryFragment_to_addEntryFragment, bundle);
    }



    @Override
    public void savePillEntries(List<PillsCategory> pillsCategoryList) {

        ArrayList<String> pills = new ArrayList<>();

        for (PillsCategory pillsCategory : pillsCategoryList) {
            pills.add(pillsCategory.getPillsEntry());
        }
        dbUserPills.deleteAllPills(email);
        dbUserPills.insertAllPillsData(email, pills);
        Toast.makeText(this, "Pill Saved", Toast.LENGTH_SHORT).show();

        navController.navigate(R.id.action_pillsEntryFragmentWithArgs_to_addEntryFragment);

    }

    // Getting the List of New Entries from AddEntryFragment then add them to Database



    @Override
    public void onRowClick(ArrayList<RowCategory> rowCategoryList, int pos) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Sample_Key", rowCategoryList);

        navController.navigate(R.id.action_nav_logbook_to_displayEntryFragment, bundle);

        //Toast.makeText(this, "" + pos, Toast.LENGTH_SHORT).show();
    }
}