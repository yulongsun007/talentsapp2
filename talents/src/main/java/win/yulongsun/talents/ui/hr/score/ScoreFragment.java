package win.yulongsun.talents.ui.hr.score;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.Talent;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ScoreRVAdapter;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *  积分排名
 */
public class ScoreFragment extends BaseRootFragment {

    @Bind(R.id.recy_score)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_score;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static final ScoreFragment INSTANCE = new ScoreFragment();

    public static ScoreFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayList<Talent> mDatas = new ArrayList<>();
        for (int i=0;i<35;i++){
            mDatas.add(new Talent());
        }
        ScoreRVAdapter mAdapter = new ScoreRVAdapter(_mActivity, mDatas, R.layout.item_score);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);



    }
}
