package inc.myself.models;

public enum  ResultCode {

    OK("00.Result.OK"),
    NOT_FOUND("01.Result.NotFound"),
    ERROR("02.Result.Error");

    private String description;

    public String getDescription() {
        return description;
    }

    ResultCode(String description) {
        this.description = description;
    }
}
