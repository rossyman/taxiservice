package uk.ac.strath.cs112;

import java.awt.Image;
import javax.swing.ImageIcon;
import lombok.Getter;
import uk.ac.strath.cs112.model.DrawableItem;

/**
 * Model a passenger wishing to get from one location to another.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
@Getter
public class Passenger implements DrawableItem {

  private Location location;
  private Location destination;
  private Image image;

  /**
   * Constructor for objects of class Passenger
   *
   * @param location The location location, must not be null.
   * @param destination The destination location, must not be null.
   * @throws NullPointerException If either location is null.
   */
  public Passenger(Location location, Location destination) {
    if (location == null) {
      throw new NullPointerException("Pickup location");
    }
    if (destination == null) {
      throw new NullPointerException("Destination location");
    }
    this.location = location;
    this.destination = destination;
    // Load the image used to represent a person.
    image = new ImageIcon(getClass().getResource(
        "images/person.jpg")).getImage();
  }

  /**
   * @return A string representation of this person.
   */
  public String toString() {
    return "Passenger travelling from " +
        location + " to " + destination;
  }

  /**
   * @return The image to be displayed on a GUI.
   */
  public Image getImage() {
    return image;
  }

  /**
   * @return The destination location.
   */
  public Location getDestination() {
    return destination;
  }
}
