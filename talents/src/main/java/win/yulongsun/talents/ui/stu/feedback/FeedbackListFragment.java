package win.yulongsun.talents.ui.stu.feedback;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.base.CommonListFragment;

/**
 * @author sunyulong on 2016/12/25.
 */
public class FeedbackListFragment extends CommonListFragment {
    public static FeedbackListFragment newInstance() {
        return new FeedbackListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "投递反馈";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return null;
    }

    @Override
    protected String getSubTag() {
        return FeedbackListFragment.class.getSimpleName();
    }
}
