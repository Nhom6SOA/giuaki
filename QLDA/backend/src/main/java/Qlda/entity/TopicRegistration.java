package Qlda.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "topic_id"}))
public class TopicRegistration {
    public enum Status { PENDING, APPROVED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private LocalDate registeredAt;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private String note;

    public TopicRegistration() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Topic getTopic() { return topic; }
    public void setTopic(Topic topic) { this.topic = topic; }

    public LocalDate getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}