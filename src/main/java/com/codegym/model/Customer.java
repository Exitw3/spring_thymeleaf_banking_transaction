package com.codegym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name", nullable = false)
    @NotEmpty(message = "Họ tên không được để trống")
//    @Size(min = 5, max = 100, message = "Họ tên có độ dài nằm trong khoảng 5-100 ký tự")
    private String fullName;

    @NotEmpty(message = "Email không được để trống")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng (VD: codegym@gmail.com)")
    private String email;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Vui lòng nhập đúng định dạng số điện thoại")
    private String phone;

    private String address;

    @Column(precision = 12, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;

    @OneToMany
    private List<Deposit> deposits;

    @OneToMany
    private List<Withdraw> withdraws;

    @OneToMany
    private List<Transfer> transfers;

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        String fullName = customer.getFullName();

        if (fullName.length() < 5){
            errors.rejectValue("fullName", "fullName.length.min");
        }

        if (fullName.length() > 20){
            errors.rejectValue("fullName", "fullName.length.max");
        }
    }
}
