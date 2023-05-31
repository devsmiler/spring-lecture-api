package member.week1.common.exception;

public class AlreadyExistsMemberException extends CustomException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public AlreadyExistsMemberException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
