import java.util.Scanner;

public class VRUI {
    private static Scanner scanner = new Scanner(System.in);

    private static CustomerService customerService;
    private static VideoService videoService;

    public static void main(String[] args) {
        VRUI ui = new VRUI();
        ui.run();
    }

    public VRUI() {
        customerService = new CustomerService();
        videoService = new VideoService();
    }

    public void run() {
        while (true) {
            int command = showCommand();
            switch (command) {
                case 0:
                    System.out.println("Bye");
                    return;
                case 1:
                    listCustomers();
                    break;
                case 2:
                    listVideos();
                    break;
                case 3:
                    registerCustomer();
                    break;
                case 4:
                    registerVideo();
                    break; // register 함수 분리
                case 5:
                    rentVideo();
                    break; // register 함수 분리
                case 6:
                    returnVideo();
                    break;
                case 7:
                    getCustomerReport();
                    break;
                case 8:
                    clearRentals();
                    break;
                case -1:
                    init();
                    break;
                default:
                    break;
            }
        }
    }

    public void clearRentals() {
        customerService.clearRentals(getCustomer());
    }

    // videoService로 역할 분리
    public void returnVideo() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();
        Customer foundCustomer = customerService.findCustomer(customerName);
        if (foundCustomer == null) return;

        System.out.println("Enter video title to return: ");
        String videoTitle = scanner.next();

        videoService.returnVideo(foundCustomer, videoTitle);
    }

    private void init() {
        Customer james = new Customer("James");
        Customer brown = new Customer("Brown");

        customerService.addCustomer(james);
        customerService.addCustomer(brown);

        Video v1 = new Video("v1", Video.VideoType.CD, Video.PriceCode.Regular);
        Video v2 = new Video("v2", Video.VideoType.DVD, Video.PriceCode.New_Release);
        videoService.addVideos(v1);
        videoService.addVideos(v2);


        james.addRental(new Rental(v1));
        james.addRental(new Rental(v2));
    }

    public void listVideos() {
        System.out.println("List of videos");

        for (Video video : videoService.getVideos()) {
            System.out.println("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
        }
        System.out.println("End of list");
    }

    public void listCustomers() {
        System.out.println("List of customers");
        for (Customer customer : customerService.getCustomerList()) {
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

        return customerService.getCustomer(customerName);
    }

    private void showCoupon(int totalPoint) { // 함수분리
        if (totalPoint >= 10) {
            System.out.println("Congrat! You earned one free coupon");
        }
        if (totalPoint >= 30) {
            System.out.println("Congrat! You earned two free coupon");
        }
    }

    public void getCustomerReport() {
        Customer foundCustomer = getCustomer();
        if (foundCustomer == null) {
            System.out.println("No customer found");
            return;
        }

        String result = customerService.generateReport(foundCustomer);
        int totalPoint = customerService.getTotalPoint(foundCustomer.getRentals());

        showCoupon(totalPoint);
        System.out.println(result);
    }

    // videoService로 역할 분리
    public void rentVideo() {
        videoService.rentVideo(getCustomer());
    }

    // register 함수 분리
    public void registerCustomer() {
        System.out.println("Enter customer name: ");
        String name = scanner.next();
        customerService.addCustomer(new Customer(name));
    }

    // register 함수 분리
    public void registerVideo() {
        System.out.println("Enter video title to register: ");
        String title = scanner.next();

        System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
        int videoType = scanner.nextInt();

        System.out.println("Enter price code( 1 for Regular, 2 for New Release ):");
        int priceCode = scanner.nextInt();

        Video video = new Video(title, Video.VideoType.getValue(videoType), Video.PriceCode.getValue(priceCode));
        videoService.addVideos(video);
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
