package com.rngtng.launchpad;

public interface LaunchpadListener {
    
    public void launchpadGridPressed(int x, int y);
    public void launchpadGridReleased(int x, int y);
    
    public void launchpadButtonPressed(int buttonCode);
    public void launchpadButtonReleased(int buttonCode);

    public void launchpadSceneButtonPressed(int buttonCode);
    public void launchpadSceneButtonReleased(int buttonCode);
}