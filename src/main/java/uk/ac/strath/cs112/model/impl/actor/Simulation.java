package uk.ac.strath.cs112.model.impl.actor;

import java.util.LinkedList;
import java.util.List;
import uk.ac.strath.cs112.City;
import uk.ac.strath.cs112.TaxiCompany;
import uk.ac.strath.cs112.model.Actor;

/**
 * Run the simulation by asking a collection of actors to act.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Simulation
{
    private List<Actor> actors;
    private int step;

    /**
     * Create the initial set of actors for the simulation.
     */
    public Simulation()
    {
        this.actors = new LinkedList<>();
        City city = new City();
        TaxiCompany company = new TaxiCompany(city);
        PassengerSource source = new PassengerSource(city, company);
        
        actors.addAll(company.getVehicles());
        actors.add(source);
        actors.add(new CityGUI(city));
    }
    
    /**
     * Run the simulation for a fixed number of steps.
     * Pause after each step to allow the GUI to keep up.
     */
    public void run()
    {
        for(int i = 0; i < 500; i++){
            step++;
            step();
            wait(100);
        }
    }

    /**
     * Take a single step of the simulation.
     */
    public void step()
    {
        for(Actor actor : actors) {
            actor.act();
        }
    }
    
    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to cause a small delay.
     * @param milliseconds The number of milliseconds to wait.
     */
    private void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        } 
        catch (InterruptedException e)
        {
            // ignore the exception
        }
    }
}
