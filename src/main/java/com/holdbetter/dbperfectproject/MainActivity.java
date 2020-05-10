package com.holdbetter.dbperfectproject;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.holdbetter.dbperfectproject.fragments.RecyclerFragment;
import com.holdbetter.dbperfectproject.fragments.ResultFragment;
import com.holdbetter.dbperfectproject.room.AuthorAndBooks;
import com.holdbetter.dbperfectproject.room.AuthorEntity;
import com.holdbetter.dbperfectproject.room.BookDatabase;
import com.holdbetter.dbperfectproject.room.BookEntity;
import com.holdbetter.dbperfectproject.room.BookDataRequest;

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
                    AuthorAndBooks gluh = new AuthorAndBooks();
                    gluh.books = new ArrayList<>();
                    AuthorAndBooks harari = new AuthorAndBooks();
                    harari.books = new ArrayList<>();
                    AuthorAndBooks orwell = new AuthorAndBooks();
                    orwell.books = new ArrayList<>();
                    AuthorAndBooks aizekson = new AuthorAndBooks();
                    aizekson.books = new ArrayList<>();
                    AuthorAndBooks dokinz = new AuthorAndBooks();
                    dokinz.books = new ArrayList<>();
                    AuthorAndBooks mandel = new AuthorAndBooks();
                    mandel.books = new ArrayList<>();

                    gluh.author = new AuthorEntity("Дмитрий", "Глуховский", 1234, 1234, "Россия");
                    harari.author = new AuthorEntity("Юваль Ной", "Харари", 1234, 1234, "Израиль");
                    mandel.author = new AuthorEntity("Осип", "Мандельштам", 1234, 1234, "Польша");
                    orwell.author = new AuthorEntity("Джордж", "Оруэлл", 1234, 1234, "Индия");
                    aizekson.author = new AuthorEntity("Уолтер", "Айзексон", 1234, 1234, "США");
                    dokinz.author = new AuthorEntity("Ричард", "Докинз", 1234, 1234, "Кения");

                    gluh.books.add(new BookEntity(1, "Будущее", R.drawable.future, 0));
                    gluh.books.add(new BookEntity(1, "Метро 2033", R.drawable.metro2033, 0));
                    gluh.books.add(new BookEntity(1, "Метро 2034", R.drawable.metro2034, 0));
                    harari.books.add(new BookEntity(2, "Sapiens. Краткая история человечества", R.drawable.sapiens, 0));
                    mandel.books.add(new BookEntity(3, "Я вернулся в мой город", R.drawable.go_back_to_my_town, 0));
                    orwell.books.add(new BookEntity(4, "1984", R.drawable.one_nine_eight_four, 0));
                    aizekson.books.add(new BookEntity(5, "Стив Джобс", R.drawable.steve_jobs, 0));
                    dokinz.books.add(new BookEntity(6, "Эгоистичный ген", R.drawable.egoist, 0));

                    database.bookDao().insert(gluh);
                    database.bookDao().insert(harari);
                    database.bookDao().insert(mandel);
                    database.bookDao().insert(orwell);
                    database.bookDao().insert(aizekson);
                    database.bookDao().insert(dokinz);
                }

//                List<BookEntity> books = database.bookDao().getBooks();
//                List<AuthorEntity> authors = database.bookDao().getAuthors();
//                List<AuthorAndBooks> authorsAndBook = database.bookDao().getAuthorAndBooks();

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooksAndAuthor()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooksAndAuthor()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooksAndAuthor()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooksAndAuthor()));
                transaction.add(R.id.mainContent, new RecyclerFragment(database.bookDao().getBooksAndAuthor())).commit();
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

            List<BookDataRequest> results = database.bookDao().search(searchQuery);

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
