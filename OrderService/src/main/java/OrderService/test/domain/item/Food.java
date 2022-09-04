package OrderService.test.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
@Getter @Setter
public class Food extends Item {

    private String FoodName;
    private String FoodCode;
}
