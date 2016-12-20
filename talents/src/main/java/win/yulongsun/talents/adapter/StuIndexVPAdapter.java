package win.yulongsun.talents.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunyulong on 2016/12/18.
 */
public class StuIndexVPAdapter extends PagerAdapter {
    private List<ImageView> itemIndexList = new ArrayList<>();

    public StuIndexVPAdapter(List<ImageView> itemIndexList) {
        this.itemIndexList = itemIndexList;
    }

    @Override
    public int getCount() {
        return itemIndexList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(itemIndexList.get(position));

        return itemIndexList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(itemIndexList.get(position));
    }
}
