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
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.Call;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.ResponseList;

import static com.raizlabs.android.dbflow.config.FlowLog.TAG;

/**
 * @author sunyulong on 2016/12/3.
 *         用于子View继承
 */
public abstract class BaseChildFragment extends SupportFragment {
    private Toolbar _Toolbar;
    protected User _User;
    protected List<? extends BaseModel> mDatas = new ArrayList<>();

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

        _User = new Select().from(User.class).querySingle();
        initView();
        initData();
        loadDataFromLocal();
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

    /** 从数据库获取数据 */
    protected void loadDataFromLocal() {
    }

    /** 从数据库获取数据 */
    protected void loadDataFromLocal(BaseModel model) {
        mDatas = new Select().from(model.getClass()).queryList();
    }

    /** 从服务器数据 */
    protected void loadDataFromServer() {

    }

    /** 从服务器数据 */
    protected void loadDataFromServer(String url, Object object, Class clazz, final Class<? extends ResponseList> respClazz) {
        PostFormBuilder builder = OkHttpUtils.post().tag(TAG).url(Constant.URL + url);
        //添加参数
        Field[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                builder.addParams(fields[i].getName(), String.valueOf(fields[i].get(object)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.toastL(_mActivity, ToastUtils.CONNECT_SERVER_EXCEPTION);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadServerDataResult(response, respClazz);
                    }
                });
    }

    /** 返回数据 */
    public void loadServerDataResult(String response, Class respClazz) {
        ResponseList resp = (ResponseList) JsonUtil.fromJson(response, respClazz);
        mDatas = resp.data;
    }

}
