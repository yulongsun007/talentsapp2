package win.yulongsun.talents.ui.msg;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.MsgAdapter;
import win.yulongsun.talents.event.StartBrotherEvent;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 消息
 */
public class MsgFragment extends BaseRootFragment {
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.recy_msg)
    RecyclerView mRecyMsg;
    @Bind(R.id.fl_msg_container)
    LinearLayout mFlMsgContainer;

    public static MsgFragment newInstance() {
        return new MsgFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "消息";
    }


    @Override
    protected void initView() {
        super.initView();


        mRecyMsg.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        ArrayList<Msg>      msgs                = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            msgs.add(new Msg());
        }
        MsgAdapter msgAdapter = new MsgAdapter(_mActivity,msgs,R.layout.item_msg);
        mRecyMsg.setLayoutManager(linearLayoutManager);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());

        mRecyMsg.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mRecyMsg.setAdapter(msgAdapter);

        msgAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(MsgDetailFragment.newInstance()));
            }
        });

    }
}
