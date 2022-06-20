package carwash.dibo.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardValidator {

    private int tenCoins;
    private int diboCoins;
    private int cashOnBox;
    private int cashBillAcceptor;
    private String errorMessage;

    public DashboardValidator(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getFieldsValidate(String tenCoins, String diboCoins, String cashOnBox){

        if (tenCoins == null || tenCoins.isEmpty()) {
            return "Не указана сумма десяток";
        }
        if (diboCoins == null || diboCoins.isEmpty()) {
            return "Не указано количество жетонов";
        }
        if (cashOnBox == null || cashOnBox.isEmpty()) {
            return "Не указано количество наличных";
        }
        return "";
    }

    public DashboardValidator fieldsTypeValidate(String tenCoins, String diboCoins, String cashOnBox, String cashBillAcceptor){
        int tenCoinsWd;
        int diboCoinsWd;
        int cashOnBoxWd;
        int cashBillAcceptorWd;

        try {
            tenCoinsWd = Integer.parseInt(tenCoins);
            diboCoinsWd = Integer.parseInt(diboCoins);
            cashOnBoxWd = Integer.parseInt(cashOnBox);
            if (cashBillAcceptor == null || cashBillAcceptor.isEmpty()){
                cashBillAcceptorWd = 0;
            }
            else cashBillAcceptorWd = Integer.parseInt(cashBillAcceptor);
        }
        catch (Exception e){
            return new DashboardValidator("Введены некорректные данные");
        }

        return new DashboardValidator(tenCoinsWd, diboCoinsWd, cashOnBoxWd, cashBillAcceptorWd, "");
    }
}
