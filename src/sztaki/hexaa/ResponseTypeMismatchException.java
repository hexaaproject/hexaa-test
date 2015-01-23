package sztaki.hexaa;

/**
 * Exception to throw if the server response is in a different than expected
 * type.
 */
public class ResponseTypeMismatchException extends Exception {

    /**
     * The type of the response.
     */
    private String type;

    /**
     * The response.
     */
    private Object response;

    /**
     * Basic constructor to support exception chaining.
     *
     * @param message the message of the exception.
     */
    public ResponseTypeMismatchException(String message) {
        super(message);
    }

    /**
     * Basic constructor to support exception chaining.
     *
     * @param message the message of the exception.
     * @param throwable other exception.
     */
    public ResponseTypeMismatchException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructor to throw the original response and its type with the
     * exception.
     *
     * @param message the message of the exception.
     * @param type the type of the response that caused an error.
     * @param response the response that caused the error.
     */
    public ResponseTypeMismatchException(String message, String type, Object response) {
        super(message);
        this.type = type;
        this.response = response;
    }

    /**
     * Returns the type of the object that caused the exception, may be null.
     *
     * @return the type of the object.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the object that caused the exception, may be null.
     *
     * @return the object.
     */
    public Object getResponse() {
        return response;
    }

    public boolean hasType() {
        if (type == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasResponse() {
        if (response == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getFullMessage() {
        if (this.hasType() && this.hasResponse()) {
            return this.getMessage() + "; type: " + this.getType() + "; server response: " + this.getResponse();
        } else if (this.hasType()) {
            return this.getMessage() + "; type: " + this.getType() + "; no server response.";
        } else if (this.hasResponse()) {
            return this.getMessage() + "\t" + this.getResponse();
        } else 
            return this.getMessage();
    }
}
