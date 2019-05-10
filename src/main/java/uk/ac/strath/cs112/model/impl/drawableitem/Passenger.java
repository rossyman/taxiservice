package uk.ac.strath.cs112.model.impl.drawableitem;

import com.sun.istack.internal.NotNull;
import java.awt.Image;
import javax.swing.ImageIcon;
import lombok.Data;
import uk.ac.strath.cs112.Location;
import uk.ac.strath.cs112.model.DrawableItem;

/**
 * Passenger model. Represents a passenger, wishing to get from one location to another.
 *
 * @author Ross MacPhee <a href="mailto:hello@rossmacphee.com">hello@rossmacphee.com</a>
 */
@Data
public class Passenger implements DrawableItem {

  private Location currentLocation;
  private Location destination;
  private Image image;

  /**
   * Constructor for objects of class Passenger
   *
   * @param currentLocation The location location, must not be null.
   * @param destination The destination location, must not be null.
   */
  public Passenger(@NotNull Location currentLocation, @NotNull Location destination) {
    this.currentLocation = currentLocation;
    this.destination = destination;
    this.image = new ImageIcon(getClass().getResource("images/person.jpg")).getImage();
  }

  /**
   * @return A string representation of the passenger
   */
  @Override
  public String toString() {
    return "Passenger travelling from: " + this.currentLocation + ", to: " + this.destination;
  }

  /**
   * @return The passenger's start location.
   */
  @Override
  public Location getCurrentLocation() {
    return this.currentLocation;
  }

}
