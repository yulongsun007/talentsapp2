package win.yulongsun.talents.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.Call;
import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.ResponseList;

/**
 * @author sunyulong on 2016/12/3.
 *         通用的带RecyclerView的布局
 */
public abstract class CommonRootListFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = CommonRootListFragment.class.getSimpleName();
    protected User _mUser;
    protected List _mDatas = new ArrayList<>();
    protected SuperAdapter       _mAdapter;
    @Bind(R.id.toolbar)
    protected Toolbar            _mToolbar;
    @Bind(R.id.ll_common_no_data)
    protected LinearLayout       _mLlCommonNoData;
    @Bind(R.id.iv_common_no_data_img)
    protected ImageView          _mIvCommonNoDataImg;
    @Bind(R.id.tv_common_no_data_tip)
    protected TextView           _mTvCommonNoDataTip;
    @Bind(R.id.recy_common_list)
    protected RecyclerView       _mRecyCommonList;
    @Bind(R.id.srf_common_list)
    protected SwipeRefreshLayout _mSrfCommonList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启用Fragment Menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_layout_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    ///////////////////////////////////// initView  //////////////////////////////////////////

    /** 获取菜单布局 */
    protected int getMenuResId() {
        return 0;
    }

    /** 获取Toolbar标题 */
    protected abstract String getToolbarTitle();

    protected abstract SuperAdapter getAdapter();

    /** 初始化控件 */
    protected void initView() {
        //Toolbar
        if (_mToolbar != null) {
            _mActivity.setSupportActionBar(_mToolbar);
            _mToolbar.setTitle(getToolbarTitle());
        }
        //srf
        _mSrfCommonList.setOnRefreshListener(this);
        _mAdapter = getAdapter();
        _mRecyCommonList.setLayoutManager(new LinearLayoutManager(_mActivity));
        _mRecyCommonList.setAdapter(_mAdapter);
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
        OkHttpUtils.getInstance().cancelTag(this);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    //////////////////////////////////////  initData /////////////////////////////////////////

    /** 初始化数据 */
    protected void initData() {
        _mUser = new Select().from(User.class).querySingle();
    }

    /** 从数据库获取数据 提供用于加载本地数据 */
    protected void loadDataFromLocal(Class<? extends BaseModel> clazz) {
        _mDatas = new Select().from(clazz).queryList();
//        if (_mDatas.isEmpty()) {
//            _mLlCommonNoData.setVisibility(View.VISIBLE);
//        } else {
//            _mLlCommonNoData.setVisibility(View.GONE);
//        }
    }


    /**
     * 从服务器数据
     *
     * @param url                请求地址
     * @param requestEntity      封装了请求数据
     * @param requestEntityClazz 封装了请求数据的Class
     * @param respClazz          返回数据
     */
    protected void loadDataFromServer(String url, Object requestEntity, Class requestEntityClazz, final Class<? extends ResponseList> respClazz) {
        _mSrfCommonList.setRefreshing(true);
        PostFormBuilder builder = OkHttpUtils.post().tag(this).url(Constant.URL + url);
        //添加参数
        Field[] fields = requestEntityClazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                builder.addParams(fields[i].getName(), String.valueOf(fields[i].get(requestEntity)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        _mSrfCommonList.setRefreshing(false);
//                        ToastUtils.toastL(_mActivity, ToastUtils.CONNECT_SERVER_EXCEPTION);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        _mSrfCommonList.setRefreshing(false);
                        loadServerDataResult(response, respClazz);
                    }
                });
    }

    /** 返回数据 */
    public void loadServerDataResult(String response, Class respClazz) {
        ResponseList resp = (ResponseList) JsonUtil.fromJson(response, respClazz);
        _mDatas = resp.data;
        _mAdapter.replaceAll(_mDatas);
        if (_mDatas.isEmpty()) {
            _mLlCommonNoData.setVisibility(View.VISIBLE);
        } else {
            _mLlCommonNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

}
