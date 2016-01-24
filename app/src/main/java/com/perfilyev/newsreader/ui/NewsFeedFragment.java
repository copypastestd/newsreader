package com.perfilyev.newsreader.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.perfilyev.newsreader.EndlessRecyclerViewScrollListener;
import com.perfilyev.newsreader.ui.adapter.NewsAdapter;
import com.perfilyev.newsreader.R;
import com.perfilyev.newsreader.models.Article;
import com.perfilyev.newsreader.models.Spotlight;
import com.perfilyev.newsreader.network.Medsolutions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Фрагмент, отображающий список с новостями.
 */

public class NewsFeedFragment extends Fragment {

    @Bind(R.id.recyclerview_news)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progress;

    private NewsAdapter adapter;
    private Medsolutions medsolutions;
    private List<Article> articleList = new ArrayList<>();
    public static final String KEY = "com.perfilyev.newsreader.ui.NewsFeedFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        medsolutions = new Medsolutions();
        medsolutions.loadNewsFeed(1);
    }

    /**
     * Метод на случай, если нам понадобится передавать аргументы при создании фрагмента.
     *
     * @return фрагмент с аргументами.
     */
    public static NewsFeedFragment newInstance() {
        NewsFeedFragment myFragment = new NewsFeedFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        progress.setVisibility(ProgressBar.VISIBLE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        adapter = new NewsAdapter(getActivity(), articleList);
        if (savedInstanceState == null) {
            medsolutions.setListener(new Medsolutions.NewsUpdatedListener() {
                @Override
                public void onNewsLoaded(List<Article> articles) {
                    int curSize = adapter.getItemCount();
                    articleList.addAll(articles);
                    adapter.notifyItemRangeInserted(curSize, articleList.size() - 1);
                    showRecyclerView();
                }

                @Override
                public void onArticleLoaded(List<Article> articles, List<Spotlight> spotlights) {
                }
            });
        } else {
            articleList = savedInstanceState.getParcelableArrayList(KEY);
            showRecyclerView();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                medsolutions.loadNewsFeed(page);
            }
        });
        return rootView;
    }

    private void showRecyclerView() {
        progress.setVisibility(ProgressBar.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY, new ArrayList<Parcelable>(articleList));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
