package win.yulongsun.talents.adapter;

import android.content.Context;
import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.User;

/**
 * @author sunyulong on 2016/11/30.
 *         积分排名适配器
 */
public class ScoreListRVAdapter extends SuperAdapter<User> {
    public ScoreListRVAdapter(Context context, List<User> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, User item) {
        //user_award
        switch (layoutPosition) {
            case 0:
                holder.setImageResource(R.id.iv_score_award, R.mipmap.ic_score_1);
                break;
            case 1:
                holder.setImageResource(R.id.iv_score_award, R.mipmap.ic_score_2);
                break;
            case 2:
                holder.setImageResource(R.id.iv_score_award, R.mipmap.ic_score_3);
                break;
            default:
                holder.setVisibility(R.id.iv_score_award, View.INVISIBLE);
                break;
        }
        //user_name
        holder.setText(R.id.tv_score_user_name, item.user_name);
        //user_score
        holder.setText(R.id.tv_score_user_score, String.valueOf(item.user_score));

    }
}
