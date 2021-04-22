package ma.emsi.my_quizzer.Controllers;

public class Upload {
    private String mName;
    private String mNameURL;

    public Upload(String name, String nameURL) {
        if (name.trim ().equals ("")) {
            mName = "no name";
        }
        mName = name;
        mNameURL = nameURL;
    }

    public Upload() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNameURL() {
        return mNameURL;
    }

    public void setmNameURL(String mNameURL) {
        this.mNameURL = mNameURL;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "mName='" + mName + '\'' +
                ", mNameURL='" + mNameURL + '\'' +
                '}';
    }
}
