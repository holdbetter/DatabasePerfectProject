package com.holdbetter.dbperfectproject;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.holdbetter.dbperfectproject.database.Book;
import com.holdbetter.dbperfectproject.database.DbHelper;
import com.holdbetter.dbperfectproject.fragments.RecyclerFragment;
import com.holdbetter.dbperfectproject.fragments.ResultFragment;
import com.holdbetter.dbperfectproject.room.BookDatabase;
import com.holdbetter.dbperfectproject.room.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
//    DbHelper dbHelper;
    BookDatabase database;
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

//        dbHelper = new DbHelper(this);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                database = Room.databaseBuilder(getApplicationContext(), BookDatabase.class, "BookDB").build();

                if (database.bookDao().getBooksCount() == 0)
                {
                    BookEntity futureBook = new BookEntity("Будущее", "Глуховский", R.drawable.future, 0);
                    BookEntity sapiensBook = new BookEntity("Sapiens. Краткая история человечества", "Харари", R.drawable.sapiens, 0);
                    BookEntity mandelBook = new BookEntity("Я вернулся в мой город", "Мандельштам", R.drawable.go_back_to_my_town, 0);
                    BookEntity orwellBook = new BookEntity("1984", "Оруэлл", R.drawable.one_nine_eight_four, 0);
                    BookEntity jobsBook = new BookEntity("Стив Джобс", "Айзексон", R.drawable.steve_jobs, 0);
                    BookEntity dokinsBook = new BookEntity("Эгоистичный ген", "Докинз", R.drawable.egoist, 0);

                    database.bookDao().insertAll(futureBook, sapiensBook, mandelBook, orwellBook,
                            jobsBook, dokinsBook);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooks()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooks()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooks()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooks()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooks())).commit();
            }
        }).start();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                handleIntent(getIntent());
            }
        }).start();

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

            searchQuery = "%" + query + "%";

            List<BookEntity> results = database.bookDao().search(searchQuery);

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

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    searchView.clearFocus();
                }
            });
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        database.close();
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
