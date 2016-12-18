package win.yulongsun.framework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

//ListView Adapter基类
public abstract class CommonAdapter extends BaseAdapter {
    private List           mList;
    private LayoutInflater mLayoutInflater;
    private Context        mContext;

    public CommonAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public List getList() {
        return mList;
    }

    public void setList(List mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList == null)
            return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
