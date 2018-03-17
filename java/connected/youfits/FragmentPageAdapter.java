package connected.youfits;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 10/12/2016.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter{
    Activity act;
    public FragmentPageAdapter(FragmentManager fm, Activity a) {

        super(fm);
        act=a;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Frag_Trending(act);

            case 1:
                return new Frag_Pinned(act);
            case 2:
                return new Frag_Answer();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
