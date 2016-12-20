package win.yulongsun.talents.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.widget.Bottom.BottomBar;
import win.yulongsun.framework.widget.Bottom.BottomBarTab;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.event.TabSelectedEvent;
import win.yulongsun.talents.ui.hr.HRIndexFragment;
import win.yulongsun.talents.ui.login.LoginActivity;
import win.yulongsun.talents.ui.me.MeFragment;
import win.yulongsun.talents.ui.msg.MsgListFragment;
import win.yulongsun.talents.ui.referrer.ReferrerIndexFragment;
import win.yulongsun.talents.ui.stu.StuIndexFragment;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : Main容器
 */
public class MainFragment extends SupportFragment {
    public static final int FIRST  = 0;
    public static final int SECOND = 1;
    public static final int THIRD  = 2;
    @Bind(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private View view;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        //判断登录者身份
        User user = new Select()
                .from(User.class)
                .querySingle();
        if (null == user) {
            ToastUtils.toastL(_mActivity, "登录信息过期，请重新登录");
            Intent intent = new Intent(_mActivity, LoginActivity.class);
            startActivity(intent);
            _mActivity.finish();
            return view;
        }
        int role = user.user_role_id;
        if (savedInstanceState == null) {
            switch (role) {
                case 1://hr
                    mFragments[FIRST] = HRIndexFragment.newInstance();
                    break;
                case 2://referrer
                    mFragments[FIRST] = ReferrerIndexFragment.newInstance();
                    break;
                case 3://stu
                    mFragments[FIRST] = StuIndexFragment.newInstance();
                    break;
            }
            mFragments[SECOND] = MsgListFragment.newInstance();
            mFragments[THIRD] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tabs_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            switch (role) {
                case 1://hr
                    mFragments[FIRST] = findFragment(HRIndexFragment.class);
                    break;
                case 2://referrer
                    mFragments[FIRST] = findFragment(ReferrerIndexFragment.class);
                    break;
                case 3://stu
                    mFragments[FIRST] = findFragment(StuIndexFragment.class);
                    break;
            }
            mFragments[SECOND] = findFragment(MsgListFragment.class);
            mFragments[THIRD] = findFragment(MeFragment.class);
        }

        initView();
        return view;
    }

    private void initView() {
        mBottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bottom_index_white, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bottom_msg_white, "消息"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bottom_me_white, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
