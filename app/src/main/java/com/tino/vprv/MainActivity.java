package com.tino.vprv;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.tino.vprv.adapter.CustomViewPagerAdapter;
import com.tino.vprv.adapter.RVAdapter;
import com.tino.vprv.model.DataModel;
import com.tino.vprv.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private LinearLayout points;
    /**
     * 应用管理每页显示最大数量
     */
    private final int PAGE_SIZE = 8;

    private List<View> viewList = new ArrayList();
    private List<RVAdapter> appAdapters = new ArrayList<>();
    private CustomViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        points = findViewById(R.id.points);

        ArrayList<DataModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataModel dataModel = new DataModel();
            dataModel.id = i;
            dataModel.name = "第" + i + "项";
        }
        initData(list);
    }


    private void initData(final ArrayList<DataModel> list) {
        viewList.clear();
        appAdapters.clear();
        points.removeAllViews();
        int pageCount = (list.size() - 1) / PAGE_SIZE + 1;
        for (int index = 0; index < pageCount; index++) {
            RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.item_rv, viewPager, false);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            RVAdapter appAdapter = null;
            if (index * PAGE_SIZE + PAGE_SIZE - 1 > list.size() - 1) {
                appAdapter = new RVAdapter(this, list.subList(index * PAGE_SIZE, list.size()));
            } else {
                appAdapter = new RVAdapter(this, list.subList(index * PAGE_SIZE, index * PAGE_SIZE + PAGE_SIZE));
            }
            recyclerView.setAdapter(appAdapter);
            appAdapters.add(appAdapter);
            viewList.add(recyclerView);
            if (pageCount > 1) {
                points.setVisibility(VISIBLE);
                View pointView = new View(this);
                pointView.setBackgroundResource(R.drawable.indicator_selector);
                points.addView(pointView);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pointView.getLayoutParams();
                if (index == 0) {
                    params.width = dip2px(5);
                    pointView.setSelected(true);
                } else {
                    params.width = dip2px(2);
                    pointView.setSelected(false);
                    params.setMargins(dip2px(2), 0, 0, 0);
                }
                params.height = dip2px(2);
                pointView.setLayoutParams(params);
            } else {
                points.setVisibility(View.GONE);
            }
        }
        if (viewPagerAdapter != null) {
            viewPagerAdapter.notifyDataSetChanged();
        } else {
            viewPagerAdapter = new CustomViewPagerAdapter(viewList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < points.getChildCount(); i++) {
                        View view = points.getChildAt(i);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                        if (i == position) {
                            params.width = dip2px(5);
                            view.setSelected(true);
                        } else {
                            params.width = dip2px(2);
                            view.setSelected(false);
                        }
                        params.height = dip2px(2);
                        view.setLayoutParams(params);
                    }
                }
            });
        }
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}