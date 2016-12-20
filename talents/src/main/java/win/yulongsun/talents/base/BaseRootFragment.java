package win.yulongsun.talents.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.framework.cache.ACache;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.User;

/**
 * Create By: yulongsun
 * Date at: 2016/8/29
 * Desc : 子View基类
 * 功能：
 * 1.双击退出app
 * 2.根View
 */
public abstract class BaseRootFragment extends SupportFragment {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME  = 2000L;
    private              long TOUCH_TIME = 0;
    private Toolbar _Toolbar;
    public ACache _Cache;
    public User   _User;

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

        _Cache = ACache.get(_mActivity);

        _User = new Select().from(User.class).querySingle();
        initView();
        initData();
        loadDataFromServer();
        return view;
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

    /** 初始化控件 */
    protected void initView() {
        //Toolbar
        _Toolbar = getToolbar();
        if (_Toolbar != null) {
            _mActivity.setSupportActionBar(_Toolbar);
            _mActivity.getSupportActionBar().setTitle(getToolbarTitle());
            if (getMenuResId() != 0) {
                _Toolbar.inflateMenu(getMenuResId());
            }
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

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
