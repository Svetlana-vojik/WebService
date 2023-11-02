package by.teachmeskills.webservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @NotNull
    @NotBlank(message = "email не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты.")
    @Column()
    private String email;

    @NotNull
    @NotNull(message = "пароль не должен быть пустым")
    @Size(min = 4, max = 10, message = "длина пароля должна быть от 4 до 10 символов")
    @Column()
    private String password;

    @NotNull
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректное имя.")
    @Column()
    private String name;

    @NotNull
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректная фамилия.")
    @Column()
    private String surname;

    @NotNull(message = "поле не должно быть пустым")
    @Past(message = "Некорректная дата.")
    private LocalDate birthday;

    @Column()
    private int balance;

    @NotNull(message = "поле не должно быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я0-9\\d]+", message = "Некорректный адрес")
    @Column()
    private String address;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> order;
}