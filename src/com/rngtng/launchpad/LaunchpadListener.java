package com.rngtng.launchpad;

/**
 * The LaunchpadListener Interface, implement this for interaction feedback
 *
 * @author rngtng - Tobias Bielohlawek
 *
 */
public interface LaunchpadListener {
    
    public void launchpadGridPressed(int x, int y);
    public void launchpadGridReleased(int x, int y);
    
    public void launchpadButtonPressed(int buttonCode);
    public void launchpadButtonReleased(int buttonCode);

    public void launchpadSceneButtonPressed(int buttonCode);
    public void launchpadSceneButtonReleased(int buttonCode);
}