package com.wujingjingguanxueyuan.yidaogan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.activity.PublishPostActivity;
import com.wujingjingguanxueyuan.yidaogan.adapter.PostAdapter;
import com.wujingjingguanxueyuan.yidaogan.base.BaseFragment;
import com.wujingjingguanxueyuan.yidaogan.event.RefreshPostEvent;
import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Post;
import com.wujingjingguanxueyuan.yidaogan.mvp.presenter.ShowPostPresenter;
import com.wujingjingguanxueyuan.yidaogan.mvp.view.ShowPostsView;
import com.wujingjingguanxueyuan.yidaogan.widget.SwipeRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created on 17/9/12 21:26
 */

public class DiscoverFragment extends BaseFragment implements ShowPostsView {
    private View rootView;

    @Bind(R.id.swipe_recycle_post)
    SwipeRecyclerView mSwipeRecyclePost;
    @Bind(R.id.tv_error)
    TextView mTvError;
    @Bind(R.id.fb_add_post)
    FloatingActionButton mFbAddPost;


    private List<Post> mPosts;
    private PostAdapter mPostAdapter;
    private ShowPostPresenter mShowPostPresenter;
    private int page = 1;
    private final int COUNT = 12;
    private final int PAGE = 1;

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, rootView);
        mPosts = new ArrayList<>();
        mShowPostPresenter = new ShowPostPresenter(this);
        mShowPostPresenter.showPosts(PAGE, COUNT);

        mSwipeRecyclePost.getRecyclerView().setHasFixedSize(true);
        mSwipeRecyclePost.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRecyclePost.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        return rootView;
    }

    /**
     *
     */
    private void loadMore() {
        page = page + 1;
        mSwipeRecyclePost.setRefreshEnable(false);
        mShowPostPresenter.showPosts(page, COUNT);
    }

    /**
     *
     */
    private void refresh() {
        page = PAGE;
        mSwipeRecyclePost.setLoadMoreEnable(false);
        mShowPostPresenter.showPosts(page, COUNT);
    }

    public DiscoverFragment() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_error, R.id.fb_add_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_error:
                break;
            case R.id.fb_add_post:
                startActivity(new Intent(getActivity(), PublishPostActivity.class));
                break;
        }
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void hideDialog() {
    }

    @Override
    public void showError(Throwable throwable) {
        //TODO 弹出toast的时候 fragment已经死掉
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPosts(List<Post> posts) {
        //TODO 显示数据
        mSwipeRecyclePost.setLoadMoreEnable(true);
        mSwipeRecyclePost.setRefreshEnable(true);
        if (page == PAGE) {
            mPosts.clear();
            mPosts.addAll(posts);
            mSwipeRecyclePost.setRefreshing(false);
            if (mPosts.size() < 1) {
                mSwipeRecyclePost.setEmptyView(mTvError);
            } else {
                if (mPosts.size() < COUNT) {
                    mSwipeRecyclePost.complete();
                    mSwipeRecyclePost.setLoadMoreEnable(false);
                }
                if (mPostAdapter == null) {
                    mPostAdapter = new PostAdapter(getContext(), mPosts);
                    mSwipeRecyclePost.setAdapter(mPostAdapter);
                } else {
                    mPostAdapter.notifyDataSetChanged();
                }
            }
        } else {
            if (posts.size() < COUNT) {
                mSwipeRecyclePost.complete();
                mSwipeRecyclePost.setLoadMoreEnable(false);
            }
            mSwipeRecyclePost.stopLoadingMore();
            mPosts.addAll(posts);
            mPostAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPostEvent(RefreshPostEvent event) {
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
