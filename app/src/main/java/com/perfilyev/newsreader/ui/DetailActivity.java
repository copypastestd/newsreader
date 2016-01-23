package com.perfilyev.newsreader.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.perfilyev.newsreader.models.Article;

/**
 * Активити, содержащая единственный фрагмент.
 */

public class DetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_ARTICLE = "com.perfilyev.newsreader.article";

    @Override
    protected Fragment createFragment() {
        return ArticleFragment.newInstance();
    }

    /**
     * Метод, служащий для создания этой активити с нужными нам аргументами.
     */
    public static Intent newIntent(Context context, Article article) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        return intent;
    }
}
