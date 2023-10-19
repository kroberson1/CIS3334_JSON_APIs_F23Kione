package css.cis3334;

import java.util.Date;
import java.util.List;

// get a list of space news articles from
// https://spaceflightnewsapi.net/
// https://test.spaceflightnewsapi.net/api/v2/articles
// Java from https://json2csharp.com/json-to-pojo
public class SpaceNews {

    public class Launch{
        public String id;
        public String provider;
    }

    public String id;
    public String title;
    public String url;
    public String imageUrl;
    public String newsSite;
    public String summary;
    public Date publishedAt;
    public Date updatedAt;
    public boolean featured;
    public List<Launch> launches;
    public List<Object> events;

    public String getSummary() {
        return summary;
    }

}
