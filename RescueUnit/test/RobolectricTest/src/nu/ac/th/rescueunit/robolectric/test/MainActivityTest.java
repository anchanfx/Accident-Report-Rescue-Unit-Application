package nu.ac.th.rescueunit.robolectric.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;
import nu.ac.th.rescueunit.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest{

	private final ActivityController<MainActivity> controller = buildActivity(MainActivity.class);
	private MainActivity mainActivity;
	
    @Before
    public void setup()
    {
        //this.mainActivity = controller.create().get();
    }
    
    @Test
    public void testTesting() throws Exception 
    {    	 
         assertThat(true, equalTo(true));
    }
}
