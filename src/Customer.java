import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}

	// addRental과 함께 추가
//	public void removeRental(Rental rental) {
//		rentals.remove(rental);
//	}
}
