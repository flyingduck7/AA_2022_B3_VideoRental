import java.util.ArrayList;
import java.util.List;

public class VideoService {
    private List<Video> videos = new ArrayList<Video>();

    public List<Video> getVideos() { return videos; }

    public void addVideos(Video video) {
        videos.add(video);
    }

    public void rentVideo(Customer foundCustomer, String videoTitle) {
        Video foundVideo = null;
        for (Video video : videos) {
            if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
                foundVideo = video;
                break;
            }
        }

        if (foundVideo == null) return;

        Rental rental = new Rental(foundVideo);
        foundVideo.setRented(true);

        List<Rental> customerRentals = foundCustomer.getRentals();
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    public void returnVideo(Customer foundCustomer, String videoTitle) {
        List<Rental> customerRentals = foundCustomer.getRentals();
        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break;
            }
        }
    }
}
