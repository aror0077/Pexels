package algonquin.cst2335.aror0077;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaskaran Singh Arora
 * @version 1.0
 */
public class ModelResponse {

    private Integer page;

    private Integer perPage;

    @Override
    public String toString() {
        return "ModelResponse{" +
                "page=" + page +
                ", perPage=" + perPage +
                ", photos=" + photos +
                ", totalResults=" + totalResults +
                '}';
    }

    private List<Photo> photos = null;

    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public static class Photo {
        private Integer id;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        @Override
        public String toString() {
            return "Photo{" +
                    "id=" + id +
                    ", small='" + small + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", url='" + url + '\'' +
                    ", photographer='" + photographer + '\'' +
                    ", photographerUrl='" + photographerUrl + '\'' +
                    ", photographerId=" + photographerId +
                    ", avgColor='" + avgColor + '\'' +
                    ", src=" + src +
                    ", liked=" + liked +
                    '}';
        }

        private String small;

        private Integer width;

        private Integer height;

        private String url;

        private String photographer;

        private String photographerUrl;

        private Integer photographerId;

        private String avgColor;

        private Src src;
        private Boolean liked;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotographer() {
            return photographer;
        }

        public void setPhotographer(String photographer) {
            this.photographer = photographer;
        }

        public String getPhotographerUrl() {
            return photographerUrl;
        }

        public void setPhotographerUrl(String photographerUrl) {
            this.photographerUrl = photographerUrl;
        }

        public Integer getPhotographerId() {
            return photographerId;
        }

        public void setPhotographerId(Integer photographerId) {
            this.photographerId = photographerId;
        }

        public String getAvgColor() {
            return avgColor;
        }

        public void setAvgColor(String avgColor) {
            this.avgColor = avgColor;
        }

        public Src getSrc() {
            return src;
        }

        public void setSrc(Src src) {
            this.src = src;
        }

        public Boolean getLiked() {
            return liked;
        }

        public void setLiked(Boolean liked) {
            this.liked = liked;
        }

        public static ModelResponse.Photo fromJson(JSONObject jsonObject) {
            ModelResponse.Photo photo = new ModelResponse.Photo();
            // Deserialize json into object fields
            try {
                photo.photographer = jsonObject.getString("photographer");
                photo.url = jsonObject.getString("url");
                Gson gson = new Gson();
                photo.src = gson.fromJson(jsonObject.getString("src"), Src.class);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            // Return new object
            return photo;
        }

        public static ArrayList<Photo> fromJson(JSONArray jsonArray) {
            JSONObject jsonObject;
            ArrayList<Photo> definitions = new ArrayList<Photo>(jsonArray.length());
            // Process each result in json array, decode and convert to business object
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                Photo photo = Photo.fromJson(jsonObject);
                if (photo != null) {
                    definitions.add(photo);
                }
            }

            return definitions;
        }

    }
}
