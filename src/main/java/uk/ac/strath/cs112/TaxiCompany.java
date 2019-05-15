package uk.ac.strath.cs112;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import uk.ac.strath.cs112.model.impl.actor.vehicle.Vehicle;
import uk.ac.strath.cs112.model.impl.drawableitem.Passenger;
import uk.ac.strath.cs112.model.impl.drawableitem.Taxi;

/**
 * Model the operation of a taxi taxiCompany, operating different types of vehicle. This version operates a only taxis.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class TaxiCompany {

  private static final int NUMBER_OF_TAXIS = 3;

  private City city;
  private List<Vehicle> vehicles;
  private Map<Vehicle, Passenger> assignments;

  /**
   * @param city The city.
   */
  public TaxiCompany(City city) {
    if (city == null) {
      throw new IllegalArgumentException("City cannot be null!");
    }
    this.city = city;
    this.vehicles = new LinkedList<>();
    this.assignments = new HashMap<>();
    this.setupVehicles();
  }

  /**
   * Request a pickup for the given passenger.
   *
   * @param passenger The passenger requesting a pickup.
   * @return Whether a free vehicle is available.
   */
  public boolean requestPickup(Passenger passenger) {
    Vehicle vehicle = scheduleVehicle();
    if (vehicle != null) {
      if (passenger == null) {
        throw new MissingPassengerException(vehicle);
      }
      assignments.put(vehicle, passenger);
      vehicle.setPickupLocation(passenger.getCurrentLocation());
      return true;
    } else {
      return false;
    }
  }

  /**
   * A vehicle has arrived at a pickup point.
   *
   * @param vehicle The vehicle at the pickup point.
   * @throws MissingPassengerException If there is no passenger waiting.
   */
  public void arrivedAtPickup(Vehicle vehicle) {
    Passenger passenger = assignments.remove(vehicle);
    if (passenger == null) {
      throw new MissingPassengerException(vehicle);
    }
    city.removeItem(passenger);
    vehicle.pickup(passenger);
  }

  /**
   * A vehicle has arrived at a passenger's destination.
   *
   * @param vehicle The vehicle at the destination.
   * @param passenger The passenger being dropped off.
   */
  public void arrivedAtDestination(Vehicle vehicle, Passenger passenger) {
  }

  /**
   * @return The list of vehicles.
   */
  public List<Vehicle> getVehicles() {
    return this.vehicles;
  }

  /**
   * Find a free vehicle, if any.
   *
   * @return A free vehicle, or null if there is none.
   */
  private Vehicle scheduleVehicle() {
    Iterator<Vehicle> it = vehicles.iterator();
    while (it.hasNext()) {
      Vehicle vehicle = it.next();
      if (vehicle.isFree()) {
        return vehicle;
      }
    }
    return null;
  }

  /**
   * Set up this taxiCompany's vehicles. The optimum number of vehicles should be determined by analysis of the data gathered from the
   * simulation.
   *
   * Vehicles start at random locations.
   */
  private void setupVehicles() {
    int cityWidth = city.getWidth();
    int cityHeight = city.getHeight();
    // Used a fixed random seed for predictable behavior.
    // Use different seeds for less predictable behavior.
    Random rand = new Random(12345);

    // Create the taxis.
    // This method may create multiple taxis in the same Location
    for (int i = 0; i < NUMBER_OF_TAXIS; i++) {
      Taxi taxi =
          new Taxi(this,
              new Location(rand.nextInt(cityWidth),
                  rand.nextInt(cityHeight)));
      vehicles.add(taxi);
      city.addItem(taxi);
    }
  }
}
