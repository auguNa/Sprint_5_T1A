package cat.itacademy.s05.t01.S05T01.chips.data;

import cat.itacademy.s05.t01.S05T01.security.data.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@Table(name = "chips")
public class Chips {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column
    @Positive
    private Long amount;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
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


