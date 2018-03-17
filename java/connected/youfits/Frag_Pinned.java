package connected.youfits;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import connected.youfit.R;

/**
 * Created by admin on 10/12/2016.
 */
public class Frag_Pinned extends Fragment {
    public Context context;
    public Frag_Pinned(Context con) {
        context=con;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.pinned,null);


        return v;
    }
}
