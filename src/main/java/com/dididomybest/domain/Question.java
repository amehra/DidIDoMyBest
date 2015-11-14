package com.dididomybest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "date_time_created", nullable = false)
    private ZonedDateTime dateTimeCreated;

    @NotNull
    @Min(value = 1)
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "date_time_deleted", nullable = false)
    private ZonedDateTime dateTimeDeleted;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ZonedDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(ZonedDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ZonedDateTime getDateTimeDeleted() {
        return dateTimeDeleted;
    }

    public void setDateTimeDeleted(ZonedDateTime dateTimeDeleted) {
        this.dateTimeDeleted = dateTimeDeleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;

        if ( ! Objects.equals(id, question.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + id +
            ", question='" + question + "'" +
            ", dateTimeCreated='" + dateTimeCreated + "'" +
            ", orderNumber='" + orderNumber + "'" +
            ", dateTimeDeleted='" + dateTimeDeleted + "'" +
            '}';
    }
}
