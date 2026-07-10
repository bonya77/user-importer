package ru.shift.userimporter.core.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я. ']+$", message = "incorrect first name")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я. ']+$", message = "incorrect last name")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zа-яА-Я. ']+$", message = "incorrect middle name")
    @Column(name = "middle_name")
    private String middleName;

    @NotBlank
    @Email(message = "incorrect email")
    @Column(nullable = false, unique = true)
    @Size(max = 100, message = "email can't be longer then 100 symbols")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "incorrect phone number")
    @Column(nullable = false, length = 11)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

}