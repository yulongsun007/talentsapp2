package win.yulongsun.talents.ui.hr.referrer;

import org.byteam.superadapter.SuperAdapter;

import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerListRVAdapter;
import win.yulongsun.talents.base.CommonSearchFragment;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.biz.UserResponse;

/**
 * @author sunyulong on 2016/12/2.
 *         推荐人搜索
 */
public class ReferrerSearchFragment extends CommonSearchFragment {
    @Override
    protected SuperAdapter getAdapter() {
        return new ReferrerListRVAdapter(_mActivity, _mDatas, R.layout.item_referrer_list);
    }

    @Override
    protected String getSubTag() {
        return ReferrerSearchFragment.class.getSimpleName();
    }

    @Override
    public String getSearchKeyHint() {
        return "推荐人姓名";
    }

    public static SupportFragment newInstance() {
        return new ReferrerSearchFragment();
    }

    //搜索
    @Override
    protected void toSearch() {
        User user = new User();
        user.user_name = _mEtCommonSearchKey.getText().toString();
        user.user_company_id = _mUser.user_company_id;
        loadDataFromServer("user/queryReferrer", user, UserResponse.class);
    }

}
