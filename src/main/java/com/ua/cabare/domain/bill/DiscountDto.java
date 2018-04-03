package com.ua.cabare.domain.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.domain.Gender;
import com.ua.cabare.models.Discount;

import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DiscountDto {

  @JsonProperty("id")
  private Long id;

  @NotBlank(message = "card number should be specified")
  @JsonProperty("card_number")
  private String cardNumber;

  @NotBlank(message = "first name should be specified")
  @JsonProperty("first_name")
  private String firstName;

  @NotBlank(message = "last name should be specified")
  @JsonProperty("last_name")
  private String lastName;

  @NotNull(message = "gender should be specified")
  @JsonProperty("gender")
  private Gender gender;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @JsonProperty("birthday")
  private LocalDate birthday;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @JsonProperty("emitted")
  private LocalDate emitted;

  @JsonProperty("total_paid")
  private String totalPaid;

  @Min(value = 0, message = "min discount: 0")
  @Max(value = 100, message = "max discount: 100")
  @NotNull(message = "discount size should be specified")
  @JsonProperty("discount_size")
  private Integer size;

  @JsonProperty("activated")
  public Boolean activated;

  public DiscountDto() {
  }

  public DiscountDto(Discount discount) {
    this.id = discount.getId();
    this.cardNumber = discount.getCardNumber();
    this.firstName = discount.getFirstName();
    this.lastName = discount.getLastName();
    this.gender = discount.getGender();
    this.birthday = discount.getBirthday();
    this.emitted = discount.getEmitted();
    this.totalPaid = discount.getTotalPaid().getValue();
    this.size = discount.getSize();
    this.activated = discount.isActivated();
  }

  public Discount build() {
    Discount discount = new Discount();
    discount.setCardNumber(this.cardNumber);
    discount.setFirstName(this.firstName);
    discount.setLastName(this.lastName);
    discount.setGender(this.gender);
    discount.setBirthday(this.birthday);
    discount.setSize(this.size);
    return discount;
  }
}
