package css.cis3334;

/**
 * This class is used to store Cat Fact data from the following site:
 * https://cat-fact.herokuapp.com/#/
 *
 * @author kglesener
 */

public class CatFact
{
    // Instance variables are public so GSON can store the values
    // directly in them.
    public String _id;
    public int __v;

    public String text;
    public String updatedAt;
    public boolean deleted;
    public String source;
    public boolean used;

    public CatFact()
    {
        _id = "0";
        __v = 0;
        text = "";
        updatedAt = "";
        deleted = false;
        source = "";
        used = true;
    }

    public String getText()
    {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}

