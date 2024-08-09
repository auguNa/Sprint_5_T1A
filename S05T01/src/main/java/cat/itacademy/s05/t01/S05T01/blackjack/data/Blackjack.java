package cat.itacademy.s05.t01.S05T01.blackjack.data;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import java.util.Date;


@Entity
@Table(name = "blackjack")
public class Blackjack {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private BlackjackGame blackjackGame;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date creationDate;

    @Column
    private Boolean gameDone;

    @OneToOne
    private User user;

    public Blackjack(){}

    public Blackjack(BlackjackGame blackjackGame, boolean gameDone, User user) {
        this.blackjackGame = blackjackGame;
        this.gameDone = gameDone;
        this.user = user;
    }

       public BlackjackGame getBlackjackGame() {
        return blackjackGame;
    }

    public void setGameDone(Boolean gameDone) {
        this.gameDone = gameDone;
    }

}
