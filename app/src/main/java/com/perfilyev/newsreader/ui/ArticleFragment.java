package com.perfilyev.newsreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private Medsolutions medsolutions;
    private static final String IMG_BASE_URL = "http://medsolutions.uxp.ru/images/";

    /**
     * Метод на случай, если нам понадобится передавать аргументы при создании фрагмента.
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
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(getActivity()).load(article.getImage()).fit().centerCrop().into(imageView);
        medsolutions.setListener(new Medsolutions.NewsUpdatedListener() {
            @Override
            public void onNewsLoaded(List<Article> articles) {
            }

            @Override
            public void onArticleLoaded(List<Article> articles, List<Spotlight> spotlights) {
                Article articleDetails = articles.get(0);

                articleBodyTv.setText(articleDetails.getText());
                articleSourceTv.setText(articleDetails.getSource());
                articleDateTv.setText(article.getDate());

                Spotlight first = spotlights.get(0);
                Spotlight second = spotlights.get(1);

                Picasso.with(getActivity()).load(IMG_BASE_URL+first.getImage()).fit().centerCrop().into(spotlightImageview);
                Picasso.with(getActivity()).load(IMG_BASE_URL+second.getImage()).fit().centerCrop().into(secondSpotlightImageview);

                spotlightTitleTextview.setText(first.getTitle());
                secondSpotlightTitleTextview.setText(second.getTitle());

                spotlightDateTextview.setText(first.getCreatedAt());
                secondSpotlightDateTextview.setText(second.getCreatedAt());
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
