import java.util.Date;

public class Rental {
	public static final int CHARGE_BASE = 2;
	public static final double CHARGE_REGULAR_RATE = 1.5;
	public static final double CHARGE_NEW_RELEASE_RATE = 3;
	private Video video ;
	private enum RentStatus {
		RENTED, RETURNED
	}
	private RentStatus status ;
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = RentStatus.RENTED ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public RentStatus getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RentStatus.RETURNED ) {
			this.status = RentStatus.RETURNED;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDayRental(){
		if (status == RentStatus.RETURNED) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
	}

	private int getDaysRented(Date date) {
		long diff = date.getTime() - rentDate.getTime();

		return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		if (getStatus() == RentStatus.RETURNED) { // returned Video
			daysRented = getDaysRented(returnDate);
		} else { // not yet returned
			daysRented = getDaysRented(new Date());
		}
		if ( daysRented <= 2) return limit ;

		switch ( video.getVideoType() ) {
			case VHS: limit = 5 ; break ;
			case CD: limit = 3 ; break ;
			case DVD: limit = 2 ; break ;
		}
		return limit ;
	}

	public double getCharge() { // Customer.getReport() 로 부터 분리
		double charge = 0;
		int daysRented = getDayRental();

		if (getVideo().isNewRelease()) {
			charge = daysRented * CHARGE_NEW_RELEASE_RATE; // magic number 삭제
		} else {
			charge += CHARGE_BASE; // magic number 삭제
			if (daysRented > 2)
				charge += (daysRented - 2) * CHARGE_REGULAR_RATE; // magic number 삭제
		}

		return charge;
	}

	public int getPoint() { // Customer.getReport() 로 부터 분리
		int point = 0;
		int daysRented = getDayRental();

		if(getVideo().isNewRelease()) {
			point++;
		}

		if ( daysRented > getDaysRentedLimit()) {
			point -= Math.min(point, getVideo().getLateReturnPointPenalty());
		}
		return point;
	}
}
