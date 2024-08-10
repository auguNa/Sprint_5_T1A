package cat.itacademy.s05.t01.S05T01.chips.data;

import cat.itacademy.s05.t01.S05T01.security.data.User;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;


@Table(name = "chips")
public class Chips {
    @Id
    private Long id;

    @Column("user")
    private User user;

    @Column
    @Positive
    private Long amount;

    @Column("creation_date")
    private Date creationDate;

    @Column("last_update")
    private Date lastUpdate;

    public Chips() {
    }

    public Chips(User user, Long amount) {
        this.user = user;
        this.amount = amount;
    }

    public void deposit(Long amount) {
        this.amount += amount;
    }

    //Removes chips from user when lose or chipsBet
    public void remove(Long amount) {
        this.amount -= amount;
    }

    public User getUser() {
        return user;
    }

    public Long getAmount() {
        return amount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}


