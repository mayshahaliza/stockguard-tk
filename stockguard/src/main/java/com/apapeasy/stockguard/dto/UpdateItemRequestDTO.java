package com.apapeasy.stockguard.dto;
import java.util.Date;

//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.PositiveOrZero;
//import org.springframework.format.annotation.DateTimeFormat;
import com.apapeasy.stockguard.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateItemRequestDTO extends CreateItemRequestDTO {
    @NotNull
    private Integer itemId;
}

