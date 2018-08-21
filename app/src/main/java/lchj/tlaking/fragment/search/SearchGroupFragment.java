package lchj.tlaking.fragment.search;


import android.support.v4.app.Fragment;

import com.dqjq.common.BaseFragment;

import lchj.tlaking.R;
import lchj.tlaking.activity.SearchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGroupFragment extends BaseFragment implements SearchActivity.SearchFragment {


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }
}
