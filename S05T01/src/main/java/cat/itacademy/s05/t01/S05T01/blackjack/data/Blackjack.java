package cat.itacademy.s05.t01.S05T01.blackjack.data;

import cat.itacademy.s05.t01.S05T01.blackjack.domain.BlackjackGame;
import cat.itacademy.s05.t01.S05T01.security.data.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table(name = "blackjack")
public class Blackjack {
    @Id
    private Long id;

    @Column("blackjack_game")
    private BlackjackGame blackjackGame;

    @Column("creation_date")
    private Date creationDate;

    @Column("game_done")
    private Boolean gameDone;

    @Column("user")
    private User user;

    public Blackjack() {
    }

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
