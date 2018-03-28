package lamit.outerspacemanager.com.outerspacemanager.data.api;

public class SimpleAPIResponse {

    private String code;

    public SimpleAPIResponse(){}

    public String getCode() {
        return code;
    }

    public SimpleAPIResponse setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString(){

        return String.format(
                "SimpleAPIResponse {code : %s}",
                this.code
        );
    }
}
