package com.holdbetter.dbperfectproject;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.holdbetter.dbperfectproject.databinding.ActivityMainBinding;
import com.holdbetter.dbperfectproject.fragments.RecyclerFragment;
import com.holdbetter.dbperfectproject.fragments.ResultFragment;
import com.holdbetter.dbperfectproject.viewmodel.BooksViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);

        searchViewSetup();

        BooksViewModel model = new ViewModelProvider(this).get(BooksViewModel.class);
        binding.setModel(model);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.bookshelf1, RecyclerFragment.getInstance(RecyclerType.RoomAndLiveData));
        transaction.replace(R.id.bookshelf2, RecyclerFragment.getInstance(RecyclerType.SQLiteOpenHelper));
        transaction.replace(R.id.bookshelf3, RecyclerFragment.getInstance(RecyclerType.DownloadingData)).commit();
    }

    private void searchViewSetup()
    {
        MenuItem searchMenuItem = binding.toolbar.getMenu().findItem(R.id.searchMenuItem);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

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
                cleanStackOnCloseOrBackPressed();
                return true;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("Поиск книг");
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        new Thread(() -> handleIntent(getIntent())).start();
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            if (searchQuery != null && searchQuery.equals(query)
                    && manager.findFragmentByTag("ResultFragment") != null)
                return;

            searchQuery = "%" + query + "%";

            List<Fragment> fragments = manager.getFragments();
            for (Fragment fragment : fragments)
            {
                transaction.detach(fragment);
            }

            transaction.replace(R.id.searchResults, new ResultFragment(searchQuery), "ResultFragment");

            if (fragments.size() > 1)
            {
                transaction.addToBackStack("Home");
            } else
            {
                transaction.addToBackStack("MoreSearch");
            }

            transaction.commit();

            runOnUiThread(() ->
            {
                MenuItem searchMenuItem = binding.toolbar.getMenu().findItem(R.id.searchMenuItem);
                SearchView searchView = (SearchView) searchMenuItem.getActionView();
                searchView.clearFocus();
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        cleanStackOnCloseOrBackPressed();
    }

    void cleanStackOnCloseOrBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStackImmediate("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
