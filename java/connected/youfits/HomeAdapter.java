package connected.youfits;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import connected.youfit.R;

/**
 * Created by admin on 10/22/2016.
 */
public class HomeAdapter extends BaseAdapter {
    private List<SingleRow> Listitems;
    Activity activity;
    private LayoutInflater inflater;

    public HomeAdapter(Activity activity, List<SingleRow> listitems) {
    this.Listitems=listitems;
    this.activity=activity;
    }

    @Override
    public int getCount() {
        return Listitems.size();
    }

    @Override
    public Object getItem(int position) {

        return Listitems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        ViewHolder hold =null;

        if(row==null){


            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.timeline_feed, null);
            hold = new ViewHolder();
            hold.name = (TextView) row.findViewById(R.id.name);
            hold.qn = (TextView) row.findViewById(R.id.qn);
            hold.ans = (TextView) row.findViewById(R.id.answer);
            hold.dp = (ImageView) row.findViewById(R.id.dp);
            hold.votes = (TextView) row.findViewById(R.id.upvotes);
            hold.date = (TextView) row.findViewById(R.id.date);
            hold.btnVotes = (TextView) row.findViewById(R.id.vote);
            hold.btnFollow = (TextView) row.findViewById(R.id.follow);
            hold.btnOptions = (TextView) row.findViewById(R.id.options);

            row.setTag(hold);


        }
        else{

            hold=(ViewHolder)row.getTag();
        }
        hold.name.setText(Listitems.get(position).name);
        hold.qn.setText(Listitems.get(position).qn);
        hold.ans.setText(Listitems.get(position).ans);
        hold.date.setText(Listitems.get(position).date);
        hold.votes.setText(Listitems.get(position).upvotes);





        return row;
    }
    private static class ViewHolder {

        public TextView name;
        public TextView qn;
        public TextView ans;
        public ImageView dp;
        public TextView date;
        public TextView votes;
        public TextView btnFollow;
        public TextView btnVotes;
        public TextView btnOptions;

        public int posi;





    }
}
