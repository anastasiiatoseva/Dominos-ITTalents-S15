package ittalents.dominos.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import ittalents.dominos.model.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {

    private int id;
    private BigDecimal price;
    private String addressName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderingTime;
    private OrderStatus orderStatus;
    List<ItemInCartInfoDTO> items;

}

