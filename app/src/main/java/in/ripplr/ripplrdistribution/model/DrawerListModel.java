package in.ripplr.ripplrdistribution.model;

/**
 * Model class for the navigation drawer list model.
 */
public class DrawerListModel {

    /**
     * List item of text
     */
    private String mTitle = null;
    private int imgRes;


    public DrawerListModel(int image , String title){
        this.mTitle = title;
        this.imgRes = image;

    }



    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }


    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
