package lamit.outerspacemanager.com.outerspacemanager.datasource.api;


public class SimpleAPIErrorResponse {

    private int code;
    private String internalCode;
    private String status;
    private String message;

    public SimpleAPIErrorResponse(){}

    public int getCode() {
        return code;
    }

    public SimpleAPIErrorResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public SimpleAPIErrorResponse setInternalCode(String internalCode) {
        this.internalCode = internalCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SimpleAPIErrorResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SimpleAPIErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "SimpleAPIErrorResponse {code : %s, internalCode : %s, status : %s, message : %s}",
                this.code,
                this.internalCode,
                this.status,
                this.message
        );
    }
}
