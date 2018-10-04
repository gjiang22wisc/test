/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Gordon Jiang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    LEC - 46373
 * 
 * Note: Warnings are suppressed on methods that construct new instances of 
 * generic PriorityQueue types.  The exceptions that occur are caught and 
 * such types are not able to be tested with this program.
 * 
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 * 
 * Credits:    N/A
 * 
 * Bugs:       no known bugs
 */


import java.util.NoSuchElementException;

/**
 * Runs black-box unit tests on the priority queue implementations
 * passed in as command-line arguments (CLA).
 * 
 * If a class with the specified class name does not exist 
 * or does not implement the PriorityQueueADT interface,
 * the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor,
 * it will also be reported as unable to test.
 * 
 * If the class exists and implements PriorityQueueADT, 
 * and has a default constructor, the tests will be run.  
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include:
 *     input, expected output, and actual output
 *     
 * Example Output:
 * Testing priority queue class: PQ01
 *    5 PASSED
 *    0 FAILED
 *    5 TOTAL TESTS RUN
 * Testing priority queue class: PQ02
 *    FAILED test00isEmpty: unexpectedly threw java.lang.NullPointerException
 *    FAILED test04insertRemoveMany: unexpectedly threw java.lang.ArrayIndexOutOfBoundsException
 *    3 PASSED
 *    2 FAILED
 *    5 TOTAL TESTS RUN
 * 
 *   ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

	// set to true to see call stack trace for exceptions
	private static final boolean DEBUG = false;

	/**
	 * Run tests to determine if each Priority Queue implementation
	 * works as specified. User names the Priority Queue types to test.
	 * If there are no command-line arguments, nothing will be tested.
	 * 
	 * @param args names of PriorityQueueADT implementation class types 
	 * to be tested.
	 */
	public static void main(String[] args) {
		for (int i=0; i < args.length; i++) 
			test(args[i]);

		if ( args.length < 1 ) 
			print("no PQs to test");
	}

	/** 
	 * Run all tests on each priority queue type that is passed as a classname.
	 * 
	 * If constructing the priority queue in the first test causes exceptions, 
	 * then no other tests are run.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 */
	private static void test(String className) {
		print("Testing priority queue class: "+className);
		int passCount = 0;
		int failCount = 0;
		try {

			if (test00isEmpty(className)) passCount++; else failCount++;		
			if (test01getMaxEXCEPTION(className)) passCount++; else failCount++;

			if (test02removeMaxEXCEPTION(className)) passCount++; else failCount++;
			if (test03insertRemoveOne(className)) passCount++; else failCount++;
			if (test04insertRemoveMany(className)) passCount++; else failCount++;

			// TODO: add calls to your additional test methods here
			if (test05duplicatesAllowed(className)) passCount++; else failCount++;
			if (test06manyDataItems(className)) passCount++; else failCount++;
			if (test07AddItems(className)) passCount++; else failCount++;
			if (test08getMax(className)) passCount++; else failCount++;
			if (test09isEmptyException(className)) passCount++; else failCount++;


			String passMsg = String.format("%4d PASSED", passCount);
			String failMsg = String.format("%4d FAILED", failCount);
			print(passMsg);
			print(failMsg);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			if (DEBUG) e.printStackTrace();
			print(className + " FAIL: Unable to construct instance of " + className);
		} finally {
			String msg = String.format("%4d TOTAL TESTS RUN", passCount+failCount);
			print(msg);
		}

	}

	/////////////////////////
	// TODO: ADD YOUR TEST METHODS HERE
	// Must test each operation of the PriorityQueueADT
	// Find and report cases that cause problems.
	// Do not try to fix or debug implementations.
	/////////////////////////
	
	/** Checks if priority queue is empty. Returns true if priority
	 * queue throws a NoSuchElementException. Test fails if priority
	 * contains data or another error type is thrown.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if it was able to get the greatest value
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private static boolean test09isEmptyException (String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		
		try {
			if (pq.isEmpty()) {
				print("FAILED test09isEmptyException: Priority Queue did not contain data.");
				return false;
			}
		}
		catch (NoSuchElementException e) {
			return true;			
		}
		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			return true;
		}
		print("FAILED test09isEmptyException: Exception did not occur");
		return false;
	}
	
	/** Adds multiple values to a priority queue. Then tests if the priority
	 * queue has data and if it has data get the greatest value.
	 * Test fails if priority queue is empty or an error is returned
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if it was able to get the greatest value
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private static boolean test08getMax(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		pq.insert(30);
		pq.insert(40);
		pq.insert(50);
		
		
		if (pq.isEmpty()) {
			return false;
		}
		else {
			try {
				pq.getMax();
			}
			catch (NoSuchElementException e) {
				print("FAILED test08getMax: NoSuchElementException thrown.");
				return false;			
			} 
			catch (Exception e) {
				if (DEBUG) e.printStackTrace();
				print("FAILED test08getMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
			return true;
			}	
		}
	
	/** Adds a value to a priority queue and will then run a check
	 * to see if the value was added successfully. The test fails 
	 * if the value was not added or a class was returned.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if there is a value in the priority queue
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private static boolean test07AddItems(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		pq.insert(50);
		if (!pq.isEmpty()) {
			return true;
		}
		else {
			print("FAILED test07AddItems: Constructed Priority Queue was empty.");
			return false;
		}
	}
	
	/** Adds 100 values to the priority queue, and tries to remove them
	 * from highest value to lowest value. If the sorting is not correct 
	 * or an error is thrown, the test will fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if the duplicate value was removed
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private static boolean test06manyDataItems(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		for (int number=1;number>100;number++) {
			pq.insert(number);	
		}
		
		
		try {
			while (!pq.isEmpty()) {
				if (pq.removeMax()==pq.getMax()) {
				}
				else {
					return false;
					}
				}
			}		
		catch (NoSuchElementException e) {
			print("FAILED test06manyDataItems: NoSuchElementException thrown");
			return false;			
		} 
		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test06manyDataItems: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}
		
	/** Adds a value to the priority queue, adds the exact same value,
	 * and then tries to see if that value can be removed without an error.
	 * An error will cause the test to fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if the duplicate value was removed
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private static boolean test05duplicatesAllowed(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		pq.insert(50);
		
		if (!pq.isEmpty()) {
			print("FAILED test05duplicatesAllowed: Constructed Priority Queue is empty");
			return false;
		}
		else {
		
		try {
			pq.insert(50);
			pq.removeMax();
		}
		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("test05insertRemoveOne: Exception occurred" + e.getClass().getName());
			return false;
		}
		return true;
	}
	}
	
	/** Inserts a value into the priority queue and tries to remove the value.
	 * If the value inserted is not equal to value removed or an exception is thrown,
	 * the test fails.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if value removed is the same as value added
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	private static boolean test04insertRemoveMany(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		pq.insert(30);
		pq.insert(40);
		pq.insert(50);
		
		if (pq.isEmpty()) {
			print("FAILED test04insertRemoveMany: Constructed Priority Queue is empty");
			return false;
		}
		else {
		
		try {
				while (!pq.isEmpty()) {
					if (pq.getMax()!=pq.removeMax()){
						print("FAILED test04insertRemoveMany: Value Inserted was not the same as value removed");
						return false;
					}
				}
			}

		catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test04insertRemoveOne: Exception occurred " + e.getClass().getName());

			return false;	
		}
	return true;
	}
	}
	

	/** Inserts a value into the priority queue and tries to remove the value
	 * If the value inserted is not equal to value removed or an exception is thrown,
	 * the test fails.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if value removed is the same as value added
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test03insertRemoveOne(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		int x=50;
		pq.insert(x);
		
		try {
			if (x==pq.removeMax()){
				return true;
			}
			else {
				print("FAILED test03insertRemoveOne: Value Inserted was not the same as value removed");
				return false;
			}
		} catch(Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test03insertRemoveOne: Exception occurred " + e.getClass().getName());
			return false;
		}
	}

	/**Tries to remove the maximum value inside the priority queue. 
	 * If an exception except a NoSuchElementException is thrown, the test fails.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if a NoSuchElementException is thrown
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test02removeMaxEXCEPTION(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		
		try {
			pq.removeMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test02removeMaxEXCEPTION: removeMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT -- provided as an example
	 * Confirm that getMax throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test01getMaxEXCEPTION(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.getMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * @return true if able to construct Integer priority queue and 
	 * the instance isEmpty.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test00isEmpty(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			if (pq.isEmpty()) 
				return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Integer using the class that is name.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Integer> newIntegerPQ(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Integer>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Double using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Double> newDoublePQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Double>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of String using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<String> newStringPQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<String>) obj;
		}
		return null;
	}


	/** DO NOT EDIT THIS METHOD
	 * Write the message to the standard output stream.
	 * Always adds a new line to ensure each message is on its own line.
	 * @param message Text string to be output to screen or other.
	 */
	private static void print(String message) {
		System.out.println(message);
	}

}
