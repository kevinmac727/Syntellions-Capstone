package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DeliveryMethodServiceTest.class, 
	DeliveryStatusServiceTest.class,
	UserServiceTest.class,
	TimeSlotServicesTest.class})
// Note that Categories is a kind of Suite
public class TestSuite {
  // Will run A.b, but not A.a or B.c
} 
