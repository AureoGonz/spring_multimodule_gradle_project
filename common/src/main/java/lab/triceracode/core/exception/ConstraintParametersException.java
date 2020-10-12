package lab.triceracode.core.exception;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ConstraintParametersException extends Exception {

    private List<String> messages = new ArrayList<>();

    public void add(String message){
        messages.add(message);
    }

    public boolean canThrows(){
        return messages.size() > 0;
    }
}
