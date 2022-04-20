package exchange.calcul.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter @Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Remittance {
    /**
     * 송금 영수 엔티티
     * 숭금 submit 후에 insert 되는 엔티티
     */
    @Id @GeneratedValue
    @Column(name = "remittance_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_rate_id")
    private CurrencyRate currencyRate;

    private Long remittancePrice;
    private float receptionPrice;

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime requestTime;

    //== 연관관계 메서드 ==//
     public void setCurrencyRate(CurrencyRate currencyRate){
         this.currencyRate = currencyRate;
         currencyRate.setRemittance(this);
     }

     //== 비즈니스 로직==//
     public float getReceptionPrice(){
         return this.currencyRate.getCurrencyRate()*this.remittancePrice;
     }

     public void setRemittancePrice(Long remittancePrice){
         if(remittancePrice <= 0){
             throw new IllegalArgumentException("need more remittancePrice");
         }
         this.remittancePrice =  remittancePrice;
     }




    
}
