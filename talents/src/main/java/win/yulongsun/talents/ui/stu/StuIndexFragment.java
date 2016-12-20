package win.yulongsun.talents.ui.stu;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.StuIndexRVAdapter;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.ItemIndex;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.stu.plan.StuPlanListFragment;

/**
 * @author sunyulong on 2016/12/15.
 *         学生端首页
 */
public class StuIndexFragment extends BaseRootFragment {
    //    @Bind(R.id.vp_stu_index)
//    ViewPager mVpStuIndex;
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.recy_stu_index)
    RecyclerView mRecyStuIndex;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_stu_index;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "首页";
    }

    public static StuIndexFragment newInstance() {
        return new StuIndexFragment();
    }

    @Override
    protected void initView() {
        super.initView();

        ArrayList<ItemIndex> mDatas = new ArrayList<>();
        mDatas.add(new ItemIndex(R.mipmap.ic_index_class, "课程"));
        mDatas.add(new ItemIndex(R.mipmap.ic_index_plan, "培养计划"));
        mDatas.add(new ItemIndex(R.mipmap.ic_index_internship, "实习"));
        mDatas.add(new ItemIndex(R.mipmap.ic_index_feedback, "投递反馈"));
//        mDatas.add(new ItemIndex(R.mipmap.ic_index_hr, "HR邀约"));
//        mDatas.add(new ItemIndex(R.mipmap.ic_index_career_talk, "宣讲会邀约"));
//        mDatas.add(new ItemIndex(R.mipmap.ic_index_talent_pool, "人才库邀约"));
//        mDatas.add(new ItemIndex(R.mipmap.ic_index_faq, "校招邀约"));
        mDatas.add(new ItemIndex(R.mipmap.ic_index_other, "其他"));

        StuIndexRVAdapter mAdapter = new StuIndexRVAdapter(_mActivity, mDatas, R.layout.item_stu_index);
        mRecyStuIndex.setAdapter(mAdapter);
        GridLayoutManager mManager = new GridLayoutManager(_mActivity, 3);
        mRecyStuIndex.setLayoutManager(mManager);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        EventBus.getDefault().post(new StartBrotherEvent(StuPlanListFragment.newInstance()));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
//        mRecyStuIndex.addItemDecoration(new MyItemDecoration());
        //添加Item点击事件
//        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view,int position) {
//                Log.d(TAG, "onItemClick: "+position);
//                switch (position){
//                    case 1://
//                        EventBus.getDefault().post(new StartBrotherEvent(InternshipFragment.newInstance()));
//                        break;
//                    case 2:
//                        break;
//                }
//
//
//            }
//        });

//        ArrayList<ImageView> mHeaderDatas = new ArrayList<>();
//        ImageView mView = new ImageView(_mActivity);
//        ImageView mView2 = new ImageView(_mActivity);
//        ImageView mView3 = new ImageView(_mActivity);
//        mView.setImageResource(R.mipmap.img_zhaoping1);
//        mView2.setImageResource(R.mipmap.img_zhaoping2);
////        mView3.setImageResource(R.mipmap.img_zhaoping3);
//        mHeaderDatas.add(mView);
//        mHeaderDatas.add(mView2);
////        mHeaderDatas.add(mView3);
//
//        mVpStuIndex.setAdapter(new StuIndexVPAdapter(mHeaderDatas));
//        ImageView imageView = (ImageView) _mActivity.findViewById(R.id.iv);
//        ImageLoadManager.getInstance().with(_mActivity).load(R.mipmap.img_zhaoping1).into(imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
