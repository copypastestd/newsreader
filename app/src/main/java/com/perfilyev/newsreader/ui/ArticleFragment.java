package com.perfilyev.newsreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.perfilyev.newsreader.R;
import com.perfilyev.newsreader.models.Article;
import com.perfilyev.newsreader.models.Spotlight;
import com.perfilyev.newsreader.network.Medsolutions;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Фрагмент, отображающий конкретную новость.
 */

public class ArticleFragment extends Fragment {

    @Bind(R.id.backdrop)
    ImageView imageView;
    @Bind(R.id.article_body_textview)
    TextView articleBodyTv;
    @Bind(R.id.article_date_textview)
    TextView articleDateTv;
    @Bind(R.id.article_source_textview)
    TextView articleSourceTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spotlight_imageview)
    ImageView spotlightImageview;
    @Bind(R.id.spotlight_title_textview)
    TextView spotlightTitleTextview;
    @Bind(R.id.spotlight_date_textview)
    TextView spotlightDateTextview;
    @Bind(R.id.second_spotlight_imageview)
    ImageView secondSpotlightImageview;
    @Bind(R.id.second_spotlight_title_textview)
    TextView secondSpotlightTitleTextview;
    @Bind(R.id.second_spotlight_date_textview)
    TextView secondSpotlightDateTextview;

    private Article article;
    private Article articleDetails;
    private Spotlight first;
    private Spotlight second;
    private Medsolutions medsolutions;
    public static final String ARTICLE_KEY = "com.perfilyev.newsreader.ui.ArticleFragment.article";
    public static final String ARTICLE_DETAILS_KEY = "com.perfilyev.newsreader.ui.ArticleFragment.articleDetails";
    public static final String SPOTLIGHT_KEY = "com.perfilyev.newsreader.ui.ArticleFragment.spotlight";
    public static final String SECOND_SPOTLIGHT_KEY = "com.perfilyev.newsreader.ui.ArticleFragment.secondSpotlight";
    private static final String TAG = ArticleFragment.class.getSimpleName();

    /**
     * Метод на случай, если нам понадобится передавать аргументы при создании фрагмента.
     *
     * @return фрагмент с аргументами.
     */
    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle extras = getActivity().getIntent().getExtras();
        medsolutions = new Medsolutions();
        if (extras != null) {
            article = extras.getParcelable(DetailActivity.EXTRA_ARTICLE);
            medsolutions.loadArticle(article.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            medsolutions.setListener(new Medsolutions.NewsUpdatedListener() {
                @Override
                public void onNewsLoaded(List<Article> articles) {
                }

                @Override
                public void onArticleLoaded(List<Article> articles, List<Spotlight> spotlights) {
                    articleDetails = articles.get(0);
                    first = spotlights.get(0);
                    second = spotlights.get(1);
                    setFields();
                }
            });
        } else {
            article = savedInstanceState.getParcelable(ARTICLE_KEY);
            articleDetails = savedInstanceState.getParcelable(ARTICLE_DETAILS_KEY);
            first = savedInstanceState.getParcelable(SPOTLIGHT_KEY);
            second = savedInstanceState.getParcelable(SECOND_SPOTLIGHT_KEY);
            setFields();
        }
        return rootView;
    }

    private void setFields() {
        articleBodyTv.setText(articleDetails.getText());
        articleSourceTv.setText(articleDetails.getSource());
        articleDateTv.setText(article.getDate());
        spotlightTitleTextview.setText(first.getTitle());
        secondSpotlightTitleTextview.setText(second.getTitle());
        spotlightDateTextview.setText(first.getCreatedAt());
        secondSpotlightDateTextview.setText(second.getCreatedAt());
        Picasso.with(getActivity()).load(article.getImage()).fit().centerCrop().into(imageView);
        Picasso.with(getActivity()).load(first.getThumbnail()).fit().centerCrop().into(spotlightImageview);
        Picasso.with(getActivity()).load(second.getThumbnail()).fit().centerCrop().into(secondSpotlightImageview);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARTICLE_KEY, article);
        outState.putParcelable(ARTICLE_DETAILS_KEY, articleDetails);
        outState.putParcelable(SPOTLIGHT_KEY, first);
        outState.putParcelable(SECOND_SPOTLIGHT_KEY, second);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
