import java.util.Date;

public class Video {

    // type code(static final로 선언한거) enum으로 변경
    enum PriceCode {Regular(1), New_Release(2);
        private final int value;
        private PriceCode(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    enum VideoType {VHS(1), CD(2), DVD(3);
        private final int value;
        private VideoType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    private String title;
    private PriceCode priceCode;
    private VideoType videoType;
    private Date registeredDate;
    private boolean rented;


    public Video(String title, VideoType videoType, PriceCode priceCode) {
        this.setTitle(title);
        this.setVideoType(videoType);
        this.setPriceCode(priceCode);
        this.registeredDate = new Date();
    }

    public int getLateReturnPointPenalty() {
        switch (videoType) {
            case VHS:
                return 1;
            case CD:
                return 2;
            case DVD:
                return 3;
            default:
                return -1;
        }
    }

    public PriceCode getPriceCode() {
        return priceCode;
    }

    public boolean isNewRelease() {
        return (priceCode == PriceCode.New_Release);
    }

    public void setPriceCode(PriceCode priceCode) {
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }


}
