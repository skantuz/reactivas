package co.com.bancolombia.reactivas.model;

import co.com.bancolombia.reactivas.model.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("practice.test")
public class Test {

    @Id
    private UUID idTest;
    private String infoUno;
    private String infoDos;

    public Test(TestDto dto){
        this.infoUno = dto.getInfoUno();
        this.infoDos = dto.getInfoDos();
    }
}
