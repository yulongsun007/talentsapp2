package win.yulongsun.talents.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import win.yulongsun.framework.cache.ACache;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.User;

/**
 * Create By: yulongsun
 * Date at: 2016/8/19
 * Desc :
 * 功能：
 * 1. 滑动移除
 */
public abstract class BaseSwipeBackFragment extends SwipeBackFragment {


    protected Toolbar _Toolbar;
    protected ACache _Cache;
    protected User _User;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启用Fragment Menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    /** 获取布局 */
    protected abstract int getLayoutResId();

    /** 获取菜单布局 */
    protected int getMenuResId() {
        return 0;
    }

    /** 获取Toolbar */
    protected abstract Toolbar getToolbar();

    /** 获取Toolbar标题 */
    protected abstract String getToolbarTitle();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _Cache=ACache.get(_mActivity);
        _User = new Select().from(User.class).querySingle();
        initView();
        initData();
        loadDataFromServer();
    }

    /** 初始化控件 */
    protected void initView() {
        //Toolbar
        _Toolbar = getToolbar();
        if (_Toolbar != null) {
            _mActivity.setSupportActionBar(_Toolbar);
            _Toolbar.setTitle(getToolbarTitle());
            _Toolbar.setNavigationIcon(R.mipmap.ic_toolbar_arrow_left_white);
            _Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _mActivity.onBackPressed();
                }
            });
        }
    }

    /** 初始化数据 */
    protected void initData() {
    }

    /** 从服务器加载数据*/
    protected void loadDataFromServer(){

    }

    /** 创建上下文菜单 */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuResId() != 0) {
            inflater.inflate(getMenuResId(), menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

