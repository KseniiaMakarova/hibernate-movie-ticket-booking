package com.booking.tickets.entity.shoppingcart.model;

import com.booking.tickets.entity.ticket.model.Ticket;
import com.booking.tickets.entity.user.model.User;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    private Long id;
    @OneToMany
    @JoinTable(
            name = "shopping_cart_ticket",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<Ticket> tickets;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart {"
                + "id=" + id
                + ", tickets=" + tickets
                + ", user=" + user
                + '}';
    }
}