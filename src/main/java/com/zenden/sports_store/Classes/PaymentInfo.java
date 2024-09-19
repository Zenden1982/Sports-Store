package com.zenden.sports_store.Classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@ToString(exclude = { "order" })
public class PaymentInfo {

    @Id
    private String id;

    private String status;

    private String url;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        return "PaymentInfo{id='" + id + "', status='" + status + "'}";
    }
}
