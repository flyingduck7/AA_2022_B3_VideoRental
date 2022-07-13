import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoService {
    private List<Video> videos = new ArrayList<Video>();
    private static Scanner scanner = new Scanner(System.in);

    public List<Video> getVideos() { return videos; }

    public void addVideos(Video video) {
        videos.add(video);
    }

    public void rentVideo(Customer foundCustomer) {
        if (foundCustomer == null)
            return;

        System.out.println("Enter video title to rent: ");
        String videoTitle = scanner.next();

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
