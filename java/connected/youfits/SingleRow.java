package connected.youfits;

/**
 * Created by admin on 10/22/2016.
 */
public class SingleRow {
    String name;
    String qn;
    String date;
    String ans;
    String upvotes;
    String dp;

    public SingleRow(String name, String qn,  String ans,  String dp,String upvotes,String date) {
        this.name = name;
        this.qn = qn;
        this.date = date;
        this.ans = ans;
        this.upvotes = upvotes;
        this.dp = dp;
    }
}
