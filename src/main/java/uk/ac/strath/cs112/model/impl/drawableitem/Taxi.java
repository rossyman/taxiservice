package uk.ac.strath.cs112.model.impl.drawableitem;

import java.awt.Image;
import javax.swing.ImageIcon;
import lombok.NonNull;
import lombok.Data;
import uk.ac.strath.cs112.Location;
import uk.ac.strath.cs112.TaxiCompany;
import uk.ac.strath.cs112.model.DrawableItem;
import uk.ac.strath.cs112.model.impl.actor.vehicle.Vehicle;

/**
 * Taxi model. Represents a taxi which can take a passenger from pickup to destination.
 *
 * @author Ross MacPhee <a href="mailto:hello@rossmacphee.com">hello@rossmacphee.com</a>
 */
@Data
public class Taxi extends Vehicle implements DrawableItem {

  private Passenger passenger;
  private Image emptyImage;
  private Image passengerImage;

  /**
   * Constructor for objects of class Taxi
   *
   * @param company The taxi company.
   * @param location The vehicle's starting point.
   * @throws NullPointerException If a company or location isn't supplied
   */
  public Taxi(@NonNull TaxiCompany company, @NonNull Location location) {

    super(company, location);

    emptyImage = new ImageIcon(getClass().getResource(
        "/images/taxi.jpg")).getImage();

    passengerImage = new ImageIcon(getClass().getResource(
        "/images/taxi+person.jpg")).getImage();

  }

  /**
   * Move towards the target location if we have one. Otherwise record that we are idle.
   */
  public void act() {

    Location targetLocation = this.getTargetLocation();

    if (targetLocation == null) {
      incrementIdleCount();
      return;
    }

    this.setCurrentLocation(this.getCurrentLocation().nextLocation(targetLocation));

    if (this.getCurrentLocation().equals(targetLocation)) {
      if (passenger != null) {
        notifyPassengerArrival(passenger);
        offloadPassenger();
      } else {
        notifyPickupArrival();
      }
    }
  }

  /**
   * Is the taxi free?
   *
   * @return Whether or not this taxi is free.
   */
  public boolean isFree() {
    return getTargetLocation() == null && passenger == null;
  }

  /**
   * Receive a pickup location. This becomes the target location.
   *
   * @param location The pickup location.
   */
  public void setPickupLocation(Location location) {
    setTargetLocation(location);
  }

  /**
   * Receive a passenger. Set their destination as the target location.
   *
   * @param passenger The passenger.
   * @throws MissingPassengerException If the passenger is null
   */
  public void pickup(Passenger passenger) {
    if (passenger == null) {
      throw new MissingPassengerException(vehicle);
    }
    this.passenger = passenger;
    setTargetLocation(passenger.getDestination());
  }

  /**
   * Offload the passenger.
   */
  public void offloadPassenger() {
    passenger = null;
    clearTargetLocation();
  }

  /**
   * Return an image that describes our state: either empty or carrying a passenger.
   */
  public Image getImage() {
    if (passenger != null) {
      return passengerImage;
    } else {
      return emptyImage;
    }
  }

  /**
   * Return details of the taxi, such as where it is.
   *
   * @return A string representation of the taxi.
   */
  public String toString() {
    return "Taxi at " + getCurrentLocation();
  }

}
