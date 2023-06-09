package member.week1.common.exception;

public class InvalidSignInInformation extends CustomException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";
    public InvalidSignInInformation() {
        super(MESSAGE);
    }
    @Override
    public int statusCode() {
        return 400;
    }
    // FIXME It would be better to have a constant class that collects status codes
}
