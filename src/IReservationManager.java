import java.util.List;

public interface IReservationManager {
    void createReservation(Reservation reservation); // 예약 하기

    Reservation getReservation(String userName); // 이름으로 예약 검색

    List<Reservation> reservationList(); //예약 전체 목록

    void deleteReservation(int id); // 예약 삭제

    void updateReservation(int id, int headCount); // 예약 수정 (인원 수만)
}
