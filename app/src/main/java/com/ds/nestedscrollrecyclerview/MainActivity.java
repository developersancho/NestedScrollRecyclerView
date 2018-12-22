package com.ds.nestedscrollrecyclerview;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ds.nestedscrollrecyclerview.Adapter.MyAdapter;
import com.ds.nestedscrollrecyclerview.Model.Model;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;

public class MainActivity extends AppCompatActivity {

    private boolean isShowingCardHeaderShadow;
    List<Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        generateModelList();
        final MaxHeightRecyclerView rv = findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new MyAdapter(this, modelList));
        rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));
        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrollToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;

                if (!isRecyclerViewScrollToTop && !isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = true;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                } else {
                    isShowingCardHeaderShadow = false;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && oldScrollY > 0) {
                    // reset recyclerview scroll to position each time card return to start position
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });
    }

    private void showOrHideView(View view, boolean isShow) {
        view.animate().alpha(isShow ? 1f : 0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    private void generateModelList() {
        modelList.add(new Model("https://i.cnnturk.com/ps/cnnturk/75/0x0/5c1dddb961361f2e0c3b94ea.jpg", "İstanbul -1"));
        modelList.add(new Model("http://gezilecekyerler.com/wp-content/uploads/2017/06/%C4%B0stanbul-Gezilecek-Yerler.jpg", "İstanbul -2"));
        modelList.add(new Model("http://2.bp.blogspot.com/-wgjI2SgLDk0/UiTqGIx4FmI/AAAAAAAADak/nModG3D6cvw/s1600/Istanbu2.jpg", "İstanbul -3"));
        modelList.add(new Model("https://cdn2.neredekal.com/res/blog/760x380/1524057774_ana_gorsel.jpg", "İstanbul -4"));
    }
}
