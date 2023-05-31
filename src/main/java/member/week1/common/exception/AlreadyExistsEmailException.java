package member.week1.common.exception;

public class AlreadyExistsEmailException extends CustomException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다..";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
