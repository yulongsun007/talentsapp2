package win.yulongsun.talents.ui.hr.talent;

import org.byteam.superadapter.SuperAdapter;

import win.yulongsun.talents.base.CommonSearchFragment;

/**
 * @author sunyulong on 2016/11/28.
 *         <p>
 *         人才搜索
 */
public class TalentSearchFragment extends CommonSearchFragment {
    public static TalentSearchFragment newInstance() {
        return new TalentSearchFragment();
    }

    @Override
    protected SuperAdapter getAdapter() {
        return null;
    }

    @Override
    protected String getSubTag() {
        return TalentSearchFragment.class.getSimpleName();
    }

    @Override
    public String getSearchKeyHint() {
        return "人才名";
    }

    @Override
    protected void toSearch() {

    }
}
