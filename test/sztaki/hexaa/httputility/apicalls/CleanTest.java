package sztaki.hexaa.httputility.apicalls;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runners.model.MultipleFailureException;
import sztaki.hexaa.httputility.Authenticator;
import sztaki.hexaa.httputility.DatabaseManipulator;

public class CleanTest {

    @Rule
    public TestErrorCollector collector = new TestErrorCollector();

    @BeforeClass
    public static void cleanDB() {
        System.out.println("BeforeClass @ CleanTest");
        new DatabaseManipulator().dropDatabase();
        new Authenticator().authenticate();
    }

    @AfterClass
    public static void checkErrors() {

    }

    public class TestErrorCollector extends ErrorCollector {

        private ArrayList<Throwable> errors = new ArrayList<>();

        public TestErrorCollector() {
            super();
        }

        @Override
        public void verify() throws Throwable {
            MultipleFailureException.assertEmpty(errors);
        }

        @Override
        public void addError(Throwable e) {
            if (!errors.contains(e)) {
                errors.add(e);
            }
        }
    }

}
