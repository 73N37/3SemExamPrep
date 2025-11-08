package dat.security.exceptions;


/**
 * Purpose: To handle exceptions in the API
 * Author: Thomas Hartmann
 */
public
class
ApiException
        extends java.lang.RuntimeException
{
    private int code;

    public
    ApiException (
            int code,
            java.lang.String msg
    ) {
        super(
                msg
        );

        this.code = code;
    }

    public
    int
    getCode(

    ) {
        return code;
    }
}
