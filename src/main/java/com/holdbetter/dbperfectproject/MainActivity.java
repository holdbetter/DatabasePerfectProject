package com.holdbetter.dbperfectproject;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.holdbetter.dbperfectproject.database.Book;
import com.holdbetter.dbperfectproject.database.DbHelper;
import com.holdbetter.dbperfectproject.fragments.RecyclerFragment;
import com.holdbetter.dbperfectproject.fragments.ResultFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    DbHelper dbHelper;
    private SearchView searchView;
    private Toolbar toolbar;
    private String searchQuery;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        MenuItem searchMenuItem = toolbar.getMenu().findItem(R.id.searchMenuItem);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                {
                    getSupportFragmentManager().popBackStackImmediate("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return true;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("Поиск книг");

        dbHelper = new DbHelper(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainContent, new RecyclerFragment(dbHelper.getBooks()));
        transaction.add(R.id.mainContent, new RecyclerFragment(dbHelper.getBooks()));
        transaction.add(R.id.mainContent, new RecyclerFragment(dbHelper.getBooks()));
        transaction.add(R.id.mainContent, new RecyclerFragment(dbHelper.getBooks()));
        transaction.add(R.id.mainContent, new RecyclerFragment(dbHelper.getBooks())).commit();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        handleIntent(getIntent());
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            FragmentManager manager = getSupportFragmentManager();

            if (searchQuery != null && searchQuery.equals(query)
                    && manager.findFragmentByTag("ResultFragment") != null) return;

            searchQuery = query;

            List<Book> results = dbHelper.search(searchQuery);
            FragmentTransaction transaction = manager.beginTransaction();

            List<Fragment> fragments = manager.getFragments();
            for (Fragment fragment : fragments)
            {
                transaction.detach(fragment);
            }

            if (results.size() != 0)
            {
                transaction.replace(R.id.searchResults, new ResultFragment(results), "ResultFragment");
            }
            else
            {
                transaction.replace(R.id.searchResults, new EmptyFragment());
            }

            if (fragments.size() > 1)
            {
                transaction.addToBackStack("Home");
                transaction.commit();
            }
            else
            {
                transaction.addToBackStack("MoreSearch");
                transaction.commit();
            }

            searchView.clearFocus();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStackImmediate("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                if (getSupportFragmentManager().getBackStackEntryCount() > 0)
//                {
//                    getSupportFragmentManager().popBackStackImmediate();
//                }
//                return true;
//        }
//
//        return false;
//    }
}
