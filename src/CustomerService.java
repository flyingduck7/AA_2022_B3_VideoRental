import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private List<Customer> customers = new ArrayList<Customer>();

    public List<Customer> getCustomerList() { return customers; }

    public Customer getCustomer(String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomer(String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    public double getTotalCharge(List<Rental> rentals) {
        double totalCharge = 0;

        for (Rental each : rentals) {
            totalCharge += each.getCharge();;
        }

        return totalCharge;
    }


    public int getTotalPoint(List<Rental> rentals) {
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

        return result;
    }

    public void clearRentals(Customer foundCustomer) {
        List<Rental> rentals = new ArrayList<Rental>();
        foundCustomer.setRentals(rentals);
    }
}
