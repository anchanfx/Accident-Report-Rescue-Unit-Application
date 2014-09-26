package nu.ac.th.rescueunit;

public abstract class Locator implements ILocator {
	private LocatorListener mLocatorListener;
	protected Position mPosition;
	
	public Locator(LocatorListener locatorListener, Position position) {
		mLocatorListener = locatorListener;
		mPosition = position;
	}
	
	protected void updatePosition(Position position) {
		mLocatorListener.onLocationUpdated(position);
	}
}
