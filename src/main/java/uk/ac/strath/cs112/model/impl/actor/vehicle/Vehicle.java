package uk.ac.strath.cs112.model.impl.actor.vehicle;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import uk.ac.strath.cs112.Location;
import uk.ac.strath.cs112.TaxiCompany;
import uk.ac.strath.cs112.model.Actor;
import uk.ac.strath.cs112.model.DrawableItem;
import uk.ac.strath.cs112.model.impl.drawableitem.Passenger;

/**
 * Vehicle model Represents a vehicle, which can travel from it's current location to a specified target location.
 *
 * @author Ross MacPhee <a href="mailto:hello@rossmacphee.com">hello@rossmacphee.com</a>
 */
@Data
public abstract class Vehicle implements Actor {

  private TaxiCompany taxiCompany;
  private Location currentLocation;
  private Location targetLocation;
  private int idleCount;

  /**
   * Constructor of class Vehicle
   *
   * @param taxiCompany The taxi taxiCompany. Must not be null.
   * @param startLocation The vehicle's starting point. Must not be null.
   */
  public Vehicle(@NotNull TaxiCompany taxiCompany, @NotNull Location startLocation) {
    this.taxiCompany = taxiCompany;
    this.currentLocation = startLocation;
  }

  /**
   * Notify the taxiCompany of our arrival at a pickup location.
   */
  public void notifyPickupArrival() {
    this.taxiCompany.arrivedAtPickup(this);
  }

  /**
   * Notify the taxiCompany of our arrival at a passenger's destination.
   */
  public void notifyPassengerArrival(Passenger passenger) {
    this.taxiCompany.arrivedAtDestination(this, passenger);
  }

  /**
   * Clear the target currentLocation.
   */
  public void clearTargetLocation() {
    this.targetLocation = null;
  }

  /**
   * Increment the number of steps on which this vehicle has been idle.
   */
  public void incrementIdleCount() {
    this.idleCount++;
  }

  /**
   * Receive a pickup currentLocation. How this is handled depends on the type of vehicle.
   *
   * @param location The pickup currentLocation.
   */
  public abstract void setPickupLocation(Location location);

  /**
   * Receive a passenger. How this is handled depends on the type of vehicle.
   *
   * @param passenger The passenger.
   */
  public abstract void pickup(Passenger passenger);

  /**
   * Is the vehicle free?
   *
   * @return Whether or not this vehicle is free.
   */
  public abstract boolean isFree();

  /**
   * Offload any passengers whose destination is the current currentLocation.
   */
  public abstract void offloadPassenger();

}
