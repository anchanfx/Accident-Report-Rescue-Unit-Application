package nu.ac.th.rescueunit.espresso.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isEnabled;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isChecked;
import static org.hamcrest.Matchers.not;
import nu.ac.th.rescueunit.MainActivity;
import nu.ac.th.rescueunit.R;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public MainActivityTest() {
		super(MainActivity.class);
	}

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getActivity();
	}
	
	//It uses JUnit 3 so remember to prefix your test methods
	//with the word "test"
	public void test_Testing() {
		onView(withText(R.string.hello_world))
			.check(matches(isDisplayed()));
	}
}