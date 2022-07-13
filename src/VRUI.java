import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
    private static Scanner scanner = new Scanner(System.in);

    private List<Customer> customers = new ArrayList<Customer>();

    private List<Video> videos = new ArrayList<Video>();

    public static void main(String[] args) {
        VRUI ui = new VRUI();

        while (true) {
            int command = ui.showCommand();
            switch (command) {
                case 0:
                    System.out.println("Bye");
                    return;
                case 1:
                    ui.listCustomers();
                    break;
                case 2:
                    ui.listVideos();
                    break;
                case 3:
                    ui.registerCustomer();
                    break;
                case 4:
                    ui.registerVideo();
                    break; // register 함수 분리
                case 5:
                    ui.rentVideo();
                    break; // register 함수 분리
                case 6:
                    ui.returnVideo();
                    break;
                case 7:
                    ui.getCustomerReport();
                    break;
                case 8:
                    ui.clearRentals();
                    break;
                case -1:
                    ui.init();
                    break;
                default:
                    break;
            }
        }
    }

    public void clearRentals() {
        Customer foundCustomer = getCustomer();
        if (foundCustomer == null) {
            System.out.println("No customer found");
            return;
        }

        System.out.println("Name: " + foundCustomer.getName() +
                "\tRentals: " + foundCustomer.getRentals().size());
        for (Rental rental : foundCustomer.getRentals()) {
            System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
            System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
        }

        List<Rental> rentals = new ArrayList<Rental>();
        foundCustomer.setRentals(rentals);
    }

    public void returnVideo() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        if (foundCustomer == null) return;

        System.out.println("Enter video title to return: ");
        String videoTitle = scanner.next();

        List<Rental> customerRentals = foundCustomer.getRentals();
        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break;
            }
        }
    }

    private void init() {
        Customer james = new Customer("James");
        Customer brown = new Customer("Brown");
        customers.add(james);
        customers.add(brown);

        Video v1 = new Video("v1", Video.VideoType.CD, Video.PriceCode.Regular);
        Video v2 = new Video("v2", Video.VideoType.DVD, Video.PriceCode.New_Release);
        videos.add(v1);
        videos.add(v2);

        Rental r1 = new Rental(v1);
        Rental r2 = new Rental(v2);

        james.addRental(r1);
        james.addRental(r2);
    }

    public void listVideos() {
        System.out.println("List of videos");

        for (Video video : videos) {
            System.out.println("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
        }
        System.out.println("End of list");
    }

    public void listCustomers() {
        System.out.println("List of customers");
        for (Customer customer : customers) {
            System.out.println("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size());
            for (Rental rental : customer.getRentals()) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
            }
        }
        System.out.println("End of list");
    }

    private Customer getCustomer() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    private void showCoupon(int totalPoint) { // 함수분리
        if (totalPoint >= 10) {
            System.out.println("Congrat! You earned one free coupon");
        }
        if (totalPoint >= 30) {
            System.out.println("Congrat! You earned two free coupon");
        }
    }

    private double getTotalCharge(List<Rental> rentals) {
        double totalCharge = 0;

        for (Rental each : rentals) {
            totalCharge += each.getCharge();;
        }

        return totalCharge;
    }


    private int getTotalPoint(List<Rental> rentals) {
        int totalPoint = 0;

        for (Rental each : rentals) {
            totalPoint += each.getPoint();;
        }

        return totalPoint;
    }

    public String generateReport(Customer customer) { // Customer.getRepot() 로 부터 move method
        String result = "Customer Report for " + customer.getName() + "\n";

        List<Rental> rentals = customer.getRentals();
        double totalCharge = getTotalCharge(rentals);
        int totalPoint = getTotalPoint(rentals);

        for (Rental each : rentals) {
            double eachCharge = each.getCharge();
            int eachPoint = each.getPoint();
            int daysRented = each.getDayRental();

            result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
                    + "\tPoint: " + eachPoint + "\n";
        }

        result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

        showCoupon(totalPoint);

        return result;
    }

    public void getCustomerReport() {
        Customer foundCustomer = getCustomer();
        if (foundCustomer == null) {
            System.out.println("No customer found");
            return;
        }

        String result = generateReport(foundCustomer);
        System.out.println(result);
    }

    public void rentVideo() {
        Customer foundCustomer = getCustomer();
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

    // register 함수 분리
    public void registerCustomer() {
        System.out.println("Enter customer name: ");
        String name = scanner.next();
        Customer customer = new Customer(name);
        customers.add(customer);
    }

    // register 함수 분리
    public void registerVideo() {
        System.out.println("Enter video title to register: ");
        String title = scanner.next();

        System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
        int videoType = scanner.nextInt();

        System.out.println("Enter price code( 1 for Regular, 2 for New Release ):");
        int priceCode = scanner.nextInt();
        Video.VideoType videoTypeEnum = Video.VideoType.values()[videoType];
        Video.PriceCode videoPriceCodeEnum =  Video.PriceCode.values()[priceCode];
//        Video video = new Video(title, (Video.VideoType)videoType, (Video.PriceCode)priceCode);
        Video video = new Video(title, videoTypeEnum, videoPriceCodeEnum); // 임시로 수정
        videos.add(video);
    }

    public int showCommand() {
        System.out.println("\nSelect a command !");
        System.out.println("\t 0. Quit");
        System.out.println("\t 1. List customers");
        System.out.println("\t 2. List videos");
        System.out.println("\t 3. Register customer");
        System.out.println("\t 4. Register video");
        System.out.println("\t 5. Rent video");
        System.out.println("\t 6. Return video");
        System.out.println("\t 7. Show customer report");
        System.out.println("\t 8. Show customer and clear rentals");

        int command = scanner.nextInt();

        return command;

    }
}
